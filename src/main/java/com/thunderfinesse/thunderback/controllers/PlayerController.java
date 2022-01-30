package com.thunderfinesse.thunderback.controllers;

import com.thunderfinesse.thunderback.daos.ThunderfinessePlayerDao;
import com.thunderfinesse.thunderback.data.enums.*;
import com.thunderfinesse.thunderback.data.graphs.GraphData;
import com.thunderfinesse.thunderback.data.Player;
import com.thunderfinesse.thunderback.data.graphs.HistogramData;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static com.thunderfinesse.thunderback.data.enums.Mode.ARCADE;
import static com.thunderfinesse.thunderback.data.enums.VehicleType.Aircraft;
import static com.thunderfinesse.thunderback.data.enums.VehicleType.GroundVehicle;


@RestController
@RequestMapping("/player")
@CrossOrigin(origins = "http://localhost:4200")
public class PlayerController {

    @GetMapping("/{login}")
    public Player getPlayerStats(@PathVariable("login") String login) {
        Player player = getPlayer(login);
        return player;
    }

    @GetMapping("/{login}/graphs")
    public GraphData[] getPlayers(@PathVariable("login") String login){
        Player player = getPlayer(login);
        GraphData[] graphsData = getGraphsData(player);
        return graphsData;
    }


//TODO (consider) is better to share whole Player class at /{login}/player or to share each VehicleList at /{login}/{modeStr}/{typeStr}

//    @GetMapping("/{login}/{modeStr}/{typeStr}") //shouldn't it return Player?
//    public VehicleList getPlayerStats(@PathVariable("login") String login, @PathVariable("modeStr") String modeStr, @PathVariable("typeStr") String typeStr) {
//        Mode mode = Mode.valOf(modeStr);
//        VehicleType type = VehicleType.valOf(typeStr);
//        VehicleList vehicleList = getList(login, mode, type);
//        return vehicleList;
//    }

    private Player getPlayer(String login) {
        Player player = new Player(login);

        //checking if player exist
        ThunderfinessePlayerDao thunderfinessePlayerDao = new ThunderfinessePlayerDao();
        if (!thunderfinessePlayerDao.checkAndUpdate(login)){
            throw new IllegalStateException("player does not exist");//somehow I need to pass this info to front
        }

        CountDownLatch latch = new CountDownLatch(6);
        new Thread(new PlayerControllerSubthread(player.getAir_ab(), player, latch)).start();
        new Thread(new PlayerControllerSubthread(player.getGround_ab(), player, latch)).start();
        new Thread(new PlayerControllerSubthread(player.getAir_rb(), player, latch)).start();
        new Thread(new PlayerControllerSubthread(player.getGround_rb(), player, latch)).start();
        new Thread(new PlayerControllerSubthread(player.getAir_sb(), player, latch)).start();
        new Thread(new PlayerControllerSubthread(player.getGround_sb(), player, latch)).start();
        try {
            latch.await();
        } catch (InterruptedException e) {}

        return player;
    }

    private GraphData[] getGraphsData(Player player) {
        HistogramData[] histograms = new HistogramData[]{
                new HistogramData(player.getAir_ab(), Aircraft),
                new HistogramData(player.getGround_ab(), GroundVehicle),
                new HistogramData(player.getAir_rb(), Aircraft),
                new HistogramData(player.getGround_rb(), GroundVehicle),
                new HistogramData(player.getAir_sb(), Aircraft),
                new HistogramData(player.getGround_sb(), GroundVehicle)
        };


        //TODO (reconsider) maybe more and different constructors in GraphData  would be better idea than this monstrosity?
        List<GraphData> ret = new LinkedList<>();
        int i = 0;
        for (var mode : Mode.values())
            for(var type : new VehicleType[]{Aircraft,GroundVehicle}) {
                ret.add(new GraphData(mode, type, "battles", "rank", Rank.values(), histograms[i].getBattlesPerRank()));
                ret.add(new GraphData(mode, type, "winRatio", "rank", Rank.values(), histograms[i].getWrPerRank()));
                ret.add(new GraphData(mode, type, "kdRatio", "rank", Rank.values(), histograms[i].getKdPerRank()));

                ret.add(new GraphData(mode, type, "battles", "nation", Nation.values(), histograms[i].getBattlesPerNation()));
                ret.add(new GraphData(mode, type, "winRatio", "nation", Nation.values(), histograms[i].getWrPerNation()));
                ret.add(new GraphData(mode, type, "kdRatio", "nation", Nation.values(), histograms[i].getKdPerNation()));

                switch(type){
                    case Aircraft -> {
                        ret.add(new GraphData(mode, type, "battles", "class", AircraftClass.values(), histograms[i].getBattlesPerClass()));
                        ret.add(new GraphData(mode, type, "winRatio", "class", AircraftClass.values(), histograms[i].getWrPerClass()));
                        ret.add(new GraphData(mode, type, "kdRatio", "class", AircraftClass.values(), histograms[i].getKdPerClass()));
                    }
                    case GroundVehicle -> {
                        ret.add(new GraphData(mode, type, "battles", "class", GroundVehicleClass.values(), histograms[i].getBattlesPerClass()));
                        ret.add(new GraphData(mode, type, "winRatio", "class", GroundVehicleClass.values(), histograms[i].getWrPerClass()));
                        ret.add(new GraphData(mode, type, "kdRatio", "class", GroundVehicleClass.values(), histograms[i].getKdPerClass()));
                    }
                }
                ret.add(new GraphData(mode, type, "battles", "class", Status.values(), histograms[i].getBattlesPerStatus()));
                ret.add(new GraphData(mode, type, "winRatio", "class", Status.values(), histograms[i].getWrPerStatus()));
                ret.add(new GraphData(mode, type, "kdRatio", "class", Status.values(), histograms[i].getKdPerStatus()));

                i++;
            }
        return ret.toArray(new GraphData[0]);
    }

//    private VehicleList getList(String login, Mode mode, VehicleType type) {
//        VehicleList retList = new VehicleList(type, mode);
//        new PlayerControllerSubthread(retList, new Player(login), new CountDownLatch(1)).run();
//        return retList;
//    }
}
