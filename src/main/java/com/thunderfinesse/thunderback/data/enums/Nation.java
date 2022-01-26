package com.thunderfinesse.thunderback.data.enums;

public enum Nation {
    USA("USA"),
    Germany("GER"),
    USSR("USSR"),
    Britain("GB"),
    Japan("JAP"),
    China("CHN"),
    Italy("ITA"),
    France("FR"),
    Sweden("SWE"),
    Israel("IDF");


    private String abbreviation;

    Nation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public static Nation getNation(String name){
        for (var nation : values()) {
            if (name.contains(nation.toString().toLowerCase())) //if statement should be written better
                return nation;
        }
        throw new IllegalArgumentException("no such nation as " + name);
    }
}
