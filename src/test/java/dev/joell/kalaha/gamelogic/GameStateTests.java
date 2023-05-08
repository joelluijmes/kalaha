package dev.joell.kalaha.gamelogic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    public void formatToString() {
        // Random fake initialization of the game state. This is just to test the
        // toString() method.
        // Testing initialization of the game state is done in GameLogicTests.
        Cell[] board = new Cell[14];
        for (int i = 0; i < board.length; i++) {
            board[i] = new Cell(4, false);
        }

        Cell[] playerACups = new Cell[6];
        for (int i = 0; i < playerACups.length; i++) {
            playerACups[i] = new Cell(i + 1, false);
        }
        PlayerState playerA = new PlayerState(playerACups, new Cell(6, true), "Player A");

        Cell[] playerBCups = new Cell[6];
        for (int i = 0; i < playerBCups.length; i++) {
            playerBCups[i] = new Cell(6 - i, false);
        }
        PlayerState playerB = new PlayerState(playerBCups, new Cell(9, true), "Player B");

        GameState game = new GameState(board, playerA, playerB, playerA);

        String expected = "" +
                "Current player: Player A\n" +
                "\n" +
                "   A     1     2     3     4     5     6     B  \n" +
                "+-----+-----+-----+-----+-----+-----+-----+-----+\n" +
                "|        1  |  2  |  3  |  4  |  5  |  6  |     |\n" +
                "|  6  +-----+-----+-----+-----+-----+-----+  9  +\n" +
                "|     |  6  |  5  |  4  |  3  |  2  |  1        |\n" +
                "+-----+-----+-----+-----+-----+-----+-----+-----+\n";

        assertEquals(expected, game.toString());
    }

    @Test
    public void isGameOverWhenPlayerAHasNoStones() {
        Cell[] board = new Cell[1];
        PlayerState playerA = new PlayerState(new Cell[] {
                new Cell(0, false),
        }, new Cell(0, true), "Player A");

        PlayerState playerB = new PlayerState(new Cell[] {
                new Cell(1, false),
        }, new Cell(0, true), "Player A");

        GameState game = new GameState(board, playerA, playerB, playerA);

        assertTrue(game.isGameOver());
    }

    @Test
    public void isGameOverWhenPlayerBHasNoStones() {
        Cell[] board = new Cell[1];
        PlayerState playerA = new PlayerState(new Cell[] {
                new Cell(1, false),
        }, new Cell(0, true), "Player A");

        PlayerState playerB = new PlayerState(new Cell[] {
                new Cell(0, false),
        }, new Cell(0, true), "Player A");

        GameState game = new GameState(board, playerA, playerB, playerA);

        assertTrue(game.isGameOver());
    }

    @Test
    public void isNotGameOverWhenPlayersHasNoStones() {
        Cell[] board = new Cell[1];
        PlayerState playerA = new PlayerState(new Cell[] {
                new Cell(1, false),
        }, new Cell(0, true), "Player A");

        PlayerState playerB = new PlayerState(new Cell[] {
                new Cell(1, false),
        }, new Cell(0, true), "Player A");

        GameState game = new GameState(board, playerA, playerB, playerA);

        assertFalse(game.isGameOver());
    }
}
