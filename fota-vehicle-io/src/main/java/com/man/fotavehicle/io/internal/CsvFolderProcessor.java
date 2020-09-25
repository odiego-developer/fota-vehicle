package com.man.fotavehicle.io.internal;

import com.man.fotavehicle.io.FileProcessor;
import com.man.fotavehicle.io.Folder;
import com.man.fotavehicle.io.FolderProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component("csvFolderProcessor")
public class CsvFolderProcessor implements FolderProcessor {

    private final Logger log = LoggerFactory.getLogger(CsvFolderProcessor.class);
    public static final String DATE_FORMAT = LocalDateTime
            .now()
            .format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-ms")
            );
    private final FileProcessor hardwareCsvProcessor;
    private final FileProcessor softwareCsvProcessor;
    private final Folder csvFolder;

    public CsvFolderProcessor( @Qualifier("csvFolder") Folder csvFolder,
                               @Qualifier("hardwareCsvProcessor") FileProcessor hardwareCsvProcessor,
                               @Qualifier("softwareCsvProcessor") FileProcessor softwareCsvProcessor) {
        this.hardwareCsvProcessor = hardwareCsvProcessor;
        this.softwareCsvProcessor = softwareCsvProcessor;
        this.csvFolder = csvFolder;
    }

    @Override
    public void process() {
        try{
            Files.walk(Paths.get(csvFolder.getPath()))
                    .filter(Files::isRegularFile)
                    .filter(this::startsWithHardOrSoft)
                    .forEach(this::processFile);

        } catch (IOException e){
            log.error("method=settleDatabaseFromFiles - Error occurred during the file extraction. ", e);
        }
    }

    private void processFile(Path path) {
        try {
            if(path.toFile().getName().startsWith(("hard_"))){
                hardwareCsvProcessor.process(path);
            } else {
                softwareCsvProcessor.process(path);
            }

            log.info("method=processFile - Moving file {}. ",path);
            moveFile(path);
            log.info("method=processFile - File moved  {}. ",path);
        } catch (IOException e) {
            log.error("method=processFile - Occurred an error processing the file content. ", e);
        }
    }

    private void moveFile(Path path) {
        File sourceFile = new File(csvFolder.getPath() + path.toFile().getName());
        File targetDirectory = new File(csvFolder.getPath() +  "processed/");
        if(!targetDirectory.exists()){
            targetDirectory.mkdir();
        }
        File targetFile = new File(
                csvFolder.getPath() +
                        "processed/" +
                        DATE_FORMAT + "_" + path.toFile().getName());

        sourceFile.renameTo(targetFile);
        sourceFile.delete();
        path.toFile().delete();
    }
    private boolean startsWithHardOrSoft(Path path) {
        return  path.toFile().getName().startsWith("hard_") || path.toFile().getName().startsWith("soft_");
    }

}
