package game;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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


public class MainMenuState extends BasicGameState {
	Image background = null;
	Image startGameOption = null;
	Image exitOption = null;
	Image highscoreOption = null;
	float scaleStep = 0.0001f;
	float startGameScale = 1;
	float exitScale = 1;
	int stateID = -1;
	Sound fx = null;
	Sound fx1 = null;
	int startgameX=90;
	int startgameY=156;
	int endX= 156;
	int endY=206;
	int highscoreX= 110;
	int highscoreY=260;
	int hsposx=176;
	boolean starting=true;
	int selection = 0;
	int[] hsposy= {300, 320, 340, 360, 380, 400, 420, 440,460,480,500};
	
	MainMenuState( int stateID ) 
	{
		this.stateID = stateID;
	}

	@Override
	public int getID() {
		return stateID;
	}
	/**
	 * run at the beginning of the program to instantiate everything
	 */
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {		
		background = new Image("assets/b1.png");
		
		//fx= new Sound("assets/Kalimba.wav");
		//fx1= new Sound("assets/standupEDIT.wav");
		//fx1.loop();
		// load the menu images
		Image startgameOptions = new Image("assets/startgame1.png");
		Image exitOptions = new Image("assets/exit1.png");
		Image highscoreOptions = new Image("assets/highscore1.png");

		startGameOption = startgameOptions.getSubImage(0, 0, 231, 39);
		highscoreOption = highscoreOptions.getSubImage(0, 0, 200, 16);
		exitOption = exitOptions.getSubImage(0, 0, 89, 29);
	
		
	}
	public void getHSX(){
		try {
			 new BufferedReader(new FileReader("assets/Highscores"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * renders the high scores and images onto the container
	 */
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// render the background
		//background.setAlpha(.1f);
		background.draw(BulletHellGame.OFFSET-17, 0);
		
		
		//fx1.play();
		 //Draw menu
		startGameOption.draw(BulletHellGame.OFFSET+startgameX, startgameY, startGameScale);
		highscoreOption.draw(BulletHellGame.OFFSET+highscoreX, highscoreY);
		exitOption.draw(BulletHellGame.OFFSET+endX, endY, exitScale);

		g.setColor(Color.white);
		BufferedReader br = null;
		BufferedReader hs = null;
		String x,y = null;
		try {
			br = new BufferedReader(new FileReader("assets/Highscores"));
			hs = new BufferedReader(new FileReader("assets/Names"));
			for(int i=0;i<10;i++){
				if((x = br.readLine()) != null && (y=hs.readLine())!=null){
					g.drawString(x, hsposx+30, hsposy[i]);
					g.drawString(y, hsposx+105, hsposy[i]);
				}
			}	

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
				if(hs!=null)hs.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		

	}
	/**
	 * A continously updated method that helps smoothly run the program
	 */
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		//fx1.play();

		if(input.isKeyPressed(Input.KEY_ENTER)){
			if(selection == 0){
				GameplayState gs = (GameplayState) sbg.getState(BulletHellGame.GAMEPLAYSTATE);
				gs.newGame();
				sbg.enterState(BulletHellGame.GAMEPLAYSTATE, new FadeOutTransition(Color.black,1000), new FadeInTransition(Color.black,500));			
			}else if(selection == 1){
				gc.exit();
			}
		}
	}

}