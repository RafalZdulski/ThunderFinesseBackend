package com.thunderfinesse.thunderback.data.graphs;

import com.thunderfinesse.thunderback.data.VehicleList;
import com.thunderfinesse.thunderback.data.enums.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

public class GameModeDetailData {
    private Mode mode;
    private VehicleType type;

    public String getMode(){
        return mode.getAbbrev();
    }
    public String getType(){ return type.getAbbrev();}

    //labels
    @Getter
    private String[] ranks;
    @Getter
    private String[] nations;
    @Getter
    private String[] classes;
    @Getter
    private String[] statuses;
    @Getter
    private double[] brs;

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


    //bar charts per status
    @Getter
    private int[] battlesPerStatus;
    @Getter
    private double[] wrPerStatus;
    @Getter
    private double[] kdPerStatus;

    //heatmaps
    class heatmapElement{
        @Getter
        double br;
        @Getter
        String nation;
        @Getter @Setter
        double val;

        public heatmapElement(double br, String nation){
            this.br = br;
            this.nation = nation;
            val = 0.;
        }

        public void increaseVal(int x){
            val += x;
        }
    }
    @Getter
    private heatmapElement[] wrBrNationHeatmap;
    @Getter
    private heatmapElement[] kdBrNationHeatmap;
    @Getter
    private heatmapElement[] battlesBrNationHeatmap;

    //auxiliary arrays
    //reference arrays
    private Rank[] ranksArray;
    private Nation[] nationsArray;
    private Enum[] classesArray;
    private Status[] statusesArray;

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

    private int[] winsPerStatus;
    private int[] killsPerStatus;
    private int[] deathsPerStatus;

    private heatmapElement[] winsBrNationHeatmap;
    private heatmapElement[] killsBrNationHeatmap;
    private heatmapElement[] deathsBrNationHeatmap;

    public GameModeDetailData(VehicleList list){
        this.mode = list.getMode();
        this.type = list.getType();

        initReferenceArrays();
        initLabels();
        initArrays();


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

            int statusIndex = getIndexOf(statusesArray, v.getStatus());
            winsPerStatus[statusIndex] += v.getVictories();
            battlesPerStatus[statusIndex] += v.getBattles();
            killsPerStatus[statusIndex] += v.getAllKills();
            deathsPerStatus[statusIndex] += v.getDeaths();

            int brIndex = getIndexOf(brs,v.getBattleRating(mode));
            winsBrNationHeatmap[brIndex+nationIndex*brs.length].increaseVal(v.getVictories());
            battlesBrNationHeatmap[brIndex+nationIndex*brs.length].increaseVal(v.getBattles());
            killsBrNationHeatmap[brIndex+nationIndex*brs.length].increaseVal(v.getAllKills());
            deathsBrNationHeatmap[brIndex+nationIndex*brs.length].increaseVal(v.getDeaths());
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
        for(int i = 0; i< statusesArray.length; i++){
            wrPerStatus[i] = winsPerStatus[i] / (double) battlesPerStatus[i];
            kdPerStatus[i] = killsPerStatus[i] / (double) deathsPerStatus[i];
        }
        for(int i=0; i<nations.length * brs.length; i++){
            wrBrNationHeatmap[i].setVal(winsBrNationHeatmap[i].getVal() / battlesBrNationHeatmap[i].getVal());
            kdBrNationHeatmap[i].setVal(killsBrNationHeatmap[i].getVal() / deathsBrNationHeatmap[i].getVal());
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
        statuses = Arrays.stream(statusesArray).map(Enum::toString).toArray(String[]::new);
        brs = new double[]{1.0,1.3,1.7,2.0,2.3,2.7,3.0,3.3,3.7,4.0,4.3,4.7,5.0,5.3,5.7,6.0,6.3,6.7,7.0,7.3,7.7,8.0,8.3,8.7,9.0,9.3,9.7,10.0,10.3,10.7,11.0,11.3};
    }
    private int getIndexOf(Enum<?>[] array, Enum<?> elem) {
        int ret = 0;
        while (array[ret] != elem)
            ret++;
        return ret;
    }
    private int getIndexOf(double[] array, String elem) {
        int ret = 0;
        double el = Double.parseDouble(elem);
        while (array[ret] != el)
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
        this.statusesArray = Status.values();
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

        int statuses = Status.values().length;
        battlesPerStatus = new int[statuses];
        wrPerStatus = new double[statuses];
        kdPerStatus = new double[statuses];
        winsPerStatus = new int[statuses];
        killsPerStatus = new int[statuses];
        deathsPerStatus = new int[statuses];

        //heatmaps
        int brs = this.brs.length;
        battlesBrNationHeatmap = new heatmapElement[brs*nations];
        wrBrNationHeatmap = new heatmapElement[brs*nations];
        kdBrNationHeatmap = new heatmapElement[brs*nations];
        winsBrNationHeatmap = new heatmapElement[brs*nations];
        killsBrNationHeatmap = new heatmapElement[brs*nations];
        deathsBrNationHeatmap = new heatmapElement[brs*nations];

        for (int i=0; i<nations;i++){
            String actNation = this.nations[i];
            for (int j=0; j<brs; j++) {
                double actBr = this.brs[j];
                battlesBrNationHeatmap[i*brs+j] = new heatmapElement(actBr, actNation);
                wrBrNationHeatmap[i*brs+j] = new heatmapElement(actBr, actNation);
                kdBrNationHeatmap[i*brs+j] = new heatmapElement(actBr, actNation);
                winsBrNationHeatmap[i*brs+j] = new heatmapElement(actBr, actNation);
                killsBrNationHeatmap[i*brs+j] = new heatmapElement(actBr, actNation);
                deathsBrNationHeatmap[i*brs+j] = new heatmapElement(actBr, actNation);
            }
        }

    }
}
