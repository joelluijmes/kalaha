package dev.joell.kalaha.board;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class FormattingTests {
    @Test
    public void testAsciiFormatStringWith6CupsAnd4Stones() {
        Board board = new Board(6, 4);
        String expected = "   A     1     2     3     4     5     6     B  \n" +
                "+-----+-----+-----+-----+-----+-----+-----+-----+\n" +
                "|        4  |  4  |  4  |  4  |  4  |  4  |     |\n" +
                "|  0  +-----+-----+-----+-----+-----+-----+  0  +\n" +
                "|     |  4  |  4  |  4  |  4  |  4  |  4        |\n" +
                "+-----+-----+-----+-----+-----+-----+-----+-----+\n";

        assertEquals(expected, board.asciiFormatString());
    }

    @Test
    public void testAsciiFormatStringWith5CupsAnd5Stones() {
        Board board = new Board(5, 5);
        String expected = "   A     1     2     3     4     5     B  \n" +
                "+-----+-----+-----+-----+-----+-----+-----+\n" +
                "|        5  |  5  |  5  |  5  |  5  |     |\n" +
                "|  0  +-----+-----+-----+-----+-----+  0  +\n" +
                "|     |  5  |  5  |  5  |  5  |  5        |\n" +
                "+-----+-----+-----+-----+-----+-----+-----+\n";

        assertEquals(expected, board.asciiFormatString());
    }

    @Test
    public void testDebugBoardIndexStringWith6CupsAnd4Stones() {
        Board board = new Board(6, 4);
        String expected = "   A     1     2     3     4     5     6     B  \n" +
                "+-----+-----+-----+-----+-----+-----+-----+-----+\n" +
                "|        1  |  2  |  3  |  4  |  5  |  6  |     |\n" +
                "|  0  +-----+-----+-----+-----+-----+-----+  7  +\n" +
                "|     | 13  | 12  | 11  | 10  |  9  |  8        |\n" +
                "+-----+-----+-----+-----+-----+-----+-----+-----+\n";

        assertEquals(expected, board.debugBoardIndexString());
    }

    @Test
    public void testDebugBoardIndexStringWith5CupsAnd5Stones() {
        Board board = new Board(5, 5);
        String expected = "   A     1     2     3     4     5     B  \n" +
                "+-----+-----+-----+-----+-----+-----+-----+\n" +
                "|        1  |  2  |  3  |  4  |  5  |     |\n" +
                "|  0  +-----+-----+-----+-----+-----+  6  +\n" +
                "|     | 11  | 10  |  9  |  8  |  7        |\n" +
                "+-----+-----+-----+-----+-----+-----+-----+\n";

        assertEquals(expected, board.debugBoardIndexString());
    }
}
