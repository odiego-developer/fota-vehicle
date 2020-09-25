package com.man.fotavehicle.service.scheduler;

import com.man.fotavehicle.io.FolderProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FotaVehicleScheduleProcessing {
    private final Logger log = LoggerFactory.getLogger(FotaVehicleScheduleProcessing.class);
    private final FolderProcessor csvFolderProcessor;

    @Autowired
    public FotaVehicleScheduleProcessing(@Qualifier("csvFolderProcessor") FolderProcessor csvFolderProcessor) {
        this.csvFolderProcessor = csvFolderProcessor;
    }

    @Scheduled(fixedRate = 5000)
    public void processCsvFiles() {
        csvFolderProcessor.process();
    }

}
