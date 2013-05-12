package bullet;

import game.GameplayState;
import game.Player;
import game.Point;

import java.util.Random;

import org.newdawn.slick.Image;


public class PowerUp extends Bullet{

	public static int DOUBLEDAMAGE = 0;
	public static int INVULNERABILITY = 1;
	public static int TIMEWARP = 2;
	public static int NUMPOWERUPS = 3;

	private Image[] sprites = {GameplayState.images[12],GameplayState.images[13], GameplayState.images[15] };
	private Random r = new Random();
	private int powerUpNum = 0;

	public PowerUp(Point p, Point vector, float speed) {
		this.position = p;
		this.speed = speed;
		this.vector = vector;
		powerUpNum = r.nextInt(NUMPOWERUPS);
		this.img = sprites[powerUpNum];
		this.radius = img.getHeight()/2;
	}

	public void applyPowerUp(Player p){
		p.turnOffPowerUps();		//Reset powerups
		p.currPowerUp = powerUpNum;
		switch(powerUpNum){
		case 0 : p.powerUpTimer = 20000;break;
		case 1 : p.powerUpTimer = 10000;break;
		case 2 : p.powerUpTimer = 20000;break; 
		}
	}
}
