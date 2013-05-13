package game;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class RenderObjectBeam implements RenderObject{

	private Color color;
	private Point position;
	private int angle=0;

	public RenderObjectBeam(Point p, Color c){
		position = p;
		color = c;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		 
	}
}
