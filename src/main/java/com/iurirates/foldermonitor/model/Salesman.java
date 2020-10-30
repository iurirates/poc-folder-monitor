package com.iurirates.foldermonitor.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Salesman {

    @Id
    @GeneratedValue
    private Long id;
    private Long cpf;
    private String name;
    private BigDecimal salary;

}
