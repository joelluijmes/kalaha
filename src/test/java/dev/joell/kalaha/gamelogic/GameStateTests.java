package dev.joell.kalaha.gamelogic;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class GameStateTests {

    @Test
    public void raisesWhenBoardIsNull() {
        PlayerState playerA = new PlayerState(new Cell[1], new Cell(0, true), "Player A");
        PlayerState playerB = new PlayerState(new Cell[1], new Cell(0, true), "Player B");

        assertThrows(IllegalArgumentException.class, () -> {
            new GameState(null, playerA, playerB, playerA);
        });
    }

    @Test
    public void raisesWhenPlayerAIsNull() {
        Cell[] board = new Cell[14];
        PlayerState playerB = new PlayerState(new Cell[1], new Cell(0, true), "Player B");

        assertThrows(IllegalArgumentException.class, () -> {
            new GameState(board, null, playerB, playerB);
        });
    }

    @Test
    public void raisesWhenPlayerBIsNull() {
        Cell[] board = new Cell[14];
        PlayerState playerA = new PlayerState(new Cell[1], new Cell(0, true), "Player A");

        assertThrows(IllegalArgumentException.class, () -> {
            new GameState(board, playerA, null, playerA);
        });
    }

    @Test
    public void raisesWhenCurrentPlayerIsNull() {
        Cell[] board = new Cell[14];
        PlayerState playerA = new PlayerState(new Cell[1], new Cell(0, true), "Player A");
        PlayerState playerB = new PlayerState(new Cell[1], new Cell(0, true), "Player B");

        assertThrows(IllegalArgumentException.class, () -> {
            new GameState(board, playerA, playerB, null);
        });
    }

    @Test
    public void raisesWhenCurrentPlayerIsNotPlayerAOrPlayerB() {
        Cell[] board = new Cell[14];
        PlayerState playerA = new PlayerState(new Cell[1], new Cell(0, true), "Player A");
        PlayerState playerB = new PlayerState(new Cell[1], new Cell(0, true), "Player B");
        PlayerState currentPlayer = new PlayerState(new Cell[1], new Cell(0, true), "Current Player");

        assertThrows(IllegalArgumentException.class, () -> {
            new GameState(board, playerA, playerB, currentPlayer);
        });
    }
}
