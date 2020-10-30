package com.iurirates.foldermonitor.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@Builder
public class FileImportDTO {

    private Collection<ClientDTO> clients ;
    private Collection<SaleDTO> sales ;
    private Collection<SaleItemDTO> salesItems ;
    private Collection<SalesmanDTO> salesman ;

}
