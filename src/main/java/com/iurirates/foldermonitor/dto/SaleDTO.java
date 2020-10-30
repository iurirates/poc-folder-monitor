package com.iurirates.foldermonitor.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class SaleDTO {
    private Long saleId;
    private String salesmanName;
    private BigDecimal totalSale;
}
