package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author Nathan Krieger
 *
 */
class EmptyTest {

	@Test
	void EmptyToCasualTest() {
		
		Town testTown = new Town(1,3);
		
		// initialize TownCells.
		testTown.grid[0][0] = new Outage(testTown, 0, 0);
		testTown.grid[0][1] = new Casual(testTown, 0, 1);
		testTown.grid[0][2] = new Empty(testTown, 0, 2);
		
		/* Town after initialization:
		 
		 	O C E
		 
		 */
		
		TownCell nextCell = testTown.grid[0][1].next(testTown);
		
		// If the cell was empty, then a Casual user takes it and it becomes a C.
		assertEquals(State.CASUAL, nextCell.who());
		
	}
	
	@Test
	void EmptyToResellerTest() {
		
		Town testTown = new Town(1,3);
		
		// initialize TownCells.
		testTown.grid[0][0] = new Casual(testTown, 0, 0);
		testTown.grid[0][1] = new Casual(testTown, 0, 1);
		testTown.grid[0][2] = new Casual(testTown, 0, 2);
		
		/* Town after initialization:
		 
		 	C C C
		 
		 */
		
		TownCell nextCell = testTown.grid[0][1].next(testTown);
		
		// Any cell that (1) is not a Reseller or Outage and (2) and has (Number of Empty +
		// Number of Outage neighbors less than or equal to 1) converts to Reseller.
		assertEquals(State.RESELLER, nextCell.who());
		
	}

}
