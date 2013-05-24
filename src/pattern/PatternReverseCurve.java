package pattern;
import game.GameplayState;
import game.Point;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Image;

import bullet.BulletCurve;
import bullet.Bullet;


public class PatternReverseCurve implements Pattern{

	/**
	 * The same as BulletCurve, but directions are reversed
	 */

	private int time=0;
	private int bulletDelay = (int) (100/GameplayState.BULLETRATE);
	private int bulletTimer=0;
	private int delay= (int) (500/GameplayState.BULLETRATE);
	private int count=0;
	private int max=10;
	private int startAngle=0;
	private Point position;
	Image img;
	Random r = new Random();
/**
 * Constructor
 * @param position
 */
	public PatternReverseCurve(Point position){
		this.position = position;
		img = GameplayState.images[5];
		startAngle = r.nextInt(90);
	}
/**
 * Updates the pattern continuously, creats the curve based on a delay
 */
	public void update(ArrayList<Bullet> bullets, int delta) {
		time+=delta;
		bulletTimer+=delta;
		if(time>delay){
			if(count<max){
				if(bulletTimer>bulletDelay){
					bullets.add(new BulletCurve(position, new Point(0,1).rotate(-startAngle+45), img, 5, -1)); // adds bullet
					count++;
					bulletTimer = 0; //resets timer
				}
			}else{
				time=0;
				count=0;
				startAngle = r.nextInt(90);
			}
		}
	}
/** 
 * Sets the position
 */
	public void setPosition(Point position) {
		this.position = position;
	}

}
