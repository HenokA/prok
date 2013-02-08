import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;


public class Enemy {

	public Point position;
	private BulletPattern[] patterns;
	private Point[] patternPos;
	private Point destination;
	private Point vector;
	private Image img;
	private float maxHP = 1000;
	public float currentHP = maxHP;
	private Color hpbar  = new Color(Color.black);

	public Enemy(Point position, BulletPattern[] p, Point[] patternPos, Image img){
		this.position = position;
		this.patterns = p;
		this.patternPos = patternPos;
		updatePatternPos();
		this.img = img;
		setHPBarColor();
	}

	public void updatePatternPos(){
		for(int i=0; i<patterns.length; i++){
			patterns[i].setPosition(patternPos[i]);
		}
	}

	public void addPatterns(ArrayList<BulletPattern> bp){
		for(int i=0; i<patterns.length; i++){
			bp.add(patterns[i]);
		}
	}

	public void draw(Graphics g){
		g.drawImage(img, (float)position.x-img.getWidth()/2, (float)position.y-img.getHeight()/2);
		g.setColor(Color.cyan);
		
		//Hitboxes
//		g.drawRect((float) position.x-50, (float) position.y-50, 100, 75);
//		g.drawRect((float) position.x-100, (float) position.y-25, 50, 75);
//		g.drawRect((float) position.x+50, (float) position.y-25, 50, 75);
	}

	public void drawHPBar(Graphics g){
		g.setColor(hpbar);
		g.fillRect(100, 50, 150*(currentHP/maxHP), 10);
		g.drawString((int)currentHP+"", 20, 20);
	}

	public void setHPBarColor(){
		if(currentHP>(.75*maxHP))
			hpbar = Color.green;
		else if(currentHP>(.25*maxHP))
			hpbar = Color.yellow;
		else
			hpbar = Color.red;
	}
	
	public void takeDamage(){
		currentHP-=5;
		setHPBarColor();
	}

}
