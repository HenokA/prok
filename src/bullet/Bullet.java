package bullet;
import game.Point;
import game.RenderObjectExplosion;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Bullet {
	
	/**
	 * Bullet class - contains data for projectile position, speed, direction, and image
	 */

	protected Point position;	//position
	protected Point vector;	//directional vector
	protected float speed = 1;
	protected int radius;
	public boolean warp = false;
	public Image img;
	RenderObjectExplosion ro;
	
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
	
	public Bullet(){
		
	}
	
	public Point getPosition(){
		return position;
	}

	public void setRO(RenderObjectExplosion ro){
		this.ro = ro;
	}
	
	/**
	 * Updates position
	 */
	public void increment(int delta){
		position = position.addVector(vector.mult(speed));
	}
	
	/**
	 * draws bullet to the screen
	 */
	public void draw(Graphics g){
		g.drawImage(img, (float)Math.round(position.x-radius), (float)Math.round(position.y-radius));
	}
	
	/**
	 * Sets speed
	 * @param speed - desired speed 
	 */
	public void setSpeed(float speed){
		this.speed = speed;
	}
	
	public float getSpeed(){
		return speed;
	}
	
	public void warp(){
		warp = true;
		speed = speed*.5f;
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
	
	public boolean checkGraze(Point player, int dist){
		return position.distanceTo(player) < (radius+dist);
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
