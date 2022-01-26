package com.thunderfinesse.thunderback.data.enums;

public enum GroundVehicleClass {
    Light_tank("LT"),
    Medium_tank("MT"),
    Heavy_tank("HT"),
    Tank_destroyer("TD"),
    SPAA("SPAA");

    private String abbreviation;

    GroundVehicleClass(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}
