package de.noahwantoch.nemsi.ScreenHandling.differentScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import de.noahwantoch.nemsi.Game.CardGame;
import de.noahwantoch.nemsi.Game.Enemy;
import de.noahwantoch.nemsi.Game.Game;
import de.noahwantoch.nemsi.Game.Player;
import de.noahwantoch.nemsi.Game.WorldGame;
import de.noahwantoch.nemsi.ScreenHandling.AbstractScreen;

public class GameScreen extends AbstractScreen {

    private static final String TAG = AbstractScreen.class.getSimpleName();

    private CardGame cardGame; //Ingame (Kartenspiel)
    private WorldGame worldGame; //Ingame Welt-Spiel (RPG)

    private Player player;
    private Enemy enemy;

    public GameScreen(){}

    @Override
    public void show() {
        Gdx.app.debug(TAG, "Game has started!");
        cardGame = new CardGame();
        worldGame = new WorldGame();

        enemy = new Enemy();
        player = Game.getPlayer();

        cardGame.startGame(player, enemy);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(cardGame.getState()){
            cardGame.draw(delta);
        }else{
            worldGame.draw(delta);
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {

    }
}
