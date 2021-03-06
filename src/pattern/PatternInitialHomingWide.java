package pattern;
import game.GameplayState;
import game.Point;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Image;

import bullet.Bullet;
import bullet.BulletInitialHoming;


public class PatternInitialHomingWide implements Pattern {
	
	/**
	 * Expanding circle pattern
	 */

	private int time=0;
	private int delay=(int) (1000/GameplayState.BULLETRATE);
	private Point position;
	Image img;
	Random r = new Random();
	int[] pointsx = {75, 50, 25,  0,  -25, -50, -75};
	int[] pointsy = {10, 7, 5,  0,  5, 7, 10};
	
	/**
	 * Constructor
	 * @param position
	 */
	public PatternInitialHomingWide(Point position){
		this.position = position;
		img = GameplayState.images[11];
	}
	
	/**
	 * Updates pattern, creates bullets based on delay, etc.
	 */
	
	public void update(ArrayList<Bullet> bullets, int delta) {
		time += delta;
		if(time > delay){
			for(int i=0; i<pointsx.length; i++){ //Create bullets in a circle
				bullets.add(new BulletInitialHoming(position.addVector(new Point(pointsx[i],pointsy[i])), img, GameplayState.BULLETSPEED*5));
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
