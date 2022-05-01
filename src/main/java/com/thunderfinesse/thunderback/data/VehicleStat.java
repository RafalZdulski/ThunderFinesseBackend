package com.thunderfinesse.thunderback.data;

import com.thunderfinesse.thunderback.data.enums.*;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class VehicleStat extends VehicleBase{
    //data from thunderskill.com
    @Getter
    protected int battles;
    @Getter
    protected int respawns;
    @Getter
    protected int deaths;
    @Getter
    protected int victories;
    @Getter
    protected int defeats;
    @Getter
    protected int airKills;
    @Getter
    protected int groundKills;
    @Getter
    protected int allKills;
    @Getter
    protected double winRatio;
    @Getter
    protected double kdRatio;
    @Getter
    protected double ksRatio;

    public VehicleStat(org.bson.Document document) {
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
