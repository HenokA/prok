package pattern;
import game.GameplayState;
import game.Point;

import java.util.ArrayList;

import org.newdawn.slick.Image;

import bullet.Bullet;


public class PatternSpiral implements Pattern{

	/**
	 * Spiral bullet pattern
	 */

	public Point position;
	private int angle;
	private int time=0;
	private int delay=(int) (1000/GameplayState.BULLETRATE);
	Image img;

	/**
	 * Constructor
	 * @param position
	 */
	public PatternSpiral(Point position){
		this.position = position;
		img = GameplayState.images[3];
	}

	/**
	 * Updates the bullets
	 */
	public void update(ArrayList<Bullet> bullets, int delta){
		time+=delta;	//Timer between patterns
		if(time > delay){	
			if(angle < 1080){ //Check if spinning is done
				bullets.add(new Bullet(position, new Point(0,1).rotate(angle), img, GameplayState.BULLETSPEED*2));
				angle+=9;
			}
			if(angle>=1080){	//reset spinner
				time = 0;
				angle = angle % 1080;
			}
		}
	}

	/**
	 * Sets position
	 */
	public void setPosition(Point position) {
		this.position = position;
	}

}
