package dev.joell.kalaha.gamelogic;

public record GameSettings(int cupsPerPlayer, int stonesPerCup) {
    public static GameSettings DEFAULT = new GameSettings(6, 4);

    public GameSettings {
        if (cupsPerPlayer < 1) {
            throw new IllegalArgumentException("Invalid cups per player, must be greater than 0.");
        }
        if (stonesPerCup < 1) {
            throw new IllegalArgumentException("Invalid stones per cup, must be greater than 0.");
        }
    }
}
