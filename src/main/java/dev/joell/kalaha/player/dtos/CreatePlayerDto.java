package dev.joell.kalaha.player.dtos;

import jakarta.validation.constraints.NotNull;

public record CreatePlayerDto(
    @NotNull(message = "Name may not be null")
    String name
) { }