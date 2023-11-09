package edu.iastate.cs228.hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Nathan Krieger
 *
 */
public class Town {

	private int length, width; // Row and column (first and second indices)

	public TownCell[][] grid;

	/**
	 * Constructor to be used when user wants to generate grid randomly, with the
	 * given seed. This constructor does not populate each cell of the grid (but
	 * should assign a 2D array to it).
	 * 
	 * @param length
	 * @param width
	 */
	public Town(int length, int width) {
		
		this.length = length;
		this.width = width;

		grid = new TownCell[length][width];
	}

	/**
	 * Constructor to be used when user wants to populate grid based on a file.
	 * Please see that it simple throws FileNotFoundException exception instead of
	 * catching it. Ensure that you close any resources (like file or scanner) which
	 * is opened in this function.
	 * 
	 * @param inputFileName
	 * @throws FileNotFoundException
	 */
	public Town(String inputFileName) throws FileNotFoundException {

		File file = new File(inputFileName);

        if (!file.exists()) {
            throw new FileNotFoundException();
        }

		Scanner scnr = new Scanner(file);

		int numRows = scnr.nextInt();
		length = numRows;
		int numCols = scnr.nextInt();
		width = numCols;
		
		// Initialize the grid with the determined dimensions
		grid = new TownCell[numRows][numCols]; 

		// Populate the grid with TownCell objects from the file
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				String cellType = scnr.next();
				switch (cellType) {
				case "R":
					grid[i][j] = new Reseller(this, i, j);
					break;
				case "E":
					grid[i][j] = new Empty(this, i, j);
					break;
				case "C":
					grid[i][j] = new Casual(this, i, j);
					break;
				case "O":
					grid[i][j] = new Outage(this, i, j);
					break;
				case "S":
					grid[i][j] = new Streamer(this, i, j);
					break;
				default:
					// This should not happen.
					break;
				}
			}
		}
		
		scnr.close();
	}

	/**
	 * Returns width of the grid.
	 * 
	 * @return grid width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Returns length of the grid.
	 * 
	 * @return grid length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Initialize the grid by randomly assigning cell with one of the following
	 * class object: Casual, Empty, Outage, Reseller OR Streamer
	 */
	public void randomInit(int seed) {

		Random rand = new Random(seed);

		for (int i = 0; i < getLength(); i++) {

			for (int j = 0; j < getWidth(); j++) {

				int randomNum = rand.nextInt(5); // Generates a random number between 0 and 4

				switch (randomNum) {

				case 0:
					grid[i][j] = new Reseller(this, i, j);
					break;
				case 1:
					grid[i][j] = new Empty(this, i, j);
					break;
				case 2:
					grid[i][j] = new Casual(this, i, j);
					break;
				case 3:
					grid[i][j] = new Outage(this, i, j);
					break;
				case 4:
					grid[i][j] = new Streamer(this, i, j);
					break;
				default:

					// (this should not happen)
					break;
				}
			}
		}
	}

	/**
	 * Output the town grid. For each square, output the first letter of the cell
	 * type. Each letter should be separated either by a single space or a tab. And
	 * each row should be in a new line. There should not be any extra line between
	 * the rows.
	 */
	@Override
	public String toString() {

		StringBuilder s = new StringBuilder();

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {

				State cellType = grid[i][j].who(); // Assuming getType() returns a String

				char c = cellType.toString().charAt(0);
				// Append the cell type to the string
				s.append(c);

				// Add a space or tab after the cell type
				if (j < grid[i].length - 1) {
					s.append("\t"); // You can change this to "\t" for a tab separator
				}
			}

			// Add a newline after each row
			if (i < grid.length - 1) {
				s.append("\n");
			}
		}

		// return s.toString();
		return s.toString();
	}
}
