package dev.joell.kalaha.gamelogic;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class PlayerStateTests {
    @Test
    public void raisesWhenCupsLengthIsZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PlayerState(new Cell[0], new Cell(0, true), "Player A");
        });
    }

    @Test
    public void raisesWhenStoreIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PlayerState(new Cell[1], null, "Player A");
        });
    }

    @Test
    public void raisesWhenNameIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PlayerState(new Cell[1], new Cell(0, true), null);
        });
    }
}
