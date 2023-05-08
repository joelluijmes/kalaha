package dev.joell.kalaha.game.dto;

public record GameDto(
        int id,
        int[] board,
        int idxPlayerAStore,
        int idxPlayerBStore,
        int cupsPerPlayer,
        int stonesPerCup,
        int idxCurrentPlayer) {
}
