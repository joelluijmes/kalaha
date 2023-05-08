package dev.joell.kalaha.board;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class MakeMoveTests {

    @ParameterizedTest
    @ValueSource(ints = { 0, 7, 100 })
    public void raisesOnInvalidCup(int cup) {
        Board board = new Board(6, 4);

        Exception exc = assertThrows(IllegalArgumentException.class, () -> {
            board.makeMove(cup);
        });
        assertEquals("Invalid cup index, must be between 1 and 6 inclusive.", exc.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 7, 100 })
    public void raisesOnEmptyCup(int cup) {
        Board board = new Board(
                new int[] { 0, 0, 0, 0, 0, 0, 0, 0 },
                0,
                7,
                6,
                4,
                0);

        Exception exc = assertThrows(IllegalArgumentException.class, () -> {
            board.makeMove(1);
        });
        assertEquals("Invalid cup index, cup is empty.", exc.getMessage());
    }

    @Test
    public void exampleMove() {
        Board board = new Board(6, 4);
        board.makeMove(1);

        // Visual representation of the board state
        String expected = "" +
                "   A     1     2     3     4     5     6     B  \n" +
                "+-----+-----+-----+-----+-----+-----+-----+-----+\n" +
                "|        0  |  4  |  4  |  4  |  4  |  4  |     |\n" +
                "|  1  +-----+-----+-----+-----+-----+-----+  0  +\n" +
                "|     |  5  |  5  |  5  |  4  |  4  |  4        |\n" +
                "+-----+-----+-----+-----+-----+-----+-----+-----+\n";

        assertEquals(expected, board.asciiFormatString());

        // Asserting the internal state
        assertEquals(Arrays.toString(new int[] { 1, 0, 4, 4, 4, 4, 4, 0, 4, 4, 4, 5, 5, 5 }),
                Arrays.toString(board.getBoard()));
        assertEquals("B", board.currentPlayer());
    }

    @Test
    public void playerKeepsTurnOnEndingOwnStore() {
        Board board = new Board(
                new int[] { 1, 0, 4, 4, 4, 4, 4, 0, 4, 4, 4, 5, 5, 5 },
                0,
                7,
                6,
                4,
                7);

        // Assert the current state
        String expected = "" +
                "   A     1     2     3     4     5     6     B  \n" +
                "+-----+-----+-----+-----+-----+-----+-----+-----+\n" +
                "|        0  |  4  |  4  |  4  |  4  |  4  |     |\n" +
                "|  1  +-----+-----+-----+-----+-----+-----+  0  +\n" +
                "|     |  5  |  5  |  5  |  4  |  4  |  4        |\n" +
                "+-----+-----+-----+-----+-----+-----+-----+-----+\n";

        assertEquals(expected, board.asciiFormatString());
        assertEquals("B", board.currentPlayer());

        // Player B makes a move
        board.makeMove(2);

        // Assert the updated state
        expected = "" +
                "   A     1     2     3     4     5     6     B  \n" +
                "+-----+-----+-----+-----+-----+-----+-----+-----+\n" +
                "|        0  |  4  |  4  |  4  |  4  |  4  |     |\n" +
                "|  1  +-----+-----+-----+-----+-----+-----+  1  +\n" +
                "|     |  5  |  0  |  6  |  5  |  5  |  5        |\n" +
                "+-----+-----+-----+-----+-----+-----+-----+-----+\n";

        assertEquals(expected, board.asciiFormatString());

        assertEquals(Arrays.toString(new int[] { 1, 0, 4, 4, 4, 4, 4, 1, 5, 5, 5, 6, 0, 5 }),
                Arrays.toString(board.getBoard()));
        // Still player B's turn
        assertEquals("B", board.currentPlayer());
    }

    @Test
    public void playerStealsOpponentStones() {
        Board board = new Board(
                new int[] { 1, 0, 4, 4, 4, 4, 4, 1, 5, 5, 5, 6, 0, 5 },
                0,
                7,
                6,
                4,
                0);

        // Assert the current state
        String expected = "" +
                "   A     1     2     3     4     5     6     B  \n" +
                "+-----+-----+-----+-----+-----+-----+-----+-----+\n" +
                "|        0  |  4  |  4  |  4  |  4  |  4  |     |\n" +
                "|  1  +-----+-----+-----+-----+-----+-----+  1  +\n" +
                "|     |  5  |  0  |  6  |  5  |  5  |  5        |\n" +
                "+-----+-----+-----+-----+-----+-----+-----+-----+\n";

        assertEquals(expected, board.asciiFormatString());
        assertEquals("A", board.currentPlayer());

        // Player A makes a move which ends in an empty cup
        board.makeMove(5);

        // Assert the updated state
        expected = "" +
                "   A     1     2     3     4     5     6     B  \n" +
                "+-----+-----+-----+-----+-----+-----+-----+-----+\n" +
                "|        0  |  5  |  5  |  5  |  0  |  4  |     |\n" +
                "|  7  +-----+-----+-----+-----+-----+-----+  1  +\n" +
                "|     |  0  |  0  |  6  |  5  |  5  |  5        |\n" +
                "+-----+-----+-----+-----+-----+-----+-----+-----+\n";

        assertEquals(expected, board.asciiFormatString());

        assertEquals(Arrays.toString(new int[] { 7, 0, 5, 5, 5, 0, 4, 1, 5, 5, 5, 6, 0, 0 }),
                Arrays.toString(board.getBoard()));
        // Still player B's turn
        assertEquals("B", board.currentPlayer());
    }

    @Test
    public void opponentStoreIsSkipped() {
        // Invalid board state, but allows to easily assert the opponent store is
        // skipped
        Board board = new Board(
                new int[] { 1, 25, 4, 4, 4, 4, 4, 1, 5, 5, 5, 6, 0, 5 },
                0,
                7,
                6,
                4,
                0);

        // Assert the current state
        String expected = "" +
                "   A     1     2     3     4     5     6     B  \n" +
                "+-----+-----+-----+-----+-----+-----+-----+-----+\n" +
                "|       25  |  4  |  4  |  4  |  4  |  4  |     |\n" +
                "|  1  +-----+-----+-----+-----+-----+-----+  1  +\n" +
                "|     |  5  |  0  |  6  |  5  |  5  |  5        |\n" +
                "+-----+-----+-----+-----+-----+-----+-----+-----+\n";

        assertEquals(expected, board.asciiFormatString());
        assertEquals("A", board.currentPlayer());

        // Player A makes a move which ends in an empty cup
        board.makeMove(1);

        // Assert the updated state
        expected = "" +
                "   A     1     2     3     4     5     6     B  \n" +
                "+-----+-----+-----+-----+-----+-----+-----+-----+\n" +
                "|        1  |  6  |  6  |  6  |  6  |  6  |     |\n" +
                "|  3  +-----+-----+-----+-----+-----+-----+  1  +\n" +
                "|     |  7  |  2  |  8  |  7  |  7  |  7        |\n" +
                "+-----+-----+-----+-----+-----+-----+-----+-----+\n";

        assertEquals(expected, board.asciiFormatString());

        assertEquals(Arrays.toString(new int[] { 3, 1, 6, 6, 6, 6, 6, 1, 7, 7, 7, 8, 2, 7 }),
                Arrays.toString(board.getBoard()));
        // Still player B's turn
        assertEquals("B", board.currentPlayer());
    }
}
