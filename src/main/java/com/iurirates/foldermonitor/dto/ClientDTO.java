package com.iurirates.foldermonitor.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ClientDTO {
    private Long cnpj;
    private String name;
    private String businessArea;
}
