package com.thunderfinesse.thunderback.data.enums;

public enum Mode {
    ARCADE("ab"),
    REALISTIC("rb"),
    SIMULATION("sb");

    String abbrev;

    Mode(String abbrev) {
     this.abbrev = abbrev;
    }

    public String getAbbrev() {
        return abbrev;
    }

    public static Mode valOf(String s){
        switch (s.toLowerCase()){
            case "a","ab","arcade": return ARCADE;
            case "r","rb","realistic":  return REALISTIC;
            case "s","sb","sim","simulation": return SIMULATION;
            default: throw new IllegalArgumentException("no enum constant com.zdulski.thunderfinesse.data.enums.Mode."+s);
            }

    }

}
