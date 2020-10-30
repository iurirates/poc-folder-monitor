package com.iurirates.foldermonitor.component;

import com.iurirates.foldermonitor.configuration.YAMLConfig;
import com.iurirates.foldermonitor.dto.FileExportDTO;
import com.iurirates.foldermonitor.dto.FileImportDTO;
import com.iurirates.foldermonitor.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

@Slf4j
@Component
public class FolderWatcher {

    @Autowired
    private FileService fileService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private SaleItemService saleItemService;

    @Autowired
    private SaleService saleService;

    @Autowired
    private SalesmanService salesmanService;

    @Autowired
    private YAMLConfig myConfig;

    private Boolean isProcessing = false;

    @Async
    @PostConstruct
    public void StartFolderWatcher() {

        log.info("The application is running...");
        clearTables();

        while (true) {
            if (fileService.countFilesFromDataInDirectory() > 0 && !isProcessing) {
                log.info("Found files to import, starting import...");
                isProcessing = true;
                clearTables();
                processAllFilesFromDirectory();
            } else if (fileService.countFilesFromDataInDirectory() == 0) {
                isProcessing = false;
            }
        }

    }

    private void processAllFilesFromDirectory() {
        Boolean haveValidFiles = false;

        for(File file : fileService.getAllFilesFromDirectory()) {

            if (file.isDirectory()){
                continue;
            }

            if (fileService.validateFile(file)){
                importFile(file);
                haveValidFiles = true;
                fileService.moveFileToReadDirectory(file);
            } else {
                fileService.moveFileToDiscardDirectory(file);
            }
        }

        if (fileService.countFilesFromDataInDirectory() == 0 && haveValidFiles){
            log.info("processing files...");
            generateResultOfImportFiles();
        } else if(fileService.countFilesFromDataInDirectory() > 0) {
            processAllFilesFromDirectory();
        }

    }

    private void importFile(File file) {
        FileImportDTO fileImportDTO = fileService.convertFileToDTO(file);
        salesmanService.saveSalesman(fileImportDTO);
        clientService.saveClient(fileImportDTO);
        saleService.saveSale(fileImportDTO);
        saleItemService.saveSaleItem(fileImportDTO);
    }

    private void clearTables(){
        saleItemService.deleteAllSalesItems();
        saleService.deleteAllSales();
        salesmanService.deleteAllSalesman();
        clientService.deleteAllClients();
    }

    private void generateResultOfImportFiles() {
        fileService.convertDtoToFileInOutDirectory(FileExportDTO
                .builder()
                .idOfBestSelling(saleService.getIdOfBestSelling())
                .numberOfClients(clientService.getCountTotalClients())
                .numberOfSellers(salesmanService.getCountTotalSellers())
                .worstSeller(salesmanService.getWorstSeller())
                .build());
    }
}
