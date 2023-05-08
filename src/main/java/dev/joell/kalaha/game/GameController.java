package dev.joell.kalaha.game;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.joell.kalaha.common.exceptions.ApiException;
import dev.joell.kalaha.game.dto.*;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/games")
public class GameController {
    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get all games", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "List of all games")
    })
    public List<GameDto> getGames() {
        return this.service.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a game by id", description = "Returns the game state as json, for pretty formatted output see /{id}/pretty", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "The game with the given id"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Game with the given id not found")
    })
    public GameDto getGame(@PathVariable int id) throws ApiException {
        return this.service.findById(id);
    }

    @GetMapping("/{id}/pretty")
    @Operation(summary = "Get a game by id", description = "Returns the game state as a pretty formatted string, for JSON see /{id}", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "The game with the given id"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Game with the given id not found")
    })
    public String getGamePretty(@PathVariable int id) throws ApiException {
        return this.service.getPrettyFormattedForId(id);
    }

    @PostMapping
    @Operation(summary = "Create a new game", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "The created game"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    public GameDto createGame(@Validated @RequestBody CreateGameDto game) throws ApiException {
        return this.service.create(game);
    }

    @PostMapping("/{id}/move/{cup}")
    @Operation(summary = "Perform a move", description = "Performs a move for the given game id and cup", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "The updated game"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Game with the given id not found")
    })
    public GameDto performMove(@PathVariable int id, @PathVariable int cup) throws ApiException {
        return this.service.performMove(id, cup);
    }
}
