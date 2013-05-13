package game;

import java.awt.geom.AffineTransform;

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
	
	public double distanceTo(Point other){
		return Math.sqrt(Math.pow(other.x-this.x, 2) + Math.pow(other.y-this.y, 2));
	}
	
	public Point addVector(Point vector){
		return new Point(x+vector.x, y+vector.y);
	}
	
	public Point subVector(Point vector){
		return new Point(x-vector.x, y-vector.y);
	}
	
	public Point mult(float constant){
		return new Point(x*constant, y*constant);
	}
	
	public Point rotate(double d){
		double xp = x*Math.cos(d*Math.PI/180) - y*Math.sin(d*Math.PI/180);
		double yp = x*Math.sin(d*Math.PI/180) + y*Math.cos(d*Math.PI/180);
		return new Point(xp, yp);
	}
	
	public Point rotateAround(Point other, double d){
		double[] pt = {x, y};
		AffineTransform.getRotateInstance(Math.toRadians(d), other.x, other.y)
		  .transform(pt, 0, pt, 0, 1); // specifying to use this double[] to hold coords
		return new Point(pt[0], pt[1]);
	}
	
	public double getMagnitude(){
		return Math.sqrt(x*x+y*y);
	}
	
	public Point getUnitVector(){
		return new Point(x/getMagnitude(), y/getMagnitude());
	}
	
	public Point vectorTo(Point other){
		Point v = new Point(other.x-x, other.y-y);
		return v.getUnitVector();
	}
	
	public String toString(){
		return x+", "+y;
	}
}
