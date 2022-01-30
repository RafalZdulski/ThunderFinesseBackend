package com.thunderfinesse.thunderback.data.graphs;

import com.thunderfinesse.thunderback.data.enums.Mode;
import com.thunderfinesse.thunderback.data.enums.VehicleType;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;

public class GraphData {
    @Getter
    private String mode;
    @Getter
    private String type;
    @Getter
    private String xLabel;
    @Getter
    private String yLabel;
    @Getter
    private String[] xVals;
    @Getter
    private String[] yVals;
    @Getter
    private String[] zVals; //for heatmaps

    public GraphData(Mode mode, VehicleType type, String yLabel, String xLabel, Enum[] xVals, Map<? extends Enum,?> map) {
        this.mode = mode.getAbbrev();
        this.type = type.getAbbrev();
        this.xLabel = xLabel;
        this.yLabel = yLabel;
        this.xVals = Arrays.stream(xVals).map(Enum::toString).toArray(String[]::new);
        yVals = new String[xVals.length];
        for( int i=0; i < yVals.length; i++)
            yVals[i] = String.valueOf(map.get(xVals[i]));
    }
}
