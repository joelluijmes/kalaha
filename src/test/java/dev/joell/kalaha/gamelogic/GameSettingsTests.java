package dev.joell.kalaha.gamelogic;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class GameSettingsTests {
    @Test
    public void testInvalidCupsPerPlayer() {
        assertThrows(IllegalArgumentException.class, () -> {
            new GameSettings(-1, 4);
        });
    }

    @Test
    public void testInvalidStonesPerCup() {
        assertThrows(IllegalArgumentException.class, () -> {
            new GameSettings(6, -1);
        });
    }
}
