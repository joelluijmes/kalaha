package dev.joell.kalaha.wip.game;

class Cell { 
    private final int idx;
    private final GameSettings settings;
    
    private int numStones;

    public Cell(GameSettings settings, int idx) {
        this.settings = settings;
        if (idx < 0) {
            throw new IllegalArgumentException("Invalid index, must be greater than 0.");
        }

        this.idx = idx;
        this.numStones = this.isCup() ? this.settings.stonesPerCup() : 0;
    }

    public boolean isPlayerAStore() {
        // Player A starts at 0, and its cups are 1 -> cupsPerPlayer.
        return idx == 0;
    }

    public boolean isPlayerBStore() {
        // Player B starts at cupsPerPlayer + 1, and its cups are cupsPerPlayer + 2 -> cupsPerPlayer * 2 + 1.
        return idx == this.settings.cupsPerPlayer() + 1;
    }

    public boolean isStore() {
        return isPlayerAStore() || isPlayerBStore();
    }

    public boolean isCup() {
        return !isStore();
    }

    public int getStones() {
        return this.numStones;
    }

    public void setStones(int numStones) {
        if (numStones < 0) {
            throw new IllegalArgumentException("Invalid number of stones, must be greater than 0.");
        }

        this.numStones = numStones;
    }
}