package dev.joell.kalaha.gamelogic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class GameStateTests {

    @Test
    public void raisesWhenBoardIsNull() {
        PlayerState playerA = new PlayerState(Arrays.asList(new Cell[1]), new Cell(0, true), "Player A");
        PlayerState playerB = new PlayerState(Arrays.asList(new Cell[1]), new Cell(0, true), "Player B");

        assertThrows(IllegalArgumentException.class, () -> {
            new GameState(null, playerA, playerB, playerA);
        });
    }

    @Test
    public void raisesWhenPlayerAIsNull() {
        List<Cell> board = new ArrayList<Cell>(14);
        PlayerState playerB = new PlayerState(Arrays.asList(new Cell[1]), new Cell(0, true), "Player B");

        assertThrows(IllegalArgumentException.class, () -> {
            new GameState(board, null, playerB, playerB);
        });
    }

    @Test
    public void raisesWhenPlayerBIsNull() {
        List<Cell> board = new ArrayList<Cell>(14);
        PlayerState playerA = new PlayerState(Arrays.asList(new Cell[1]), new Cell(0, true), "Player A");

        assertThrows(IllegalArgumentException.class, () -> {
            new GameState(board, playerA, null, playerA);
        });
    }

    @Test
    public void raisesWhenCurrentPlayerIsNull() {
        List<Cell> board = new ArrayList<Cell>(14);
        PlayerState playerA = new PlayerState(Arrays.asList(new Cell[1]), new Cell(0, true), "Player A");
        PlayerState playerB = new PlayerState(Arrays.asList(new Cell[1]), new Cell(0, true), "Player B");

        assertThrows(IllegalArgumentException.class, () -> {
            new GameState(board, playerA, playerB, null);
        });
    }

    @Test
    public void raisesWhenCurrentPlayerIsNotPlayerAOrPlayerB() {
        List<Cell> board = new ArrayList<Cell>(14);
        PlayerState playerA = new PlayerState(Arrays.asList(new Cell[1]), new Cell(0, true), "Player A");
        PlayerState playerB = new PlayerState(Arrays.asList(new Cell[1]), new Cell(0, true), "Player B");
        PlayerState currentPlayer = new PlayerState(Arrays.asList(new Cell[1]), new Cell(0, true),
                "Current Player");

        assertThrows(IllegalArgumentException.class, () -> {
            new GameState(board, playerA, playerB, currentPlayer);
        });
    }

    @Test
    public void formatToString() {
        // Random fake initialization of the game state. This is just to test the
        // toString() method.
        // Testing initialization of the game state is done in GameLogicTests.
        List<Cell> board = new ArrayList<Cell>();
        for (int i = 0; i < 14; i++) {
            board.add(new Cell(4, false));
        }

        List<Cell> playerACups = new ArrayList<Cell>();
        for (int i = 0; i < 6; i++) {
            playerACups.add(new Cell(i + 1, false));
        }
        PlayerState playerA = new PlayerState(playerACups, new Cell(6, true), "Player A");

        List<Cell> playerBCups = new ArrayList<Cell>();
        for (int i = 0; i < 6; i++) {
            playerBCups.add(new Cell(6 - i, false));
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
        List<Cell> board = new ArrayList<Cell>(1);
        PlayerState playerA = new PlayerState(Arrays.asList(new Cell[] {
                new Cell(0, false),
        }), new Cell(0, true), "Player A");

        PlayerState playerB = new PlayerState(Arrays.asList(new Cell[] {
                new Cell(1, false),
        }), new Cell(0, true), "Player A");

        GameState game = new GameState(board, playerA, playerB, playerA);

        assertTrue(game.isGameOver());
    }

    @Test
    public void isGameOverWhenPlayerBHasNoStones() {
        List<Cell> board = new ArrayList<Cell>(1);
        PlayerState playerA = new PlayerState(Arrays.asList(new Cell[] {
                new Cell(1, false),
        }), new Cell(0, true), "Player A");

        PlayerState playerB = new PlayerState(Arrays.asList(new Cell[] {
                new Cell(0, false),
        }), new Cell(0, true), "Player A");

        GameState game = new GameState(board, playerA, playerB, playerA);

        assertTrue(game.isGameOver());
    }

    @Test
    public void isNotGameOverWhenPlayersHasNoStones() {
        List<Cell> board = new ArrayList<Cell>(1);
        PlayerState playerA = new PlayerState(Arrays.asList(new Cell[] {
                new Cell(1, false),
        }), new Cell(0, true), "Player A");

        PlayerState playerB = new PlayerState(Arrays.asList(new Cell[] {
                new Cell(1, false),
        }), new Cell(0, true), "Player A");

        GameState game = new GameState(board, playerA, playerB, playerA);

        assertFalse(game.isGameOver());
    }
}
