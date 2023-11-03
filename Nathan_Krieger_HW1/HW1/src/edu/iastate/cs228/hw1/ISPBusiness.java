package edu.iastate.cs228.hw1;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Nathan Krieger
 *
 * The ISPBusiness class performs simulation over a grid 
 * plain with cells occupied by different TownCell types.
 *
 */
public class ISPBusiness {
	
	/**
	 * Returns a new Town object with updated grid value for next billing cycle.
	 * @param tOld: old/current Town object.
	 * @return: New town object.
	 */
	public static Town updatePlain(Town tOld) {

		Town tNew = new Town(tOld.getLength(), tOld.getWidth());

		for (int i = 0; i < tOld.getLength(); i++) {

			for (int j = 0; j < tOld.getWidth(); j++) {

				tNew.grid[i][j] = tOld.grid[i][j].next(tNew);

			}

		}

		return tNew;
	}
	
	/**
	 * Returns the profit for the current state in the town grid.
	 * @param town
	 * @return The amount of casual cells (or profit).
	 */
	public static int getProfit(Town town) {

		int casualCellCount = 0;

		for (int i = 0; i < town.getLength(); i++) {

			for (int j = 0; j < town.getWidth(); j++) {
				if (town.grid[i][j].who().toString().equals("CASUAL")) {
					casualCellCount++;
				}
			}
		}

		return casualCellCount;
	}
	
	
	/**
	 *  Main method. Interact with the user and ask if user wants to specify elements of grid
	 *  via an input file (option: 1) or wants to generate it randomly (option: 2).
	 *  
	 *  Depending on the user choice, create the Town object using respective constructor and
	 *  if user choice is to populate it randomly, then populate the grid here.
	 *  
	 *  Finally: For 12 billing cycle calculate the profit and update town object (for each cycle).
	 *  Print the final profit in terms of %. You should print the profit percentage
	 *  with two digits after the decimal point:  Example if profit is 35.5600004, your output
	 *  should be:
	 *
	 *	35.56%
	 *  
	 * Note that this method does not throw any exception, so you need to handle all the exceptions
	 * in it.
	 * 
	 * @param args
	 * 
	 */
	public static void main(String[] args) {

		Town t = null;

		Scanner scnr = new Scanner(System.in);

		System.out.println("How to populate grid (type 1 or 2): 1: from a file. 2: randomly\n" + "with seed");

		int inputChoice = scnr.nextInt();

		// File read
		if (inputChoice == 1) {

			System.out.println("Enter file path: ");

			String fileName = scnr.next();

			try { 

				t = new Town(fileName);

			} catch (FileNotFoundException e) {
				System.out.println("Error - File not found.");
				scnr.close();
				return;
			}

		} // Random seed
		else if (inputChoice == 2) {

			System.out.println("Provide rows, cols and seed integer separated by spaces: ");

			try {

				int rows = scnr.nextInt();
				int columns = scnr.nextInt();
				int randSeed = scnr.nextInt();

				t = new Town(rows, columns);
				t.randomInit(randSeed);

			} catch (Exception e) {
				System.out.println("Error.");
			}

		} else {
			System.out.println("Invalid input choice.");
			scnr.close();
			return;
		}

		scnr.close();

		int totalProfit = 0;

		for (int i = 0; i < 12; i++) {

			t = updatePlain(t);

			totalProfit += getProfit(t);
		}

		System.out.println(String.format("%.2f", totalProfit * 100.00 / (t.getLength() * t.getWidth() * 12)) + "%");

	}

}
