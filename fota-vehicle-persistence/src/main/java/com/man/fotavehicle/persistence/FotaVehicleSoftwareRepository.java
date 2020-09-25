package com.man.fotavehicle.persistence;

import com.man.fotavehicle.domain.FotaVehicleSoftware;
import com.man.fotavehicle.domain.id.FotaVehicleSoftwareId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FotaVehicleSoftwareRepository extends JpaRepository<FotaVehicleSoftware, FotaVehicleSoftwareId> {

    List<FotaVehicleSoftware> findByVinAndSoftwareCode(String vin, String softwareCode);


    List<FotaVehicleSoftware> findByVin(String vin);

    @Query("SELECT s FROM FotaVehicleSoftware s where s.vin <> ?1")
    List<FotaVehicleSoftware> findDifferentFrom(String vin);

    @Query("SELECT count(1) FROM FotaVehicleSoftware s where s.vin = ?1")
    int countByVin(String vin);
}
