package dev.joell.kalaha.game;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.joell.kalaha.game.dto.*;

@RestController
@RequestMapping("/api/games")
public class GameController {
    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    @GetMapping
    public List<GameDto> getGames() {
        return this.service.findAll();
    }

    @GetMapping("/{id}")
    public GameDto getGame(@PathVariable int id) {
        return this.service.findById(id);
    }

    @GetMapping("/{id}/pretty")
    public String getGamePretty(@PathVariable int id) {
        return this.service.getPrettyFormattedForId(id);
    }

    @PostMapping
    public GameDto createGame(@Validated @RequestBody CreateGameDto game) {
        return this.service.create(game);
    }

    @PostMapping("/{id}/move/{cup}")
    public GameDto performMove(@PathVariable int id, @PathVariable int cup) {
        return this.service.performMove(id, cup);
    }
}
