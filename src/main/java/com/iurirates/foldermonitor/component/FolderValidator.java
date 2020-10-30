package com.iurirates.foldermonitor.component;
import com.iurirates.foldermonitor.configuration.YAMLConfig;
import com.iurirates.foldermonitor.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

@Slf4j
@Component
public class FolderValidator {

    @Autowired
    private YAMLConfig myConfig;

    @Autowired
    private FileService fileService;

    private File file;
    private String HOME_DIR;

    @Async
    @PostConstruct
    public void StartFolderValidator() {
        HOME_DIR = myConfig.getHomeDir();

        log.info("Starting the application, validating structure of directory...");

        validateHomeDir();
        validateDataIn();
        validateDataOut();
        validateDataRead();
        validateDataDiscard();
    }

    private void validateHomeDir(){
        file = new File(HOME_DIR);
        if (!file.exists()) {
            log.info("Home Dir not found, creating...");
            file.mkdirs();
        }
        file = null;
    }

    private void validateDataIn(){
        file = new File(HOME_DIR + myConfig.getDataIn());
        if (!file.exists()) {
            log.info("Data In not found, creating...");
            file.mkdirs();
        }
        file = null;
    }

    private void validateDataOut(){
        file = new File(HOME_DIR + myConfig.getDataOut());
        if (!file.exists()) {
            log.info("Data Out not found, creating...");
            file.mkdirs();
        }
        file = null;
    }

    private void validateDataRead(){
        file = new File(HOME_DIR + myConfig.getDataIn() + myConfig.getDataRead());
        if (!file.exists()) {
            log.info("Data Read not found, creating...");
            file.mkdirs();
        }
        file = null;
    }

    private void validateDataDiscard(){
        file = new File(HOME_DIR + myConfig.getDataIn() + myConfig.getDataDiscard());
        if (!file.exists()) {
            log.info("Data Discard not found, creating...");
            file.mkdirs();
        }
        file = null;
    }
}
