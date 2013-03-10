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
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import pattern.Pattern;
import pattern.PatternCircle;
import pattern.PatternCurve;
import pattern.PatternDoubleCurve;
import pattern.PatternDoubleSinCurve;
import pattern.PatternQuadSpiral;
import pattern.PatternReverseCurve;
import pattern.PatternReverseSinCurve;
import pattern.PatternSinCircle;
import pattern.PatternSinCurve;
import pattern.PatternSpiral;

import ability.Ability;
import ability.AbilityLockOnMissiles;
import bullet.Bullet;
/**
 * This class is the game play class where the actual game is played.
 * @author 912606
 *
 */

public class GameplayState extends BasicGameState {

	ArrayList<Bullet> ebullets;
	ArrayList<Bullet> pbullets;
	ArrayList<Pattern> patterns;
	ArrayList<Double> highscores;
	ArrayList<Ability> abilities;
	public static Player player;
	double score = 0;
	double multiplier = 1;
	public static Image[] images;
	public static float BULLETSPEED = 1f;
	public static float BULLETRATE = 2f;
	int[] levelUps = {3, 6, 10, 15, 20};
	int level = 0, lvlIndex = 0;
	int nextTier = levelUps[lvlIndex];
	Enemy enemy;
	boolean paused;
	int grazeDisplayTimer = 0;
	Point grazeDisplayPoint;
	int grazeBonus = 0;
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
	boolean dead;
	int deathTimer = 0;
	double deadx;
	double deady;
	Bullet deadBullet;
	BufferedWriter out;

	public GameplayState( int stateID ) 
	{
		this.stateID = stateID;
	}

	/**
	 * Loads images into the images array.  
	 */
	public void loadImages(){
		String[] files = {"ship2.png", "BulletGreen.png", "BulletBlue.png", "BulletRed.png", 
				"samusship.png", "BulletOrange.png","BulletPurple.png", "gameplaybg.png", "PDot.png" };
		images = new Image[files.length];
		try {
			for(int i=0; i<files.length; i++){
				images[i] = new Image("assets/"+files[i], new Color(92,189,186));
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
	 */
	public void newGame(){
		player = new Player(new Point(200,500));
		ebullets = new ArrayList<Bullet>();
		pbullets = new ArrayList<Bullet>();
		patterns = new ArrayList<Pattern>();
		abilities = new ArrayList<Ability>();
		createEnemy();
		paused = false;
		dead = false;
		score=0;
		multiplier=1;
		BULLETSPEED = 1f;
		BULLETRATE = 2f;
		grazeDisplayTimer = 0;
	}
	/**
	 * run at the beginning of the program to instantiate everything
	 */
	public void init(GameContainer container, StateBasedGame sbg ) throws SlickException {
		container.setVSync(true);
		container.setTargetFrameRate(60);
		loadImages(); // loads images from assets
		score=0;
	}

	/**
	 * Creates an enemy object.  Once we have more content, this should become randomized.
	 */
	public void createEnemy(){
		Pattern[] temppatterns = new Pattern[3];
		Point[] temppoints = new Point[3];
		Random r = new Random();
		ArrayList<Integer> patternIds = new ArrayList<Integer>();
		int pid =  0;
		for(int i=0; i<3; i++){
			patternIds.add(pid);
			while(patternIds.contains(pid))
				pid = r.nextInt(10);
			switch(pid){
			case 0 : temppatterns[i] = new PatternCircle(); break;
			case 1 : temppatterns[i] = new PatternSpiral(); break;
			case 2 : temppatterns[i] = new PatternQuadSpiral(); break;
			case 3 : temppatterns[i] = new PatternCurve(); break;
			case 4 : temppatterns[i] = new PatternReverseCurve(); break;
			case 5 : temppatterns[i] = new PatternDoubleCurve(); break;
			case 6 : temppatterns[i] = new PatternSinCurve(); break;
			case 7 : temppatterns[i] = new PatternReverseSinCurve(); break;
			case 8 : temppatterns[i] = new PatternDoubleSinCurve(); break;
			case 9 : temppatterns[i] = new PatternSinCircle(); break;
			}
			temppoints[i] = new Point(200, 200);
		}

		enemy = new Enemy( new Point(200, 200), temppatterns, temppoints, images[4]);
		enemy.addPatterns(patterns); //adds patterns to the enemy
		//abilities.add(new AbilityLockOnMissiles(new Point(posx, posy)));
		//ebullets.add(new Bullet(new Point(300, 400), new Point(0,0), images[2], 1));
	}
	/**
	 * Allows the program to increase in difficulty by increasing bullet speed
	 */
	public void levelUp(){
		level+=1;
		if(level > nextTier){
			BULLETSPEED += .2; //increases bullet speed across screen
			BULLETRATE += .2; //increases bullet rate
			lvlIndex++;
			nextTier = levelUps[lvlIndex];
			System.out.println(BULLETSPEED+" "+BULLETRATE);
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
				sbg.enterState(BulletHellGame.GAMEOVERSTATE, new FadeOutTransition(Color.black, 100),new FadeInTransition(Color.black, 100));
			}
		}
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
		if(!paused && !dead){		
			if (container.getInput().isKeyDown(Input.KEY_LSHIFT)){player.setSpeed(1f);}
			if (container.getInput().isKeyDown(Input.KEY_LEFT) && player.position.x>0) {player.increment(Player.LEFT);} // move player left
			if (container.getInput().isKeyDown(Input.KEY_RIGHT) && player.position.x<BulletHellGame.WIDTH) {player.increment(Player.RIGHT);} //move player right
			if (container.getInput().isKeyDown(Input.KEY_UP) && player.position.y>0) {player.increment(Player.UP);} //move player up
			if (container.getInput().isKeyDown(Input.KEY_DOWN) && player.position.y<BulletHellGame.HEIGHT) {player.increment(Player.DOWN);} //move player down
			if (container.getInput().isKeyDown(Input.KEY_SPACE)) {player.shoot(pbullets);}
			player.setSpeed(3);
			if(grazeDisplayTimer > 0)
				grazeDisplayTimer -=delta;
			if(grazeDisplayTimer <= 0)
				grazeBonus = 0;
			if(enemy == null){
				if(respawnTimer <= 0)
					createEnemy(); //creates enemy
				else
					respawnTimer -= delta;
			}

			if(enemy != null){
				enemy.update(delta);
				enemy.updatePatternPos(patterns);
			}

			for(Pattern p : patterns){
				p.update(ebullets, delta);
			}

			Iterator<Ability> iAbility = abilities.iterator();
			while(iAbility.hasNext()){
				Ability a = iAbility.next();
				if(!a.update(delta))
					iAbility.remove();
			}

			Iterator<Bullet> i = ebullets.iterator(); 
			while(i.hasNext()) {
				Bullet bullet = i.next();
				if(bullet.checkCollision(player.position)){
					dead = true;
					deadBullet=bullet;
					deadx=player.position.x; //stores position of death
					deady=player.position.y;
					deathTimer = 1000; //time until the game over screen is made
					try {
						out = new BufferedWriter(new FileWriter("assets/CurrentScore"));
						out.write(Double.toString(Math.floor(score)));
						out.close(); //writes score to the current score file
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(bullet.checkGraze(player.position)){
					System.out.println("graze");
					score += 10*multiplier;
					grazeDisplayTimer += 100;
					grazeBonus += 10*multiplier;
					grazeDisplayPoint = player.position.addVector(new Point(0, -10));
				}
				bullet.increment();
				if(bullet.checkOffscreen()){
					i.remove();
				}
			}

			i = pbullets.iterator(); 
			while(i.hasNext()) {
				Bullet bullet = i.next();
				if(enemy != null && bullet.checkCollisionEnemy(enemy.position)){
					i.remove();
					enemy.takeDamage(); //enemy has taken damage
				}
				bullet.increment();
				if(bullet.checkOffscreen()){
					i.remove();
				}
			}			

			if(enemy != null && enemy.currentHP<=0){
				multiplier+=.5;
				enemy = null;
				patterns = new ArrayList<Pattern>();
				respawnTimer = 5000;
				levelUp();
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
			b.draw(g);
		}
		for(Bullet b : pbullets){
			b.draw(g); //draws bullets
		}
		for(Ability a: abilities){
			a.draw(g);
		}
		player.drawHitBox(g);	
		g.setColor(Color.cyan);
		if(grazeDisplayTimer > 0)
			g.drawString("GRAZE! +"+grazeBonus, (float)grazeDisplayPoint.x, (float)grazeDisplayPoint.y);
		g.setColor(new Color(255, 200, 0));
		g.drawString((int)Math.floor(score)+"", 0, 0);
		if(enemy!=null) enemy.drawHPBar(g);
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
			deadBullet.draw(g);
		}
	}
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return stateID;
	}
}
