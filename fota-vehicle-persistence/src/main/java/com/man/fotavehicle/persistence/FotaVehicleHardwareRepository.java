package com.man.fotavehicle.persistence;


import com.man.fotavehicle.domain.FotaVehicleHardware;
import com.man.fotavehicle.domain.id.FotaVehicleHardwareId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FotaVehicleHardwareRepository extends JpaRepository<FotaVehicleHardware, FotaVehicleHardwareId> {

    @Query("SELECT h FROM FotaVehicleHardware h where h.vin = ?1 and h.hardwareCode = ?2")
    List<FotaVehicleHardware> findByVinAndHardwareCode(String vin, String hardwareCode);

    List<FotaVehicleHardware> findByVin(String vin);

    @Query("SELECT h FROM FotaVehicleHardware h where h.vin <> ?1")
    List<FotaVehicleHardware> findDifferentFrom(String vin);


    @Query("SELECT count(1) FROM FotaVehicleHardware h where h.vin = ?1")
    int countByVin(String vin);
}
