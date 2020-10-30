package com.iurirates.foldermonitor.service;

import com.iurirates.foldermonitor.configuration.YAMLConfig;
import com.iurirates.foldermonitor.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service()
public class FileService {

    @Autowired
    private YAMLConfig myConfig;

    public FileImportDTO convertFileToDTO(File file){
        FileImportDTO fileImportDTO = FileImportDTO
                .builder()
                .clients(new ArrayList<ClientDTO>())
                .sales(new ArrayList<SaleDTO>())
                .salesItems(new ArrayList<SaleItemDTO>())
                .salesman(new ArrayList<SalesmanDTO>())
                .build();

        try (Stream<String> stream = Files.lines(Paths.get(file.getAbsolutePath()))) {
            stream.forEach(a -> extractDataFromFile(a, fileImportDTO));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileImportDTO;
    }

    private void extractDataFromFile(String fileLine,FileImportDTO fileImportDTO ){
        List<String> lineConverted = Stream.of(fileLine.split("ç"))
                .map (String::new)
                .collect(Collectors.toList());

        extractSalesmanFromFileLine(lineConverted, fileImportDTO);
        extractClientFromFileLine(lineConverted, fileImportDTO);
        extractSaleFromFileLine(lineConverted, fileImportDTO);
        extractSaleItemFromFileLine(lineConverted, fileImportDTO);
    }

    private void extractSalesmanFromFileLine(List<String> lineConverted, FileImportDTO fileImportDTO){
        if (lineConverted.get(0).equals("001")){
            fileImportDTO.getSalesman()
                    .add(SalesmanDTO
                            .builder()
                            .cpf(Long.parseLong(lineConverted.get(1)))
                            .name(lineConverted.get(2))
                            .salary(new BigDecimal(lineConverted.get(3)))
                            .build());
        }
    }

    private void extractClientFromFileLine(List<String> lineConverted, FileImportDTO fileImportDTO){
        if (lineConverted.get(0).equals("002")){
            fileImportDTO.getClients()
                    .add(ClientDTO
                            .builder()
                            .cnpj(Long.parseLong(lineConverted.get(1)))
                            .name(lineConverted.get(2))
                            .businessArea(lineConverted.get(3))
                            .build());
        }
    }

    private void extractSaleFromFileLine(List<String> lineConverted, FileImportDTO fileImportDTO){
        if (lineConverted.get(0).equals("003")){
            fileImportDTO.getSales()
                    .add(SaleDTO
                            .builder()
                            .saleId(Long.parseLong(lineConverted.get(1)))
                            .salesmanName(lineConverted.get(3))
                            .build());
        }
    }

    private void extractSaleItemFromFileLine(List<String> lineConverted, FileImportDTO fileImportDTO){
        if (lineConverted.get(0).equals("003")){
            Long saleId = Long.parseLong(lineConverted.get(1));
            String lineItems = lineConverted.get(2).replace("[","").replace("]","");

            List<String> items = Stream.of(lineItems.split(","))
                    .map (String::new)
                    .collect(Collectors.toList());

            items.forEach(lineItem -> {
                List<String> item = Stream.of(lineItem.split("-"))
                        .map (String::new)
                        .collect(Collectors.toList());

                BigDecimal totalPrice =  new BigDecimal(item.get(2)).multiply(new BigDecimal(item.get(1)));

                fileImportDTO.getSalesItems()
                        .add(SaleItemDTO
                                .builder()
                                .saleItemId(Long.parseLong(item.get(0)))
                                .saleId(saleId)
                                .quantity(Long.parseLong(item.get(1)))
                                .price(new BigDecimal(item.get(2)))
                                .totalPrice(totalPrice)
                                .build());
            });
        }
    }

    public Boolean validateFile(File file ) {
        return (file.getName().toLowerCase().endsWith(".dat"));
    }

    public File[] getAllFilesFromDirectory(){
        String directory = myConfig.getHomeDir() + "\\" + myConfig.getDataIn();
        File directoryPath = new File(directory);
        File[] filesList = directoryPath.listFiles();

        return filesList;
    }

    public Long countFilesFromDataInDirectory(){
        String directory = myConfig.getHomeDir() + "\\" + myConfig.getDataIn();
        File directoryPath = new File(directory);
        Long countFiles = 0L;

        for(File file : directoryPath.listFiles()) {
            if (!file.isDirectory()){
                countFiles ++;
                continue;
            }
        }

        return countFiles;
    }

    public void moveFileToDiscardDirectory(File file) {
        try {
            String directory = myConfig.getHomeDir() + "\\" + myConfig.getDataIn();
            Files.move(Paths.get(file.getAbsolutePath()), Paths.get(directory + '\\' + myConfig.getDataDiscard() + "\\" + file.getName()),  StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.info("Error on moving file " + file.getName());
        }
    }

    public void moveFileToReadDirectory(File file) {
        try {
            String directory = myConfig.getHomeDir() + "\\" + myConfig.getDataIn();
            Files.move(Paths.get(file.getAbsolutePath()), Paths.get(directory + '\\' + myConfig.getDataRead() + "\\" + file.getName()),  StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.info("Error on moving file " + file.getName());
        }
    }

    public void convertDtoToFileInOutDirectory(FileExportDTO fileExportDto) {
        String fileName = myConfig.getHomeDir() + myConfig.getDataOut() + "\\" +
                new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + ".done.dat";
        log.info("Import of files finished, generated a file " + fileName + " with the result.");

        try {
            FileWriter fileWriter = new FileWriter(fileName);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println("Quantidade de clientes: " + fileExportDto.getNumberOfClients());
            printWriter.println("Quantidade de vendedores: " + fileExportDto.getNumberOfSellers());
            printWriter.println("Id da venda mais cara: " + fileExportDto.getIdOfBestSelling());
            printWriter.println("Pior vendedor: " + fileExportDto.getWorstSeller());
            printWriter.close();
        } catch (IOException e) {
            log.info("Error on creating file " + fileName);
        }
    }

}
