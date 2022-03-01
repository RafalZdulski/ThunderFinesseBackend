package com.thunderfinesse.thunderback.data.graphs;

import com.thunderfinesse.thunderback.data.VehicleList;
import com.thunderfinesse.thunderback.data.enums.*;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**@deprecated
 * @see GameModeDetailData
 * */
public class HistogramData {
    /*
     *  generating data for histogram graphs in additional class and
     *  doing it all in one function to minimize amount of looping by list
     */
    private VehicleType type;

    //basic statistics
    @Getter
    private Map<Rank,Integer> battlesPerRank;
    @Getter
    private Map<Rank,Double> wrPerRank;
    @Getter
    private Map<Rank,Double> kdPerRank;

    @Getter
    private Map<Nation,Integer> battlesPerNation;
    @Getter
    private Map<Nation,Double> wrPerNation;
    @Getter
    private Map<Nation,Double> kdPerNation;

    @Getter
    private Map<Enum,Integer> battlesPerClass;
    @Getter
    private Map<Enum,Double> wrPerClass;
    @Getter
    private Map<Enum,Double> kdPerClass;

    //do this statistics are valid by any means?
    @Getter
    private Map<Status,Integer> battlesPerStatus;
    @Getter
    private Map<Status,Double> wrPerStatus;
    @Getter
    private Map<Status,Double> kdPerStatus;

    public HistogramData(VehicleList list, VehicleType type){
        this.type = type;
        initMaps(type);

        if (list.size() > 0){
            calculateBasicStats(list,type);
        }else {
            /* list is empty - set everything to 0
            * (maybe should it set to null or NaN) */
            setToZero(type);
        }
    }

    private void initMaps(VehicleType type) {
        battlesPerRank = Arrays.stream(Rank.values()).collect(Collectors.toMap(val->val,val->0));
        wrPerRank = new HashMap<>();
        kdPerRank = new HashMap<>();
        battlesPerNation = Arrays.stream(Nation.values()).collect(Collectors.toMap(val->val,val->0));
        wrPerNation = new HashMap<>();
        kdPerNation = new HashMap<>();
        battlesPerStatus = Arrays.stream(Status.values()).collect(Collectors.toMap(val->val,val->0));
        wrPerStatus = new HashMap<>();
        kdPerStatus = new HashMap<>();
        switch (type) {
            case GroundVehicle -> {
                battlesPerClass = Arrays.stream(GroundVehicleClass.values()).collect(Collectors.toMap(val -> val, val -> 0));
            }
            case Aircraft -> {
                battlesPerClass = Arrays.stream(AircraftClass.values()).collect(Collectors.toMap(val -> val, val -> 0));
                }
            default -> throw new IllegalArgumentException("does not support vehicle type as: " + type);
        }
        wrPerClass = new HashMap<>();
        kdPerClass = new HashMap<>();
    }

    private void setToZero(VehicleType type){
        battlesPerRank = Arrays.stream(Rank.values()).collect(Collectors.toMap(val->val,val->0));
        wrPerRank = Arrays.stream(Rank.values()).collect(Collectors.toMap(val->val,val->.0));
        kdPerRank = Arrays.stream(Rank.values()).collect(Collectors.toMap(val->val,val->.0));
        battlesPerNation = Arrays.stream(Nation.values()).collect(Collectors.toMap(val->val,val->0));
        wrPerNation = Arrays.stream(Nation.values()).collect(Collectors.toMap(val->val,val->.0));
        kdPerNation = Arrays.stream(Nation.values()).collect(Collectors.toMap(val->val,val->.0));
        battlesPerStatus = Arrays.stream(Status.values()).collect(Collectors.toMap(val->val,val->0));
        wrPerStatus = Arrays.stream(Status.values()).collect(Collectors.toMap(val->val,val->.0));
        kdPerStatus = Arrays.stream(Status.values()).collect(Collectors.toMap(val->val,val->.0));
        switch (type) {
            case GroundVehicle -> {
                battlesPerClass = Arrays.stream(GroundVehicleClass.values()).collect(Collectors.toMap(val -> val, val -> 0));
                wrPerClass = Arrays.stream(GroundVehicleClass.values()).collect(Collectors.toMap(val -> val, val -> .0));
                kdPerClass = Arrays.stream(GroundVehicleClass.values()).collect(Collectors.toMap(val -> val, val -> .0));
            }
            case Aircraft -> {
                battlesPerClass = Arrays.stream(AircraftClass.values()).collect(Collectors.toMap(val -> val, val -> 0));
                wrPerClass = Arrays.stream(AircraftClass.values()).collect(Collectors.toMap(val -> val, val -> .0));
                kdPerClass = Arrays.stream(AircraftClass.values()).collect(Collectors.toMap(val -> val, val -> .0));
            }
            default -> throw new IllegalArgumentException("does not support vehicle type as: " + type);
        }
    }

    /**
     * calculates data for histograms - doing it all in one function to minimize amount of looping by list
     * @param list list containing one type of vehicles
     * @param type GroundVehicle or Aircraft since thunderskill do not collect data of other types of vehicles
     */
    private void calculateBasicStats(VehicleList list, VehicleType type) {
        //initializing maps
        Map<Rank,Integer> killsPerRank = Arrays.stream(Rank.values()).collect(Collectors.toMap(val->val,val->0));
        Map<Rank,Integer> deathsPerRank = Arrays.stream(Rank.values()).collect(Collectors.toMap(val->val,val->0));
        Map<Rank,Integer> winsPerRank = Arrays.stream(Rank.values()).collect(Collectors.toMap(val->val,val->0));
        Map<Nation,Integer> killsPerNation = Arrays.stream(Nation.values()).collect(Collectors.toMap(val->val,val->0));
        Map<Nation,Integer> deathsPerNation = Arrays.stream(Nation.values()).collect(Collectors.toMap(val->val,val->0));
        Map<Nation,Integer> winsPerNation = Arrays.stream(Nation.values()).collect(Collectors.toMap(val->val,val->0));
        //battlesPerClass initialized in switch below
        Map<Enum,Integer> killsPerClass;
        Map<Enum,Integer> deathsPerClass;
        Map<Enum,Integer> winsPerClass;
        Map<Status,Integer> killsPerStatus = Arrays.stream(Status.values()).collect(Collectors.toMap(val->val,val->0));
        Map<Status,Integer> deathsPerStatus = Arrays.stream(Status.values()).collect(Collectors.toMap(val->val,val->0));
        Map<Status,Integer> winsPerStatus = Arrays.stream(Status.values()).collect(Collectors.toMap(val->val,val->0));
        switch (type) {
            case GroundVehicle -> {
                killsPerClass = Arrays.stream(GroundVehicleClass.values()).collect(Collectors.toMap(val -> val, val -> 0));
                deathsPerClass = Arrays.stream(GroundVehicleClass.values()).collect(Collectors.toMap(val -> val, val -> 0));
                winsPerClass = Arrays.stream(GroundVehicleClass.values()).collect(Collectors.toMap(val -> val, val -> 0));
            }
            case Aircraft -> {
                killsPerClass = Arrays.stream(AircraftClass.values()).collect(Collectors.toMap(val -> val, val -> 0));
                deathsPerClass = Arrays.stream(AircraftClass.values()).collect(Collectors.toMap(val -> val, val -> 0));
                winsPerClass = Arrays.stream(AircraftClass.values()).collect(Collectors.toMap(val -> val, val -> 0));
            }
            default -> throw new IllegalArgumentException("does not support vehicle type as: " + type);
        }

        //getting all required information
        for(var v : list){
            //per rank
            battlesPerRank.compute(v.getRank(),(k,val)->val+v.getBattles());
            winsPerRank.compute(v.getRank(),(k,val)->val+v.getVictories());
            killsPerRank.compute(v.getRank(),(k,val)->val+v.getAllKills());
            deathsPerRank.compute(v.getRank(),(k,val)->val+v.getDeaths());
            //per nation
            battlesPerNation.compute(v.getNation(),(k,val)->val+v.getBattles());
            winsPerNation.compute(v.getNation(),(k,val)->val+v.getVictories());
            killsPerNation.compute(v.getNation(),(k,val)->val+v.getAllKills());
            deathsPerNation.compute(v.getNation(),(k,val)->val+v.getDeaths());
            //per class
            battlesPerClass.compute(v.getKlass(),(k,val)->val+v.getBattles());
            winsPerClass.compute(v.getKlass(),(k,val)->val+v.getVictories());
            killsPerClass.compute(v.getKlass(),(k,val)->val+v.getAllKills());
            deathsPerClass.compute(v.getKlass(),(k,val)->val+v.getDeaths());
            //per status
            battlesPerStatus.compute(v.getStatus(),(k,val)->val+v.getBattles());
            winsPerStatus.compute(v.getStatus(),(k,val)->val+v.getVictories());
            killsPerStatus.compute(v.getStatus(),(k,val)->val+v.getAllKills());
            deathsPerStatus.compute(v.getStatus(),(k,val)->val+v.getDeaths());
        }

        //calculating information for histograms
        //per rank
        for (var key : Rank.values()){
            //in case of no battles should it be 0, null or Nan?
            wrPerRank.put(key, battlesPerRank.get(key) == 0? 0 : winsPerRank.get(key)/(double)battlesPerRank.get(key));
            kdPerRank.put(key, deathsPerRank.get(key) == 0? 0 : killsPerRank.get(key)/(double)deathsPerRank.get(key));
        }
        //per nation
        for (var key : Nation.values()){
            wrPerNation.put(key, battlesPerNation.get(key) == 0? 0 : winsPerNation.get(key)/(double)battlesPerNation.get(key));
            kdPerNation.put(key, deathsPerNation.get(key) == 0? 0 : killsPerNation.get(key)/(double)deathsPerNation.get(key));
        }
        //per class
        switch (type) {
            case GroundVehicle :
                for (var key : GroundVehicleClass.values()) {
                    wrPerClass.put(key, battlesPerClass.get(key) == 0 ? 0 : winsPerClass.get(key) / (double) battlesPerClass.get(key));
                    kdPerClass.put(key, deathsPerClass.get(key) == 0 ? 0 : killsPerClass.get(key) / (double) deathsPerClass.get(key));
                }break;
            case Aircraft:
                for (var key : AircraftClass.values()) {
                    wrPerClass.put(key, battlesPerClass.get(key) == 0 ? 0 : winsPerClass.get(key) / (double) battlesPerClass.get(key));
                    kdPerClass.put(key, deathsPerClass.get(key) == 0 ? 0 : killsPerClass.get(key) / (double) deathsPerClass.get(key));
                }break;
            default:
                throw new IllegalArgumentException("does not support vehicle type as: " + type);
        }
        //per status
        for (var key : Status.values()){
            wrPerStatus.put(key, battlesPerStatus.get(key) == 0? 0 : winsPerStatus.get(key)/(double)battlesPerStatus.get(key));
            kdPerStatus.put(key, deathsPerStatus.get(key) == 0? 0 : winsPerStatus.get(key)/(double)battlesPerStatus.get(key));
        }
    }

    public String getBattlesPerRankJson(){
        StringBuilder ret = new StringBuilder("{");
        for (var key : Rank.values()){
            ret.append("\""+key+"\":"+battlesPerRank.get(key)+",");
        }
        ret.deleteCharAt(ret.lastIndexOf(","));
        ret.append("}");
        return ret.toString();
    }

    public String getWrPerRankJson(){
        StringBuilder ret = new StringBuilder("{");
        for (var key : Rank.values()){
            ret.append("\""+key+"\":"+wrPerRank.get(key)+",");
        }
        ret.deleteCharAt(ret.lastIndexOf(","));
        ret.append("}");
        return ret.toString();
    }

    public String getKdPerRankJson(){
        StringBuilder ret = new StringBuilder("{");
        for (var key : Rank.values()){
            ret.append("\""+key+"\":"+kdPerRank.get(key)+",");
        }
        ret.deleteCharAt(ret.lastIndexOf(","));
        ret.append("}");
        return ret.toString();
    }

    public String getBattlesPerNationJson(){
        StringBuilder ret = new StringBuilder("{");
        for (var key : Nation.values()){
            ret.append("\""+key.getAbbreviation() +
                    "\":"+battlesPerNation.get(key)+",");
        }
        ret.deleteCharAt(ret.lastIndexOf(","));
        ret.append("}");
        return ret.toString();
    }

    public String getWrPerNationJson(){
        StringBuilder ret = new StringBuilder("{");
        for (var key : Nation.values()){
            ret.append("\""+key.getAbbreviation() +
                    "\":"+wrPerNation.get(key)+",");
        }
        ret.deleteCharAt(ret.lastIndexOf(","));
        ret.append("}");
        return ret.toString();
    }

    public String getKdPerNationJson(){
        StringBuilder ret = new StringBuilder("{");
        for (var key : Nation.values()){
            ret.append("\""+key.getAbbreviation() +
                    "\":"+kdPerNation.get(key)+",");
        }
        ret.deleteCharAt(ret.lastIndexOf(","));
        ret.append("}");
        return ret.toString();
    }

    public String getBattlesPerClassJson(){
        StringBuilder ret = new StringBuilder("{");
        if (type == VehicleType.GroundVehicle)
            for (var key : GroundVehicleClass.values()){
                ret.append("\""+key.getAbbreviation()+
                        "\":"+battlesPerClass.get(key)+",");
            }
        else if (type == VehicleType.Aircraft)
            for (var key : AircraftClass.values()){
                ret.append("\""+key.getAbbreviation()+
                        "\":"+battlesPerClass.get(key)+",");
            }
        else
            throw new IllegalArgumentException("type not supported: " + type);
        ret.deleteCharAt(ret.lastIndexOf(","));
        ret.append("}");
        return ret.toString();
    }

    public String getWrPerClassJson(){
        StringBuilder ret = new StringBuilder("{");
        if (type == VehicleType.GroundVehicle)
            for (var key : GroundVehicleClass.values()){
                ret.append("\""+key.getAbbreviation()+
                        "\":"+wrPerClass.get(key)+",");
            }
        else if (type == VehicleType.Aircraft)
            for (var key : AircraftClass.values()){
                ret.append("\""+key.getAbbreviation()+
                        "\":"+wrPerClass.get(key)+",");
            }
        else
            throw new IllegalArgumentException("type not supported: " + type);
        ret.deleteCharAt(ret.lastIndexOf(","));
        ret.append("}");
        return ret.toString();
    }

    public String getKdPerClassJson(){
        StringBuilder ret = new StringBuilder("{");
        if (type == VehicleType.GroundVehicle)
            for (var key : GroundVehicleClass.values()){
                ret.append("\""+key.getAbbreviation()+
                        "\":"+kdPerClass.get(key)+",");
            }
        else if (type == VehicleType.Aircraft)
            for (var key : AircraftClass.values()){
                ret.append("\""+key.getAbbreviation()+
                        "\":"+kdPerClass.get(key)+",");
            }
        else
            throw new IllegalArgumentException("type not supported: " + type);
        ret.deleteCharAt(ret.lastIndexOf(","));
        ret.append("}");
        return ret.toString();
    }

    public String getBattlesPerStatusJson(){
        StringBuilder ret = new StringBuilder("{");
        for (var key : Status.values()){
            ret.append("\""+key+"\":"+battlesPerStatus.get(key)+",");
        }
        ret.deleteCharAt(ret.lastIndexOf(","));
        ret.append("}");
        return ret.toString();
    }

    public String getWrPerStatusJson(){
        StringBuilder ret = new StringBuilder("{");
        for (var key : Status.values()){
            ret.append("\""+key+"\":"+wrPerStatus.get(key)+",");
        }
        ret.deleteCharAt(ret.lastIndexOf(","));
        ret.append("}");
        return ret.toString();
    }

    public String getKdPerStatusJson(){
        StringBuilder ret = new StringBuilder("{");
        for (var key : Status.values()){
            ret.append("\""+key+"\":"+kdPerStatus.get(key)+",");
        }
        ret.deleteCharAt(ret.lastIndexOf(","));
        ret.append("}");
        return ret.toString();
    }
}
