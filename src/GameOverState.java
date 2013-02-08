import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTextField;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 * This class is the game over menu which displays the users score, highscores and the option to go to main menu, exit, or play again
 * @author 912606
 *
 */
public class GameOverState extends BasicGameState{

	Image background = null;
	Image playAgainOption = null;
	Image exitOption = null;
	Image highscoreOption = null;
	Image menuOption = null;
	float scaleStep = 0.0001f;
	float playAgainScale = 1;
	float exitScale = 1;
	float menuScale = 1;
	int stateID = -1;
	Sound fx = null;
	Sound fx1 = null;
	int playAgainX=30;
	int playAgainY=106;
	int menuX=100;
	int menuY=146;
	int endX= 156;
	int endY=206;
	int highscoreX= 95;
	int highscoreY=250;
	int hsposx=176;
	boolean starting=true;
	int[] hsposy= {300, 320, 340, 360, 380, 400, 420, 440,460,480,500};
	Double currentScore;
	boolean checkScore=true;
	BufferedWriter out;
	ArrayList<Double> highscores;
	boolean highscore=false;
	String nameHS=null;


	GameOverState( int stateID ) 
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
		background = new Image("assets/gameplaybg.png");
		// load the menu images
		Image playAgainOptions = new Image("assets/playagain.png");
		Image exitOptions = new Image("assets/exit1.png");
		Image highscoreOptions = new Image("assets/highscore1.png");
		Image menuOptions = new Image("assets/menu.png");

		playAgainOption = playAgainOptions.getSubImage(0, 0, 360, 72);
		highscoreOption = highscoreOptions.getSubImage(0, 0, 231, 39);
		exitOption = exitOptions.getSubImage(0, 0, 89, 29);
		menuOption = menuOptions.getSubImage(0, 0, 288, 72);
	}

/**
 * gets the score of the just recently completed game
 */
	public void getScore(){
		BufferedReader hs = null;
		String x;
		try {
			hs = new BufferedReader(new FileReader("assets/CurrentScore"));
			if((x = hs.readLine()) != null){
				currentScore=Double.parseDouble(x);
			}	

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (hs != null)hs.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * renders the high scores and images onto the container
	 */
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// render the background
		background.draw(0, 0);

		//Draw menu
		getHighscores();
		if(checkScore){
			getScore();
			compare();
			checkScore=false;
		}
		playAgainOption.draw(playAgainX, playAgainY, playAgainScale);
		highscoreOption.draw(highscoreX, highscoreY);
		exitOption.draw(endX, endY, exitScale);
		menuOption.draw(menuX, menuY, menuScale);
		publishHS();
		displayHS(g);

		g.setColor(Color.red);
		//g.setFont(new TrueTypeFont(new java.awt.Font("Verdana", Font.PLAIN, 32), true));
		g.drawString("Score:" + currentScore, 140, 60);
	}
/**
 * adds the score
 */

	public void publishHS(){
		try {
			out = new BufferedWriter(new FileWriter("assets/HighScores"));
			for(int i=0;i<10;i++){
				out.write(Double.toString(Math.floor(highscores.get(i))));
				out.newLine();
			}
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * gets the high scores from the Highscore text file
	 */
	public void getHighscores(){
		BufferedReader hs = null;
		highscores=new ArrayList<Double>();
		String x = null;
		String y=null;
		try {
			hs = new BufferedReader(new FileReader("assets/Highscores"));
			for(int i=0;i<10;i++){
				if((x = hs.readLine()) != null){
					highscores.add(Double.parseDouble(x));
				}

			}	

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (hs != null)hs.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * compares current score to the high scores to add it to the list
	 */
	public void compare(){
		for(int i=0;i<10;i++){
			if((currentScore)==highscores.get(i)){
				if(i!=10){
					highscores.add(i,(currentScore));
					highscore=true;
					break;
				}
			} else if(Math.floor(currentScore)>highscores.get(i)){
				highscores.add(i,(currentScore));
				highscore=true;
				break;
			}
		}
	}

/**
 * writes all of the highscores on the Screen
 * @param g
 */
	public void displayHS(Graphics g){

		BufferedReader br = null;
		BufferedReader hs = null;
		String x,y = null;
		try {
			br = new BufferedReader(new FileReader("assets/Highscores"));
			hs = new BufferedReader(new FileReader("assets/Names"));
			for(int i=0;i<10;i++){
				if((x = br.readLine()) != null && (y=hs.readLine())!=null){
					g.drawString(x, hsposx, hsposy[i]);
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
 * checks if the key is pressed, not implemented in Dodge
 * @param key
 */
	public void keyPressed(KeyEvent key){
		if(key.getKeyCode() == KeyEvent.VK_M){
		char i = key.getKeyChar();
		nameHS=Character.toString(i);
		}
	}
	
	/**
	 * A continously updated method that helps smoothly run the program
	 */
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();

		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		if(highscore){

		}
		boolean insideplayAgain = false;
		boolean insideExit = false;
		boolean insideMenu = false;

		if( ( mouseX >= playAgainX && mouseX <= playAgainX + playAgainOption.getWidth()) &&
				( mouseY >= playAgainY && mouseY <= playAgainY + playAgainOption.getHeight()) ){
			insideplayAgain = true;
		}else if( ( mouseX >= endX && mouseX <= endX + exitOption.getWidth()) &&
				( mouseY >= endY && mouseY <= endY + exitOption.getHeight()) ){
			insideExit = true;
		}else if( ( mouseX >= menuX && mouseX <= menuX + menuOption.getWidth()) &&
				( mouseY >= menuY && mouseY <= menuY + menuOption.getHeight()) ){
			insideMenu = true;
		}

		if(insideplayAgain){
			if(playAgainScale < 1.05f)
				playAgainScale += scaleStep * delta;

			if ( input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) ){
				//fx1.stop();
				//fx1.loop();	
				GameplayState gs = (GameplayState) sbg.getState(BulletHellGame.GAMEPLAYSTATE);
				gs.newGame();
				sbg.enterState(BulletHellGame.GAMEPLAYSTATE, new FadeOutTransition(Color.black,1000), new FadeInTransition(Color.black,500));
			}
		}else {
			if(playAgainScale > 1.0f)
				playAgainScale -= scaleStep * delta;
		}

		if(insideExit)
		{
			if(exitScale < 1.05f)
				exitScale +=  scaleStep * delta;
			if ( input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) )
				gc.exit();
		}else{
			if(exitScale > 1.0f)
				exitScale -= scaleStep * delta;
		}

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


}
