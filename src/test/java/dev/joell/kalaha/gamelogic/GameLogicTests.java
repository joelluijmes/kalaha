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
}
