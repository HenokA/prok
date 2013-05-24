package pattern;

import game.GameplayState;
import game.Point;
import game.RenderObjectBeam;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

import bullet.Bullet;
import bullet.BulletBeamHitbox;

public class PatternTrackingBeam implements Pattern{
/**
 * Creates a laser pattern that follows the player, for example if they use invincibility and go through the laser will change directions
 */
	private boolean created=false;
	private boolean started=false;
	private Point position;
	private Point initial;
	private int timer=3000;
	private double beamTimer=15000;
	private Animation beamImg;
	private Image[] frames = {GameplayState.images[22],GameplayState.images[21],GameplayState.images[20]};
	private Image hitboxImg = GameplayState.images[11];
	private ArrayList<BulletBeamHitbox> hitboxes = new ArrayList<BulletBeamHitbox>();
	private RenderObjectBeam ro;
/**
 * Constructor
 * @param position
 */
	public PatternTrackingBeam(Point position){
		this.position = position;
		initial = position;
		beamImg = new Animation(frames, 100);
	}
/**
 * Updates pattern and bullet rendering based on a delay
 */
	@Override
	public void update(ArrayList<Bullet> bullets, int delta) {
		// TODO Auto-generated method stub
		if(timer>0)
			timer-=delta;
		if(!started){
			initial = position;
			ro = new RenderObjectBeam(initial, new Color(255, 20 , 147), beamImg);
			GameplayState.renderObjs.add(ro);
			ro.setRender(true);
			started=true;
		}
		if(!created && timer <=0){
			for(int i=-2; i<3; i+=4){
				for(int j=0; j<120; j++){
					BulletBeamHitbox currhb1 = new BulletBeamHitbox(new Point(initial.x+i*5, initial.y+j*5), hitboxImg);
					BulletBeamHitbox currhb2 = new BulletBeamHitbox(new Point(initial.x+i*5, initial.y-j*5), hitboxImg);
					BulletBeamHitbox currhb3 = new BulletBeamHitbox(new Point(initial.x+j*5, initial.y+i*5), hitboxImg);
					BulletBeamHitbox currhb4 = new BulletBeamHitbox(new Point(initial.x-j*5, initial.y+i*5), hitboxImg);
					bullets.add(currhb1);
					hitboxes.add(currhb1);
					bullets.add(currhb2);
					hitboxes.add(currhb2);
					bullets.add(currhb3);
					hitboxes.add(currhb3);
					bullets.add(currhb4);
					hitboxes.add(currhb4);
				}
			}
			ro.setBeam(true);
			created = true;
		}
		else{
			if(created)
				if(beamTimer>0){
					double a = GameplayState.player.position.x < initial.x ? .1 : -.1;
					for(BulletBeamHitbox hb : hitboxes){						
						hb.rotate(initial, position, a);	
					}
					beamTimer-=delta;
					ro.increaseAngle(a);
				}
				else{
					for(BulletBeamHitbox hb : hitboxes){
						GameplayState.bulletsToBeRemoved.add(hb);
					}
					hitboxes.clear();
					timer = 3000;
					beamTimer = 15000;
					created = false;
					started = false;
					ro.setRender(false);
				}
		}
	}
/**
 * Sets position
 */
	@Override
	public void setPosition(Point position) {
		// TODO Auto-generated method stub
		this.position = position;
	}

}
