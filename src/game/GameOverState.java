package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


import org.newdawn.slick.Animation;
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
import org.newdawn.slick.util.InputAdapter;

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
	int playAgainX=35;
	int playAgainY=136;
	//	int menuX=100;
	int menuY=146;
	int endX= 126;
	int endY=206;
	int highscoreX= 100;
	int highscoreY=270;
	int hsposx=176;
	boolean starting=true;
	int[] hsposy= {300, 320, 340, 360, 380, 400, 420, 440,460,480,500};
	int currentScore;
	private static boolean checkScore=true;
	BufferedWriter out;
	ArrayList<Integer> highscores;
	ArrayList<String> names;
	boolean highscore=false;
	String nameHS=null;
	int selection = 0;
	int xselection=playAgainX;
	String highScoreName= "Noobslayer";
	float alphablock=(float).95;
	boolean up=false;
	int blinker=280;
	int changeNames=-1;
	boolean addName=false;
	boolean firstChar=true;
	boolean game=true;
	boolean instate=false;
	Animation select;

	public void enter(GameContainer container, StateBasedGame sbg){
		instate = true;
		container.getInput().clearKeyPressedRecord();
	}

	public void leave(GameContainer container, StateBasedGame sbg){
		instate = false;
		container.getInput().clearKeyPressedRecord();
	}

	GameOverState( int stateID ) 
	{
		this.stateID = stateID;
	}

	@Override
	public int getID() {
		return stateID;
	}
	public static void setCheckScore(boolean x){
		checkScore=x;
	}
	/**
	 * run at the beginning of the program to instantiate everything
	 */
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		background = new Image("assets/gameplaybg.png");
		// load the menu images
		Image playAgainOptions = new Image("assets/playagain.png");
		Image exitOptions = new Image("assets/exit.png");
		Image highscoreOptions = new Image("assets/highscore1.png");
		//Image menuOptions = new Image("assets/menu.png");
		select = new Animation(new Image[]{
		new Image("assets/selector1.png"),
		new Image("assets/selector2.png"),
		new Image("assets/selector.png"),
		}, 100);
		select.start();

		gc.getInput().addPrimaryListener(new InputAdapter(){
			public void keyPressed(int key, char c){
				if(addName){
					game=false;
					if(changeNames!=-1){
						if(key==Input.KEY_BACK){
							if(highScoreName.length()>0){
								if(highScoreName!=null){
									highScoreName= highScoreName.substring(0, highScoreName.length()-1);
									blinker=blinker-9;
								}
							}
						}
						else{
							if(key==Input.KEY_ENTER){
								if(addName){
									highScoreName=highScoreName.trim();
									names.set(changeNames,highScoreName);
									publishNames();
									addName=false;
									firstChar=true;

									game=true;
								}
							}
							if(highScoreName.length()<13  && key!=Input.KEY_LSHIFT && key!=Input.KEY_ENTER && key!=Input.KEY_UP && key!=Input.KEY_DOWN && key!=Input.KEY_LEFT && key!=Input.KEY_RIGHT){
								highScoreName=highScoreName+c;
								blinker=blinker+9;
								firstChar=false;
							}
						}
					}
				}
			}
		});

		playAgainOption = playAgainOptions.getSubImage(0, 0, 329, 29);
		highscoreOption = highscoreOptions.getSubImage(0, 0, 200, 16);
		exitOption = exitOptions.getSubImage(0, 0, 148, 27);
		//menuOption = menuOptions.getSubImage(0, 0, 288, 72);

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
				currentScore=Integer.parseInt(x);
				System.out.println(currentScore);
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
		background.draw(BulletHellGame.OFFSET, 0);
		//Draw menu

		getHighscores();
		getNames();
		if(checkScore){
			selection = 0;
			getScore();
			compare();
			checkScore=false;
		}
		switch(selection){
		case 0: xselection=playAgainX;
		break;
		//	case 1: xselection=menuX;
		//	break;
		case 1: xselection=endX;
		break;
		}
		g.setColor(Color.cyan);
		select.draw(BulletHellGame.OFFSET+xselection-24, 140+70*selection, 16, 16);
		playAgainOption.draw(BulletHellGame.OFFSET+playAgainX, playAgainY, playAgainScale);
		highscoreOption.draw(BulletHellGame.OFFSET+highscoreX, highscoreY);
		exitOption.draw(BulletHellGame.OFFSET+endX, endY, exitScale);
		//	menuOption.draw(BulletHellGame.OFFSET+menuX, menuY, menuScale);
		publishHS();
		displayHS(g);

		g.drawString("Score:" + (int)currentScore, BulletHellGame.OFFSET+50, 60);
		g.drawString("Enter Name:", BulletHellGame.OFFSET+180, 60);
		g.drawString(highScoreName,BulletHellGame.OFFSET+278, 60);
		g.setColor(new Color(0f,0f,0f,alphablock));
		if(addName)
			g.fillRect(BulletHellGame.OFFSET+blinker, 62, 7, 15);

	}
	/**
	 * adds the score
	 */

	public void publishHS(){
		try {
			out = new BufferedWriter(new FileWriter("assets/HighScores"));
			for(int i=0;i<10;i++){
				out.write(Integer.toString((highscores.get(i))));
				out.newLine();
			}
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void publishNames(){
		if(addName){
			try {
				out = new BufferedWriter(new FileWriter("assets/Names"));
				for(int i=0;i<10;i++){
					out.write((names.get(i)));
					System.out.println(names.get(i));
					out.newLine();
				}
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * gets the high scores from the Highscore text file
	 */
	public void getHighscores(){
		BufferedReader hs = null;
		highscores=new ArrayList<Integer>();
		String x = null;
		try {
			hs = new BufferedReader(new FileReader("assets/Highscores"));
			for(int i=0;i<10;i++){
				if((x = hs.readLine()) != null){
					highscores.add(Integer.parseInt(x));
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
	public void getNames(){
		BufferedReader hs = null;
		names=new ArrayList<String>();
		String x = null;
		try {
			hs = new BufferedReader(new FileReader("assets/Names"));
			for(int i=0;i<10;i++){
				if((x = hs.readLine()) != null){
					names.add(x);
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
			if(currentScore>highscores.get(i)){
				addName=true;
				names.add(i, "<Your Name Here>");	
				highscores.add(i,(currentScore));
				publishNames();
				System.out.println(names.get(i));
				highscore=true;

				changeNames=i;
				break;
			}

			if(currentScore==highscores.get(i)){
				if(i!=10){
					addName=true;
					highscores.add(i,(currentScore));
					names.add(i, "-------");
					publishNames();
					System.out.println(names.get(i));
					highscore=true;

					changeNames=i;
					break;
				}
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
					g.drawString(x, hsposx+50, hsposy[i]);
					g.drawString(y, hsposx+150, hsposy[i]);
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

		if(instate){
			if(alphablock<=.95 && up!=true)
				alphablock-=.03;
			if(alphablock<=.05&&up!=true){
				up=true;
				alphablock=(float).05;
			}				
			if(alphablock>=.05&& up==true)
				alphablock+=.03;
			if(alphablock>=.95 &&up==true){
				up=false;
				alphablock=(float).95;
			}				

			if(game){
				Input input = gc.getInput();

				if(input.isKeyPressed(Input.KEY_DOWN)){
					if(selection<=1)
						selection++;
					if(selection==2)
						selection=0;
				}
				if(input.isKeyPressed(Input.KEY_UP)){
					if(selection>=0)
						selection--;
					if(selection==-1)
						selection=1;
				}
				if(input.isKeyPressed(Input.KEY_ENTER)){
					if(selection == 0){
						GameplayState gs = (GameplayState) sbg.getState(BulletHellGame.GAMEPLAYSTATE);
						//addName=true;
						gs.newGame();
						sbg.enterState(BulletHellGame.GAMEPLAYSTATE, new FadeOutTransition(Color.black,1000), new FadeInTransition(Color.black,500));
					}else if(selection ==1){
						//addName=true;
						sbg.enterState(BulletHellGame.MAINMENUSTATE, new FadeOutTransition(Color.black,400), new FadeInTransition(Color.black,400));
					}
				}
			}		

		}
	}

}
