package dev.joell.kalaha.game;

import java.util.Arrays;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entity for the game. Contains the board state and the game settings.
 * Updated by the `GameService`.
 */
@Entity
@Table(name = "games")
public class GameEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;

    /**
     * Array containing the board state. Tracks the amount of stones per location
     * (including stores).
     */
    @Column(nullable = false)
    private int[] board;

    /** Index of the player A store in the board. */
    private int idxPlayerAStore;

    /** Index of the player B store in the board. */
    private int idxPlayerBStore;

    /** The amount of cups per player. */
    private int cupsPerPlayer;

    /** The amount of stones per cup. */
    private int stonesPerCup;

    /** The index of the current player. */
    private int idxCurrentPlayer;

    public GameEntity() {

    }

    public GameEntity(int id,
            int[] board,
            int idxPlayerAStore,
            int idxPlayerBStore,
            int cupsPerPlayer,
            int stonesPerCup,
            int idxCurrentPlayer) {
        this.id = id;
        this.board = board;
        this.idxPlayerAStore = idxPlayerAStore;
        this.idxPlayerBStore = idxPlayerBStore;
        this.cupsPerPlayer = cupsPerPlayer;
        this.stonesPerCup = stonesPerCup;
        this.idxCurrentPlayer = idxCurrentPlayer;
    }

    public int getId() {
        return id;
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

    public void setIdxCurrentPlayer(int idxCurrentPlayer) {
        this.idxCurrentPlayer = idxCurrentPlayer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBoard(int[] board) {
        this.board = Arrays.copyOf(board, board.length);
    }

    public void setIdxPlayerAStore(int idxPlayerAStore) {
        this.idxPlayerAStore = idxPlayerAStore;
    }

    public void setIdxPlayerBStore(int idxPlayerBStore) {
        this.idxPlayerBStore = idxPlayerBStore;
    }

    public void setCupsPerPlayer(int cupsPerPlayer) {
        this.cupsPerPlayer = cupsPerPlayer;
    }

    public void setStonesPerCup(int stonesPerCup) {
        this.stonesPerCup = stonesPerCup;
    }

    public int getIdxCurrentPlayer() {
        return idxCurrentPlayer;
    }

}
