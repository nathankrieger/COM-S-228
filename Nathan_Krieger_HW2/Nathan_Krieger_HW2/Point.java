package edu.iastate.cs228.hw2;

/**
 * 
 * @author Nathan Krieger
 *
 */
public class Point implements Comparable<Point> {
	
	/**
	 * The x coordinate.
	 */
	private int x;
	
	/**
	 * The y coordinate.
	 */
	private int y;

	/**
	 * compare x coordinates if xORy == true and y coordinates otherwise
	 * To set its value, use Point.xORy = true or false.
	 */
	public static boolean xORy;
								
	/**
	 * Default constructor.
	 */
	public Point()
	{
		// x and y get default value 0
	}

	/**
	 * Point constructor that creates a point based off of a 2D location.
	 * 
	 * @param x
	 * @param y
	 */
	public Point(int x, int y) {
		
		this.x = x;
		this.y = y;
	}

	/*
	 * Copy constructor.
	 */
	public Point(Point p) {
		
		x = p.getX();
		y = p.getY();
	}

	/**
	 * 
	 * @return the value of the x coordinate.
	 */
	public int getX() {
		
		return x;
	}

	/**
	 * 
	 * @return the value of the y coordinate.
	 */
	public int getY() {
		
		return y;
	}

	/**
	 * Set the value of the static instance variable xORy.
	 * 
	 * @param xORy
	 */
	public static void setXorY(boolean xORy) {
		
		Point.xORy = xORy;
	}

	/**
	 * Checks of 2 points are equal to each other.
	 * @return true if they are equal, false if they aren't equal.
	 */
	@Override
	public boolean equals(Object obj) {
		
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}

		Point other = (Point) obj;
		return x == other.x && y == other.y;
	}

	/**
	 * Compare this point with a second point q depending on the value of the static
	 * variable xORy
	 * 
	 * @param q
	 * @return -1 if (xORy == true && (this.x < q.x || (this.x == q.x && this.y <
	 *         q.y))) || (xORy == false && (this.y < q.y || (this.y == q.y && this.x
	 *         < q.x))) 0 if this.x == q.x && this.y == q.y) 1 otherwise
	 */
	public int compareTo(Point q) {

		if (xORy == true && (this.x < q.x || (this.x == q.x && this.y < q.y))
				|| (xORy == false && (this.y < q.y || (this.y == q.y && this.x < q.x)))) {

			return -1;
		}
		if (this.x == q.x && this.y == q.y) {
			return 0;
		}

		return 1;
	}

	/**
	 * Output a point in the standard form (x, y).
	 */
	@Override
	public String toString() {
		
		return "(" + getX() + ", " + getY() + ")";
	}
	
}
