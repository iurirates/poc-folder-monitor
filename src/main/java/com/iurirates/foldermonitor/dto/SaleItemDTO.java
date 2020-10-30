package com.iurirates.foldermonitor.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class SaleItemDTO {
    private Long saleItemId;
    private Long saleId;
    private Long quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;

}
