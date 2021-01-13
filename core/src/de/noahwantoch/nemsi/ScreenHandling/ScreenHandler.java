package de.noahwantoch.nemsi.ScreenHandling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Game;

public class ScreenHandler {
    private static ScreenHandler instance = new ScreenHandler();

    private static final String TAG = ScreenHandler.class.getSimpleName();
    private static Screen currentScreen;
    private Game game;

    public static ScreenHandler getInstance(){
        return instance;
    }

    private ScreenHandler(){}

    public void setCurrentScreen(ScreenEnum screenEnum){
        currentScreen = screenEnum.getScreen();
        game.setScreen(currentScreen);

        Gdx.app.debug(TAG, "Screen was set to: " + screenEnum.name());
    }

    public Screen getCurrentScreen(){
        return currentScreen;
    }

    public void initializeGame(Game game){
        this.game = game;
    }
}
