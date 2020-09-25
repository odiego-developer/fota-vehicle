package com.man.fotavehicle.service.internal;

import com.man.fotavehicle.domain.FotaVehicleHardware;
import com.man.fotavehicle.domain.FotaVehicleSoftware;
import com.man.fotavehicle.domain.VehicleFeature;
import com.man.fotavehicle.persistence.FotaVehicleHardwareRepository;
import com.man.fotavehicle.persistence.FotaVehicleSoftwareRepository;
import com.man.fotavehicle.service.FotaVehicleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class DefaultFotaVehicleService implements FotaVehicleService {

    private final FotaVehicleHardwareRepository fotaVehicleHardwareRepository;

    private final FotaVehicleSoftwareRepository fotaVehicleSoftwareRepository;

    public DefaultFotaVehicleService(FotaVehicleHardwareRepository fotaVehicleHardwareRepository,
                                     FotaVehicleSoftwareRepository fotaVehicleSoftwareRepository) {
        this.fotaVehicleHardwareRepository = fotaVehicleHardwareRepository;
        this.fotaVehicleSoftwareRepository = fotaVehicleSoftwareRepository;
    }
    @Transactional
    @Override
    public Optional<VehicleFeature> getInstallable(String vin) {


        if (notExists(vin)){
            return Optional.empty();
        }

        return Optional.of(
                new VehicleFeature(
                        vin,
                        getInstallableSofwareCodesByVin(vin),
                        getInstallableHardwareCodesByVin(vin)
                )
        );

    }

    @Override
    public Optional<VehicleFeature> getIncompatible(String vin) {


        if (notExists(vin)){
            return Optional.empty();
        }

        return Optional.of(
                new VehicleFeature(
                        vin,
                        getIncompatibleSofwareCodesByVin(vin),
                        getIncompatibleHardwareCodes(vin)
                )
        );
    }

    private boolean notExists(String vin) {

        int hardwareCount = this.fotaVehicleHardwareRepository.countByVin(vin);

        int softwareCount = this.fotaVehicleSoftwareRepository.countByVin(vin);

        return softwareCount < 1 && hardwareCount < 1;
    }


    private Set<String> getInstallableSofwareCodesByVin(String vin) {
        Set<String> softwareCodes = new HashSet<>();
        List<FotaVehicleSoftware> fotaVehicleSoftwares = this.fotaVehicleSoftwareRepository.findByVin(vin);
        if(!fotaVehicleSoftwares.isEmpty()){
            fotaVehicleSoftwares
                    .stream()
                    .map(FotaVehicleSoftware::getSoftwareCode)
                    .forEach(softwareCodes::add);
        }
        return softwareCodes;
    }

    private Set<String> getIncompatibleSofwareCodesByVin(String vin) {
        Set<String> softwareCodes = new HashSet<>();
        List<FotaVehicleSoftware> fotaVehicleSoftwares = this.fotaVehicleSoftwareRepository.findDifferentFrom(vin);
        if(!fotaVehicleSoftwares.isEmpty()){
            fotaVehicleSoftwares
                    .stream()
                    .map(FotaVehicleSoftware::getSoftwareCode)
                    .forEach(softwareCodes::add);
        }
        return softwareCodes;
    }

    private Set<String> getInstallableHardwareCodesByVin(String vin) {
        Set<String> hardwareCodes = new HashSet<>();

        List<FotaVehicleHardware> fotaVehicleHardwares = this.fotaVehicleHardwareRepository.findByVin(vin);

        if(!fotaVehicleHardwares.isEmpty()){
            fotaVehicleHardwares
                    .stream()
                    .map(FotaVehicleHardware::getHardwareCode)
                    .forEach(hardwareCodes::add);
        }
        return hardwareCodes;
    }

    private Set<String> getIncompatibleHardwareCodes(String vin) {
        Set<String> hardwareCodes = new HashSet<>();

        List<FotaVehicleHardware> fotaVehicleHardwares = this.fotaVehicleHardwareRepository.findDifferentFrom(vin);

        if(!fotaVehicleHardwares.isEmpty()){
            fotaVehicleHardwares
                    .stream()
                    .map(FotaVehicleHardware::getHardwareCode)
                    .forEach(hardwareCodes::add);
        }
        return hardwareCodes;
    }
}
