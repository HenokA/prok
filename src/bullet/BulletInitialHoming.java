package bullet;

import game.GameplayState;
import game.Point;

import org.newdawn.slick.Image;

/**
 * A bullet whose intial vector is pointed at the player
 * @author prashan
 *
 */

public class BulletInitialHoming extends Bullet{

	/**
	 * Constructor
	 * @param p - intial position
	 * @param img - sprite
	 * @param speed - speed
	 */
	public BulletInitialHoming(Point p, Image img, float speed) {
		this.position = p;
		this.img = img;
		this.speed = speed;
		Point player = GameplayState.player.position;
		this.vector = new Point(player.x-p.x, player.y-p.y).getUnitVector();
		this.radius = img.getHeight()/2;
	}

}
