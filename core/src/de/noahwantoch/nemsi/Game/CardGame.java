package de.noahwantoch.nemsi.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import de.noahwantoch.nemsi.TextureHandling.TextureEnum;
import de.noahwantoch.nemsi.Utility.BatchInstance;

/**
 * @author Noah O. Wantoch
 * @see de.noahwantoch.nemsi.ScreenHandling.differentScreens.GameScreen
 * Diese Klasse wird im GameScreen initialisiert.
 * Sie kümmert sich um das Kartenspiel und alles drumherum
 */
public class CardGame {

    private static final String TAG = CardGame.class.getSimpleName();

    private de.noahwantoch.nemsi.Game.Player player;
    private de.noahwantoch.nemsi.Game.Enemy enemy;

    private boolean state;
    private boolean playerTurn;

    private final Sprite board;

    private MessageBox messageBox;

    public CardGame(){
        //Die Chance anzufangen
        float turnChance = (int) (Math.random() * 100f);

        if(turnChance > 50) playerTurn = true; //else playerTurn = false

        //Die Textur des "Spielbretts"
        board = new Sprite(new Texture(TextureEnum.BOARD.getPath()));
        board.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        messageBox = new MessageBox("Ein Kartenspiel wurde initialisiert. :)", 5, MessageBox.MessageBoxType.OKAY_MESSAGE_BOX);
        messageBox.showMessage();
    }

    /**
     * @author Noah O. Wantoch
     * @param delta delta
     * Malt das Spielbrett, den Spieler und den Gegner sowie die ganzen Karten und Zonen.
     */
    public void draw(float delta){
        de.noahwantoch.nemsi.Utility.BatchInstance.batch.begin();
        board.draw(de.noahwantoch.nemsi.Utility.BatchInstance.batch);
        player.draw(delta);
        enemy.draw(delta);

        if(messageBox.showMessageBox()){
            messageBox.draw(BatchInstance.batch, delta);
        }
        BatchInstance.batch.end();
    }

    /**
     * @author Noah O. Wantoch
     * Wechselt den Zug von Spieler auf Gegner oder andersherum.
     */
    public void switchTurn(){
        Gdx.app.debug(TAG, "Turn switched");
        playerTurn = !playerTurn;
    }

    /**
     * @author Noah O. Wantoch
     * Beendet das aktuelle Kartenspiel.
     */
    public void endGame(){
        state = false;
    }

    /**
     * @author Noah O. Wantoch
     * @param player Der Spieler.
     * @param enemy Ein Gegner.
     * @see Enemy
     * @see Player
     * Startet ein Kartenspiel mit einem Spieler und einem Gegner.
     */
    public void startGame(Player player, Enemy enemy){
        state = true;
        this.player = player;
        this.enemy = enemy;
        this.player.drawCard(5);
        //this.player.drawCard(GameSettings.handcardsStartNumber);
        this.enemy.drawCard(GameSettings.handcardsStartNumber);
    }

    /**
     * @author Noah O. Wantoch
     * @return Zustand des Spiels.
     */
    public boolean getState(){
        return state;
    }
}
