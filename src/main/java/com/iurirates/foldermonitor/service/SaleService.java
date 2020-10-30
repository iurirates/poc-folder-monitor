package com.iurirates.foldermonitor.service;

import com.iurirates.foldermonitor.dto.FileImportDTO;
import com.iurirates.foldermonitor.dto.SaleDTO;
import com.iurirates.foldermonitor.model.Sale;
import com.iurirates.foldermonitor.model.SaleItem;
import com.iurirates.foldermonitor.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    public void saveSale(FileImportDTO fileImportDTO){
        fileImportDTO.getSales().forEach( sale -> saleRepository.save(convertSaleDTOToSale(sale)));
    }

    public void deleteAllSales(){
        saleRepository.deleteAll();
    }

    public Long getIdOfBestSelling(){
        TreeMap<BigDecimal, Long> sorted = new TreeMap<>();
        Collection<Sale> sales = (Collection<Sale>) saleRepository.findAll();

        for (Sale sale : sales) {
            sorted.put(getTotalSale(sale.getSaleItems()), sale.getSaleId());
        }

        return sorted.lastEntry().getValue();
    }

    private Sale convertSaleDTOToSale(SaleDTO saleDTO){
        return Sale
                .builder()
                .saleId(saleDTO.getSaleId())
                .salesmanName(saleDTO.getSalesmanName())
                .build();
    }

    private BigDecimal getTotalSale(Collection<SaleItem> saleItems){
        return saleItems.stream()
                .map(SaleItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
