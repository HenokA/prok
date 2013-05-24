package bullet;
import game.Point;

import org.newdawn.slick.Image;

/**
 * A bullet whose path is curved
 * @author prashan
 *
 */

public class BulletCurve extends Bullet{
	
	private double angle;

	/**
	 * Constructor
	 * @param p - initial position
	 * @param vector - initial vector
	 * @param img - sprite
	 * @param speed - speed
	 * @param angle - angle to curve by
	 */
	public BulletCurve(Point p, Point vector, Image img, float speed, int angle) {
		super(p, vector, img, speed);
		this.angle = angle;
		// TODO Auto-generated constructor stub
	}

	/**
	 * Incrementation works by rotating the direction vector
	 * by an angle every time-step
	 */
	@Override
	public void increment(int delta){
		setVector(vector.rotate(angle));
		position = position.addVector(vector.mult(speed));
	}
	
	/**
	 * Angle is decreased by half when time-warped
	 */
	public void warp(){
		super.warp();
		angle = angle*.5;
	}
}
