package game;
import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

import bullet.Bullet;


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

	public int powerUpTimer = 0;
	public int currPowerUp = -1;
	public boolean dd = false;
	public boolean invul = false;

	public Player(Point p){
		position = p;
		img = GameplayState.images[0];
		hitbox = GameplayState.images[8];
		bulletImg = GameplayState.images[9];
		time = System.currentTimeMillis();
	}

	public void checkPowerUps(int delta){
		System.out.println(currPowerUp+", "+powerUpTimer);
		if(currPowerUp >= 0){
			if(powerUpTimer>0){
				switch(currPowerUp){
				case 0: dd = true;break;
				case 1: invul = true;break;
				}
				powerUpTimer-=delta;
			}
			else{
				turnOffPowerUps();
			}
		}
	}

	public void turnOffPowerUps(){
		dd = false;
		invul = false;
		currPowerUp = -1;
	}

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

	public void setSpeed(float s){
		speed = s;
	}

	public void drawShip(Graphics g){
		g.drawImage(img, (float)position.x-img.getWidth()/2, (float)position.y-img.getHeight()/2);
	}

	public void drawHitBox(Graphics g){
		g.drawImage(hitbox, (float)position.x-hitbox.getWidth()/2, (float)position.y-hitbox.getHeight()/2);
	}

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
