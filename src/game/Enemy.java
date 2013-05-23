package game;
import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import pattern.Pattern;

/**
 * Enemy class - holds data for the Enemy and provides methods for interaction
 * @author prashan
 *
 */

public class Enemy {

	public Point position;
	private Point vector;
	private double dAngle;
	private Image img;
	private float maxHP;
	public float currentHP;
	private Color hpbar  = new Color(Color.black);
	private float speed = .5f;
	private int mvtimer = 0;
	private Random r = new Random();
	private static int XLOWERBOUND = 100;
	private static int XUPPERBOUND = BulletHellGame.WIDTH-XLOWERBOUND;
	private static int YLOWERBOUND = 50;
	private static int YUPPERBOUND = 200;
	private Image outline = GameplayState.images[14];

	/**
	 * Constructor
	 * @param position - starting position
	 * @param img - sprite
	 * @param hp - starting hp
	 */
	public Enemy(Point position, Image img, float hp){
		this.position = position;
		this.img = img;
		maxHP = hp;
		currentHP = maxHP;
		setHPBarColor();
		rollNewDirection(0, 360);
	}

	/**
	 * Update the position of the patterns to match the enemy's movement
	 * @param bp
	 */
	public void updatePos(ArrayList<Pattern> bp){
		for(Pattern p : bp){
			p.setPosition(position);
		}
	}
	
	/**
	 * Randomly chooses a direction between min and max degrees
	 * @param min - minimum angle
	 * @param max - max angle
	 */
	public void rollNewDirection(double min, double max){
		dAngle = min+r.nextDouble()*(max-min);
		vector = new Point(0,1).rotate(dAngle);
		mvtimer = 1000 + r.nextInt(4000); 
	}

	/**
	 * Updates position and other game logic related to the enemy
	 * @param delta - time in ms since last call
	 */
	public void update(int delta){
		mvtimer -=delta;
		position = position.addVector(vector.mult(speed));
		if( mvtimer <= 0){
			rollNewDirection(0, 360);
		}else if(position.x <= XLOWERBOUND){
			rollNewDirection(225, 315);
		}else if(position.x >= XUPPERBOUND){
			rollNewDirection(45, 135);
		}else if(position.y <= YLOWERBOUND){
			rollNewDirection(315, 405);
		}else if(position.y >= YUPPERBOUND){
			rollNewDirection(135, 225);
		}
	}

	/**
	 * Draws the enemy to the screen
	 * @param g - the Graphics object
	 */
	public void draw(Graphics g){
		g.drawImage(img, (int)position.x-img.getWidth()/2, (int)position.y-img.getHeight()/2);
		g.setColor(Color.cyan);

		//Hitboxes
//				g.drawRect((float) position.x-50, (float) position.y-50, 100, 75);
//				g.drawRect((float) position.x-100, (float) position.y-25, 50, 75);
//				g.drawRect((float) position.x+50, (float) position.y-25, 50, 75);
	}

	/**
	 * Draws the enemy's hp bar on the side of the screen
	 * @param g - Graphics object
	 */
	public void drawHPBar(Graphics g){
		g.setColor(hpbar);
		g.fillRect(BulletHellGame.WIDTH+15, 50, 150*(currentHP/maxHP), 13);
		g.drawString("BOSS HP: "+(int)currentHP+"", BulletHellGame.WIDTH+15, 65);
		g.drawImage(outline, BulletHellGame.WIDTH+11, 46);
	}

	/**
	 * Sets the color of the HP bar depending on % current health
	 */
	public void setHPBarColor(){
		if(currentHP>(.75*maxHP))
			hpbar = Color.green;
		else if(currentHP>(.25*maxHP))
			hpbar = Color.yellow;
		else
			hpbar = Color.red;
	}

	/**
	 * inflict damage to the enemy
	 */
	public void takeDamage(){
		currentHP-=15;
		setHPBarColor();
	}

}
