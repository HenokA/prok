package game;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * RenderObject for the little explosions when the bullets are destroyed
 * @author prashan
 *
 */

public class RenderObjectExplosion implements RenderObject{

	public Animation exp;
	Color col;
	Point pos;
	
	/**
	 * Constructor
	 * Sets color based on bullet image
	 * @param pos
	 * @param img
	 */
	public RenderObjectExplosion(Point pos, Image img){
		this.pos = pos;
		exp = new Animation(new Image[]{GameplayState.images[28],
				GameplayState.images[27], GameplayState.images[26]}, 100);
		
		// Set color based on what image the bullet is #sodirty
		this.col = Color.white;
		if(img == GameplayState.images[1])
			col = Color.green;
		else if(img == GameplayState.images[2])
			col = Color.blue;
		else if(img == GameplayState.images[3])
			col = Color.red;
		else if(img == GameplayState.images[5])
			col = Color.orange;
		else if(img == GameplayState.images[6])
			col = new Color(144, 0, 255);
		else if(img == GameplayState.images[10])
			col = Color.blue;
		else if(img == GameplayState.images[11])
			col = new Color(255, 20, 147);
		
		exp.start();
		exp.stopAt(2);	//stop the animation when it reaches the last frame
	}
	
	/**
	 * Draw the animation
	 */
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		exp.draw((float)pos.x-exp.getWidth()/2, (float)pos.y-exp.getHeight()/2, col);
	}

}
