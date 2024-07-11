package com.evaza.etwitch.external;

import com.evaza.etwitch.external.model.Game;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GameController {
    private final ETwitchService etwitchService;

    public GameController(ETwitchService etwitchService) {
        this.etwitchService = etwitchService;
    }

    @GetMapping("/game")
    public List<Game> getGames(@RequestParam(value = "game_name", required = false) String gameName) {
        if (gameName == null) {
            return etwitchService.getTopGames();
        } else {
            return etwitchService.getGames(gameName);
        }
    }
}
