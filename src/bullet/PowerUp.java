package bullet;

import game.GameplayState;
import game.Player;
import game.Point;

import java.util.Random;

import org.newdawn.slick.Image;


public class PowerUp extends Bullet{

	public static int DOUBLEDAMAGE = 0;
	public static int INVULNERABILITY = 1;
	public static int NUMPOWERUPS = 2;

	private Image[] sprites = {GameplayState.images[10],GameplayState.images[10] };
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
		p.currPowerUp = powerUpNum;
		switch(powerUpNum){
		case 0 : p.powerUpTimer = 30000;
		case 1 : p.powerUpTimer = 5000;
		}
	}
}
