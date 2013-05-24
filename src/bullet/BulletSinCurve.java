package bullet;
import game.Point;

import org.newdawn.slick.Image;

/**
 * A bullet whose path is a sin curve
 * @author prashan
 *
 */

public class BulletSinCurve extends Bullet{

	private double angle;
	private boolean increasing;
	private int totalAngle=0;

	/**
	 * Constructor
	 * @param p - position
	 * @param vector - vector
	 * @param img - sprite
	 * @param speed - speed
	 * @param angle - angle
	 */
	public BulletSinCurve(Point p, Point vector, Image img, float speed, int angle) {
		super(p, vector, img, speed);
		this.angle = angle;
		// TODO Auto-generated constructor stub
	}

	/**
	 * HOW THE ALGORITHM WORKS:
	 * We check if the angle should be increasing or decreasing
	 * Then we increment the angle in that direction
	 * if we reach either 180 or 0, switch directions
	 */
	@Override
	public void increment(int delta){
		if(increasing){
			if(totalAngle<180){
				setVector(vector.rotate(angle));
				position = position.addVector(vector.mult(speed));
				totalAngle+=Math.abs(angle);
			}else{
				increasing = false;
			}
		}else if(!increasing){
			if(totalAngle>0){
				setVector(vector.rotate(-angle));
				position = position.addVector(vector.mult(speed));
				totalAngle-=Math.abs(angle);
			}else{
				increasing = true;
			}
		}
	}
	
	/**
	 * Angle should be halved if affected by time-warp
	 */
	public void warp(){
		super.warp();
		angle = angle*.5;
	}
}
