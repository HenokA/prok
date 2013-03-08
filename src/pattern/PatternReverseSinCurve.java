package pattern;
import game.GameplayState;
import game.Point;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Image;

import bullet.BulletCurve;
import bullet.Bullet;
import bullet.BulletSinCurve;


public class PatternReverseSinCurve implements Pattern{

	/**
	 * The same as BulletCurve, but directions are reversed
	 */
	
	private int PATTERNID = 5;
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

	public PatternReverseSinCurve(Point position){
		this.position = position;
		img = GameplayState.images[6];
		startAngle = r.nextInt(90);
	}

	public PatternReverseSinCurve(){
		img = GameplayState.images[6];
		startAngle = r.nextInt(90);
	}

	public void update(ArrayList<Bullet> bullets, int delta) {
		time+=delta;
		bulletTimer+=delta;
		if(time>delay){
			if(count<max){
				if(bulletTimer>bulletDelay){
					bullets.add(new BulletSinCurve(position, new Point(0,1).rotate(-startAngle+135), img, 4, -5));
					count++;
					bulletTimer = 0;
				}
			}else{
				time=0;
				count=0;
				startAngle = r.nextInt(90);
			}
		}
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public int getPatternID() {
		return PATTERNID;
	}
}
