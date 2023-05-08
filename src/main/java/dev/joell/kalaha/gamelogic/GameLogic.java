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

    public GameState move(GameState state, int cup) {
        // TODO: does GameState as immutable make sense? Tracking current player is bit
        // awkward.

        if (cup < 1 || cup > state.currentPlayer().cups().length) {
            throw new IllegalArgumentException(
                    "Cup index must be between 1 and " + state.currentPlayer().cups().length);
        }

        if (state.isGameOver()) {
            throw new IllegalStateException("Game is over");
        }

        // Player cup arrays are 0-indexed, but the cup index is 1-indexed
        // Alternative: use the idxBoard, but this illustrates the semantics better
        int numStones = state.currentPlayer().cups()[cup - 1].numStones();
        if (numStones == 0) {
            throw new IllegalArgumentException("Cannot move from cup with no stones");
        }

        // Calculate the index of the cup in the board array. The board array acts
        // like a circular array such that we can easily iterate over the board.
        // However, the cup is given from the perspective of the current player.
        // This means, when the current player is player B, the cup is reversed.
        int idxBoard = state.currentPlayer() == state.playerA() ? cup : state.board().length - cup;

        // Pickup the stones from the current cup.
        // As long we have stones, keep iterating and dropping in the following cups
        // (except the opponent's store).
        Cell currentCell = state.board()[idxBoard];
        currentCell.removeStones(numStones);
        while (numStones > 0) {
            // Rules state we work counter-clockwise, so we need to decrement the index
            --idxBoard;

            // Wrap around to the end of the board
            if (idxBoard < 0) {
                idxBoard = state.board().length - 1;
            }

            // Skip the opponent's store
            if (state.isIndexAtOpponentStore(idxBoard)) {
                continue;
            }

            // Drop a stone in the cup
            currentCell = state.board()[idxBoard];
            currentCell.addStones(1);
            --numStones;
        }

        // Check if the last stone was dropped in the current player's store
        if (currentCell == state.currentPlayer().store()) {
            // If so, the current player gets another turn
            return state;
        }

        // Check if the last stone was dropped in an empty cup. This is checked
        // after the last stone drop, so we actually check for 1.
        if (currentCell.numStones() == 1) {
            // If so, the current player gets to capture the stones in the opposite cup
            // and add it to the store.

            // Calculate the index of the opposite cup (the board is circular)
            int idxOppositeCup = state.board().length - idxBoard;
            Cell oppositeCup = state.board()[idxOppositeCup];

            // Clear the opposite cup and add the stones to the current player's store
            int numOppositeStones = oppositeCup.numStones();
            oppositeCup.removeStones(numOppositeStones);
            currentCell.removeStones(1); // Don't forget to remove the stone that was just dropped

            state.currentPlayer().store().addStones(numOppositeStones + 1);
        }

        // Switch the current player
        PlayerState nextPlayer = state.currentPlayer() == state.playerA() ? state.playerB() : state.playerA();
        return new GameState(state.board(), state.playerA(), state.playerB(), nextPlayer);
    }
}