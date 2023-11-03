package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author Nathan Krieger
 *
 */
class ResellerTest {

	@Test
    void ResellerToEmptyTest() {

        Town testTown = new Town(1, 3);

        // initialize TownCells.
        testTown.grid[0][0] = new Casual(testTown, 0, 0);
        testTown.grid[0][1] = new Casual(testTown, 0, 1);
        testTown.grid[0][2] = new Reseller(testTown, 0, 2);

        // Town after initialization:
        // C C R

        TownCell nextCell = testTown.grid[0][2].next(testTown);

        // If the cell was a Reseller and has 3 or fewer Casual neighbors, or
        // has 3 or more Empty neighbors, it becomes Empty.
        assertEquals(State.EMPTY, nextCell.who());
    }

    @Test
    void ResellerToStreamerTest() {

        Town testTown = new Town(2, 3);

        // initialize TownCells.
        testTown.grid[0][0] = new Casual(testTown, 0, 0);
        testTown.grid[0][1] = new Casual(testTown, 0, 1);
        testTown.grid[0][2] = new Casual(testTown, 0, 2);
        testTown.grid[1][0] = new Casual(testTown, 1, 0);
        testTown.grid[1][1] = new Reseller(testTown, 1, 1);
        testTown.grid[1][2] = new Casual(testTown, 1, 2);

        // Town after initialization:
        // C C C
        // C R C

        TownCell nextCell = testTown.grid[1][1].next(testTown);

        // If the cell was a Reseller and has 5 or more Casual neighbors, it becomes Streamer.
        assertEquals(State.STREAMER, nextCell.who());
    }

    @Test
    void ResellerToResellerTest() {

        Town testTown = new Town(2, 3);

        // initialize TownCells.
        testTown.grid[0][0] = new Empty(testTown, 0, 0);
        testTown.grid[0][1] = new Casual(testTown, 0, 1);
        testTown.grid[0][2] = new Casual(testTown, 0, 2);
        testTown.grid[1][0] = new Casual(testTown, 1, 0);
        testTown.grid[1][1] = new Reseller(testTown, 1, 1);
        testTown.grid[1][2] = new Casual(testTown, 1, 2);

        // Town after initialization:
        // E C C
        // C R C

        TownCell nextCell = testTown.grid[1][1].next(testTown);

        // If the cell was a Reseller and does not meet the conditions for Empty or Streamer,
        // it remains a Reseller.
        assertEquals(State.RESELLER, nextCell.who());
    }

}
