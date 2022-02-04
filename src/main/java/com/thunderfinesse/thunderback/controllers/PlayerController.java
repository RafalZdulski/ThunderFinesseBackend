package com.thunderfinesse.thunderback.controllers;

import com.thunderfinesse.thunderback.daos.ThunderfinessePlayerDao;
import com.thunderfinesse.thunderback.data.graphs.GraphData;
import com.thunderfinesse.thunderback.data.Player;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

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
        List<GraphData> ret = new LinkedList<>();
        ret.add(new GraphData(player.getAir_ab()));
        ret.add(new GraphData(player.getGround_ab()));
        ret.add(new GraphData(player.getAir_rb()));
        ret.add(new GraphData(player.getGround_rb()));
        ret.add(new GraphData(player.getAir_sb()));
        ret.add(new GraphData(player.getGround_sb()));

        return ret.toArray(new GraphData[0]);
    }

//    private VehicleList getList(String login, Mode mode, VehicleType type) {
//        VehicleList retList = new VehicleList(type, mode);
//        new PlayerControllerSubthread(retList, new Player(login), new CountDownLatch(1)).run();
//        return retList;
//    }
}
