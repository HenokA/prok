package pattern;

import game.GameplayState;
import game.Point;
import game.RenderObjectBeam;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

import bullet.Bullet;
import bullet.BulletBeamHitbox;

public class PatternDiamondBeam implements Pattern{

	private boolean created=false;
	private boolean started=false;
	private Point position;
	private Point[] initial = {new Point(50, 100),
			new Point(350, 100),
			new Point(350, 300),
			new Point(50, 300)};
	private double[] directions = {.1, -.1, -.1, .1};
	private int timer=3000;
	private double beamTimer=30000;
	private Animation beamImg;
	private Image[] frames = {GameplayState.images[19], GameplayState.images[18],GameplayState.images[17]};
	private Image hitboxImg = GameplayState.images[11];
	private ArrayList<BulletBeamHitbox> hitboxes = new ArrayList<BulletBeamHitbox>();
	private RenderObjectBeam[] ro;

	public PatternDiamondBeam(Point position){
		this.position = position;
		ro = new RenderObjectBeam[4];
		beamImg = new Animation(frames, 100);
		for(int i=0; i<initial.length; i++){
			ro[i] = new RenderObjectBeam(initial[i], new Color(20, 193 , 255), beamImg);
			ro[i].increaseAngle(45);
		}
	}

	@Override
	public void update(ArrayList<Bullet> bullets, int delta) {
		// TODO Auto-generated method stub
		if(timer>0)
			timer-=delta;
		if(!started){
			for(int k=0; k< initial.length; k++){
		//		startBullet[k] = new Bullet(initial[k], new Point(0,0), frames[3], 0);
		//		bullets.add(startBullet[k]);
				GameplayState.renderObjs.add(ro[k]);
				ro[k].setRender(true);
				if(k==3)
					started=true;
			}
		}
		for(int k=0; k<initial.length; k++){	
			if(!created && timer <=0){
				for(int i=-2; i<3; i+=4){
					for(int j=0; j<120; j++){
						BulletBeamHitbox currhb1 = new BulletBeamHitbox(new Point(initial[k].x+i*5, initial[k].y+j*5), hitboxImg, k);
						BulletBeamHitbox currhb2 = new BulletBeamHitbox(new Point(initial[k].x+i*5, initial[k].y-j*5), hitboxImg, k);
						BulletBeamHitbox currhb3 = new BulletBeamHitbox(new Point(initial[k].x+j*5, initial[k].y+i*5), hitboxImg, k);
						BulletBeamHitbox currhb4 = new BulletBeamHitbox(new Point(initial[k].x-j*5, initial[k].y+i*5), hitboxImg, k);
						currhb1.rotate(initial[k], null, 45);
						currhb2.rotate(initial[k], null, 45);
						currhb3.rotate(initial[k], null, 45);
						currhb4.rotate(initial[k], null, 45);
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
				ro[k].setBeam(true);
				if(k==3)
					created = true;
			}
			else{
				if(created)
					if(beamTimer>0){
						for(BulletBeamHitbox hb : hitboxes){	
							if(hb.getBeamId()==k)
								hb.rotate(initial[k], position, directions[k]);	
						}
						beamTimer-=delta;
						ro[k].increaseAngle(directions[k]);
					}
					else{
						for(BulletBeamHitbox hb : hitboxes){
							GameplayState.bulletsToBeRemoved.add(hb);
						}
						for(int c=0; c<4; c++){
							GameplayState.renderObjs.remove(ro[c]);
							ro[c] = new RenderObjectBeam(initial[c], new Color(20, 193 , 255), beamImg);
							ro[c].increaseAngle(45);
						}
						hitboxes.clear();
						timer = 3000;
						beamTimer = 30000;
						created = false;
						started = false;
					}
			}
		}
	}

	@Override
	public void setPosition(Point position) {
		// TODO Auto-generated method stub
		this.position = position;
	}

}
