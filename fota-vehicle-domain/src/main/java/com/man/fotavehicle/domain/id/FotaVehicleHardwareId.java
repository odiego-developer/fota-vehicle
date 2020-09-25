package com.man.fotavehicle.domain.id;

import java.io.Serializable;
import java.util.Objects;

public class FotaVehicleHardwareId implements Serializable {

    private final String vin;

    private final String hardwareCode;

    public FotaVehicleHardwareId() {
       this(null,null);
    }

    public FotaVehicleHardwareId(String vin, String hardwareCode) {
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
        FotaVehicleHardwareId that = (FotaVehicleHardwareId) o;
        return Objects.equals(vin, that.vin) &&
                Objects.equals(hardwareCode, that.hardwareCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vin, hardwareCode);
    }
}
