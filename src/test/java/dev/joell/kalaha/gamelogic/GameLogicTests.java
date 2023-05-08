package dev.joell.kalaha.gamelogic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class GameLogicTests {
    @Test
    public void initializeGameRaisesWhenSettingsIsNull() {
        GameLogic gameLogic = new GameLogic();

        assertThrows(IllegalArgumentException.class, () -> {
            gameLogic.initializeGame(null);
        });
    }

    @Test
    public void initializeGame() {
        GameLogic gameLogic = new GameLogic();
        GameSettings settings = new GameSettings(6, 4);
        GameState state = gameLogic.initializeGame(settings);

        assertEquals(14, state.board().length);
        assertEquals(6, state.playerA().cups().length);
        assertEquals(6, state.playerB().cups().length);
        assertEquals(0, state.playerA().store().numStones());
        assertEquals(0, state.playerB().store().numStones());
        assertEquals("Player A", state.playerA().name());
        assertEquals("Player B", state.playerB().name());
        assertEquals(state.playerA(), state.currentPlayer());

        for (int i = 0; i < 6; i++) {
            assertEquals(4, state.playerA().cups()[i].numStones());
            assertEquals(4, state.playerB().cups()[i].numStones());
        }
    }

    @Test
    public void moveRaisesWhenCupIsOutOfBounds() {
        GameLogic gameLogic = new GameLogic();
        GameSettings settings = new GameSettings(6, 4);
        GameState state = gameLogic.initializeGame(settings);

        Exception exc = assertThrows(IllegalArgumentException.class, () -> {
            gameLogic.move(state, 0);
        });
        assertEquals("Cup index must be between 1 and 6", exc.getMessage());

        exc = assertThrows(IllegalArgumentException.class, () -> {
            gameLogic.move(state, 7);
        });
        assertEquals("Cup index must be between 1 and 6", exc.getMessage());
    }

    @Test
    public void moveRaisesWhenGameIsOver() {
        GameLogic gameLogic = new GameLogic();

        // Game is over because there are no stones in any of the cups
        PlayerState player = new PlayerState(
                new Cell[] { new Cell(0, false) },
                new Cell(0, true),
                "Player X");

        GameState state = new GameState(new Cell[0], player, player, player);

        Exception exc = assertThrows(IllegalStateException.class, () -> {
            gameLogic.move(state, 1);
        });
        assertEquals("Game is over", exc.getMessage());
    }

    @Test
    public void moveRaisesWhenCupIsEmpty() {
        GameLogic gameLogic = new GameLogic();

        // Game is over because there are no stones in any of the cups
        PlayerState player = new PlayerState(
                new Cell[] { new Cell(0, false), new Cell(1, false) },
                new Cell(0, true),
                "Player X");

        GameState state = new GameState(new Cell[0], player, player, player);

        Exception exc = assertThrows(IllegalArgumentException.class, () -> {
            gameLogic.move(state, 1);
        });
        assertEquals("Cannot move from cup with no stones", exc.getMessage());
    }

    @Test
    public void movePlayerGetsAnotherTurnWhenEndsInOwnStore() {
        GameLogic gameLogic = new GameLogic();

        // NOTE: variable not used for assertions as not to be dependent of
        // the toString() implementation. Its just here for visualizing the board
        // state. Unfortunately, code formatter removes spaces if inserted as
        // comment.
        String boardLooksLike = "" +
                "A     1     B  " +
                "+-----+-----+-----+" +
                "|        1  |     |" +
                "|  0  +-----+  0  +" +
                "|     |  1        |" +
                "+-----+-----+-----+";

        GameSettings settings = new GameSettings(1, 1);
        GameState state = gameLogic.initializeGame(settings);

        state = gameLogic.move(state, 1);

        boardLooksLike = "" +
                "A     1     B  " +
                "+-----+-----+-----+" +
                "|        0  |     |" +
                "|  1  +-----+  0  +" +
                "|     |  1        |" +
                "+-----+-----+-----+";

        // Assertion we are testing in this test
        assertEquals(state.playerA(), state.currentPlayer());

        // Other assertions
        assertEquals(state.playerA().cups()[0].numStones(), 0);
        assertEquals(state.playerA().store().numStones(), 1);
    }

    @Test
    public void moveOpponentStoreIsSkipped() {
        GameLogic gameLogic = new GameLogic();

        GameSettings settings = new GameSettings(2, 1);
        GameState state = gameLogic.initializeGame(settings);
        state.playerA().cups()[0].addStones(3);

        // NOTE: variable not used for assertions as not to be dependent of
        // the toString() implementation. Its just here for visualizing the board
        // state. Unfortunately, code formatter removes spaces if inserted as
        // comment.
        String boardLooksLike = "" +
                "   A     1     2     B  " +
                "+-----+-----+-----+-----+" +
                "|        4  |  1  |     |" +
                "|  0  +-----+-----+  0  +" +
                "|     |  1  |  1        |" +
                "+-----+-----+-----+-----+";

        state = gameLogic.move(state, 1);

        boardLooksLike = "" +
                "   A     1     2     B  " +
                "+-----+-----+-----+-----+" +
                "|        0  |  2  |     |" +
                "|  1  +-----+-----+  0  +" +
                "|     |  2  |  2        |" +
                "+-----+-----+-----+-----+";

        // Assertion we are testing in this test: only own store modified
        assertEquals(state.playerB().store().numStones(), 0);
        assertEquals(state.playerA().store().numStones(), 1);
    }

    @Test
    public void moveStealsStoneWhenLandingOwnEmptyCup() {
        GameLogic gameLogic = new GameLogic();

        GameSettings settings = new GameSettings(2, 4);
        GameState state = gameLogic.initializeGame(settings);
        state.playerA().cups()[1].removeStones(4);

        // NOTE: variable not used for assertions as not to be dependent of
        // the toString() implementation. Its just here for visualizing the board
        // state. Unfortunately, code formatter removes spaces if inserted as
        // comment.
        String boardLooksLike = "" +
                "   A     1     2     B  " +
                "+-----+-----+-----+-----+" +
                "|        4  |  0  |     |" +
                "|  0  +-----+-----+  0  +" +
                "|     |  4  |  4        |" +
                "+-----+-----+-----+-----+";

        state = gameLogic.move(state, 1);

        boardLooksLike = "" +
                "   A     1     2     B  " +
                "+-----+-----+-----+-----+" +
                "|        0  |  0  |     |" +
                "|  7  +-----+-----+  0  +" +
                "|     |  5  |  0        |" +
                "+-----+-----+-----+-----+";

        // Assertion we are testing in this test
        // Player A steals the 4 stones from player B's cup
        assertEquals(state.playerA().cups()[1].numStones(), 0);
        assertEquals(state.playerB().cups()[1].numStones(), 0);
        assertEquals(state.playerA().store().numStones(), 7);
    }

    @Test
    public void moveSwitchesPlayerOnPlainMove() {
        GameLogic gameLogic = new GameLogic();

        GameSettings settings = new GameSettings(2, 2);
        GameState state = gameLogic.initializeGame(settings);

        // NOTE: variable not used for assertions as not to be dependent of
        // the toString() implementation. Its just here for visualizing the board
        // state. Unfortunately, code formatter removes spaces if inserted as
        // comment.
        String boardLooksLike = "" +
                "   A     1     2     B  " +
                "+-----+-----+-----+-----+" +
                "|        2  |  2  |     |" +
                "|  0  +-----+-----+  0  +" +
                "|     |  2  |  2        |" +
                "+-----+-----+-----+-----+";

        state = gameLogic.move(state, 1);
        System.out.println(state);

        boardLooksLike = "" +
                "   A     1     2     B  " +
                "+-----+-----+-----+-----+" +
                "|        0  |  2  |     |" +
                "|  1  +-----+-----+  0  +" +
                "|     |  3  |  2        |" +
                "+-----+-----+-----+-----+";

        // Assertion we are testing in this test
        assertEquals(state.currentPlayer(), state.playerB());
        // Other assertions
        assertEquals(state.playerA().cups()[0].numStones(), 0);
        assertEquals(state.playerA().cups()[1].numStones(), 2);
        assertEquals(state.playerA().store().numStones(), 1);
        assertEquals(state.playerB().cups()[0].numStones(), 3);
        assertEquals(state.playerB().cups()[1].numStones(), 2);
        assertEquals(state.playerB().store().numStones(), 0);

    }
}
