package dev.joell.kalaha.gamelogic;

record Cell(int numStones, boolean isStore) {
    public Cell {
        if (numStones < 0) {
            throw new IllegalArgumentException("Invalid number of stones, must be greater than 0.");
        }
    }
}
