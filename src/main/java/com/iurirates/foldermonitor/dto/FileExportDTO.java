package com.iurirates.foldermonitor.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FileExportDTO {
    private Integer numberOfClients;
    private Integer numberOfSellers;
    private Long idOfBestSelling;
    private String worstSeller;
}
