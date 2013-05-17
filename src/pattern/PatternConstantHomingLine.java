package pattern;
import game.GameplayState;
import game.Point;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Image;

import bullet.Bullet;
import bullet.BulletConstantHoming;


public class PatternConstantHomingLine implements Pattern{
	
	/**
	 * Curving bullet pattern
	 */

	private int time=0;
	private int bulletDelay = (int) (75/GameplayState.BULLETRATE);
	private int bulletTimer=0;
	private int delay= (int) (500/GameplayState.BULLETRATE);
	private int count=0;
	private int max=5;
	private Point position;
	Image img;
	Random r = new Random();

	/**
	 * Constructor
	 * @param position
	 */
	public PatternConstantHomingLine(Point position){
		this.position = position;
		img = GameplayState.images[16];
	}

	/**
	 * Updates bullets
	 */

	public void update(ArrayList<Bullet> bullets, int delta) {
		time+=delta;	//Timer between patterns
		bulletTimer+=delta;	//Timer between bullets
		if(time>delay){
			if(count<max){	//Max bullets per pattern
				if(bulletTimer>bulletDelay){
					bullets.add(new BulletConstantHoming(position, img, 5f, -.9f));
					count++;
					bulletTimer = 0;
				}
			}else{
				time=0;
				count=0;
			}
		}
	}

	/**
	 * Set position
	 */
	public void setPosition(Point position) {
		this.position = position;
	}

}
