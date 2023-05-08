package dev.joell.kalaha.gamelogic;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CellTests {
    @Test
    public void testInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Cell(-1, true);
        });
    }

    @Test
    public void testRemoveStones() {
        Cell cell = new Cell(5, false);
        cell.removeStones(3);
        assert cell.numStones() == 2;
    }

    @Test
    public void testRemoveStonesInvalid() {
        Cell cell = new Cell(5, false);
        assertThrows(IllegalArgumentException.class, () -> {
            cell.removeStones(6);
        });
    }

    @Test
    public void testAddStones() {
        Cell cell = new Cell(5, false);
        cell.addStones(3);
        assert cell.numStones() == 8;
    }
}
