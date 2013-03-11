package bullet;

import game.GameplayState;
import game.Point;

import org.newdawn.slick.Image;

public class BulletBigExploding extends Bullet {

	int timer;
	Image miniImg;
	
	public BulletBigExploding(Point p, Point vector, Image img, Image miniImg, float speed, int timer) {
		super(p, vector, img, speed);
		this.timer = timer;
		this.miniImg = miniImg;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void increment(int delta){
		timer -=delta;
		super.increment(delta);
		if(timer <= 0){
			int startAngle = 0; //Random starting angle
			for(int i=startAngle; i<360+startAngle; i+=30){ //Create bullets in a circle
				GameplayState.bulletsToBeAdded.add(new Bullet(position, new Point(0,1).rotate(i), miniImg, GameplayState.BULLETSPEED));
			}
			GameplayState.bulletsToBeRemoved.add(this);
		}
	}

}
