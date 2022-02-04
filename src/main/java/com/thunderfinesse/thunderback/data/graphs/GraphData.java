package com.thunderfinesse.thunderback.data.graphs;

import com.thunderfinesse.thunderback.data.VehicleList;
import com.thunderfinesse.thunderback.data.enums.*;
import lombok.Getter;

import java.util.Arrays;

public class GraphData {
    private Mode mode;
    private VehicleType type;

    public String getMode(){
        return type.getAbbrev()+"_"+mode.getAbbrev();
    }

    //labels
    @Getter
    private String[] ranks;
    @Getter
    private String[] nations;
    @Getter
    private String[] classes;

    //bar charts per Rank
    @Getter
    private int[] battlesPerRank;
    @Getter
    private double[] wrPerRank;
    @Getter
    private double[] kdPerRank;

    //bar charts per nation
    @Getter
    private int[] battlesPerNation;
    @Getter
    private double[] wrPerNation;
    @Getter
    private double[] kdPerNation;

    //bar charts per class
    @Getter
    private int[] battlesPerClass;
    @Getter
    private double[] wrPerClass;
    @Getter
    private double[] kdPerClass;


    //auxiliary arrays
    //reference arrays
    private Rank[] ranksArray;
    private Nation[] nationsArray;
    private Enum[] classesArray;

    //for calculating stats
    private int[] winsPerRank;
    private int[] killsPerRank;
    private int[] deathsPerRank;

    private int[] winsPerNation;
    private int[] killsPerNation;
    private int[] deathsPerNation;

    private int[] winsPerClass;
    private int[] killsPerClass;
    private int[] deathsPerClass;

    public GraphData(VehicleList list){
        this.mode = list.getMode();
        this.type = list.getType();
        initReferenceArrays();
        initArrays();
        initLabels();

        for(var v : list){
            int rankIndex = getIndexOf(ranksArray,v.getRank());
            winsPerRank[rankIndex] += v.getVictories();
            battlesPerRank[rankIndex] += v.getBattles();
            killsPerRank[rankIndex] += v.getAllKills();
            deathsPerRank[rankIndex] += v.getDeaths();

            int nationIndex = getIndexOf(nationsArray, v.getNation());
            winsPerNation[nationIndex] += v.getVictories();
            battlesPerNation[nationIndex] += v.getBattles();
            killsPerNation[nationIndex] += v.getAllKills();
            deathsPerNation[nationIndex] += v.getDeaths();

            int classIndex = getIndexOf(classesArray, v.getKlass());
            winsPerClass[classIndex] += v.getVictories();
            battlesPerClass[classIndex] += v.getBattles();
            killsPerClass[classIndex] += v.getAllKills();
            deathsPerClass[classIndex] += v.getDeaths();
        }

        for(int i = 0; i< ranksArray.length; i++){
            wrPerRank[i] = winsPerRank[i] / (double) battlesPerRank[i];
            kdPerRank[i] = killsPerRank[i] / (double) deathsPerRank[i];
        }
        for(int i = 0; i< nationsArray.length; i++){
            wrPerNation[i] = winsPerNation[i] / (double) battlesPerNation[i];
            kdPerNation[i] = killsPerNation[i] / (double) deathsPerNation[i];
        }
        for(int i = 0; i< classesArray.length; i++){
            wrPerClass[i] = winsPerClass[i] / (double) battlesPerClass[i];
            kdPerClass[i] = killsPerClass[i] / (double) deathsPerClass[i];
        }

    }

    private void initLabels() {
        ranks = Arrays.stream(ranksArray).map(Enum::toString).toArray(String[]::new);
        nations = Arrays.stream(nationsArray).map(Nation::getAbbreviation).toArray(String[]::new);
        switch (type){
            case Aircraft -> classes = Arrays.stream(AircraftClass.values()).map(AircraftClass::getAbbreviation).toArray(String[]::new);
            case GroundVehicle -> classes = Arrays.stream(GroundVehicleClass.values()).map(GroundVehicleClass::getAbbreviation).toArray(String[]::new);
            default -> throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    private int getIndexOf(Enum<?>[] array, Enum<?> elem) {
        int ret = 0;
        while (array[ret] != elem)
            ret++;
        return ret;
    }

    private void initReferenceArrays() {
        this.ranksArray = Rank.values();
        this.nationsArray = Nation.values();
        switch (type){
            case Aircraft -> classesArray = AircraftClass.values();
            case GroundVehicle -> classesArray = GroundVehicleClass.values();
            default -> throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    private void initArrays() {
        int ranks = Rank.values().length;
        battlesPerRank = new int[ranks];
        wrPerRank = new double[ranks];
        kdPerRank = new double[ranks];

        winsPerRank = new int[ranks];
        killsPerRank = new int[ranks];
        deathsPerRank = new int[ranks];

        int nations = Nation.values().length;
        battlesPerNation = new int[nations];
        wrPerNation = new double[nations];
        kdPerNation = new double[nations];

        winsPerNation = new int[nations];
        killsPerNation = new int[nations];
        deathsPerNation = new int[nations];

        int classes;
        switch (type){
            case Aircraft -> classes = AircraftClass.values().length;
            case GroundVehicle -> classes = GroundVehicleClass.values().length;
            default -> throw new IllegalStateException("Unexpected value: " + type);
        }
        battlesPerClass = new int[classes];
        wrPerClass = new double[classes];
        kdPerClass = new double[classes];

        winsPerClass = new int[classes];
        killsPerClass = new int[classes];
        deathsPerClass = new int[classes];

    }
}
