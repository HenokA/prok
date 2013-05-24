package bullet;

import org.newdawn.slick.Image;

import game.Point;

/**
 * Bullet used to build the hitboxes for the Laser patterns
 * @author prashan
 *
 */

public class BulletBeamHitbox extends Bullet{

	int beamId;
	
	/**
	 * Constructor 
	 * @param p - initial position
	 * @param img - image (image doesn't matter b/c it's only drawn at the end)
	 */
	public BulletBeamHitbox(Point p, Image img){
		this.speed = 0;
		this.position = p;
		this.vector = new Point(0,0);
		this.img = img;
		this.radius = img.getHeight()/2;
		beamId = 0;
	}
	
	/**
	 * Constuctor
	 * @param p - intiail position
	 * @param img - image
	 * @param beamId - which beam it belongs to
	 */
	public BulletBeamHitbox(Point p, Image img, int beamId){
		this.speed = 0;
		this.position = p;
		this.vector = new Point(0,0);
		this.img = img;
		this.radius = img.getHeight()/2;
		this.beamId = beamId;
	}
	
	/**
	 * Returns the beam id
	 * @return beamId
	 */
	public int getBeamId(){
		return beamId;
	}
	
	/**
	 * Rotates the bullet around the center of the laser
	 * @param init - center of laser
	 * @param curr
	 * @param d - degrees
	 */
	public void rotate(Point init, Point curr, double d){
		position = position.rotateAround(init, d);
	}
}
