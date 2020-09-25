package com.man.fotavehicle.io.internal;

import com.man.fotavehicle.domain.FotaVehicleHardware;
import com.man.fotavehicle.io.FileProcessor;
import com.man.fotavehicle.persistence.FotaVehicleHardwareRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Component("hardwareCsvProcessor")
public class HardwareCsvProcessor implements FileProcessor {

    private final Logger log = LoggerFactory.getLogger(HardwareCsvProcessor.class);

    private final CsvFolder folder;
    private final FotaVehicleHardwareRepository fotaVehicleHardwareRepository;

    public HardwareCsvProcessor(CsvFolder folder,
                                FotaVehicleHardwareRepository fotaVehicleHardwareRepository) {
        this.folder = folder;
        this.fotaVehicleHardwareRepository = fotaVehicleHardwareRepository;
    }

    @Override
    public void process(Path path) throws IOException {
        log.info("method:process - Processing file {} ...", path.toFile().getName());
        Stream<String> lines = Files.lines(Paths.get(folder.getPath() + path.toFile().getName()));
        lines.forEach( line -> {
            String[] codes = line.split(",");
            String vin = codes[0];
            String hardwareCode = codes[1];
            if(!fotaVehicleHardwareRepository.findByVinAndHardwareCode(vin,hardwareCode).isEmpty()){
                log.warn(String.format("Vin and hardware code already processed { %s , %s }.",vin, hardwareCode));
            } else {
                this.fotaVehicleHardwareRepository.save(new FotaVehicleHardware(vin,hardwareCode));
            }
        });
        log.info("method:process - File processed {}.", path.toFile().getName());
    }
}
