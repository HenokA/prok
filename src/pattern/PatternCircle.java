package pattern;
import game.GameplayState;
import game.Point;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Image;

import bullet.Bullet;


public class PatternCircle implements Pattern {
	
	/**
	 * Expanding circle pattern
	 */

	private int time=0;
	private int delay=(int) (1000/GameplayState.BULLETRATE);
	private Point position;
	Image img;
	Random r = new Random();
	
	/**
	 * Constructor
	 * @param position
	 */
	public PatternCircle(Point position){
		this.position = position;
		img = GameplayState.images[1];
	}

	
	/**
	 * Updates pattern, creates bullets based on delay, etc.
	 */
	
	public void update(ArrayList<Bullet> bullets, int delta) {
		time += delta;
		if(time > delay){
			int startAngle = r.nextInt(10); //Random starting angle
			for(int i=startAngle; i<360+startAngle; i+=10){ //Create bullets in a circle
				bullets.add(new Bullet(position, new Point(0,1).rotate(i), img, GameplayState.BULLETSPEED));
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
