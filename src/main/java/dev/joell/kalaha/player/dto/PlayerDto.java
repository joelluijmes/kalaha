package dev.joell.kalaha.player.dto;

import java.time.LocalDateTime;

public record PlayerDto(int id, String username, LocalDateTime createdAt) {
}