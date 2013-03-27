package ability;

import org.newdawn.slick.Graphics;

import game.Point;

public interface Ability {

	public void update(int delta);
	
	public void setPosition(Point position);
	
	public int getAbilityID();
	
	public void draw(Graphics g);
}
