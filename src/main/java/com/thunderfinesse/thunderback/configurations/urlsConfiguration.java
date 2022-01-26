package com.thunderfinesse.thunderback.configurations;

public enum urlsConfiguration {
    WIKI_TREE("https://wiki.warthunder.com/%vehicle%"),
    WIKI_VEHICLE("https://wiki.warthunder.com/Category:%category%"),

    THUNDERSKILL_PLAYER("https://thunderskill.com/en/stat/%login%"),
    THUNDERSKILL_PLAYER_VEHICLES("https://thunderskill.com/en/stat/%login%/vehicles/%mode-short%"),
    THUNDERSKILL_VEHICLES("https://thunderskill.com/en/vehicles"),

    WTSITE_PLAYER("https://warthunder.com/en/community/userinfo/?nick=%login%");

    private String urlTemplate;

    urlsConfiguration(String urlTemplate){
        this.urlTemplate = urlTemplate;
    }

}
