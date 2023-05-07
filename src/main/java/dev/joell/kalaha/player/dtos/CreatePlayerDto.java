package dev.joell.kalaha.player.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreatePlayerDto(
    @NotBlank(message = "name is missing")
    String name
) { }