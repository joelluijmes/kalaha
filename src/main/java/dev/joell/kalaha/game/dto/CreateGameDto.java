package dev.joell.kalaha.game.dto;

import jakarta.validation.constraints.Min;

public record CreateGameDto(
        @Min(value = 4, message = "cupsPerPlayer must be at least 4") int cupsPerPlayer,
        @Min(value = 1, message = "stonesPerCup must be at least 1") int stonesPerCup) {

}