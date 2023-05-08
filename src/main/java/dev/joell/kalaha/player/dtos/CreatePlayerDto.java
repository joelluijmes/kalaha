package dev.joell.kalaha.player.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePlayerDto(
        @NotBlank(message = "name is missing") String username,
        // TODO: add proper password constraint
        @NotBlank(message = "password is missing") @Size(min = 6, message = "password must be at least 6 characters") String password) {
}