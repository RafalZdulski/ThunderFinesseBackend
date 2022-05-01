package com.thunderfinesse.thunderback.dtos.graphs;

import com.thunderfinesse.thunderback.data.VehicleList;
import com.thunderfinesse.thunderback.data.enums.Mode;
import com.thunderfinesse.thunderback.data.enums.VehicleType;
import lombok.Getter;

public class GameModeOverallResponse {
    private Mode mode;
    private VehicleType type;

    public String getMode(){
        return mode.getAbbrev();
    }
    public String getType(){ return type.getAbbrev();}

    @Getter
    private int battles;
    @Getter
    private double winRatio;
    @Getter
    private double kdRatio;
    @Getter
    private double ksRatio;
    @Getter
    private double airKdRatio; //for planes

    //TODO add 'efficiency' or whatever it can be called - single value for assessing skill

    public GameModeOverallResponse(VehicleList list){
        mode = list.getMode();
        type = list.getType();

        int victories = 0;
        int kills = 0;
        int deaths = 0;
        int spawns = 0;
        int airKills = 0;

        for (var vehicle : list){
            battles += vehicle.getBattles();
            victories += vehicle.getVictories();
            kills += vehicle.getAllKills();
            airKills += vehicle.getAirKills();
            spawns += vehicle.getRespawns();
            deaths += vehicle.getDeaths();
        }

        winRatio = victories / (double) battles;
        kdRatio = kills / (double) deaths;
        ksRatio = kills / (double) spawns;
        airKdRatio = airKills / (double) deaths;
    }

}
