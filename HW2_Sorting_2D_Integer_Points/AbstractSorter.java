package edu.iastate.cs228.hw2;

/**
 *  
 *  The constructor takes an existing array pts[] of points, and copies it over to the array
	points[]. It throws an IllegalArgumentException if pts == null or pts.length == 0.
	Besides having an array points[] to store points, AbstractSorter also includes two
	instance variables.
	• algorithm: type of sorting algorithm to be initialized by a child constructor.
	• pointComparator: comparator used for point comparison. Set by calling
	setComparator(). It compares two points by their 𝑥-coordinates or 𝑦-coordinates

 * @author Nathan Krieger
 *
 */

import java.util.Comparator;
import java.io.FileNotFoundException;
import java.lang.IllegalArgumentException;
import java.util.InputMismatchException;

/**
 * 
 * This abstract class is extended by SelectionSort, InsertionSort, MergeSort,
 * and QuickSort. It stores the input (later the sorted) sequence.
 *
 */
public abstract class AbstractSorter {

	/**
	 * 
	 * array of points operated on by a sorting algorithm.
	 * stores ordered points after a call to sort().
	 */
	protected Point[] points;

	/**
	 * "selection sort", "insertion sort", "mergesort", or
	 * "quicksort". Initialized by a subclass constructor.
	 * 
	 */
	protected String algorithm = null;
	
	/**
	 * Comparator for comparing x or y coordinates.
	 */
	protected Comparator<Point> pointComparator = null;

	/**
	 * Default constructor
	 */
	protected AbstractSorter() {
	}

	/**
	 * This constructor accepts an array of points as input. Copy the points into
	 * the array points[].
	 * 
	 * @param pts input array of points
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	protected AbstractSorter(Point[] pts) throws IllegalArgumentException {
		
		if (pts == null || pts.length == 0) {
			throw new IllegalArgumentException("Input array is null or empty.");
		}

		points = new Point[pts.length];
		
		for (int i = 0; i < pts.length; i++) {
			points[i] = new Point(pts[i]);
		}

	}

	/**
	 * Generates a comparator on the fly that compares by x-coordinate if order ==
	 * 0, by y-coordinate if order == 1. Assign the comparator to the variable
	 * pointComparator.
	 * 
	 * 
	 * @param order 0 by x-coordinate 1 by y-coordinate
	 * 
	 * 
	 * @throws IllegalArgumentException if order is less than 0 or greater than 1
	 * 
	 */
	public void setComparator(int order) throws IllegalArgumentException {
		
		if (order < 0 || order > 1) {
			throw new IllegalArgumentException("Order must be 0 or 1");
		}

		if (order == 0) {
			
			Point.setXorY(true);
			
		} else if (order == 1) {
			
			Point.setXorY(false);
			
		}
		
	}

	/**
	 * Use the created pointComparator to conduct sorting.
	 * 
	 * Should be protected. Made public for testing.
	 */
	public abstract void sort();

	/**
	 * Obtain the point in the array points[] that has median index
	 * 
	 * @return median point
	 */
	public Point getMedian() {
		
		return points[points.length / 2];
	}

	/**
	 * Copys the array points[] onto the array pts[].
	 * 
	 * @param pts
	 */
	public void getPoints(Point[] pts) {
		
		for (int i = 0; i < points.length; i++) {
	        pts[i] = new Point(points[i]);
	    }
	}

	/**
	 * Swaps the two elements indexed at i and j respectively in the array points[].
	 * 
	 * @param i
	 * @param j
	 */
	protected void swap(int i, int j) {
		
		Point temp = points[i];
		points[i] = points[j];
		points[j] = temp;
	}
	
}
