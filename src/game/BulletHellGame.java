package game;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Main game class - Contains code for initializing and starting the game
 * @author prashan
 *
 */

public class BulletHellGame extends StateBasedGame {
 
    public static final int MAINMENUSTATE = 0;
    public static final int GAMEPLAYSTATE = 1;
    public static final int GAMEOVERSTATE = 2;
    public static final int APPWIDTH = 600;
    public static final int HEIGHT = 610;
    public static final int WIDTH = 400;
    public static final int OFFSET= (APPWIDTH-WIDTH)/2;
    
    
    public BulletHellGame()
    {
        super("PROK");
    }
 
    /**
     * Main method - runs the game
     * @param args
     * @throws SlickException
     */
    public static void main(String[] args) throws SlickException
    {
         AppGameContainer app = new AppGameContainer(new BulletHellGame());
         app.setDisplayMode(APPWIDTH, HEIGHT, false);
         app.setShowFPS(false);
         app.start();
    }
 
    /**
     * Initialize the game states
     */
    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.addState(new MainMenuState(MAINMENUSTATE));
        this.addState(new GameplayState(GAMEPLAYSTATE));
        this.addState(new GameOverState(GAMEOVERSTATE));
    }
    
}