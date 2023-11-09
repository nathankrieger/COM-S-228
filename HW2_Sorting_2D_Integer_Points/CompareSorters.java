package edu.iastate.cs228.hw2;

/**
 *  
 * @author Nathan Krieger
 *
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;

public class CompareSorters {
	/**
	 * Repeatedly take integer sequences either randomly generated or read from
	 * files. Use them as coordinates to construct points. Scan these points with
	 * respect to their median coordinate point four times, each time using a
	 * different sorting algorithm.
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner scanner = new Scanner(System.in);
		Random rand = new Random();
		int trialCounter = 1;

		while (true) {

			System.out.println("Performances of Four Sorting Algorithms in Point Scanning");
			System.out.println("keys: 1 (random integers) 2 (file input) 3 (exit)");
			System.out.print("Trial " + trialCounter + ": ");

			int choice = scanner.nextInt();

			if (choice == 1 || choice == 2) {
				int numPoints;
				Point[] points = null;
				PointScanner[] scanners = new PointScanner[4];

				// Random points
				if (choice == 1) {
					System.out.print("Enter number of random points: ");
					numPoints = scanner.nextInt();
					points = generateRandomPoints(numPoints, rand);

					for (int i = 0; i < 4; i++) {
						Algorithm algo = Algorithm.values()[i];
						scanners[i] = new PointScanner(points, algo);
						scanners[i].scan();
					}
					
				// File input
				} else {
					System.out.println("Points from a file");
					System.out.print("File name: ");
					String fileName = scanner.next();

					for (int i = 0; i < 4; i++) {
						Algorithm algo = Algorithm.values()[i];
						scanners[i] = new PointScanner(fileName, algo);
						scanners[i].scan();
					}
				}

				System.out.println();
				System.out.printf("%-16s%-8s%-8s%n", "algorithm", "size", "time (ns)");
				System.out.println("----------------------------------");

				for (PointScanner scanner1 : scanners) {
					System.out.println(scanner1.stats());
				}

				System.out.println("----------------------------------");

				trialCounter++;

			// Invalid
			} else if (choice == 3) {
				break;
			} else {
				System.out.println("Invalid choice. Please try again.");
			}
		}

		scanner.close();

	}

	/**
	 * This method generates a given number of random points. The coordinates of
	 * these points are pseudo-random numbers within the range [-50,50] � [-50,50].
	 * Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing.
	 * 
	 * @param numPts - number of points
	 * @param rand - Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException {

		if (numPts < 1) {
			throw new IllegalArgumentException("Number of points must be at least 1.");
		}

		Point[] points = new Point[numPts];
		for (int i = 0; i < numPts; i++) {
			points[i] = new Point(rand.nextInt(101) - 50, rand.nextInt(101) - 50);
		}

		return points;
	}

}
