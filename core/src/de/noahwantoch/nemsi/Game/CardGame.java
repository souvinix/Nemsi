package de.noahwantoch.nemsi.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import de.noahwantoch.nemsi.TextureHandling.TextureEnum;
import de.noahwantoch.nemsi.Utility.BatchInstance;

public class CardGame {

    private static final String TAG = CardGame.class.getSimpleName();

    private de.noahwantoch.nemsi.Game.Player player;
    private de.noahwantoch.nemsi.Game.Enemy enemy;

    private boolean state;
    private boolean playerTurn;

    private Sprite board;

    public CardGame(){
        //Die Chance anzufangen
        float turnChance = (int) (Math.random() * 100f);
        if(turnChance > 50){ playerTurn = true; } else playerTurn = false;

        //Die Textur des "Spielbretts"
        board = new Sprite(new Texture(TextureEnum.BOARD.getPath()));
        board.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void draw(float delta){
        de.noahwantoch.nemsi.Utility.BatchInstance.batch.begin();
        board.draw(de.noahwantoch.nemsi.Utility.BatchInstance.batch);
        player.draw(delta);
        enemy.draw(delta);
        BatchInstance.batch.end();
    }

    //Der Zug wird gewechselt z.B. von Spieler auf Gegner
    public void switchTurn(){
        Gdx.app.debug(TAG, "Turn switched");
        playerTurn = !playerTurn;
    }

    public void endGame(){
        state = false;
    } //Das Spiel wird beendet
    //Das Spiel wird gestartet
    public void startGame(Player player, Enemy enemy){
        state = true;
        this.player = player;
        this.enemy = enemy;
        this.player.drawCard(de.noahwantoch.nemsi.Game.GameSettings.handcardsStartNumber);
        this.enemy.drawCard(GameSettings.handcardsStartNumber);
    }

    //Zustand des Spiels
    public boolean getState(){
        return state;
    }
}
