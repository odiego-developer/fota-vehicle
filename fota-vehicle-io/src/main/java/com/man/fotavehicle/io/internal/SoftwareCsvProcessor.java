package com.man.fotavehicle.io.internal;

import com.man.fotavehicle.domain.FotaVehicleSoftware;
import com.man.fotavehicle.io.FileProcessor;
import com.man.fotavehicle.persistence.FotaVehicleSoftwareRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Component("softwareCsvProcessor")
public class SoftwareCsvProcessor implements FileProcessor {

    private final Logger log = LoggerFactory.getLogger(SoftwareCsvProcessor.class);

    private final CsvFolder folder;
    private final FotaVehicleSoftwareRepository fotaVehicleSoftwareRepository;

    public SoftwareCsvProcessor(CsvFolder folder,
                                FotaVehicleSoftwareRepository fotaVehicleSoftwareRepository) {
        this.folder = folder;
        this.fotaVehicleSoftwareRepository = fotaVehicleSoftwareRepository;
    }

    @Override
    public void process(Path path) throws IOException {
        log.info("method:process - Processing file {} ...", path.toFile().getName());
        Stream<String> lines = Files.lines(Paths.get(folder.getPath() + path.toFile().getName()));
        lines.forEach( line -> {
            String[] codes = line.split(",");
            String vin = codes[0];
            String softwareCode = codes[1];
            if(!fotaVehicleSoftwareRepository.findByVinAndSoftwareCode(vin,softwareCode).isEmpty()){
                log.warn(String.format("Vin and software code already processed { %s , %s }.",vin, softwareCode));
            } else {
                this.fotaVehicleSoftwareRepository.save(new FotaVehicleSoftware(vin,softwareCode));
            }
        });
        log.info("method:process - File processed {}.", path.toFile().getName());
    }
}
