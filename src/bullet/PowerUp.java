package bullet;

import game.GameplayState;
import game.Player;
import game.Point;

import org.newdawn.slick.Color;
import java.util.Random;

import org.newdawn.slick.Image;

/**
 * A bullet that isn't actually a bullet.
 * It's a powerup
 * @author prashan
 *
 */

public class PowerUp extends Bullet{

	public static int DOUBLEDAMAGE = 0;
	public static int SHIELD = 1;
	public static int TIMEWARP = 2;
	public static int NUMPOWERUPS = 3;
	private Image[] sprites = {GameplayState.images[12],GameplayState.images[13], GameplayState.images[15] };
	private Random r = new Random();
	private int powerUpNum = 0;

	/**
	 * Constructor
	 * @param p - position
	 * @param vector - vector 
	 * @param speed - speed
	 */
	public PowerUp(Point p, Point vector, float speed) {
		this.position = p;
		this.speed = speed;
		this.vector = vector;
		powerUpNum = r.nextInt(NUMPOWERUPS);	// randomly choose a powerup
		this.img = sprites[powerUpNum];		//set the image for the powerup
		this.radius = img.getHeight()/2;
	}


	/**
	 * Called once the player collides with the powerup
	 * Applies the set powerup to the player
	 * @param p
	 */
	public void applyPowerUp(Player p){
		p.turnOffPowerUps();		//Reset powerups
		p.currPowerUp = powerUpNum;
		switch(powerUpNum){
		case 0 : p.powerUpTimer = 10000; p.powerTime=10000; p.colorBar=new Color(Color.red); break;
		case 1 : p.powerUpTimer = 10000; p.powerTime=10000; p.colorBar=new Color(Color.green); break;
		case 2 : p.powerUpTimer = 20000; p.powerTime=20000; p.colorBar=new Color(Color.magenta); break;  
		}
	}
}
