package game;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import pattern.Pattern;
import pattern.PatternBigExplodingCircle;
import pattern.PatternCircle;
import pattern.PatternConstantHomingLine;
import pattern.PatternConstantHomingWide;
import pattern.PatternCurve;
import pattern.PatternDiamondBeam;
import pattern.PatternDoubleCurve;
import pattern.PatternDoubleSinCurve;
import pattern.PatternInitialHomingLine;
import pattern.PatternInitialHomingWide;
import pattern.PatternQuadSpiral;
import pattern.PatternReverseCurve;
import pattern.PatternReverseSinCurve;
import pattern.PatternRotatingBeam;
import pattern.PatternSinCircle;
import pattern.PatternSinCurve;
import pattern.PatternSpiral;
import pattern.PatternTrackingBeam;
import bullet.Bullet;
import bullet.BulletBeamHitbox;
import bullet.PowerUp;
/**
 * This class is the game play class where the actual game is played.
 * @author 912606
 *
 */

public class GameplayState extends BasicGameState {

	public static  ArrayList<Bullet> bulletsToBeAdded;
	public static  ArrayList<Bullet> bulletsToBeRemoved;
	public static ArrayList<RenderObject> renderObjs;
	ArrayList<Bullet> ebullets;
	ArrayList<Bullet> pbullets;
	ArrayList<Pattern> patterns;
	ArrayList<Double> highscores;
	public static Player player;
	double score = 0;
	double multiplier = 1;
	public static Image[] images;
	public static float BULLETSPEED = 1f;
	public static float BULLETRATE = 2f;
	int[] levelUps = {3, 6, 10, 15, 21, 27, 34};
	//int[] enemyHP = {1000, 1000, 1500, 2000, 2500, 3000, 3500};
	int level = 0, lvlIndex = 0;
	int nextTier = levelUps[lvlIndex];
	int plvl = 6;
	Enemy enemy;
	boolean paused;
	int invulDisplayTimer =0;
	Point invulDisplayPoint;
	int shieldDisplayTimer =0;
	Point shieldDisplayPoint;
	int grazeDisplayTimer = 0;
	Point grazeDisplayPoint;
	int grazeBonus = 0;
	float totalGraze=0;
	int respawnTimer = 0;
	int stateID = -1;
	Image resumeOption = null;
	Image exitOption = null;
	Image menuOption = null;
	float scaleStep = 0.0001f;
	float resumeScale = 1;
	float menuScale = 1;
	float exitScale = 1;
	int resumeX=70;
	int resumeY=170;
	int menuX=100;
	int menuY=220;
	public static boolean dead;
	int deathTimer = 0;
	double deadx;
	double deady;
	public static Object deadBullet;
	public boolean grazeMove=false;
	BufferedWriter out;
	Sound fx=null;
	int enemieskilled =0;
	boolean instate=false;
	
	/**
	 * Code when the state is entered
	 */
	public void enter(GameContainer container, StateBasedGame sbg){
		instate = true;
		container.getInput().clearKeyPressedRecord();
	}
	
	/**
	 * Code to run when the state is left
	 */
	public void leave(GameContainer container, StateBasedGame sbg){
		instate = false;
		container.getInput().clearKeyPressedRecord();
	}

	/**
	 * Constructor
	 * @param stateID - state id
	 */
	public GameplayState( int stateID ) 
	{
		this.stateID = stateID;
	}

	/**
	 * Loads images into the images array.  
	 */
	public void loadImages(){
		String[] files = {"ship2.png", "BulletGreen.png", "BulletBlue.png", "BulletRed.png", 
				"finalship2.png", "BulletOrange.png","BulletPurple.png", "gameplaybg.png", "PDot.png",
				"PlayerBullet.png", "BulletBigBlue.png", "BulletPink.png", "PowerUpDD.png", "PowerUpInvul.png",
				"HPBarOutline.png", "PowerUpTWarp.png", "BulletCyan.png", 
				"LaserCenterLightBlue.png","LaserCenterLightBlue1.png", "LaserCenterLightBlue2.png",
				"LaserCenterPink.png", "LaserCenterPink1.png", "LaserCenterPink2.png",
				"LaserCenterPurple.png", "LaserCenterPurple1.png", "LaserCenterPurple2.png",
				"Explosion.png", "Explosion1.png", "Explosion2.png"
		};
		images = new Image[files.length];
		try {
			for(int i=0; i<files.length; i++){
				images[i] = new Image("assets/"+files[i]);
			}
			Image resumeOptions = new Image("assets/resume.png");
			Image exitOptions = new Image("assets/exit1.png");
			Image menuOptions = new Image("assets/menu.png");
			resumeOption = resumeOptions.getSubImage(0, 0, 288, 72);
			menuOption = menuOptions.getSubImage(0, 0, 288, 72);
			exitOption = exitOptions.getSubImage(0, 0, 89, 29);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * resets everything to make it a new game when we click start game or play again
	 * @throws SlickException 
	 */
	public void newGame() throws SlickException{
		player = new Player(new Point(200,500));
		ebullets = new ArrayList<Bullet>();
		bulletsToBeAdded = new ArrayList<Bullet>();
		bulletsToBeRemoved = new ArrayList<Bullet>();
		renderObjs = new ArrayList<RenderObject>();
		pbullets = new ArrayList<Bullet>();
		patterns = new ArrayList<Pattern>();
		createEnemy();
		paused = false;
		dead = false;
		score=0;
		multiplier=1;
		level=0;
		lvlIndex=0;
		BULLETSPEED = 1f;
		BULLETRATE = 2f;
		grazeDisplayTimer = 0;
		invulDisplayTimer = 0;
		level=0;
		lvlIndex=0;
		nextTier = levelUps[lvlIndex];
		plvl=6;
		enemieskilled=0;
		//fx.play();
	}
	
	/**
	 * run at the beginning of the program to instantiate everything
	 */
	public void init(GameContainer container, StateBasedGame sbg ) throws SlickException {
		container.setVSync(true);
		container.setTargetFrameRate(60);
		loadImages(); // loads images from assets
		score=0;
		//fx=new Sound("assets/GameMusic.wav");
		//fx.loop();
	}

	/**
	 * Creates an enemy object.  Once we have more content, this should become randomized.
	 */
	public void createEnemy(){
		Random r = new Random();
		ArrayList<Integer> patternIds = new ArrayList<Integer>();
		int pid =  -1;
		Point enemyxy = new Point(200, 200);
		
		/**
		 * How patterns are chosen: depending on difficulty (plvl), 
		 * 3 patterns are randomly chosen and added to the patterns ArrayList
		 */
		for(int i=0; i<3; i++){
			patternIds.add(pid);
			while(patternIds.contains(pid))
				pid = r.nextInt(plvl);
			switch(pid){

			//easy
			case 0 : patterns.add(new PatternCircle(enemyxy)); break;
			case 1 : patterns.add(new PatternSpiral(enemyxy)); break;
			case 2 : patterns.add(new PatternCurve(enemyxy)); break;
			case 3 : patterns.add(new PatternReverseCurve(enemyxy)); break;
			case 4 : patterns.add(new PatternDoubleCurve(enemyxy)); break;
			case 5 : patterns.add(new PatternBigExplodingCircle(enemyxy)); break;

			//medium
			case 6 : patterns.add(new PatternSinCurve(enemyxy)); break;
			case 7 : patterns.add(new PatternReverseSinCurve(enemyxy)); break;
			case 8 : patterns.add(new PatternDoubleSinCurve(enemyxy)); break;
			case 9 : patterns.add(new PatternSinCircle(enemyxy)); break;
			case 10: patterns.add(new PatternQuadSpiral(enemyxy)); break;
			case 11: patterns.clear();
			patterns.add(new PatternDiamondBeam(enemyxy));
			i=3;break;
			case 12: patterns.clear();
			patterns.add(new PatternDoubleSinCurve(enemyxy)); 
			patterns.add(new PatternDoubleSinCurve(enemyxy)); 
			patterns.add(new PatternDoubleSinCurve(enemyxy)); 
			i=3;break;

			//hard
			case 13: patterns.add(new PatternInitialHomingWide(enemyxy)); break;
			case 14: patterns.add(new PatternInitialHomingLine(enemyxy)); break;
			case 15: patterns.add(new PatternRotatingBeam(enemyxy)); break;
			case 16: patterns.add(new PatternTrackingBeam(enemyxy)); break;
			case 17: patterns.add(new PatternConstantHomingLine(enemyxy)); break;
			case 18: patterns.add(new PatternConstantHomingWide(enemyxy)); break;
			}
		}
		enemy = new Enemy( enemyxy, images[4], 1500f);
	}
	
	/**
	 * Allows the program to increase in difficulty by increasing bullet speed
	 */
	public void levelUp(){
		level+=1;
		if(level > nextTier){
			lvlIndex++;
			if(lvlIndex==1)
				plvl=13;
			else if(lvlIndex==2)
				plvl=17;
			else if(plvl>2){
				BULLETSPEED += .1; //increases bullet speed across screen
				BULLETRATE += .1; //increases bullet rate
			}
			nextTier = levelUps[lvlIndex];
		}
	}

	/**
	 * A continously updated method that helps smoothly run the program
	 */
	public void update(GameContainer container, StateBasedGame sbg, int delta) { 
		Input input = container.getInput();
		if (container.getInput().isKeyPressed(Input.KEY_P)) {paused=!paused;}
		if (container.getInput().isKeyPressed(Input.KEY_ESCAPE)) {System.exit(0);}
		if(dead){
			deathTimer -= delta;
			if(deathTimer <= 0){
				GameOverState.setCheckScore(true);
				totalGraze=0;
				level=0;
				lvlIndex=0;
				plvl=6;
				sbg.enterState(BulletHellGame.GAMEOVERSTATE, new FadeOutTransition(Color.black, 100),new FadeInTransition(Color.black, 100));
				//fx.stop();
			}
		}
		//If paused, draw the pause menu
		if(paused){
			int mouseX = input.getMouseX();
			int mouseY = input.getMouseY();

			boolean insideResume = false;
			boolean insideMenu = false;

			if( ( mouseX >= resumeX && mouseX <= resumeX + resumeOption.getWidth()) &&
					( mouseY >= resumeY && mouseY <= resumeY + resumeOption.getHeight()) ){
				insideResume = true;} //if inside the resume icon
			else if( ( mouseX >= menuX && mouseX <= menuX + menuOption.getWidth()) &&
					( mouseY >= menuY && mouseY <= menuY + menuOption.getHeight()) ){
				insideMenu = true;
			} //if inside the menu icon

			if(insideResume){
				if(resumeScale < 1.05f)
					resumeScale += scaleStep * delta; //increasing the scale of the icon

				if ( input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) ){
					sbg.enterState(BulletHellGame.GAMEPLAYSTATE, new FadeOutTransition(Color.black,200), new FadeInTransition(Color.black,200));
					paused=false;

				}
			}else {
				if(resumeScale > 1.0f)
					resumeScale -= scaleStep * delta;
			}//decreasing the scale of the icon

			if(insideMenu){
				if(menuScale < 1.05f)
					menuScale += scaleStep * delta;

				if ( input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) ){
					sbg.enterState(BulletHellGame.MAINMENUSTATE, new FadeOutTransition(Color.black,400), new FadeInTransition(Color.black,400));
				}
			} else {
				if(menuScale > 1.0f)
					menuScale -= scaleStep * delta;
			}

		}
		//If not paused, update the game
		if(!paused && !dead){		
			if (container.getInput().isKeyDown(Input.KEY_LSHIFT)){player.setSpeed(1f);}
			if (container.getInput().isKeyDown(Input.KEY_LEFT) && player.position.x>0) {player.increment(Player.LEFT);} // move player left
			if (container.getInput().isKeyDown(Input.KEY_RIGHT) && player.position.x<BulletHellGame.WIDTH) {player.increment(Player.RIGHT);} //move player right
			if (container.getInput().isKeyDown(Input.KEY_UP) && player.position.y>0) {player.increment(Player.UP);} //move player up
			if (container.getInput().isKeyDown(Input.KEY_DOWN) && player.position.y<BulletHellGame.HEIGHT) {player.increment(Player.DOWN);} //move player down
			if (container.getInput().isKeyDown(Input.KEY_SPACE)) {player.shoot(pbullets);}
			if (container.getInput().isKeyDown(Input.KEY_Z )) {player.grazeMove(grazeMove); if(grazeMove){grazeMove=false; totalGraze=0;}}
			player.setSpeed(3);
			player.checkPowerUps(delta);

			//Update display timers
			if(shieldDisplayTimer > 0)
				shieldDisplayTimer -=delta;
			if(invulDisplayTimer > 0)
				invulDisplayTimer -=delta;
			if(grazeDisplayTimer > 0)
				grazeDisplayTimer -=delta;
			if(grazeDisplayTimer <= 0)
				grazeBonus = 0;
			
			//Check enemy respawn
			if(enemy == null){
				if(respawnTimer <= 0){
					createEnemy(); //creates enemy
				}
				else
					respawnTimer -= delta;
			}

			//Update the enemy
			if(enemy != null){
				enemy.update(delta);
				enemy.updatePos(patterns);
			}

			//Update patterns
			for(Pattern p : patterns){
				p.update(ebullets, delta);
			}

			/*
			 * Update bullets:
			 * Remove bullets that have been destroyed
			 * Add new bullets that have been created
			 * Update positions
			 * Check for collision
			 */
			ebullets.removeAll(bulletsToBeRemoved);
			bulletsToBeRemoved.clear();
			ebullets.addAll(bulletsToBeAdded);
			bulletsToBeAdded.clear();
			Iterator<Bullet> i = ebullets.iterator(); 
			while(i.hasNext()) {
				Bullet bullet = i.next();
				if(bullet instanceof PowerUp){
					if(bullet.checkCollision(player.position)){
						((PowerUp) bullet).applyPowerUp(player);
						i.remove();
					}
				}
				else{
					if(bullet.checkCollision(player.position)){
						if(!player.invul && !player.shield){
							dead = true;
							deadBullet=bullet;
							try {
								out = new BufferedWriter(new FileWriter("assets/CurrentScore"));
								out.write(Integer.toString((int)(score)));
								out.close(); //writes score to the current score file
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}else if(player.invul){
							invulDisplayTimer = 500;
							invulDisplayPoint = player.position.addVector(new Point(5, 10));
						}else if(player.shield){
							renderObjs.add(new RenderObjectExplosion(bullet.getPosition(), bullet.img));
							i.remove();
							player.shieldcount = player.shieldcount-1;
							System.out.println(player.shieldcount);
							shieldDisplayTimer = 500;
							shieldDisplayPoint = player.position.addVector(new Point(5, 10));
							if(player.shieldcount==0)
								player.turnOffPowerUps();
						}
					}else if(!(bullet instanceof BulletBeamHitbox) && bullet.checkGraze(player.position, 7)){
						if(!player.invul){
							score += 10*multiplier;
							totalGraze+=10*multiplier;
							if(totalGraze>=1000)
								grazeMove=true;
							grazeDisplayTimer = 500;
							grazeBonus += 10*multiplier;
							grazeDisplayPoint = player.position.addVector(new Point(5, -10));
						}
					}else if(player.twarp && !bullet.warp && bullet.checkGraze(player.position, 45)){
						bullet.warp();
					}

				}
				bullet.increment(delta);
				if(bullet.checkOffscreen() && !(bullet instanceof BulletBeamHitbox)){
					i.remove();
				}
			}

			//Check player bullets
			i = pbullets.iterator(); 
			while(i.hasNext()) {
				Bullet bullet = i.next();
				if(enemy != null && bullet.checkCollisionEnemy(enemy.position)){
					i.remove();
					enemy.takeDamage(); //enemy has taken damage
				}
				bullet.increment(delta);
				if(bullet.checkOffscreen()){
					i.remove();
				}
			}			

			//Check if dead
			if(dead){
				deadx=player.position.x; //stores position of death
				deady=player.position.y;
				deathTimer = 1000; //time until the game over screen is made
			}

			//Check if enemy has been destroyed
			if(enemy != null && enemy.currentHP<=0){
				multiplier+=.5;
				Point epos = enemy.position;
				Bullet powup = new PowerUp(epos, new Point(0,1), 2);
				enemieskilled++;
				enemy = null;
				patterns = new ArrayList<Pattern>();
				respawnTimer = 2000;
				levelUp();
				renderObjs.clear();
				for(Bullet b : ebullets)
					if(!(b instanceof BulletBeamHitbox))
						renderObjs.add(new RenderObjectExplosion(b.getPosition(), b.img));
				ebullets.clear();
				ebullets.add(powup);
			}

			score += delta*.1*multiplier; //score increases
		}
	}


	/**
	 * renders the high scores and images onto the container
	 */
	public void render(GameContainer container,StateBasedGame sbg, Graphics g)  {
		g.drawImage(images[7], 0, 0);
		player.drawShip(g);


		if(enemy!=null) enemy.draw(g);
		for(Bullet b : ebullets){
			if(!(b instanceof BulletBeamHitbox))
				b.draw(g);
		}
		for(Bullet b : pbullets){
			b.draw(g); //draws bullets
		}

		player.drawHitBox(g);

		Iterator<RenderObject> r = renderObjs.iterator();
		while(r.hasNext()){
			RenderObject ro = r.next();
			ro.draw(g);
			if(ro instanceof RenderObjectExplosion)
				if(((RenderObjectExplosion) ro).exp.isStopped())
					r.remove();
		}

		player.drawHitBox(g);	

		g.setColor(Color.cyan);
		if(grazeDisplayTimer > 0)
			g.drawString("GRAZE! +"+grazeBonus, (float)grazeDisplayPoint.x, (float)grazeDisplayPoint.y);
		g.setColor(Color.green);
		if(invulDisplayTimer > 0)
			g.drawString("INVUL!", (float)invulDisplayPoint.x, (float)invulDisplayPoint.y);
		if(shieldDisplayTimer > 0)
			g.drawString("SHIELD!", (float)shieldDisplayPoint.x, (float)shieldDisplayPoint.y);
		if (paused) {
			Color trans = new Color(0f,0f,0f,0.7f);
			g.setColor(trans);
			g.fillRect(0,0, BulletHellGame.WIDTH, BulletHellGame.HEIGHT); //makes the rectangle on pause screen to emphasize icons
			resumeOption.draw(resumeX, resumeY,resumeScale);
			menuOption.draw(menuX, menuY, menuScale);
		}
		if(dead){
			Color trans = new Color(0f,0f,0f,0.8f);
			g.setColor(trans);
			g.fillRect(0,0, BulletHellGame.WIDTH, BulletHellGame.HEIGHT);
			Player died=new Player(new Point(deadx,deady));
			died.drawShip(g);
			died.drawHitBox(g);
			Bullet dBullet = (Bullet) deadBullet;
			dBullet.draw(g);
		}
		g.setColor(Color.black);
		g.fillRect(BulletHellGame.WIDTH, 0, BulletHellGame.APPWIDTH-BulletHellGame.WIDTH, BulletHellGame.HEIGHT);
		g.setColor(new Color(255, 200, 0));
		g.drawString("SCORE: "+(int)Math.floor(score)+"", BulletHellGame.WIDTH+15, 5);
		g.drawString("BOSSES DEFEATED: "+enemieskilled, BulletHellGame.WIDTH+15, 20);
		player.drawPowerUpBar(g);
		drawGraze(g);
		if(enemy!=null) enemy.drawHPBar(g);
	}

	public void drawGraze(Graphics g){
		g.setColor(new Color(Color.cyan));
		if(totalGraze/1000<=1)
			g.fillRect(BulletHellGame.WIDTH+15, 215, (150*(totalGraze/1000)), 13);
		else
			g.fillRect(BulletHellGame.WIDTH+15, 215, (150), 13);
		if(1000-totalGraze<0)
			g.drawString("Graze Left: "+0+"", BulletHellGame.WIDTH+15, 230);
		else
			g.drawString("Graze Left: "+(1000-(int)totalGraze)+"", BulletHellGame.WIDTH+15, 230);	
		g.drawImage(images[14], BulletHellGame.WIDTH+11, 211);
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return stateID;
	}
}
