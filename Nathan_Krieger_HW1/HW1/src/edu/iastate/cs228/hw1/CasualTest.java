package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author Nathan Krieger
 *
 */
class CasualTest {

	@Test
	void CasualToOutageTest() {
		
		Town testTown = new Town(2,3);
		
		// initialize TownCells.
		testTown.grid[0][0] = new Casual(testTown, 0, 0);
		testTown.grid[1][0] = new Casual(testTown, 1, 0);
		testTown.grid[0][1] = new Casual(testTown, 0, 1);
		testTown.grid[1][1] = new Reseller(testTown, 1, 1);
		testTown.grid[0][2] = new Empty(testTown, 0, 2);
		testTown.grid[1][2] = new Outage(testTown, 1, 2);

		/* Town after initialization:
		 * 
			 C C E
			 C R O
			 
		 */
		
		//If there is any reseller in its neighborhood, then the reseller causes outage in the
		//casual user cell. Thus, the state of the cell changes from C (Casual) to O
		//(Outage).
		TownCell nextCell = testTown.grid[0][1].next(testTown);
		
		assertEquals(State.OUTAGE, nextCell.who());
		
	}
	
	@Test
	void CasualToStreamerTest() {
		
		Town testTown = new Town(2,3);
		
		// initialize TownCells.
		testTown.grid[0][0] = new Streamer(testTown, 0, 0);
		testTown.grid[1][0] = new Casual(testTown, 1, 0);
		testTown.grid[0][1] = new Casual(testTown, 0, 1);
		testTown.grid[1][1] = new Casual(testTown, 1, 1);
		testTown.grid[0][2] = new Empty(testTown, 0, 2);
		testTown.grid[1][2] = new Outage(testTown, 1, 2);
		
		/* Town after initialization:
		 
		 S C E
		 C C O
		 
		 */
		
		TownCell nextCell1 = testTown.grid[0][1].next(testTown);
		TownCell nextCell2 = testTown.grid[1][1].next(testTown);
		
		// Otherwise, if there is any neighbor who is a streamer, then the casual user also
		// becomes a streamer, in the hopes of making it big on the internet.
		assertEquals(State.STREAMER, nextCell1.who());
		assertEquals(State.STREAMER, nextCell2.who());
		
	}
	
	@Test 
	void CasualToResellerTest() {
		
		Town testTown = new Town(2,3);
		
		// initialize TownCells.
		testTown.grid[0][0] = new Streamer(testTown, 0, 0);
		testTown.grid[1][0] = new Casual(testTown, 1, 0);
		testTown.grid[0][1] = new Casual(testTown, 0, 1);
		testTown.grid[1][1] = new Casual(testTown, 1, 1);
		testTown.grid[0][2] = new Empty(testTown, 0, 2);
		testTown.grid[1][2] = new Outage(testTown, 1, 2);
		
		/* Town after initialization:
		 
		 S C E
		 C C O
		 
		 */
		
		TownCell nextCell = testTown.grid[1][0].next(testTown);
		
		// Any cell that (1) is not a Reseller or Outage and (2) and has (Number of Empty +
		// Number of Outage neighbors less than or equal to 1) converts to Reseller.
		assertEquals(State.RESELLER, nextCell.who());
		
	}
	
}
