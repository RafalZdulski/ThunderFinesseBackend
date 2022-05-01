package com.thunderfinesse.thunderback.services;

import com.thunderfinesse.thunderback.controllers.PlayerControllerSubthread;
import com.thunderfinesse.thunderback.daos.ThunderfinessePlayerDao;
import com.thunderfinesse.thunderback.dtos.PlayerResponse;

import java.util.concurrent.CountDownLatch;

public class PlayerService {

    public PlayerResponse getPlayer(String login) {
        PlayerResponse player = new PlayerResponse(login);

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
}
