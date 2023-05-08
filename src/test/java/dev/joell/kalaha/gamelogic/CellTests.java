package dev.joell.kalaha.gamelogic;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CellTests {
    @Test
    public void testInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Cell(-1, true);
        });
    }
}
