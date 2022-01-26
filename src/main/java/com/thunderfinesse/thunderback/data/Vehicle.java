package com.thunderfinesse.thunderback.data;

import com.thunderfinesse.thunderback.data.enums.*;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Vehicle {
    @Id @Getter
    private String _id;
    //data from wiki
    @Setter @Getter
    private String name;
    @Setter @Getter
    private VehicleType type;
    @Setter @Getter
    private Status status;
    @Setter @Getter
    private Enum klass;
    @Setter @Getter
    private Nation nation;
    @Setter @Getter
    private Rank rank;
    @Setter @Getter
    private String[] battleRating;

    //data from thunderskill
    @Getter
    private int battles;
    @Getter
    private int respawns;
    @Getter
    private int deaths;
    @Getter
    private int victories;
    @Getter
    private int defeats;
    @Getter
    private int airKills;
    @Getter
    private int groundKills;
    @Getter
    private int allKills;
    @Getter
    private double winRatio;
    @Getter
    private double kdRatio;
    @Getter
    private double ksRatio;

    public Vehicle(org.bson.Document document) {
        _id = document.getString("_id");
        battles = document.getInteger("battles");
        respawns = document.getInteger("respawns");
        deaths = document.getInteger("deaths");
        victories = document.getInteger("victories");
        defeats = document.getInteger("defeats");
        airKills = document.getInteger("airKills");
        groundKills = document.getInteger("groundKills");
        allKills = document.getInteger("allKills");
        winRatio = document.getDouble("winRatio");
        kdRatio = document.getDouble("kdRatio");
        ksRatio = document.getDouble("ksRatio");
    }

    public String getBattleRating(Mode mode){
        switch (mode){
            case ARCADE : return battleRating[0];
            case REALISTIC : return battleRating[1];
            case SIMULATION : return battleRating[2];
            default : throw new IllegalArgumentException("could not get battle rating for "+mode);
        }
    }


    public String toJsonString() {
        JSONObject json = new JSONObject();

        json.put("id",_id);
        json.put("name",name);
        json.put("type",type);
        json.put("status",status);
        json.put("class",klass);
        json.put("nation",nation);
        json.put("rank",rank);
        json.put("battleRating",battleRating);
        json.put("battles",battles);
        json.put("respawns",respawns);
        json.put("deaths",deaths);
        json.put("victories",victories);
        json.put("defeats",defeats);
        json.put("airKills",airKills);
        json.put("groundKills",groundKills);
        json.put("allKills",allKills);
        json.put("winRatio",winRatio);
        json.put("kdRatio",kdRatio);
        json.put("ksRatio",ksRatio);

        return json.toJSONString();
    }
}
