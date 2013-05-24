package bullet;

import java.util.Random;

import game.GameplayState;
import game.Point;

import org.newdawn.slick.Image;

/**
 * A bullet that after a bried period of time explodes into 6 other bullets
 * @author prashan
 *
 */

public class BulletBigExploding extends Bullet {

	int timer;
	Image miniImg;
	
	/**
	 * Constructor
	 * @param p - intial position
	 * @param vector - vector
	 * @param img - image to use
	 * @param miniImg - image for small bullets
	 * @param speed - speed
	 * @param timer - time till detonation
	 */
	public BulletBigExploding(Point p, Point vector, Image img, Image miniImg, float speed, int timer) {
		super(p, vector, img, speed);
		this.timer = timer;
		this.miniImg = miniImg;
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Game logic for the bullet.
	 * The algorithm works like this:
	 * Move the bullet by calling super.increment();
	 * If the detonation timer is <= 0,
	 * Create a new random int to serve as a starting angle
	 * Go around in increments of 45 degrees, adding new small bullets with their
	 * intial vectors rotated by the angle
	 * Then delete this big bullet
	 */
	@Override
	public void increment(int delta){
		timer -=delta;
		super.increment(delta);
		if(timer <= 0){
			Random r = new Random();
			int startAngle = r.nextInt(45); //Random starting angle
			for(int i=startAngle; i<360+startAngle; i+=45){ //Create bullets in a circle
				GameplayState.bulletsToBeAdded.add(new Bullet(position, new Point(0,1).rotate(i), miniImg, GameplayState.BULLETSPEED));
			}
			GameplayState.bulletsToBeRemoved.add(this);
		}
	}

}
