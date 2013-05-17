package game;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

public class RenderObjectBeam implements RenderObject{

	private Color color;
	private Point position;
	private double angle=0;
	private boolean render =false;
	private boolean beam = false;
	private Animation center;

	public RenderObjectBeam(Point p, Color c, Animation img){
		position = p;
		color = c;
		center = img;
		center.start();
	}

	public void increaseAngle(double d) {
		this.angle += d;
	}

	public void setRender(boolean r){
		render = r;
	}

	public void setBeam(boolean b){
		beam = b;
	}

	@Override
	public void draw(Graphics g) {
		if(render){
			if(beam){
				Rectangle laser1 = new Rectangle((float)position.x-600, (float)position.y-20, 1200f, 40f);
				Rectangle beam1 = new Rectangle((float)position.x-600, (float)position.y-10, 1200f, 20f);
				Shape rotatedLaser1 = laser1.transform(Transform.createRotateTransform(
						(float)Math.toRadians(angle), (float) position.x, (float) position.y));		
				Shape rotatedBeam1 = beam1.transform(Transform.createRotateTransform(
						(float)Math.toRadians(angle), (float) position.x, (float) position.y));
				g.setColor(color);
				g.fill(rotatedLaser1);
				g.setColor(Color.white);
				g.fill(rotatedBeam1);

				Rectangle laser2 = new Rectangle((float)position.x-20, (float)position.y-600, 40f, 1200f);
				Rectangle beam2 = new Rectangle((float)position.x-10, (float)position.y-600, 20f, 1200f);
				Shape rotatedLaser2 = laser2.transform(Transform.createRotateTransform(
						(float)Math.toRadians(angle), (float) position.x, (float) position.y));		
				Shape rotatedBeam2 = beam2.transform(Transform.createRotateTransform(
						(float)Math.toRadians(angle), (float) position.x, (float) position.y));
				g.setColor(color);
				g.fill(rotatedLaser2);
				g.setColor(Color.white);
				g.fill(rotatedBeam2);
			}else{
				Rectangle laser1 = new Rectangle((float)position.x-600, (float)position.y-2.5f, 1200f, 5f);
				Shape rotatedLaser1 = laser1.transform(Transform.createRotateTransform(
						(float)Math.toRadians(angle), (float) position.x, (float) position.y));		
				g.setColor(color);
				g.draw(rotatedLaser1);

				Rectangle laser2 = new Rectangle((float)position.x-2.5f, (float)position.y-600, 5f, 1200f);
				Shape rotatedLaser2 = laser2.transform(Transform.createRotateTransform(
						(float)Math.toRadians(angle), (float) position.x, (float) position.y));		
				g.draw(rotatedLaser2);
			}
			//g.drawImage(center, (float) position.x-center.getWidth()/2, (float) position.y-center.getHeight()/2);
			center.draw((float) position.x-center.getWidth()/2, (float) position.y-center.getHeight()/2);
		}
	}
}
