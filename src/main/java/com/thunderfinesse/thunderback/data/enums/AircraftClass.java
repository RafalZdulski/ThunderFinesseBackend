package com.thunderfinesse.thunderback.data.enums;

public enum AircraftClass {
    Fighter("Fighter"),
    Strike_aircraft("Attacker"),
    Bomber("Bomber");

    private String abbreviation;

    AircraftClass(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}
