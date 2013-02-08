import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.BlobbyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;
/**
 * This class is the game play class where the actual game is played.
 * @author 912606
 *
 */

public class GameplayState extends BasicGameState {

	ArrayList<Bullet> ebullets;
	ArrayList<Bullet> pbullets;
	ArrayList<BulletPattern> patterns;
	ArrayList<Double> highscores;
	int[] positionsx= {100,200,250,300,350};
	int[] positionsy = {50,100,150,200, 250};
	Player player;
	double score = 0;
	double multiplier = 1;
	public static Image[] images;
	public static float BULLETSPEED = .5f;
	public static float BULLETRATE = .5f;
	int[] levelUps = {3, 6, 10, 15, 21};
	int level = 0, lvlIndex = 0;
	int nextTier = levelUps[lvlIndex];
	Enemy enemy;
	boolean paused;
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
				"samusship.png", "BulletOrange.png","BulletPurple.png", "gameplaybg.png" };
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
		patterns = new ArrayList<BulletPattern>();
		createEnemy();
		paused = false;
		dead = false;
		score=0;
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
	 * creates positions for the enemies
	 */
	public int createPositions(){
		Random random = new Random();
		int randomInt=random.nextInt(5); //creates random position for boss
		return randomInt;
	}

	/**
	 * Creates an enemy object.  Once we have more content, this should become randomized.
	 */
	public void createEnemy(){
		int posx=positionsx[createPositions()];
		int posy=positionsy[createPositions()];
		enemy = new Enemy(new Point(posx, posy), new BulletPattern[]{new BulletQuadSpiral(), new BulletReverseCurve(),
			new BulletCurve(), new BulletCircle()}, new Point[]{new Point(posx,posy), new Point(posx-100, posy), new Point(posx+100,posy), new Point(posx,posy)}, images[4]);
		enemy.addPatterns(patterns); //adds patterns to the enemy
	}
/**
 * Allows the program to increase in difficulty by increasing bullet speed
 */
	public void levelUp(){
		level+=1;
		if(level > nextTier){
			BULLETSPEED += .5; //increases bullet speed across screen
			BULLETRATE += .5; //increases bullet rate
			lvlIndex++;
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
				sbg.enterState(BulletHellGame.GAMEOVERSTATE, new FadeOutTransition(),new FadeInTransition());
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
			if (container.getInput().isKeyDown(Input.KEY_LEFT) && player.position.x!=0) {player.position.x-=2;} // move player left
			if (container.getInput().isKeyDown(Input.KEY_RIGHT) && player.position.x!=400) {player.position.x+=2;} //move player right
			if (container.getInput().isKeyDown(Input.KEY_UP) && player.position.y!=0) {player.position.y-=2;} //move player up
			if (container.getInput().isKeyDown(Input.KEY_DOWN) && player.position.y!=640) {player.position.y+=2;} //move player down
			if (container.getInput().isKeyDown(Input.KEY_SPACE)) {player.shoot(pbullets);}

			if(enemy == null){
				if(respawnTimer <= 0)
					createEnemy(); //creates enemy
				else
					respawnTimer -= delta;
			}

			for(BulletPattern p : patterns){
				p.update(ebullets, delta);
			}

			Iterator<Bullet> i = ebullets.iterator(); 
			while(i.hasNext()) {
				Bullet bullet = i.next();
				if(bullet.checkCollision(player.position)){
					dead = true;
					deadBullet=bullet;
					deadx=player.position.x; //stores position of death
					deady=player.position.y;
					deathTimer = 2000; //time until the game over screen is made
					try {
						out = new BufferedWriter(new FileWriter("assets/CurrentScore"));
						out.write(Double.toString(Math.floor(score)));
						out.close(); //writes score to the current score file
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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
				patterns = new ArrayList<BulletPattern>();
				respawnTimer = 5000;
			}

			score += delta*.01*multiplier; //score increases
		}
	}
	
	/**
	 * renders the high scores and images onto the container
	 */
	public void render(GameContainer container,StateBasedGame sbg, Graphics g)  {
		g.drawImage(images[7], 0, 0);
		if(enemy!=null) enemy.draw(g);
		for(Bullet b : ebullets){
			b.draw(g);
		}
		for(Bullet b : pbullets){
			b.draw(g); //draws bullets
		}
		player.draw(g);		
		g.setColor(new Color(255, 200, 0));
		g.drawString((int)score+"", 0, 0);
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
			died.draw(g);
			deadBullet.draw(g);
		}
	}
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return stateID;
	}
}
