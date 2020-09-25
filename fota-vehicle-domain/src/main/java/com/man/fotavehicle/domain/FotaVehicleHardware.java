package com.man.fotavehicle.domain;

import com.man.fotavehicle.domain.id.FotaVehicleHardwareId;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.Objects;

@Entity
@IdClass(FotaVehicleHardwareId.class)
public class FotaVehicleHardware {

    @Id
    private final String vin;

    @Id
    private final String hardwareCode;

    public FotaVehicleHardware() {
       this(null,null);
    }

    public FotaVehicleHardware(String vin, String hardwareCode) {
        this.vin = vin;
        this.hardwareCode = hardwareCode;
    }

    public String getVin() {
        return vin;
    }

    public String getHardwareCode() {
        return hardwareCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FotaVehicleHardware that = (FotaVehicleHardware) o;
        return Objects.equals(vin, that.vin) &&
                Objects.equals(hardwareCode, that.hardwareCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vin, hardwareCode);
    }
}
