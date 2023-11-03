package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author Nathan Krieger
 *
 */
class TownCellTest {

    @Test
    void censusTest() {
    	
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
        
    	// The center cell with type Streamer
        TownCell cell = town.grid[1][1];
        
        cell.census(TownCell.nCensus);
        
        assertEquals(2, TownCell.nCensus[0]); // Reseller count
        assertEquals(3, TownCell.nCensus[1]); // Empty count
        assertEquals(2, TownCell.nCensus[2]); // Casual count
        assertEquals(1, TownCell.nCensus[3]); // Outage count
    }

}
