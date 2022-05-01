package com.thunderfinesse.thunderback.controllers;

import com.thunderfinesse.thunderback.dtos.graphs.GameModeDetailResponse;
import com.thunderfinesse.thunderback.dtos.PlayerResponse;
import com.thunderfinesse.thunderback.dtos.graphs.GameModeOverallResponse;
import com.thunderfinesse.thunderback.services.GameModeService;
import com.thunderfinesse.thunderback.services.PlayerService;
import com.thunderfinesse.thunderback.subprograms.executors.ThunderSkillPlayerExecutor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/player")
@CrossOrigin(origins = "http://localhost:4200")
public class PlayerController {

    @GetMapping("/{login}")
    public PlayerResponse getPlayerStats(@PathVariable("login") String login) {
        PlayerService playerService = new PlayerService();
        return playerService.getPlayer(login);
    }

    @PostMapping()
    public boolean updatePlayer(@RequestBody String login){
        new ThunderSkillPlayerExecutor().executeWithUpdate(login);
        return true;
    }

    @GetMapping("/{login}/graphs")
    public GameModeDetailResponse[] getPlayerModeDetail(@PathVariable("login") String login){
        PlayerService playerService = new PlayerService();
        PlayerResponse player = playerService.getPlayer(login);
        GameModeService gameModeService = new GameModeService();
        return gameModeService.getGameModeDetailData(player);
    }

    @GetMapping("/{login}/overall")
    public GameModeOverallResponse[] getPlayerModeOverall(@PathVariable("login") String login){
        PlayerService playerService = new PlayerService();
        PlayerResponse player = playerService.getPlayer(login);
        GameModeService gameModeService = new GameModeService();
        return gameModeService.getGameModeOverallData(player);
    }

//TODO (consider) is better to share whole Player class at /{login}/player or to share each VehicleList at /{login}/{modeStr}/{typeStr}

//    @GetMapping("/{login}/{modeStr}/{typeStr}") //shouldn't it return Player?
//    public VehicleList getPlayerStats(@PathVariable("login") String login, @PathVariable("modeStr") String modeStr, @PathVariable("typeStr") String typeStr) {
//        Mode mode = Mode.valOf(modeStr);
//        VehicleType type = VehicleType.valOf(typeStr);
//        VehicleList vehicleList = getList(login, mode, type);
//        return vehicleList;
//    }





//    private VehicleList getList(String login, Mode mode, VehicleType type) {
//        VehicleList retList = new VehicleList(type, mode);
//        new PlayerControllerSubthread(retList, new Player(login), new CountDownLatch(1)).run();
//        return retList;
//    }
}
