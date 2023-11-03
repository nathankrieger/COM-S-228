package edu.iastate.cs228.hw2;

/**
 * 
 * @author Nathan Krieger
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 
 * This class sorts all the points in an array of 2D points to determine a
 * reference point whose x and y coordinates are respectively the medians of the
 * x and y coordinates of the original points.
 * 
 * It records the employed sorting algorithm as well as the sorting time for
 * comparison.
 *
 */
public class PointScanner {

	/**
	 * Array of 2D coordinate points.
	 */
	private Point[] points;

	/**
	 * point whose x and y coordinates are respectively the medians of
	 * the x coordinates and y coordinates of those points in the array points[]
	 * 
	 */
	private Point medianCoordinatePoint;
	
	/**
	 * The sorting algorithm
	 */
	private Algorithm sortingAlgorithm;

	/**
	 *  execution time in nanoseconds.
	 */
	protected long scanTime; 

	/**
	 * This constructor accepts an array of points and one of the four sorting
	 * algorithms as input. Copy the points into the array points[].
	 * 
	 * @param pts  - input array of points
	 * @param algo - the sorting algorithm
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException {

		if (pts == null || pts.length == 0) {
			throw new IllegalArgumentException("Input array is null or empty.");
		}

		sortingAlgorithm = algo;
		points = new Point[pts.length];

		for (int i = 0; i < pts.length; i++) {
			points[i] = new Point(pts[i]);
		}
	}

	/**
	 * This constructor reads points from a file.
	 * 
	 * @param inputFileName
	 * @param algo - the sorting algorithm
	 * 
	 * @throws FileNotFoundException
	 * @throws InputMismatchException if the input file contains an odd number of
	 *  integers
	 * 
	 */
	protected PointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException {

		File file = new File(inputFileName);
		// A scanner that is measuring the amount of points in the file.
		Scanner lengthScanner = new Scanner(file);

		// A scanner that is used after the amount of points are counted.
		Scanner pointScanner = new Scanner(file);

		int numsInFileCount = 0;
		while (lengthScanner.hasNextInt()) {
			lengthScanner.nextInt();
			numsInFileCount++;
		}
		// Need two numbers to make a 2D point.
		if (numsInFileCount % 2 != 0) {
			lengthScanner.close();
			pointScanner.close();
			throw new InputMismatchException("The count is odd.");
		}
		points = new Point[numsInFileCount / 2];
		for (int i = 0; i < points.length; i++) {
			int x = pointScanner.nextInt();
			int y = pointScanner.nextInt();
			points[i] = new Point(x, y);
		}

		lengthScanner.close();
		pointScanner.close();

		// Initialize sortingAgorithm.
		sortingAlgorithm = algo;
	}

	/**
	 * Carry out two rounds of sorting using the algorithm designated by
	 * sortingAlgorithm as follows:
	 * 
	 * a) Sort points[] by the x-coordinate to get the median x-coordinate. b) Sort
	 * points[] again by the y-coordinate to get the median y-coordinate. c)
	 * Construct medianCoordinatePoint using the obtained median x- and
	 * y-coordinates.
	 * 
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter,
	 * InsertionSorter, MergeSorter, or QuickSorter to carry out sorting.
	 * 
	 */
	public void scan() {

		AbstractSorter aSorter;

		if (sortingAlgorithm == Algorithm.SelectionSort) {
			aSorter = new SelectionSorter(points);
		} else if (sortingAlgorithm == Algorithm.InsertionSort) {
			aSorter = new InsertionSorter(points);
		} else if (sortingAlgorithm == Algorithm.MergeSort) {
			aSorter = new MergeSorter(points);
		} else if (sortingAlgorithm == Algorithm.QuickSort) {
			aSorter = new QuickSorter(points);
		} else {
			throw new IllegalArgumentException("Invalid sorting algorithm.");
		}

		aSorter.setComparator(0);

		// Start x timer
		long xStartTime = System.nanoTime();
		aSorter.sort();
		// End x timer
		long xSortTime = System.nanoTime() - xStartTime;

		Point xMedian = aSorter.getMedian();
		int x = xMedian.getX();

		aSorter.setComparator(1);

		// Start y timer
		long yStartTime = System.nanoTime();

		aSorter.sort();
		// End y timer
		long ySortTime = System.nanoTime() - yStartTime;

		Point yMedian = aSorter.getMedian();
		int y = yMedian.getY();

		medianCoordinatePoint = new Point(x, y);

		scanTime = xSortTime + ySortTime;

	}

	/**
	 * Outputs performance statistics in the format:
	 * 
	 * <sorting algorithm> <size> <time>
	 * 
	 * For instance,
	 * 
	 * selection sort 1000 9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description.
	 * 
	 * @return formatted stats.
	 */
	public String stats() {
		
		return String.format("%-16s%-8d%-8d", sortingAlgorithm, points.length, scanTime);
	}

	/**
	 * Write MCP after a call to scan(), in the format "MCP: (x, y)" The x and y
	 * coordinates of the point are displayed on the same line with exactly one
	 * blank space in between.
	 * 
	 * @return the median coordinate point in the format "MCP: (x, y)"
	 */
	@Override
	public String toString() {
		
		return "MCP: (" + medianCoordinatePoint.getX() + ", " + medianCoordinatePoint.getY() + ")";
	}

	/**
	 * 
	 * This method, called after scanning, writes point data into a file by
	 * outputFileName. The format of data in the file is the same as printed out
	 * from toString(). The file can help you verify the full correctness of a
	 * sorting result and debug the underlying algorithm.
	 * 
	 * @throws FileNotFoundException
	 */
	public void writeMCPToFile() throws FileNotFoundException {
		
		// Outputs MCP to MCP.txt
		PrintWriter out = new PrintWriter("MCP.txt");
		out.println(toString());
		out.close();
	}

}
