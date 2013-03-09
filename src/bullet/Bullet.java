package bullet;
import game.Point;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;

public class Bullet {
	
	/**
	 * Bullet class - contains data for projectile position, speed, direction, and image
	 */

	protected Point position;	//position
	protected Point vector;	//directional vector
	protected float speed = 1;
	private int radius;
	Image img;
	
	/**
	 * Constructor
	 * @param p - Starting position
	 * @param vector - Starting direction vector
	 * @param img - Image to use
	 * @param speed - Speed per frame
	 */
	public Bullet(Point p, Point vector, Image img, float speed){
		position = p;
		this.vector = vector;
		this.img = img;
		this.speed = speed;
		radius = img.getHeight()/2;
	}

	/**
	 * Updates position
	 */
	public void increment(){
		position = position.addVector(vector.mult(speed));
	}
	
	/**
	 * draws bullet to the screen
	 */
	public void draw(Graphics g){
		g.drawImage(img, (float)position.x-radius, (float)position.y-radius);
	}
	
	/**
	 * Sets speed
	 * @param speed - desired speed 
	 */
	public void setSpeed(float speed){
		this.speed = speed;
	}
	
	/**
	 * Sets direction vector
	 * @param v - Unit vector
	 */
	public void setVector(Point v){
		this.vector = v;
	}
	
	/**
	 * 
	 * @return current direction vector
	 */
	public Point getVector(){
		return vector;
	}
	
	/**
	 * Checks collision on player (used by enemy bullets)
	 * @param player - player's current position
	 * @return
	 */
	public boolean checkCollision(Point player){
		return position.distanceTo(player) < radius;
	}
	
	public boolean checkGraze(Point player){
		return position.distanceTo(player) < (radius+7);
	}
	
	/**
	 * Checks collision on enemy (used by player bullets)
	 * @param enemy - current enemy position
	 * @return
	 */
	public boolean checkCollisionEnemy(Point enemy){
		if(position.x > enemy.x-50 && position.x < enemy.x+50 && position.y > enemy.y-50 && position.y < enemy.y+25)
			return true;
		if(position.x > enemy.x-100 && position.x < enemy.x-50 && position.y > enemy.y-25 && position.y < enemy.y+50)
			return true;
		if(position.x > enemy.x+50 && position.x < enemy.x+100 && position.y > enemy.y-25 && position.y < enemy.y+50)
			return true;
		return false;
	}
	
	/**
	 * Checks if offscreen
	 * @return true if offscreen, false if not.
	 */
	public boolean checkOffscreen(){
		return position.x+radius < 0 || position.y+radius < 0 || position.x-radius > 400 || position.y-radius > 640;
	}
}
