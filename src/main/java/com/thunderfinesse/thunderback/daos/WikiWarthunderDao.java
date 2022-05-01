package com.thunderfinesse.thunderback.daos;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.thunderfinesse.thunderback.data.VehicleBase;
import com.thunderfinesse.thunderback.data.VehicleStat;
import com.thunderfinesse.thunderback.data.enums.*;
import com.thunderfinesse.thunderback.mongodb.configurations.WikiWarthunderMongoDB;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class WikiWarthunderDao {

    private final WikiWarthunderMongoDB wikiWarthunderMongoDB;
    private final String collectionNameTemplate = "all_%type%";

    public WikiWarthunderDao(){
        wikiWarthunderMongoDB = WikiWarthunderMongoDB.getInstance();
    }

    public void getWikiStats(VehicleStat vehicle, VehicleType type) {
        MongoCollection<Document> collection = wikiWarthunderMongoDB.getCollection(getCollectionName(type));
        Bson filter = Filters.eq("_id",vehicle.get_id());
        Document document = collection.find(filter).first();

        if (document == null)
            throw new IllegalStateException("could not find wikiDB document for "+vehicle.get_id());

        vehicle.setName(document.getString("name"));
        vehicle.setType(VehicleType.valueOf(document.getString("type")));
        vehicle.setStatus(Status.valueOf(document.getString("status")));
        vehicle.setRank(Rank.valueOf(document.getString("rank")));
        vehicle.setNation(Nation.valueOf(document.getString("nation")));
        Document brDoc = document.get("battle_rating",Document.class);
        vehicle.setBattleRating(new String[]{
                brDoc.getString("arcade"),
                brDoc.getString("realistic"),
                brDoc.getString("simulation"),
        });

        switch (vehicle.getType()){
            case Aircraft -> vehicle.setKlass(AircraftClass.valueOf(document.getString("class")));
            case GroundVehicle -> vehicle.setKlass(GroundVehicleClass.valueOf(document.getString("class")));
            default -> throw new RuntimeException("wrong type of vehicle: " + vehicle.getType());
        }
    }

    public List<VehicleBase> getAllVehicles(){
        MongoCollection<Document> collection = wikiWarthunderMongoDB.getCollection("all_vehicles");
        List<VehicleBase> ret = new ArrayList<>();
        FindIterable<Document> iterable = collection.find();
        iterable.forEach(x -> ret.add(new VehicleBase(x)));
        return ret;
    }

    private String getCollectionName(VehicleType type) {
        return collectionNameTemplate.replace("%type%", type.toString());
    }
}
