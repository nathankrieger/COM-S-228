package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author Nathan Krieger
 *
 */
class StreamerTest {

	@Test
    void StreamerToResellerTest() {

        Town testTown = new Town(1, 3);

        // initialize TownCells.
        testTown.grid[0][0] = new Casual(testTown, 0, 0);
        testTown.grid[0][1] = new Casual(testTown, 0, 1);
        testTown.grid[0][2] = new Streamer(testTown, 0, 2);

        // Town after initialization:
        // C C S

        TownCell nextCell = testTown.grid[0][2].next(testTown);

        // If the cell was a Streamer and has 1 or fewer Empty/Outage neighbors,
        // it becomes a Reseller.
        assertEquals(State.RESELLER, nextCell.who());
    }

    @Test
    void StreamerToOutageTest() {

        Town testTown = new Town(2, 3);

        // initialize TownCells.
        testTown.grid[0][0] = new Casual(testTown, 0, 0);
        testTown.grid[0][1] = new Reseller(testTown, 0, 1);
        testTown.grid[0][2] = new Casual(testTown, 0, 2);
        testTown.grid[1][0] = new Empty(testTown, 1, 0);
        testTown.grid[1][1] = new Streamer(testTown, 1, 1);
        testTown.grid[1][2] = new Outage(testTown, 1, 2);

        // Town after initialization:
        // C R C
        // E S O

        TownCell nextCell = testTown.grid[1][1].next(testTown);

        // If the cell was a Streamer and there is at least one Reseller neighbor,
        // it becomes an Outage.
        assertEquals(State.OUTAGE, nextCell.who());
    }

    @Test
    void StreamerToEmptyTest() {

        Town testTown = new Town(1, 3);

        // initialize TownCells.
        testTown.grid[0][0] = new Outage(testTown, 0, 0);
        testTown.grid[0][1] = new Streamer(testTown, 0, 1);
        testTown.grid[0][2] = new Outage(testTown, 0, 2);

        // Town after initialization:
        // O S O

        TownCell nextCell = testTown.grid[0][1].next(testTown);

        // If the cell was a Streamer and there is at least one Outage neighbor,
        // it becomes Empty.
        assertEquals(State.EMPTY, nextCell.who());
    }

    @Test
    void StreamerToStreamerTest() {

        Town testTown = new Town(2, 3);

        // initialize TownCells.
        testTown.grid[0][0] = new Casual(testTown, 0, 0);
        testTown.grid[0][1] = new Casual(testTown, 0, 1);
        testTown.grid[0][2] = new Casual(testTown, 0, 2);
        testTown.grid[1][0] = new Empty(testTown, 1, 0);
        testTown.grid[1][1] = new Streamer(testTown, 1, 1);
        testTown.grid[1][2] = new Empty(testTown, 1, 2);

        // Town after initialization:
        // C C C
        // E S E

        TownCell nextCell = testTown.grid[1][1].next(testTown);

        // If the cell was a Streamer and none of the conditions for Reseller, Outage, or Empty
        // are met, it remains a Streamer.
        assertEquals(State.STREAMER, nextCell.who());
    }

}
