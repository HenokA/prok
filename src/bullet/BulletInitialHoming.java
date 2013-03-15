package bullet;

import game.GameplayState;
import game.Point;

import org.newdawn.slick.Image;

public class BulletInitialHoming extends Bullet{

	public BulletInitialHoming(Point p, Image img, float speed) {
		this.position = p;
		this.img = img;
		this.speed = speed;
		Point player = GameplayState.player.position;
		this.vector = new Point(player.x-p.x, player.y-p.y).getUnitVector();
	}

}
