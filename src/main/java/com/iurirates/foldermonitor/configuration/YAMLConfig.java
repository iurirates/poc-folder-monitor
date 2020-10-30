package com.iurirates.foldermonitor.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class YAMLConfig {

    private String homeDir;
    private String dataIn;
    private String dataOut;
    private String dataRead;
    private String dataDiscard;

}