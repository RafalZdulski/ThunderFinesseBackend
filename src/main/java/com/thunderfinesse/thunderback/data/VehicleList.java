package com.thunderfinesse.thunderback.data;

import com.thunderfinesse.thunderback.data.enums.Mode;
import com.thunderfinesse.thunderback.data.enums.VehicleType;
import lombok.Getter;

import java.util.ArrayList;

public class VehicleList extends ArrayList<VehicleStat> {
    @Getter
    private VehicleType type;
    @Getter
    private Mode mode;

    public VehicleList(VehicleType type, Mode mode){
        this.type = type;
        this.mode = mode;
    }

    public int getVictories(){
        int ret = 0;
        for (var v:this)
            ret = ret+v.getVictories();
        return ret;
    }

    public int getBattles(){
        int ret = 0;
        for (var v:this)
            ret = ret+v.getBattles();
        return ret;
    }

    public double getWinRatio(){
        return getVictories()/(double)getBattles();
    }

    public int getRespawns(){
        int ret = 0;
        for (var v:this)
            ret = ret+v.getRespawns();
        return ret;
    }

    public int getDeaths(){
        int ret = 0;
        for (var v:this)
            ret = ret+v.getDeaths();
        return ret;
    }

    public int getGroundKills(){
        int ret = 0;
        for (var v:this)
            ret = ret+v.getGroundKills();
        return ret;
    }

    public int getAirKills(){
        int ret = 0;
        for (var v:this)
            ret = ret+v.getAirKills();
        return ret;
    }

    public int getAllKills(){
        int ret = 0;
        for (var v:this)
            ret = ret+v.getAllKills();
        return ret;
    }

    public double getKdRatio(){
        return getAllKills()/(double)getDeaths();
    }

    public double getKsRatio(){
        return getAllKills()/(double)getRespawns();
    }

}
