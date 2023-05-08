package dev.joell.kalaha.gamelogic;

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
}
