import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;


public class Enemy {

	public Point position;
	private BulletPattern[] patterns;
	private Point[] patternPos;
	private Point vector;
	private Image img;
	private float maxHP = 1000;
	public float currentHP = maxHP;
	private Color hpbar  = new Color(Color.black);
	private float speed = .5f;
	private int mvtimer = 0;
	private Random r = new Random();
	private static int XLOWERBOUND = 100;
	private static int XUPPERBOUND = 300;
	private static int YLOWERBOUND = 50;
	private static int YUPPERBOUND = 300;

	public Enemy(Point position, BulletPattern[] p, Point[] patternPos, Image img){
		this.position = position;
		this.patterns = p;
		this.patternPos = patternPos;
		updatePatternPos();
		this.img = img;
		setHPBarColor();
		rollNewDirection();
	}

	public void updatePatternPos(){
		for(int i=0; i<patterns.length; i++){
			patterns[i].setPosition(patternPos[i]);
		}
	}

	public void updatePatternPos(ArrayList<BulletPattern> bp){
		for(BulletPattern p : bp){
			p.setPosition(position);
		}
	}

	public void addPatterns(ArrayList<BulletPattern> bp){
		for(int i=0; i<patterns.length; i++){
			bp.add(patterns[i]);
		}
	}

	public void rollNewDirection(){
		vector = new Point(0,1).rotate(r.nextDouble()*360.0);
		mvtimer = 1000 + r.nextInt(4000); 
	}

	public void update(int delta){
		mvtimer -=delta;
		position = position.addVector(vector.mult(speed));

		if( mvtimer <= 0 || position.x < XLOWERBOUND || position.x > XUPPERBOUND || 
				position.y < YLOWERBOUND || position.y > YUPPERBOUND){
			rollNewDirection();
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
