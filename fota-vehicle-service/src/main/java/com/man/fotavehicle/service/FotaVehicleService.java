package com.man.fotavehicle.service;
import com.man.fotavehicle.domain.VehicleFeature;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface FotaVehicleService {

    @Transactional
    Optional<VehicleFeature> getInstallable(String id);

    Optional<VehicleFeature> getIncompatible(String id);
}
