package com.man.fotavehicle.domain.id;

import java.io.Serializable;
import java.util.Objects;

public class FotaVehicleSoftwareId implements Serializable {

    private final String vin;

    private final String softwareCode;

    public FotaVehicleSoftwareId() {
       this(null,null);
    }

    public FotaVehicleSoftwareId(String vin, String softwareCode) {
        this.vin = vin;
        this.softwareCode = softwareCode;
    }

    public String getVin() {
        return vin;
    }

    public String getSoftwareCode() {
        return softwareCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FotaVehicleSoftwareId that = (FotaVehicleSoftwareId) o;
        return Objects.equals(vin, that.vin) &&
                Objects.equals(softwareCode, that.softwareCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vin, softwareCode);
    }
}
