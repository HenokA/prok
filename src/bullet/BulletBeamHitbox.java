package bullet;

import org.newdawn.slick.Image;

import game.Point;

public class BulletBeamHitbox extends Bullet{

	public BulletBeamHitbox(Point p, Image img){
		this.speed = 0;
		this.position = p;
		this.vector = new Point(0,0);
		this.img = img;
		this.radius = img.getHeight()/2;
	}
	
	public void rotate(Point init, Point curr, double d){
		Point translation = new Point(curr.x-init.x, curr.y-init.y);
	//	System.out.println(translation);
	//    position = position.subVector(translation);
		position = position.rotateAround(init, d);
	//	position = position.addVector(translation);
	}
}
