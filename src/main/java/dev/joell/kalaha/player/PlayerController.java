package dev.joell.kalaha.player;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.joell.kalaha.player.dtos.*;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService service;

    public PlayerController(PlayerService service) {
        this.service = service;
    }

    @GetMapping
    public List<PlayerDto> getPlayers() {
        return this.service.findAll();
    }

    @PostMapping
    public PlayerDto createPlayer(@Validated @RequestBody CreatePlayerDto player) {
        return this.service.create(player);
    }
}
