package edu.iastate.cs228.hw1;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Nathan Krieger
 *	
 * This class represents a single cell within a Town object.
 * A TownCell can either be a RESELLER, EMPTY, CASUAL, OUTAGE, or a STREAMER.
 *
 */
public abstract class TownCell {

	protected Town plain;
	protected int row;
	
	protected int col;
	
	// constants to be used as indices.
	protected static final int RESELLER = 0;
	protected static final int EMPTY = 1;
	protected static final int CASUAL = 2;
	protected static final int OUTAGE = 3;
	protected static final int STREAMER = 4;
	
	public static final int NUM_CELL_TYPE = 5;
	
	//Use this static array to take census.
	public static final int[] nCensus = new int[NUM_CELL_TYPE];

	public TownCell(Town p, int r, int c) {
		plain = p;
		row = r;
		col = c;
	}
	
	/**
	 * Checks all neighborhood cell types in the neighborhood.
	 * Refer to homework pdf for neighbor definitions (all adjacent
	 * neighbors excluding the center cell).
	 * Use who() method to get who is present in the neighborhood
	 *  
	 * @param counts of all customers
	 */
	public void census(int nCensus[]) {
		// zero the counts of all customers
		nCensus[RESELLER] = 0; 
		nCensus[EMPTY] = 0; 
		nCensus[CASUAL] = 0; 
		nCensus[OUTAGE] = 0; 
		nCensus[STREAMER] = 0; 

	        int numRows = plain.getLength();
	        int numCols = plain.getWidth();

	        List<String> neighbors = new ArrayList<>();

	        for (int i = -1; i <= 1; i++) {
	            for (int j = -1; j <= 1; j++) {
	                int newRow = row + i;
	                int newCol = col + j;

	                // Exclude out-of-bounds cells and the given coordinate
	                if (newRow >= 0 && newRow < numRows && newCol >= 0 && newCol < numCols
	                        && !(i == 0 && j == 0)) {
	                	
	                    neighbors.add(plain.grid[newRow][newCol].who().toString());
	                }
	            }
	        }

			// Neighbors are now in a List
			// Example: [ S, S, C, O, R]
			for (int i = 0; i < neighbors.size(); i++) {

				if (neighbors.get(i).equals("RESELLER")) {
					nCensus[RESELLER]++;
				} else if (neighbors.get(i).equals("EMPTY")) {
					nCensus[EMPTY]++;
				} else if (neighbors.get(i).equals("CASUAL")) {
					nCensus[CASUAL]++;
				} else if (neighbors.get(i).equals("OUTAGE")) {
					nCensus[OUTAGE]++;
				} else if (neighbors.get(i).equals("STREAMER")) {
					nCensus[STREAMER]++;
				}
	        	
	        }

	}

	/**
	 * Gets the identity of the cell.
	 * 
	 * @return State
	 */
	public abstract State who();

	/**
	 * Determines the cell type in the next cycle.
	 * 
	 * @param tNew: town of the next cycle
	 * @return TownCell
	 */
	public abstract TownCell next(Town tNew);
}
