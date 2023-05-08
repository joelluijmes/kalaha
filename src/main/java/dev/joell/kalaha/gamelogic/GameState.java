package dev.joell.kalaha.gamelogic;

public record GameState(
        Cell[] board,
        PlayerState playerA,
        PlayerState playerB,
        PlayerState currentPlayer) {

    public GameState {
        if (board == null) {
            throw new IllegalArgumentException("Board cannot be null.");
        }
        if (playerA == null) {
            throw new IllegalArgumentException("Player A cannot be null.");
        }
        if (playerB == null) {
            throw new IllegalArgumentException("Player B cannot be null.");
        }
        if (currentPlayer == null) {
            throw new IllegalArgumentException("Current player cannot be null.");
        }
        if (currentPlayer != playerA && currentPlayer != playerB) {
            throw new IllegalArgumentException("Current player must be either player A or player B.");
        }
    }

}
