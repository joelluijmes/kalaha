package dev.joell.kalaha.gamelogic;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameLogic {

    /**
     * Initialize the game with the given settings.
     */
    public GameState initializeGame(GameSettings settings) {
        if (settings == null) {
            throw new IllegalArgumentException("settings cannot be null");
        }

        // board is the amount of cups per player, plus the stores for each player
        // Creates a fixed size list
        Cell[] board = new Cell[settings.cupsPerPlayer() * 2 + 2];

        // The player stores index at the start of their respective board array.
        int idxPlayerAStore = 0;
        int idxPlayerBStore = settings.cupsPerPlayer() + 1;

        // Initialize the board state
        for (int i = 0; i < board.length; i++) {
            boolean isStore = i == idxPlayerAStore || i == idxPlayerBStore;
            int stones = isStore ? 0 : settings.stonesPerCup();
            board[i] = new Cell(stones, isStore);
        }

        // Slice the board array into the two players' distinct states. This enables
        // easier manipulation of the board state instead of accessing the array
        // directly (OOP).
        PlayerState playerA = new PlayerState(
                Arrays.copyOfRange(board, 1, settings.cupsPerPlayer() + 1),
                board[idxPlayerAStore],
                "Player A");

        // The board state is stored in a circular array, so to create the right
        // representation of the board for player B, we need to reverse the order of the
        // array.
        // TODO: seems bit complicated to reverse an array in Java?
        List<Cell> playerBCups = Arrays.asList(Arrays.copyOfRange(board, idxPlayerBStore + 1, board.length));
        Collections.reverse(playerBCups);
        PlayerState playerB = new PlayerState(
                playerBCups.toArray(new Cell[0]),
                board[idxPlayerBStore],
                "Player B");

        // TODO: initialize?
        PlayerState currentPlayer = playerA;

        return new GameState(board, playerA, playerB, currentPlayer);
    }

}