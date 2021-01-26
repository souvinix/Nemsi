package de.noahwantoch.nemsi.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import de.noahwantoch.nemsi.EffectModule;
import de.noahwantoch.nemsi.TextureHandling.TextureEnum;
import de.noahwantoch.nemsi.UI.Button;
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

    private final Sprite board;

    private MessageBox messageBox;

    private Button endTurnButton;

    public CardGame(){
        //Die Textur des "Spielbretts"
        board = new Sprite(new Texture(TextureEnum.BOARD.getPath()));
        board.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        messageBox = new MessageBox(GameSettings.messageBoxSize, MessageBox.MessageBoxType.OKAY_MESSAGE_BOX);

        endTurnButton = new Button(GameSettings.endTurnButtonPosition.x, GameSettings.endTurnButtonPosition.y, 5f, "Zug beenden");
        endTurnButton.setFunction(false);
    }

    /**
     * @author Noah O. Wantoch
     * @param delta delta
     * Malt das Spielbrett, den Spieler und den Gegner sowie die ganzen Karten und Zonen.
     */
    public void draw(float delta){
        de.noahwantoch.nemsi.Utility.BatchInstance.batch.begin();
        board.draw(de.noahwantoch.nemsi.Utility.BatchInstance.batch);

        enemy.draw(delta);
        player.draw(delta);

        messageBox.draw(BatchInstance.batch, delta);

        endTurnButton.draw(delta);
        BatchInstance.batch.end();

        if(enemy.isFinished()){ //Wenn der Bot fertig ist
            switchTurn();
            enemy.resetFinished();
        }

        if(player.getTurn()){ //Man kann den Knopf nur drücken, wenn der Spieler am Zug ist
            if(endTurnButton.isPressedDelayed()){
                switchTurn();
                endTurnButton.reset();
                Gdx.app.debug("CARDGAME", "KNOPF WURDE GEDRÜCKT");
            }
        }else{
            if(endTurnButton.isPressedDelayed()){
                messageBox.showMessage("Du bist leider nicht am Zug!");
                endTurnButton.reset();
            }
        }
    }

    /**
     * @author Noah O. Wantoch
     * Wechselt den Zug von Spieler auf Gegner oder andersherum.
     */
    public void switchTurn(){
        messageBox.showMessage("Zug beendet.");
        player.setTurn(!player.getTurn());
        enemy.setTurn(!enemy.getTurn());
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

        float turnChance = (int) (Math.random() * 100f); //Die Chance anzufangen
        boolean playerTurn = false;

        if(turnChance > 50) playerTurn = true; //else playerTurn = false

        if(playerTurn) messageBox.showMessage("Du fängst an!");
        else messageBox.showMessage("Der Gegner fängt an!");

        player.setTurn(playerTurn);
        enemy.setTurn(!playerTurn);

        enemy.setLife(3000);
        this.enemy = enemy;

        //FOR TESTING
        Deck deck = new Deck();
        deck.addCard(new Card("TEST_KARTE", 30, 30, Element.ANTIMATTER, new Effect("Heile eine Animaterie-Karte", EffectModule.HEAL_N, 10, Element.ANTIMATTER)),
                    new Card("TEST_KARTE", 30, 30, Element.WIND, new Effect("Heile eine Wind-Karte", EffectModule.HEAL_N, 10, Element.WIND)),
                    new Card("Heilerin", 20, 3000, Element.NATURE, new Effect("Heile dich selbst um 240.", EffectModule.HEAL_HERO, 240), new Tribute(Element.ANTIMATTER, 1)),
                    new Card("BESCHTE HEILERIN", 1, 1, Element.ICE, new Effect("Heile dein Team um 400", EffectModule.HEAL_TEAM, 400, Element.WIND)) );

        player.setDeck(deck);
        player.setLife(6000);
        this.player = player;
        this.player.drawCard(GameSettings.handcardsStartNumber);
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
