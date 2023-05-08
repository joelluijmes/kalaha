package dev.joell.kalaha.gamelogic;

public record GameState(
        Cell[] board,
        PlayerState playerA,
        PlayerState playerB,
        PlayerState currentPlayer) {

    public GameState {
        if (board == null) {
            throw new IllegalArgumentException("Board cannot be null.");
        }
        if (playerA == null) {
            throw new IllegalArgumentException("Player A cannot be null.");
        }
        if (playerB == null) {
            throw new IllegalArgumentException("Player B cannot be null.");
        }
        if (currentPlayer == null) {
            throw new IllegalArgumentException("Current player cannot be null.");
        }
        if (currentPlayer != playerA && currentPlayer != playerB) {
            throw new IllegalArgumentException("Current player must be either player A or player B.");
        }
    }

    /**
     * Check if the game is over. The game is over when either player has no stones
     * in any of their cups.
     */
    public boolean isGameOver() {
        return !playerA.hasStonesInAnyCup() || !playerB.hasStonesInAnyCup();
    }

    /**
     * Format the board as a pretty human readable string.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Current player: %s\n\n", currentPlayer.name()));

        int len = board.length / 2;

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
        for (Cell cell : playerA.cups()) {
            sb.append(String.format(" %2d  |", cell.numStones()));
        }
        sb.append("     |\n");

        // Draw the store value for player A
        sb.append(String.format("| %2d  ", playerA.store().numStones()));

        // Draw the separator line between the two cup rows
        for (int i = 0; i < len - 1; i++) {
            sb.append("+-----");
        }

        // Draw the store value for player B
        sb.append(String.format("+ %2d  ", playerB.store().numStones()));
        sb.append("+\n");

        // Draw the cup row for player B
        sb.append("|     ");
        for (Cell cell : playerB.cups()) {
            sb.append(String.format("| %2d  ", cell.numStones()));
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
