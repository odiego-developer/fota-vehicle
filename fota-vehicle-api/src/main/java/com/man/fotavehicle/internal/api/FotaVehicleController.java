package com.man.fotavehicle.internal.api;

import com.man.fotavehicle.service.FotaVehicleService;
import com.man.fotavehicle.domain.VehicleFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(
        path = "/fota/vehicles"
)
public class FotaVehicleController {

    private final FotaVehicleService fotaVehicleService;

    @Autowired
    public FotaVehicleController(FotaVehicleService fotaVehicleService) {
        this.fotaVehicleService = fotaVehicleService;
    }

    @GetMapping(
            path = "/{vin}/installable"
    )
    public ResponseEntity<VehicleFeature> getInstallable(@PathVariable("vin") String id){

        Optional<VehicleFeature> feature = this.fotaVehicleService
                .getInstallable(id);

        if(feature.isPresent()){
            return ResponseEntity
                    .ok()
                    .body(feature.get());
        }

        return ResponseEntity
                .notFound()
                .build();
    }

    @GetMapping(
            path = "/{vin}/incompatible"
    )
    public ResponseEntity<VehicleFeature> getIncompatible(@PathVariable("vin") String id){

        Optional<VehicleFeature> feature = this.fotaVehicleService
                .getIncompatible(id);

        if(feature.isPresent()){
            return ResponseEntity.ok().body(feature.get());
        }

        return ResponseEntity
                .notFound()
                .build();
    }
}
