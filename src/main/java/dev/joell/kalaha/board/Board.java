package dev.joell.kalaha.board;

public class Board {
    /** Array containing the board state. Tracks the amount of stones per location (including stores). */
    private final int[] board;

    /** Index of the player 1 store in the board. */
    private final int idxPlayerAStore;

    /** Index of the player 2 store in the board. */
    private final int idxPlayerBStore;

    /** The amount of cups per player. */
    private final int cupsPerPlayer;

    /** The amount of stones per cup. */
    private final int stonesPerCup;

    public Board(int cupsPerPlayer, int stonesPerCup) {
        this.cupsPerPlayer = cupsPerPlayer;
        this.stonesPerCup = stonesPerCup;

        // The player stores index at the start of their respective board array. 
        // Player A starts at 0, and its cups are 1 -> cupsPerPlayer.
        // Player B starts at cupsPerPlayer + 1, and its cups are cupsPerPlayer + 2 -> cupsPerPlayer * 2 + 1.
        this.idxPlayerAStore = 0;
        this.idxPlayerBStore = this.cupsPerPlayer + 1;

        // board is the amount of cups per player, plus the stores for each player
        this.board = new int[this.cupsPerPlayer * 2 + 2];
        for (int i = 0; i < this.board.length; i++) {
            if (i != this.idxPlayerAStore && i != this.idxPlayerBStore) {
                this.board[i] = this.stonesPerCup;
            }
        }
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
        for (int i = this.idxPlayerBStore + 1; i < this.board.length; i++) {
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
        for (int i = this.idxPlayerBStore + 1; i < this.board.length; i++) {
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
}
