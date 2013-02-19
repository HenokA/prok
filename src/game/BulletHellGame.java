package game;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
 

public class BulletHellGame extends StateBasedGame {
 
    public static final int MAINMENUSTATE = 0;
    public static final int GAMEPLAYSTATE = 1;
    public static final int GAMEOVERSTATE = 2;
    public static final int HEIGHT = 610;
    public static final int WIDTH = 400;
    
    public BulletHellGame()
    {
        super("Bullet Hell Game");
    }
 
    public static void main(String[] args) throws SlickException
    {
         AppGameContainer app = new AppGameContainer(new BulletHellGame());
         app.setDisplayMode(WIDTH, HEIGHT, false);
         //make non resizable
         app.start();
    }
 
    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.addState(new MainMenuState(MAINMENUSTATE));
        this.addState(new GameplayState(GAMEPLAYSTATE));
        this.addState(new GameOverState(GAMEOVERSTATE));
    }
    
}