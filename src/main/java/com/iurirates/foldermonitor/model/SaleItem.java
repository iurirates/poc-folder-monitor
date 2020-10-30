package com.iurirates.foldermonitor.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SaleItem {
    @Id
    @GeneratedValue
    private Long id;
    private Long saleId;
    private Long saleItemId;
    private Long quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "saleId" , insertable = false, updatable = false)
    private Sale sale;
}
