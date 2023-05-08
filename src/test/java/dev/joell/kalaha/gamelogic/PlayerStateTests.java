package dev.joell.kalaha.gamelogic;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class PlayerStateTests {
    @Test
    public void raisesWhenCupsLengthIsZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PlayerState(Arrays.asList(new Cell[0]), new Cell(0, true), "Player A");
        });
    }

    @Test
    public void raisesWhenStoreIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PlayerState(Arrays.asList(new Cell[1]), null, "Player A");
        });
    }

    @Test
    public void raisesWhenNameIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PlayerState(Arrays.asList(new Cell[1]), new Cell(0, true), null);
        });
    }

    @Test
    public void hasStonesInAnyCupWithStones() {
        PlayerState player = new PlayerState(
                List.of(new Cell(1, false)),
                new Cell(0, true),
                "Player A");

        assertTrue(player.hasStonesInAnyCup());
    }

    @Test
    public void hasStonesInAnyCupWithNoStones() {
        PlayerState player = new PlayerState(
                List.of(new Cell(0, false)),
                new Cell(0, true),
                "Player A");

        assertFalse(player.hasStonesInAnyCup());
    }

}
