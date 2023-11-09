package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author Nathan Krieger
 *
 */
class ISPBusinessTest {

	@Test
	void updatePlainTest() {

		Town town = new Town(4, 4);

		town.randomInit(10);

		Town updatedTown = ISPBusiness.updatePlain(town);

		assertEquals(State.EMPTY, updatedTown.grid[0][0].who());
		assertEquals(State.EMPTY, updatedTown.grid[0][1].who());
		assertEquals(State.EMPTY, updatedTown.grid[0][2].who());
		assertEquals(State.EMPTY, updatedTown.grid[0][3].who());

		assertEquals(State.CASUAL, updatedTown.grid[1][0].who());
		assertEquals(State.CASUAL, updatedTown.grid[1][1].who());
		assertEquals(State.OUTAGE, updatedTown.grid[1][2].who());
		assertEquals(State.EMPTY, updatedTown.grid[1][3].who());

		assertEquals(State.CASUAL, updatedTown.grid[2][0].who());
		assertEquals(State.OUTAGE, updatedTown.grid[2][1].who());
		assertEquals(State.EMPTY, updatedTown.grid[2][2].who());
		assertEquals(State.OUTAGE, updatedTown.grid[2][3].who());

		assertEquals(State.CASUAL, updatedTown.grid[3][0].who());
		assertEquals(State.EMPTY, updatedTown.grid[3][1].who());
		assertEquals(State.EMPTY, updatedTown.grid[3][2].who());
		assertEquals(State.EMPTY, updatedTown.grid[3][3].who());

	}

	@Test
	void getProfitTest() {

		Town town = new Town(3, 3);

		town.grid[0][0] = new Reseller(town, 0, 0);
		town.grid[0][1] = new Empty(town, 0, 1);
		town.grid[0][2] = new Casual(town, 0, 2);
		town.grid[1][0] = new Outage(town, 1, 0);
		town.grid[1][1] = new Streamer(town, 1, 1);
		town.grid[1][2] = new Casual(town, 1, 2);
		town.grid[2][0] = new Reseller(town, 2, 0);
		town.grid[2][1] = new Empty(town, 2, 1);
		town.grid[2][2] = new Empty(town, 2, 2);

		int profit = ISPBusiness.getProfit(town);

		assertEquals(2, profit);

	}

}
