package dev.joell.kalaha.player;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.joell.kalaha.Auth.JwtUtils;
import dev.joell.kalaha.common.exceptions.ApiException;
import dev.joell.kalaha.player.dtos.*;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService service;

    @Autowired
    private JwtUtils jwtUtils;

    public PlayerController(PlayerService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get all players", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "List of all players")
    })
    public List<PlayerDto> getPlayers() {
        return this.service.findAll();
    }

    @PostMapping
    @Operation(summary = "Create a new player", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "The created player"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    public String createPlayer(@Validated @RequestBody CreatePlayerDto player) throws Exception {
        PlayerDto createdPlayer = this.service.create(player);
        return this.jwtUtils.createToken(createdPlayer);
    }
}
