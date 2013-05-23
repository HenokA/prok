package game;

import java.awt.geom.AffineTransform;

/**
 * Point class - represents both a point or a vector and handles
 * rotation and all vector mathematics
 * @author prashan
 *
 */

public class Point {

	public double x;
	public double y;
	/**
	 * point constructor
	 * @param x
	 * @param y
	 */
	public Point(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Calculates distance to another point
	 * @param other - other point
	 * @return the distance between the two points
	 */
	public double distanceTo(Point other){
		return Math.sqrt(Math.pow(other.x-this.x, 2) + Math.pow(other.y-this.y, 2));
	}
	
	/**
	 * Adds a vector to the current point or vector
	 * @param vector - vector to be added
	 * @return the resulting point or vector
	 */
	public Point addVector(Point vector){
		return new Point(x+vector.x, y+vector.y);
	}
	
	/**
	 * Subtracts a vector to the current point or vector
	 * @param vector - vector to be subtracted
	 * @return the resulting point or vector
	 */
	public Point subVector(Point vector){
		return new Point(x-vector.x, y-vector.y);
	}
	
	/**
	 * Multiplies a vector by a constant
	 * @param constant - desired constant
	 * @return the resulting vector
	 */
	public Point mult(float constant){
		return new Point(x*constant, y*constant);
	}
	
	/**
	 * Rotates a vector by d degrees
	 * @param d - amount to rotate
	 * @return the resulting vector
	 */
	public Point rotate(double d){
		double xp = x*Math.cos(d*Math.PI/180) - y*Math.sin(d*Math.PI/180);
		double yp = x*Math.sin(d*Math.PI/180) + y*Math.cos(d*Math.PI/180);
		return new Point(xp, yp);
	}
	
	/**
	 * Rotates a point around another point
	 * @param other - point to be rotated about
	 * @param d - amount of degrees
	 * @return the resulting point
	 */
	public Point rotateAround(Point other, double d){
		double[] pt = {x, y};
		AffineTransform.getRotateInstance(Math.toRadians(d), other.x, other.y)
		  .transform(pt, 0, pt, 0, 1); // specifying to use this double[] to hold coords
		return new Point(pt[0], pt[1]);
	}
	
	/**
	 * Calculates the magnitude of a vector
	 * @return - the magnitude
	 */
	public double getMagnitude(){
		return Math.sqrt(x*x+y*y);
	}
	
	/**
	 * Calculates the unit vector of a vector
	 * @return - the unit vector
	 */
	public Point getUnitVector(){
		return new Point(x/getMagnitude(), y/getMagnitude());
	}
	
	/**
	 * Calculates the vector from one point to another
	 * @param other - other point
	 * @return the resulting vector
	 */
	public Point vectorTo(Point other){
		Point v = new Point(other.x-x, other.y-y);
		return v.getUnitVector();
	}
	
	/**
	 * Prints the point as a nice string
	 */
	public String toString(){
		return x+", "+y;
	}
}
