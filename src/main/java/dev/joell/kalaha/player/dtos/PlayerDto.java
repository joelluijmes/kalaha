package dev.joell.kalaha.player.dtos;

import java.time.LocalDateTime;

public record PlayerDto(int id, String username, LocalDateTime createdAt) {
}