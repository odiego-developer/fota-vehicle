package com.man.fotavehicle.domain;



import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

public class VehicleFeature implements Serializable {

    private final String vin;

    private final Set<String> softwareCodes;

    private final Set<String> hardwareCodes;

    public VehicleFeature(){
        this(null,null,null);
    }

    public VehicleFeature(String vin, Set<String> softwareCodes, Set<String> hardwareCodes) {
        this.vin = vin;
        this.softwareCodes = softwareCodes;
        this.hardwareCodes = hardwareCodes;
    }

    public String getVin() {
        return vin;
    }

    public Set<String> getSoftwareCodes() {
        return softwareCodes;
    }

    public Set<String> getHardwareCodes() {
        return hardwareCodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleFeature vehicleFeature = (VehicleFeature) o;
        return vin.equals(vehicleFeature.vin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vin);
    }
}
