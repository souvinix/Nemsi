package de.noahwantoch.nemsi;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import de.noahwantoch.nemsi.Game.All_Cards;
import de.noahwantoch.nemsi.ScreenHandling.ScreenEnum;
import de.noahwantoch.nemsi.ScreenHandling.ScreenHandler;

public class MainGameClass extends Game {

	private static final String TAG = MainGameClass.class.getSimpleName();

	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Debug.debug();
		All_Cards.initialize();
		ScreenHandler.getInstance().initializeGame(this);
		ScreenHandler.getInstance().setCurrentScreen(ScreenEnum.MENU_SCREEN);
	}

	@Override
	public void dispose () {

	}
}
