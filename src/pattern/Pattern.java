package pattern;
import game.Point;

import java.util.ArrayList;

import org.newdawn.slick.Image;

import bullet.Bullet;

/**
 * Interface for Bullet Patterns
 * Used as a template for creating new patterns
 * 
 */


public interface Pattern {

	public void update(ArrayList<Bullet> bullets, int delta);
	
	public void setPosition(Point position);
	
	public int getPatternID();
}
