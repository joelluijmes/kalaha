package dev.joell.kalaha.board;

import dev.joell.kalaha.common.mapstruct.Default;

public class Board {
    /**
     * Array containing the board state. Tracks the amount of stones per location
     * (including stores).
     */
    private final int[] board;

    /** Index of the player A store in the board. */
    private final int idxPlayerAStore;

    /** Index of the player B store in the board. */
    private final int idxPlayerBStore;

    /** The amount of cups per player. */
    private final int cupsPerPlayer;

    /** The amount of stones per cup. */
    private final int stonesPerCup;

    /** The index of the current player. */
    private int idxCurrentPlayer;

    public Board(int cupsPerPlayer, int stonesPerCup) {
        this.cupsPerPlayer = cupsPerPlayer;
        this.stonesPerCup = stonesPerCup;

        // The player stores index at the start of their respective board array.
        // Player A starts at 0, and its cups are 1 -> cupsPerPlayer.
        // Player B starts at cupsPerPlayer + 1, and its cups are cupsPerPlayer + 2 ->
        // cupsPerPlayer * 2 + 1.
        this.idxPlayerAStore = 0;
        this.idxPlayerBStore = this.cupsPerPlayer + 1;

        // board is the amount of cups per player, plus the stores for each player
        this.board = new int[this.cupsPerPlayer * 2 + 2];
        for (int i = 0; i < this.board.length; i++) {
            if (i != this.idxPlayerAStore && i != this.idxPlayerBStore) {
                this.board[i] = this.stonesPerCup;
            }
        }

        // TODO: initialize?
        this.idxCurrentPlayer = this.idxPlayerAStore;
    }

    @Default
    public Board(int[] board, int idxPlayerAStore, int idxPlayerBStore, int cupsPerPlayer, int stonesPerCup,
            int idxCurrentPlayer) {
        this.board = board;
        this.idxPlayerAStore = idxPlayerAStore;
        this.idxPlayerBStore = idxPlayerBStore;
        this.cupsPerPlayer = cupsPerPlayer;
        this.stonesPerCup = stonesPerCup;
        this.idxCurrentPlayer = idxCurrentPlayer;
    }

    public void makeMove(int cup) {
        // TODO: make custom exceptions such they can be caught and handled by the
        // controller
        if (cup < 1 || cup > this.cupsPerPlayer) {
            throw new IllegalArgumentException(
                    "Invalid cup index, must be between 1 and " + this.cupsPerPlayer + " inclusive.");
        }

        // Calculate the index of the cup in the board array. As we store player B's
        // cups in reverse order, when B
        // picks a cup, we actually need to subtract the cup index from the total amount
        // of cups per player.
        int idx = this.idxCurrentPlayer == this.idxPlayerAStore
                ? cup
                : this.board.length - cup;

        int numStones = this.board[idx];
        if (numStones == 0) {
            throw new IllegalArgumentException("Invalid cup index, cup is empty.");
        }

        // "pick up" the stones from the cup
        System.out.println(String.format("Player %s picking %s stones at cup %s (index %s)",
                this.idxCurrentPlayer == this.idxPlayerAStore ? "A" : "B", numStones, cup, idx));
        this.board[idx] = 0;
        while (numStones > 0) {
            // Rules state counter clockwise direction, so we decrement the index
            idx--;
            // Wrap around to the start of player B cup
            if (idx < 0) {
                idx = this.board.length - 1;
            }

            // Skip the opponent's store
            if (idx == this.idxPlayerBStore && this.idxCurrentPlayer == this.idxPlayerAStore) {
                idx--;
            } else if (idx == this.idxPlayerAStore && this.idxCurrentPlayer == this.idxPlayerBStore) {
                idx--;
            }

            // Place a stone in the cup (or current player's store)
            System.out.println(String.format("Placing stone at index %s", idx));
            this.board[idx]++;
            numStones--;
        }

        if ((idx == this.idxPlayerAStore && this.idxCurrentPlayer == this.idxPlayerAStore) ||
                (idx == this.idxPlayerBStore && this.idxCurrentPlayer == this.idxPlayerBStore)) {
            // Player turn ends in their store, so they get to go again
            System.out.println("Player gets to go again!");
            return;
        }

        if (this.board[idx] == 1 && idx != this.idxPlayerAStore && idx != this.idxPlayerBStore) {
            System.out.println("Player steals stones!");
            // Player turn ends in an empty cup on their side, so they get to take the
            // stones from the opposite cup
            int oppositeIdx = this.board.length - idx;
            int oppositeStones = this.board[oppositeIdx];
            System.out.println(String.format("Opposite index is %s, stones %s", oppositeIdx, oppositeStones));

            this.board[this.idxCurrentPlayer] += oppositeStones + 1;
            this.board[oppositeIdx] = 0;
            this.board[idx] = 0;
            System.out.println(this.idxCurrentPlayer);
        }

        // Switch players
        System.out.println("Switching players!");
        this.idxCurrentPlayer = this.idxCurrentPlayer == this.idxPlayerAStore ? this.idxPlayerBStore
                : this.idxPlayerAStore;
    }

    /**
     * Format the board as a pretty human readable string.
     */
    public String asciiFormatString() {
        StringBuilder sb = new StringBuilder();

        int len = this.board.length / 2;

        // Draw the index row, which indicates the index of each cup for player A
        sb.append("   A  ");
        for (int i = 1; i < len; i++) {
            sb.append(String.format("   %s  ", i));
        }
        sb.append("   B  \n");

        // Draw separator line
        for (int i = 0; i <= len; i++) {
            sb.append("+-----");
        }
        sb.append("+\n");

        // Draw the cup row for player A
        sb.append("|      ");
        for (int i = 1; i < len; i++) {
            sb.append(String.format(" %2d  |", this.board[i]));
        }
        sb.append("     |\n");

        // Draw the store value for player A
        sb.append(String.format("| %2d  ", this.board[this.idxPlayerAStore]));

        // Draw the separator line between the two cup rows
        for (int i = 0; i < len - 1; i++) {
            sb.append("+-----");
        }

        // Draw the store value for player B
        sb.append(String.format("+ %2d  ", this.board[this.idxPlayerBStore]));
        sb.append("+\n");

        // Draw the cup row for player B
        sb.append("|     ");
        for (int i = this.board.length - 1; i > this.idxPlayerBStore; i--) {
            sb.append(String.format("| %2d  ", this.board[i]));
        }
        sb.append("      |\n");

        // Draw the separator line
        for (int i = 0; i < len + 1; i++) {
            sb.append("+-----");
        }
        sb.append("+\n");

        return sb.toString();
    }

    /**
     * Print the indexes of the board for debugging purposes.
     */
    public String debugBoardIndexString() {
        StringBuilder sb = new StringBuilder();

        int len = this.board.length / 2;

        // Draw the index row, which indicates the index of each cup for player A
        sb.append("   A  ");
        for (int i = 1; i < len; i++) {
            sb.append(String.format("   %s  ", i));
        }
        sb.append("   B  \n");

        // Draw separator line
        for (int i = 0; i <= len; i++) {
            sb.append("+-----");
        }
        sb.append("+\n");

        // Draw the cup row for player A
        sb.append("|      ");
        for (int i = 1; i < len; i++) {
            sb.append(String.format(" %2d  |", i));
        }
        sb.append("     |\n");

        // Draw the store value for player A
        sb.append(String.format("| %2d  ", this.idxPlayerAStore));

        // Draw the separator line between the two cup rows
        for (int i = 0; i < len - 1; i++) {
            sb.append("+-----");
        }

        // Draw the store value for player B
        sb.append(String.format("+ %2d  ", this.idxPlayerBStore));
        sb.append("+\n");

        // Draw the cup row for player B
        sb.append("|     ");
        for (int i = this.board.length - 1; i > this.idxPlayerBStore; i--) {
            sb.append(String.format("| %2d  ", i));
        }
        sb.append("      |\n");

        // Draw the separator line
        for (int i = 0; i < len + 1; i++) {
            sb.append("+-----");
        }
        sb.append("+\n");

        return sb.toString();
    }

    public int[] getBoard() {
        return board;
    }

    public int getIdxPlayerAStore() {
        return idxPlayerAStore;
    }

    public int getIdxPlayerBStore() {
        return idxPlayerBStore;
    }

    public int getCupsPerPlayer() {
        return cupsPerPlayer;
    }

    public int getStonesPerCup() {
        return stonesPerCup;
    }

    public int getIdxCurrentPlayer() {
        return idxCurrentPlayer;
    }

}
