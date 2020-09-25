package com.man.fotavehicle;

import com.man.fotavehicle.domain.FotaVehicleHardware;
import com.man.fotavehicle.domain.FotaVehicleSoftware;
import com.man.fotavehicle.domain.VehicleFeature;
import com.man.fotavehicle.persistence.FotaVehicleHardwareRepository;
import com.man.fotavehicle.persistence.FotaVehicleSoftwareRepository;
import com.man.fotavehicle.service.FotaVehicleService;
import com.man.fotavehicle.service.internal.DefaultFotaVehicleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FotaVehicleServiceTests {

    @Autowired
    private FotaVehicleHardwareRepository fotaVehicleHardwareRepository;

    @Autowired
    private FotaVehicleSoftwareRepository fotaVehicleSoftwareRepository;

    private FotaVehicleService fotaVehicleService;

    private String vin;

    @Before
    public void startUp(){
        vin = "3C3CFFER4ET929645";
        fotaVehicleHardwareRepository.saveAll(createHardwareCodes());
        fotaVehicleSoftwareRepository.saveAll(createSoftwareCodes());
        fotaVehicleService = new DefaultFotaVehicleService(fotaVehicleHardwareRepository,fotaVehicleSoftwareRepository);
    }


    @Test
    public void test_getInstallablePositive() {

        Optional<VehicleFeature> feature = fotaVehicleService.getInstallable(vin);
        assertTrue(feature.isPresent());
        assertFalse(feature.get().getSoftwareCodes().isEmpty());
        assertFalse(feature.get().getHardwareCodes().isEmpty());
        assertTrue(feature.get().getSoftwareCodes().containsAll(
            Arrays.asList("6VO6Uq","ZCLFOe","I25pUg"))
        );
        assertTrue(feature.get().getHardwareCodes().containsAll(
                Arrays.asList("FhFXVE","FVlp0N","jyP5PK"))
        );
    }


    private List<FotaVehicleSoftware> createSoftwareCodes() {
        return Arrays.asList(
                new FotaVehicleSoftware(vin,"6VO6Uq"),
                new FotaVehicleSoftware(vin,"ZCLFOe"),
                new FotaVehicleSoftware(vin,"I25pUg")
        );
    }

    private List<FotaVehicleHardware> createHardwareCodes() {
        return Arrays.asList(
                new FotaVehicleHardware(vin,"FhFXVE"),
                new FotaVehicleHardware(vin,"FVlp0N"),
                new FotaVehicleHardware(vin,"jyP5PK")
        );
    }


}
