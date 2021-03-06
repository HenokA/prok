package game;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;

import bullet.Bullet;

/**
 * Player class - holds data for the player
 * @author prashan
 *
 */

public class Player {

	public Point position;
	public static int RIGHT = 0;
	public static int LEFT = 1;
	public static int UP = 2;
	public static int DOWN = 3;
	private Image img;
	private Image hitbox;
	private Image bulletImg;
	private float speed = 2;
	private long time;
	private long delay=100;
	private Image outline = GameplayState.images[14];
	public float powerUpTimer = 0;
	public int currPowerUp = -1;
	public boolean dd = false;
	public boolean invul = false;
	public boolean twarp = false;
	public boolean shield = false;
	public int shieldcount = 5;
	public float powerTime=1;
	public Color colorBar;
	public boolean changePow = true;
	boolean grazeMove1;
	
	/**
	 * Constructor
	 * @param p - starting position
	 */
	public Player(Point p){
		position = p;
		img = GameplayState.images[0];
		hitbox = GameplayState.images[8];
		bulletImg = GameplayState.images[9];
		time = System.currentTimeMillis();

	}
	
	/**
	 * Activates invulnerability 
	 * @param grazeMove
	 */
	public void grazeMove(boolean grazeMove){

		if(grazeMove){
			turnOffPowerUps();
			powerUpTimer=2000;
			powerTime=2000;
			currPowerUp=3;
			invul = true;
			colorBar = Color.cyan;
		}
	}
	
	/**
	 * Draws the power up bar on the side of the screen
	 * @param g
	 */
	public void drawPowerUpBar(Graphics g){
		g.setColor(colorBar);
		g.fillRect(BulletHellGame.WIDTH+15, 127, (150*(powerUpTimer/powerTime)), 13);
		if(powerUpTimer<0)
			g.drawString("Power Up Timer: "+0+"", BulletHellGame.WIDTH+15, 142);
		else
			g.drawString("Power Up Timer: "+(int)powerUpTimer+"", BulletHellGame.WIDTH+15, 142);	
		g.drawImage(outline, BulletHellGame.WIDTH+11, 123);

	}

	/**
	 * Checks current power ups and turns them on if their timers have expired
	 * @param delta
	 */
	public void checkPowerUps(int delta){
		if(currPowerUp >= 0){
			if(powerUpTimer>0 ){
				if(changePow){
					switch(currPowerUp){
					case 0: dd = true;break;
					case 1: shield = true; shieldcount=5;break;
					case 2: twarp = true;break;
					case 3: invul = true;break;
					}
					changePow = false;
				}
				powerUpTimer-=delta;
			}
			else{
				turnOffPowerUps();
			}
		}
	}

	/**
	 * Turns off all powerups
	 */
	public void turnOffPowerUps(){
		dd = false;
		invul = false;
		twarp = false;
		shield = false;
		shieldcount = 0;
		currPowerUp = -1;
		powerUpTimer = 0;
		powerTime = 1;
		changePow=true;
	}

	/**
	 * Increments position in the given direction
	 * @param direction - the desired direction to move
	 */
	public void increment(int direction){
		if(direction == LEFT)
			position.x-=speed;
		else if(direction == RIGHT)
			position.x+=speed;
		else if(direction == UP)
			position.y-=speed;
		else if(direction == DOWN)
			position.y+=speed;
	}

	/**
	 * Sets the player's speed
	 * @param s - desired speed
	 */
	public void setSpeed(float s){
		speed = s;
	}

	/**
	 * Draws the player's ship to the screen
	 * @param g
	 */
	public void drawShip(Graphics g){
		g.drawImage(img, (float)position.x-img.getWidth()/2, (float)position.y-img.getHeight()/2);
	}

	/**
	 * Draws the player's hitbox as well as any powerup graphics
	 * @param g
	 */
	public void drawHitBox(Graphics g){
		g.drawImage(hitbox, (float)position.x-hitbox.getWidth()/2, (float)position.y-hitbox.getHeight()/2);
		if(shield || twarp || invul){
			g.setColor(colorBar);
			g.setLineWidth(3.0f);
			g.draw(new Circle((float)position.x, (float)position.y, (invul||shield) ? 15.0f : 45.0f));
		}
	}

	/**
	 * Shoots bullets by adding to the pbullets ArrayList in GameplayState.
	 * @param bullets
	 */
	public void shoot(ArrayList<Bullet> bullets){
		if(System.currentTimeMillis() -time > delay){
			if(dd){
				bullets.add(new Bullet(new Point(position.x-10, position.y), new Point(0,-1), bulletImg, 7));
				bullets.add(new Bullet(new Point(position.x+10, position.y), new Point(0,-1), bulletImg, 7));
			}
			else
				bullets.add(new Bullet(position, new Point(0,-1), bulletImg, 7));
			time = System.currentTimeMillis();
		}
	}
}
