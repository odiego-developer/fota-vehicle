package com.man.fotavehicle.domain;

import com.man.fotavehicle.domain.id.FotaVehicleSoftwareId;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.Objects;

@Entity
@IdClass(FotaVehicleSoftwareId.class)
public class FotaVehicleSoftware {

    @Id
    private final String vin;

    @Id
    private final String softwareCode;

    public FotaVehicleSoftware() {
       this(null,null);
    }

    public FotaVehicleSoftware(String vin, String softwareCode) {
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
        FotaVehicleSoftware that = (FotaVehicleSoftware) o;
        return Objects.equals(vin, that.vin) &&
                Objects.equals(softwareCode, that.softwareCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vin, softwareCode);
    }
}
