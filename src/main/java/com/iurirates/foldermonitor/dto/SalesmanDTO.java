package com.iurirates.foldermonitor.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class SalesmanDTO {
    private Long cpf;
    private String name;
    private BigDecimal salary;
}
