package ability;

import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.Graphics;

import game.GameplayState;
import game.Point;

public class AbilityLockOnMissiles implements Ability {

	private static int ABILITYID = 0;
	private int NUMMISSILES = 10;
	private int fireRate = 250;
	private int timer=0;
	private ArrayList<Missile> missiles = new ArrayList<Missile>();
	private Point position;

	public AbilityLockOnMissiles(Point position){
		this.position = position;
	}


	public boolean update(int delta) {
		if(NUMMISSILES > 0){
			timer+= delta;
			if(timer > fireRate){
				Point p = GameplayState.player.position;
				Point v = new Point(p.x-position.x, p.y-position.y);
				missiles.add(new Missile(position, v, 3f, -.5f ));
				timer = 0;
				NUMMISSILES -= 1;
			}
		}
		Iterator<Missile> i = missiles.iterator();
		while(i.hasNext()){
			Missile m = i.next();
			if(!m.update(missiles, delta))
				i.remove();
		}
		if(NUMMISSILES > 0){
			return true;
		}else{
			return !missiles.isEmpty();
		}

	}


	public void setPosition(Point position) {
		// TODO Auto-generated method stub
		this.position = position;
	}


	public int getAbilityID() {
		// TODO Auto-generated method stub
		return ABILITYID;
	}


	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		for(Missile m : missiles){
			m.draw(g);
		}
	}

}
