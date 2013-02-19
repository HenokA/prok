import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;


public class Enemy {

	public Point position;
	private Pattern[] patterns;
	private Point vector;
	private double dAngle;
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
	private static int YUPPERBOUND = 200;

	public Enemy(Point position, Pattern[] p, Point[] patternPos, Image img){
		this.position = position;
		this.patterns = p;
		for(int i=0; i<patterns.length; i++){
			patterns[i].setPosition(patternPos[i]);
		}
		this.img = img;
		setHPBarColor();
		rollNewDirection(0, 360);
	}

	public void updatePatternPos(ArrayList<Pattern> bp){
		for(Pattern p : bp){
			p.setPosition(position);
		}
	}

	public void addPatterns(ArrayList<Pattern> bp){
		for(int i=0; i<patterns.length; i++){
			bp.add(patterns[i]);
		}
	}
	
	/**
	 * Randomly chooses a direction between min and max degrees
	 * @param min
	 * @param max
	 */
	public void rollNewDirection(double min, double max){
		dAngle = min+r.nextDouble()*(max-min);
		vector = new Point(0,1).rotate(dAngle);
		mvtimer = 1000 + r.nextInt(4000); 
	}

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
