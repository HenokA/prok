package bullet;

import game.GameplayState;
import game.Point;

import org.newdawn.slick.Image;

public class BulletConstantHoming extends Bullet{
	
	private float vo; //velocity inital
	private float vf; //velocity final
	private float accel;
	private float time;

	public BulletConstantHoming(Point p, Image img, float speed, float accel) {
		this.position = p;
		this.img = img;
		this.speed = speed;
		this.accel = accel;
		Point player = GameplayState.player.position;
		this.vector = new Point(player.x-p.x, player.y-p.y).getUnitVector();
		this.radius = img.getHeight()/2;
	}
	
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
