package ability;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import game.GameplayState;
import game.Point;

public class Missile {

	private Point vector;
	private Point position;
	private float vo; //velocity inital
	private float vf; //velocity final
	private float accel;
	private float time;
	private Image img;

	public Missile(Point position, Point vector, float vo, float accel ){
		this.position = position;
		this.vector = vector;
		this.vo = vo;
		this.accel = accel;
		time = 0;
		img = GameplayState.images[6];
	}

	public boolean update(ArrayList<Missile> m, int delta){
		time += (float) delta;
		float timeSeconds = time/1000;
		vf = vo+accel*(timeSeconds*timeSeconds); //		vf = vo + at^2
		//vf = 1.1f;
		Point p = GameplayState.player.position;
		vector = new Point(p.x-position.x, p.y-position.y).getUnitVector();
		position = position.addVector(vector.mult(vf));
		if(checkCollision(p))
			return false;
		if(vf > 0){
			return true;
		}
		else{
			return false;
		}

	}
	
	public boolean checkCollision(Point player){
		return position.distanceTo(player) < 1;
	}
	
	public void draw(Graphics g){
		g.drawImage(img, (float)position.x-img.getWidth(), (float)position.y-img.getHeight());
	}
}
