package pattern;
import game.GameplayState;
import game.Point;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

import bullet.Bullet;
import bullet.BulletBigExploding;


public class PatternBigExplodingCircle implements Pattern {
	
	/**
	 * Expanding circle pattern
	 */
	private int time=0;
	private int delay=(int) (1500/GameplayState.BULLETRATE);
	private Point position;
	Image img;
	Image miniImg;
	Random r = new Random();
	
	/**
	 * Constructor
	 * @param position
	 */
	public PatternBigExplodingCircle(Point position){
		this.position = position;
		img = GameplayState.images[10];
		miniImg = GameplayState.images[2];
	}


	/**
	 * Updates pattern, creates bullets based on delay, etc.
	 */
	
	public void update(ArrayList<Bullet> bullets, int delta) {
		time += delta;
		if(time > delay){
			int startAngle = r.nextInt(10); //Random starting angle
			for(int i=startAngle; i<360+startAngle; i+=60){ //Create bullets in a circle
				bullets.add(new BulletBigExploding(position, new Point(0,1).rotate(i), img, miniImg, GameplayState.BULLETSPEED, 3000));
			}
			time = 0;
		}
	}

	/**
	 * Sets position
	 */
	public void setPosition(Point position) {
		this.position = position;
	}
}
