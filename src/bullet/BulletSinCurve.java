package bullet;
import game.Point;

import org.newdawn.slick.Image;


public class BulletSinCurve extends Bullet{

	private double angle;
	private boolean increasing;
	private int totalAngle=0;

	public BulletSinCurve(Point p, Point vector, Image img, float speed, int angle) {
		super(p, vector, img, speed);
		this.angle = angle;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void increment(int delta){
		if(increasing){
			if(totalAngle<180){
				setVector(vector.rotate(angle));
				position = position.addVector(vector.mult(speed));
				totalAngle+=Math.abs(angle);
			}else{
				increasing = false;
			}
		}else if(!increasing){
			if(totalAngle>0){
				setVector(vector.rotate(-angle));
				position = position.addVector(vector.mult(speed));
				totalAngle-=Math.abs(angle);
			}else{
				increasing = true;
			}
		}
	}
	
	public void warp(){
		super.warp();
		angle = angle*.5;
	}
}
