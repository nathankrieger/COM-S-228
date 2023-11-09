package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author Nathan Krieger
 *
 */
class OutageTest {

	@Test
    void OutageToStreamerTest() {

        Town testTown = new Town(2, 3);

        // initialize TownCells.
        testTown.grid[0][0] = new Casual(testTown, 0, 0);
        testTown.grid[0][1] = new Casual(testTown, 0, 1);
        testTown.grid[0][2] = new Casual(testTown, 0, 2);
        testTown.grid[1][0] = new Casual(testTown, 1, 0);
        testTown.grid[1][1] = new Outage(testTown, 1, 1);
        testTown.grid[1][2] = new Casual(testTown, 1, 2);

        // Town after initialization:
        // C C C
        // C O C

        TownCell nextCell = testTown.grid[1][1].next(testTown);

        // If the cell was an Outage, and there are 5 or more Casual neighbors,
        // it becomes a Streamer.
        assertEquals(State.STREAMER, nextCell.who());
    }

    @Test
    void OutageToEmptyTest() {

        Town testTown = new Town(2, 3);

        // initialize TownCells.
        testTown.grid[0][0] = new Casual(testTown, 0, 0);
        testTown.grid[0][1] = new Casual(testTown, 0, 1);
        testTown.grid[0][2] = new Casual(testTown, 0, 2);
        testTown.grid[1][0] = new Casual(testTown, 1, 0);
        testTown.grid[1][1] = new Outage(testTown, 1, 1);
        testTown.grid[1][2] = new Empty(testTown, 1, 2);

        // Town after initialization:
        // C C C
        // C O E

        TownCell nextCell = testTown.grid[1][1].next(testTown);

        // If the cell was an Outage, and there are less than 5 Casual neighbors,
        // it becomes Empty.
        assertEquals(State.EMPTY, nextCell.who());
    }

}
