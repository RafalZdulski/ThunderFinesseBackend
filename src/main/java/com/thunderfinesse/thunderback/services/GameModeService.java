package com.thunderfinesse.thunderback.services;

import com.thunderfinesse.thunderback.dtos.graphs.GameModeDetailResponse;
import com.thunderfinesse.thunderback.dtos.graphs.GameModeOverallResponse;
import com.thunderfinesse.thunderback.dtos.PlayerResponse;

import java.util.LinkedList;
import java.util.List;

public class GameModeService {


    public GameModeDetailResponse[] getGameModeDetailData(PlayerResponse player) {
        List<GameModeDetailResponse> ret = new LinkedList<>();
        ret.add(new GameModeDetailResponse(player.getAir_ab()));
        ret.add(new GameModeDetailResponse(player.getGround_ab()));
        ret.add(new GameModeDetailResponse(player.getAir_rb()));
        ret.add(new GameModeDetailResponse(player.getGround_rb()));
        ret.add(new GameModeDetailResponse(player.getAir_sb()));
        ret.add(new GameModeDetailResponse(player.getGround_sb()));
        return ret.toArray(new GameModeDetailResponse[0]);
    }

    public GameModeOverallResponse[] getGameModeOverallData(PlayerResponse player) {
        List<GameModeOverallResponse> ret = new LinkedList<>();
        ret.add(new GameModeOverallResponse(player.getAir_ab()));
        ret.add(new GameModeOverallResponse(player.getGround_ab()));
        ret.add(new GameModeOverallResponse(player.getAir_rb()));
        ret.add(new GameModeOverallResponse(player.getGround_rb()));
        ret.add(new GameModeOverallResponse(player.getAir_sb()));
        ret.add(new GameModeOverallResponse(player.getGround_sb()));
        return ret.toArray(new GameModeOverallResponse[0]);
    }

}
