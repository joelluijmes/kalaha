package dev.joell.kalaha.gamelogic;

/**
 * Represents the state of a player. The player has a number of cups and a
 * store. The passed in cells must refer to the same cells in the board array.
 */
public record PlayerState(Cell[] cups, Cell store, String name) {
    public PlayerState {
        if (cups.length < 1) {
            throw new IllegalArgumentException("Invalid number of cups, must be greater than 0.");
        }
        if (store == null) {
            throw new IllegalArgumentException("Store cannot be null.");
        }
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null.");
        }
    }

    /**
     * Check if the player has any stones in any of their cups.
     */
    public boolean hasStonesInAnyCup() {
        for (Cell cup : cups) {
            if (cup.numStones() > 0) {
                return true;
            }
        }

        return false;
    }
}
