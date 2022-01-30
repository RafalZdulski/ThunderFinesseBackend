package com.thunderfinesse.thunderback.controllers;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.thunderfinesse.thunderback.daos.ThunderskillPlayerDao;
import com.thunderfinesse.thunderback.daos.WikiWarthunderDao;
import com.thunderfinesse.thunderback.data.graphs.HistogramData;
import com.thunderfinesse.thunderback.data.Player;
import com.thunderfinesse.thunderback.data.Vehicle;
import com.thunderfinesse.thunderback.data.VehicleList;
import com.thunderfinesse.thunderback.data.enums.Mode;
import com.thunderfinesse.thunderback.data.enums.VehicleType;
import org.bson.Document;

import java.util.concurrent.CountDownLatch;

public class PlayerControllerSubthread implements Runnable{
    private final VehicleList vehicleList;
    private final Player player;
    private final CountDownLatch latch;


    public PlayerControllerSubthread(VehicleList vehicleList, Player player, CountDownLatch latch){
        this.vehicleList = vehicleList;
        this.player = player;
        this.latch = latch;
    }

    @Override
    public void run() {
        VehicleType type = vehicleList.getType();
        Mode mode = vehicleList.getMode();
        String login = player.getLogin();

        ThunderskillPlayerDao thunderskillPlayerDao = new ThunderskillPlayerDao();
        WikiWarthunderDao wikiWarthunderDao = new WikiWarthunderDao();

        //TODO: refactor this so it did not require getting collection but already list of Vehicles
        MongoCollection<Document> collection = thunderskillPlayerDao.getCollection(login,type,mode);
        FindIterable<Document> iterDoc = collection.find();
        MongoCursor<Document> it = iterDoc.iterator();

        while (it.hasNext()) {
            try {
                Vehicle v = new Vehicle(Document.parse(it.next().toJson()));
                wikiWarthunderDao.getWikiStats(v, type);
                vehicleList.add(v);
            }catch (IllegalStateException e){
                System.out.println(e.getMessage());
            }
        }
        //vehicleList.setHistogramData(new HistogramData(vehicleList,type));

        latch.countDown();
    }
}
