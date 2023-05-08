package dev.joell.kalaha.gamelogic;

class Cell {
    private final boolean isStore;
    private int numStones;

    public Cell(int initialStones, boolean isStore) {
        if (initialStones < 0) {
            throw new IllegalArgumentException("Invalid number of stones, must be greater than 0.");
        }

        this.numStones = initialStones;
        this.isStore = isStore;
    }

    public int numStones() {
        return numStones;
    }

    public boolean isStore() {
        return isStore;
    }

    public void addStones(int stones) {
        numStones += stones;
    }

    public void removeStones(int stones) {
        if (stones > numStones) {
            throw new IllegalArgumentException("Cannot remove more stones than there are in the cell.");
        }

        numStones -= stones;
    }
}
