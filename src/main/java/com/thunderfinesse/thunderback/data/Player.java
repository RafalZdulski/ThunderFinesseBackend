package com.thunderfinesse.thunderback.data;

import com.thunderfinesse.thunderback.data.enums.Mode;
import com.thunderfinesse.thunderback.data.enums.VehicleType;
import lombok.Data;

@Data
public class Player {
    private String login;

    private VehicleList air_ab;
    private VehicleList air_rb;
    private VehicleList air_sb;
    private VehicleList ground_ab;
    private VehicleList ground_rb;
    private VehicleList ground_sb;

    public Player(String login) {
        this.login = login;
        initLists();
    }

    private void initLists() {
        air_ab = new VehicleList(VehicleType.Aircraft, Mode.ARCADE);
        air_rb = new VehicleList(VehicleType.Aircraft, Mode.REALISTIC);
        air_sb = new VehicleList(VehicleType.Aircraft, Mode.SIMULATION);
        ground_ab = new VehicleList(VehicleType.GroundVehicle, Mode.ARCADE);
        ground_rb = new VehicleList(VehicleType.GroundVehicle, Mode.REALISTIC);
        ground_sb = new VehicleList(VehicleType.GroundVehicle, Mode.SIMULATION);
    }
}
