package bullet;

import game.GameplayState;
import game.Point;

import org.newdawn.slick.Image;

/**
 * A bullet that constantly follows the player and loses velocity
 * It is destroyed when velocity < 2
 * @author prashan
 *
 */

public class BulletConstantHoming extends Bullet{
	
	private float vf; //velocity final
	private float accel;
	private float time;

	/**
	 * Constructor 
	 * @param p - initial position
	 * @param img - sprite
	 * @param speed - initial speed
	 * @param accel - acceleration (should be negative)
	 */
	public BulletConstantHoming(Point p, Image img, float speed, float accel) {
		this.position = p;
		this.img = img;
		this.speed = speed;
		this.accel = accel;
		Point player = GameplayState.player.position;
		this.vector = new Point(player.x-p.x, player.y-p.y).getUnitVector();
		this.radius = img.getHeight()/2;
	}
	
	/**
	 * Increments game logic.
	 * Algorithm works by physics.
	 * Recalculates velocity everytime and uses that as speed
	 */
	public void increment(int delta){
		time += (float) delta;
		float timeSeconds = time/1000;
		vf = speed+accel*(timeSeconds*timeSeconds); //		vf = vo + at^2
		Point p = GameplayState.player.position;
		vector = new Point(p.x-position.x, p.y-position.y).getUnitVector();
		position = position.addVector(vector.mult(vf));
		if(vf < 2){
			GameplayState.bulletsToBeRemoved.add(this);
		}
	}
}
