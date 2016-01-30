package deserted;
import java.util.Date;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameIntro extends BasicGameState implements GameState {
	public static final int STATE_INTRO = 0;
	Image logo;
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {

		 logo = new Image("images/backgrounds/TitleScreen.png");
		 logo.setFilter(Image.FILTER_NEAREST);
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame state, Graphics g)
			throws SlickException {
		g.fillRect(0, 0, container.getWidth(), container.getHeight());
		g.setColor(Color.white);


		logo.draw(0, 0, container.getWidth(), container.getHeight(),0,0,logo.getWidth(), logo.getHeight());

		g.drawString("Press Enter to continue", 20 /*container.getWidth() / 2 - 130*/, 20 /*container.getHeight() - 150*/);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		if (arg0.getInput().isKeyDown(Input.KEY_ENTER) || arg0.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			arg1.enterState(Play.STATE_PLAY);
			arg1.getState(1).init(arg0, arg1);
		}
	}

	@Override
	public int getID() {
		return STATE_INTRO;
	}
}
