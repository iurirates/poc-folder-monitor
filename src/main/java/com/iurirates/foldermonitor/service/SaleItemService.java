package com.iurirates.foldermonitor.service;

import com.iurirates.foldermonitor.dto.FileImportDTO;
import com.iurirates.foldermonitor.dto.SaleItemDTO;
import com.iurirates.foldermonitor.model.SaleItem;
import com.iurirates.foldermonitor.repository.SaleItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleItemService {

    @Autowired
    private SaleItemRepository saleItemRepository;

    public void saveSaleItem(FileImportDTO fileImportDTO){
        fileImportDTO.getSalesItems().forEach( saleItem -> saleItemRepository.save(convertSaleItemDtoToSaleItem(saleItem)));
    }

    public void deleteAllSalesItems(){
        saleItemRepository.deleteAll();
    }

    private SaleItem convertSaleItemDtoToSaleItem(SaleItemDTO saleItemDTO){
        return SaleItem
                .builder()
                .saleId(saleItemDTO.getSaleId())
                .saleItemId(saleItemDTO.getSaleItemId())
                .quantity(saleItemDTO.getQuantity())
                .price(saleItemDTO.getPrice())
                .totalPrice(saleItemDTO.getTotalPrice())
                .build();
    }
}
