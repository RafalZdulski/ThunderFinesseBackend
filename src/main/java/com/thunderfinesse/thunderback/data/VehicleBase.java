package com.thunderfinesse.thunderback.data;

import com.thunderfinesse.thunderback.data.enums.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.Document;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
public class VehicleBase {
    @Id
    @Getter
    protected String _id;
    //data from wiki
    @Setter
    @Getter
    protected String name;
    @Setter @Getter
    protected VehicleType type;
    @Setter @Getter
    protected Status status;
    @Setter @Getter
    protected Enum klass;
    @Setter @Getter
    protected Nation nation;
    @Setter @Getter
    protected Rank rank;
    @Setter @Getter
    protected String[] battleRating;

    public VehicleBase(Document document){
        _id = document.getString("_id");
        name = document.getString("name");
        type = VehicleType.valueOf(document.getString("type"));
        status = Status.valueOf(document.getString("status"));
        nation = Nation.valueOf(document.getString("nation"));
        rank = Rank.valueOf(document.getString("rank"));
        Document brDoc = document.get("battle_rating",Document.class);
        battleRating = new String[]{
                brDoc.getString("arcade"),
                brDoc.getString("realistic"),
                brDoc.getString("simulation"),
        };

        switch (type){
            case Aircraft -> klass = AircraftClass.valueOf(document.getString("class"));
            case GroundVehicle -> klass = GroundVehicleClass.valueOf(document.getString("class"));
            case Helicopter -> klass = HelicopterClass.valueOf(document.getString("class"));
            case BluewaterVessel -> klass = BluewaterVesselClass.valueOf(document.getString("class"));
            case CoastalVessel -> klass = CoastalVesselClass.valueOf(document.getString("class"));

            default -> throw new RuntimeException("wrong type of vehicle: " + type + " of vehicle: " + _id);
        }
    }

}
