package bullet;
import game.Point;

import org.newdawn.slick.Image;


public class BulletCurve extends Bullet{
	
	private int angle;

	public BulletCurve(Point p, Point vector, Image img, float speed, int angle) {
		super(p, vector, img, speed);
		this.angle = angle;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void increment(){
		setVector(vector.rotate(angle));
		position = position.addVector(vector.mult(speed));
	}
}
