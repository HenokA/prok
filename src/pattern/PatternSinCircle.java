package pattern;
import game.GameplayState;
import game.Point;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

import bullet.Bullet;
import bullet.BulletSinCurve;


public class PatternSinCircle implements Pattern {
	
	/**
	 * Expanding circle pattern
	 */

	private int PATTERNID = 7;
	private int time=0;
	private int delay=(int) (1500/GameplayState.BULLETRATE);
	private Point position;
	Image img;
	Random r = new Random();
	
	/**
	 * Constructor
	 * @param position
	 */
	public PatternSinCircle(Point position){
		this.position = position;
		img = GameplayState.images[1];
	}

	/**
	 * Constructor
	 */
	public PatternSinCircle(){
		img = GameplayState.images[1];
	}
	
	/**
	 * Updates pattern, creates bullets based on delay, etc.
	 */

	public void update(ArrayList<Bullet> bullets, int delta) {
		time += delta;
		if(time > delay){
			int startAngle = r.nextInt(15); //Random starting angle
			for(int i=startAngle; i<360+startAngle; i+=15){ //Create bullets in a circle
				bullets.add(new BulletSinCurve(position, new Point(0,1).rotate(i), img, GameplayState.BULLETSPEED*1.5f, 5));
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

	
	public int getPatternID() {
		return PATTERNID;
	}
}
