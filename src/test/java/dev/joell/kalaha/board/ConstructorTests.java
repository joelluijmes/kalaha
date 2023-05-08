package dev.joell.kalaha.board;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ConstructorTests {
    @Test
    public void BoardRaisesWhenInvalidCups() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Board(0, 4);
        });
    }

    @Test
    public void BoardRaisesWhenInvalidStones() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Board(4, 0);
        });
    }
}
