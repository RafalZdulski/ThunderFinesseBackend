package com.thunderfinesse.thunderback.data.enums;

import lombok.Getter;

public enum VehicleType {
    Aircraft("air"),
    GroundVehicle("ground"),
    Helicopter(""),
    BluewaterVessel(""),
    CoastalVessel("");

    @Getter
    private String abbrev;

    VehicleType(String abbrev) {
        this.abbrev = abbrev;
    }

    public static VehicleType valOf(String s){
        switch (s.toLowerCase()){
            case "air","aviation","aircraft": return Aircraft;
            case "ground","army","groundvehicle","groundvehicles" :  return GroundVehicle;
            case "heli" : return Helicopter;
            case "bluewater" :  return BluewaterVessel;
            case "coastal" : return CoastalVessel;
            default: throw new IllegalArgumentException("no enum constant as com.zdulski.thunderfinesse.data.enums.VehicleType."+s);
        }

    }
}
