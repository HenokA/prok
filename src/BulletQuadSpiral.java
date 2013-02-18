import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Image;


public class BulletQuadSpiral implements BulletPattern{
	
	/**
	 * Literally the same as BulletSpiral but creates 4 bullets in update() per call instead of 1
	 */

	private int PATTERNID = 2;
	public Point position;
	public Point nextPosition;
	private int angle;
	private int time=0;
	private int delay=(int) (1000/GameplayState.BULLETRATE);
	private int maxAngle = 270;
	private int startAngle = 0;
	Random r = new Random();
	Image img;

	public BulletQuadSpiral(Point position){
		this.position = position;
		nextPosition = position;
		img = GameplayState.images[3];
	}
	
	public BulletQuadSpiral(){
		img = GameplayState.images[3];
	}
	
	public void update(ArrayList<Bullet> bullets, int delta){
		time+=delta;
		if(time > delay){
			if(angle < maxAngle+startAngle ){
				for(int i=0; i<4;i++)
				bullets.add(new Bullet(position, new Point(0,1).rotate(angle+i*90), img, GameplayState.BULLETSPEED*3));
				angle+=6;
			}
			if(angle>=maxAngle+startAngle){
				time = 0;
				startAngle = r.nextInt(90);
				angle = startAngle;
				position = nextPosition;
			}
		}
	}

	public void setPosition(Point position) {
		if(this.position == null)
			this.position = position;
		this.nextPosition = position;
	}

	public int getPatternID() {
		return PATTERNID;
	}
}
