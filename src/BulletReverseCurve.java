import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Image;


public class BulletReverseCurve implements BulletPattern{

	/**
	 * The same as BulletCurve, but directions are reversed
	 */
	
	private int PATTERNID = 3;
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
	ArrayList<Bullet> cBullets = new ArrayList<Bullet>();

	public BulletReverseCurve(Point position){
		this.position = position;
		img = GameplayState.images[5];
		startAngle = r.nextInt(90);
	}

	public BulletReverseCurve(){
		img = GameplayState.images[5];
		startAngle = r.nextInt(90);
	}

	public void update(ArrayList<Bullet> bullets, int delta) {
		time+=delta;
		bulletTimer+=delta;
		if(time>delay){
			if(count<max){
				if(bulletTimer>bulletDelay){
					Bullet b = new Bullet(position, new Point(0,1).rotate(-startAngle+45), img, 4);
					bullets.add(b);
					cBullets.add(b);
					count++;
					bulletTimer = 0;
				}
			}else{
				time=0;
				count=0;
				startAngle = r.nextInt(90);
			}
		}		
		for(int i=0; i<cBullets.size(); i++){
			cBullets.get(i).setVector(cBullets.get(i).getVector().rotate(-1));
		}
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public int getPatternID() {
		return PATTERNID;
	}
}
