package de.noahwantoch.nemsi.Game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import de.noahwantoch.nemsi.TextureHandling.TextureEnum;
import de.noahwantoch.nemsi.Utility.BatchInstance;
import de.noahwantoch.nemsi.Utility.TouchDetector;

public class PlayingPossibilities {

    public int life;

    public de.noahwantoch.nemsi.Game.Deck deck;
    public de.noahwantoch.nemsi.Game.Deck graveyard;
    public de.noahwantoch.nemsi.Game.Deck banishedCards;

    public ArrayList<de.noahwantoch.nemsi.Game.Card> handcards;
    public ArrayList<de.noahwantoch.nemsi.Game.Card> fieldcards;

    public Sprite deckZone;
    public Sprite graveyardZone;
    public Sprite banishedCardsZone;

    public Vector2 handPosition; //Position der Handkarten (Mitte)

    public TouchDetector touchDetector;

    //for drawing animation
    public ArrayList<Card> currentDrawingCards;

    public PlayingPossibilities(){
        deck = new de.noahwantoch.nemsi.Game.Deck();
        graveyard = new de.noahwantoch.nemsi.Game.Deck();
        banishedCards = new de.noahwantoch.nemsi.Game.Deck();

        handcards = new ArrayList<>();
        fieldcards = new ArrayList<>();

        //Die Positionen der Deckzonen werden in den Tochterklassen verändert
        deckZone = new Sprite(new Texture(TextureEnum.STAPEL_ZONE.getPath()));
        deckZone.setSize(de.noahwantoch.nemsi.Game.GameSettings.cardWidth, de.noahwantoch.nemsi.Game.GameSettings.cardHeight);
        graveyardZone = new Sprite(new Texture(TextureEnum.STAPEL_ZONE.getPath()));
        graveyardZone.setSize(de.noahwantoch.nemsi.Game.GameSettings.cardWidth, de.noahwantoch.nemsi.Game.GameSettings.cardHeight);
        banishedCardsZone = new Sprite(new Texture(TextureEnum.STAPEL_ZONE.getPath()));
        banishedCardsZone.setSize(de.noahwantoch.nemsi.Game.GameSettings.cardWidth, GameSettings.cardHeight);

        handPosition = new Vector2();

        //Die Karten, die temporär in die Liste gesetzt wurden, um sie für die Zieh-Animation zu verwenden
        currentDrawingCards = new ArrayList<>();

        touchDetector = new TouchDetector();
    }

    public void draw(float delta){
        deckZone.draw(BatchInstance.batch);
        graveyardZone.draw(BatchInstance.batch);
        banishedCardsZone.draw(BatchInstance.batch);

        deck.draw(delta);
        graveyard.draw(delta);
        banishedCards.draw(delta);

        //Die oberste Karten (die gezogen wird), wird gemalt
        if(currentDrawingCards.size() > 0){
            currentDrawingCards.get(currentDrawingCards.size() - 1).draw(delta);
        }
    }

    public void setDeck(Deck deck){
        this.deck = deck;
    }

    //Eine Karte ziehen
    public void drawCard(int number){

    }
}
