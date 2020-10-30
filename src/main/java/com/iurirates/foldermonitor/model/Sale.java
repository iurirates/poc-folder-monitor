package com.iurirates.foldermonitor.model;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Sale {

    @Id
    private Long saleId;
    private String salesmanName;

    @OneToMany(mappedBy="saleId", fetch = FetchType.EAGER)
    private Collection<SaleItem> saleItems;

}
