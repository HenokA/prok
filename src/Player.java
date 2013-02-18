import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;


public class Player {

	public Point position;
	private Image img;
	private Image hitbox;
	private Image bulletImg;
	private long time;
	private long delay=100;

	public Player(Point p){
		position = p;
		img = GameplayState.images[0];
		hitbox = GameplayState.images[8];
		bulletImg = GameplayState.images[2];
		time = System.currentTimeMillis();
	}

	public void drawShip(Graphics g){
		g.drawImage(img, (float)position.x-img.getWidth()/2, (float)position.y-img.getHeight()/2);
	}
	
	public void drawHitBox(Graphics g){
		g.drawImage(hitbox, (float)position.x-hitbox.getWidth()/2, (float)position.y-hitbox.getHeight()/2);
	}

	public void shoot(ArrayList<Bullet> bullets){
		if(System.currentTimeMillis() -time > delay){
			bullets.add(new Bullet(position, new Point(0,-1), bulletImg, 7));
			time = System.currentTimeMillis();
		}
	}
}
