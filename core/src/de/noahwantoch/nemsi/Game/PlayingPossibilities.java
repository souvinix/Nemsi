package de.noahwantoch.nemsi.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import de.noahwantoch.nemsi.TextureHandling.TextureEnum;
import de.noahwantoch.nemsi.Utility.BatchInstance;
import de.noahwantoch.nemsi.Utility.TouchDetector;

/**
 * @author Noah. O. Wantoch
 * Diese Klasse liefert die Vorraussetzungen für die Tochterklassen Enemy und Player
 * @see Enemy
 * @see Player
 */
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
    public float drawCardCounter;

    public float deckDrawingOffset;
    public float graveyardDrawingOffset;
    public float banishedCardsDrawingOffset;

    //for drawing animation
    public ArrayList<Card> currentDrawingCards; //Karten, die zu einem bestimmten Zeitpunkt gezogen werden
    public ArrayList<Card> currentPlayingCards; //Karten, die zu einem bestimmten Zeitpunkt gespielt werden

    public float drawSpeed;
    public float heightOffset; //Die Höhe der Handkarten / des Decks
    public float hoveringOffset; //Wie viele px soll die Karte nach oben/unten gehen, wenn man über sie drüber "fährt"/"hovered"

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
        //Die Karten, die temporär in die Liste gesetzt wurden, um sie für die Spiel-Animation zu verwenden
        currentPlayingCards = new ArrayList<>();

        touchDetector = new TouchDetector();
    }

    /**
     * @author Noah. O. Wantoch
     * Die Methode, welche die Position der Karten (die gerade gezogen werden) animiert
     * @param handPosition Die Position der Handposition des Objekt
     * @see Deck
     */
    public void moveCardsAnimation(Vector2 handPosition){
        //Zieh-Animation --> Kopien werden auf die Hand geslided
        for(int i = 0; i < handcards.size(); i++){
            handcards.get(i).setPosition(handPosition.x - (GameSettings.cardWidth / 2f) * handcards.size() + i * (GameSettings.cardWidth + GameSettings.handcardOffset) - GameSettings.handcardOffset,
                    handPosition.y);
        }
    }

    /**
     * @author Noah O. Wantoch
     * Aktualisiert die Position der Feldkarten
     */
    public void updateFieldcards(float fieldcardY){
        for(int i = 0; i < fieldcards.size(); i++){
            fieldcards.get(i).setPosition((Gdx.graphics.getWidth() / 2f - fieldcards.get(i).getWidth() / 2f + i * GameSettings.fieldcardOffset) - (fieldcards.size() - 1) * GameSettings.fieldcardOffset / 2f,
                    fieldcardY);
        }
    }

    /**
     * @author Noah O. Wantoch
     * @param delta delta
     * @param deckPosition Die Position des Decks
     * @param openCards Ob die Karten aufgedeckt werden sollen beim Ziehen
     * moveCardsAnimation() passiert innerhalb einer bestimmen Zeit bzw. bis zu einer bestimmten Position, weil sie hier gestoppt wird
     * Zieh-Animation: Die Karten wechseln sichtbar ihre X-Koordinaten
     */
    public void updateCardAnimation(float delta, Vector2 deckPosition, boolean openCards){
        drawCardCounter += delta;
        if(currentDrawingCards.size() > 0 && drawCardCounter < GameSettings.drawCardSeconds){
            drawCardAnimation(deckPosition, openCards);
            checkDrawingCondition();
        }else if(currentDrawingCards.size() > 0 && drawCardCounter >= GameSettings.drawCardSeconds){
            drawCardCounter = 0.0f;
            handcards.add(currentDrawingCards.get(currentDrawingCards.size() - 1));
            currentDrawingCards.remove(currentDrawingCards.size() - 1);
        }
    }

    /**
     * @author Noah O. Wantoch
     * Hier definieren die Tochterklasse die Bedingung dafür, wann die "Zieh"-Animation stoppt
     */
    public void checkDrawingCondition(){}

    /**
     * @author Noah. O. Wantoch
     * Das Game-Object (Player, Enemy) wird gemalt
     * @param delta delta
     */
    public void draw(float delta){
        deckZone.draw(BatchInstance.batch);
        graveyardZone.draw(BatchInstance.batch);
        banishedCardsZone.draw(BatchInstance.batch);

        deck.draw(delta, deckDrawingOffset);
        graveyard.draw(delta, graveyardDrawingOffset);
        banishedCards.draw(delta, banishedCardsDrawingOffset);

        //Die oberste Karten (die gezogen wird), wird gemalt
        if(currentDrawingCards.size() > 0){
            currentDrawingCards.get(currentDrawingCards.size() - 1).draw(delta);
        }

        //Feldkarten werden gemalt
        for(Card fieldcard : fieldcards){
            fieldcard.draw(delta);
        }

        //Touch-Detection wird erkannt
        checkForInteraction();

        //Handkarten werden gemalt
        for(Card handcard : handcards){
            handcard.draw(delta);
        }
    }

    /**
     * @author Noah O. Wantoch
     * Überprüft, ob der Benutzer über eine Karte "hovered". Wenn ja: die Karte wird ein kleines Stück nach oben bzw. unten bewegt
     */
    public void checkForInteraction(){
        for(int i = 0; i < handcards.size(); i++){
            //wenn man über eine Karte drüber "hovered"
            if(touchDetector.isHoveredOverRectangle(handcards.get(i).getX(), heightOffset, GameSettings.cardWidth, GameSettings.cardHeight)){
                handcards.get(i).setPosition(handcards.get(i).getX(), heightOffset + hoveringOffset);
                if(Gdx.input.justTouched()){
                    cardInteraction(i);
                }
            }else{
                handcards.get(i).setPosition(handcards.get(i).getX(), heightOffset);
            }
        }

        for(int i = 0; i < fieldcards.size(); i++){
            //wenn man über eine Karte drüber "hovered"
            if(touchDetector.isHoveredOverRectangle(fieldcards.get(i).getX(), fieldcards.get(i).getY(), GameSettings.cardWidth, GameSettings.cardHeight)){
                if(fieldcards.get(i).getSize() == 1f){
                    fieldcards.get(i).reinitialize(GameSettings.cardZoom);
                }
            }else{
                if(fieldcards.get(i).getSize() == GameSettings.cardZoom){
                    fieldcards.get(i).reinitialize(1f);
                }
            }
        }
    }

    /**
     * @author Noah O. Wantoch
     * @param index Der Index der Karte in handcards, welcher bestimmt, mit welcher Karte interagiert werden soll
     * Es geschieht eine beliebige Interaktion mit der Karte handcards.get(index)
     */
    public void cardInteraction(int index){}

    /**
     * @author Noah O. Wantoch
     * Die Animation des Ziehens wird hier umgesetzt (Positionen verändern sich pro render(delta))
     * @param deckPosition Die Position des Decks
     * @param openCards Ob die Karten aufgedeckt werden sollen beim Ziehen
     */
    public void drawCardAnimation(Vector2 deckPosition, boolean openCards){
        if(openCards) currentDrawingCards.get(currentDrawingCards.size() - 1).toOpen(); //Letzte Karte wird umgedreht
        currentDrawingCards.get(currentDrawingCards.size() - 1).setPosition(currentDrawingCards.get(currentDrawingCards.size() - 1).getX() - drawSpeed, deckPosition.y);
    }

    /**
     * @author Noah O. Wantoch
     * @param deck Das Deck, welches gesetzt wird
     * @see Deck
     * Setzt das Deck auf ein Bestimmtes
     */
    public void setDeck(Deck deck){
        this.deck = deck;
    }

    /**
     * @author Noah O. Wantoch
     * Eine Karte ziehen
     * @param number Wie viele Karten gezogen werden sollen
     * @param deckPosition Die Position des Decks
     */
    public void drawCard(int number, Vector2 deckPosition){
        for(int i = 0; i < number; i++){
            if(deck.getSize() > 0){
                Card card = deck.pop();
                card.setPosition(deckPosition.x, deckPosition.y);
                currentDrawingCards.add(card); //Eine Liste, welche dafür verantwortlich ist, die Karten-Zieh-Animation der darin enthaltenen Karten zu malen
            }
        }
    }

    /**
     * @author Noah O. Wantoch
     * @param index index in handcards
     * Bringt die Karte handcards.get(index) auf das Spielfeld
     */
    public void playCard(int index){
        Card card = handcards.get(index);
        fieldcards.add(card);
        handcards.remove(index);
    }

    /**
     * @author Noah O. Wantoch
     * @param life Das Leben des Objects
     * Setzt das Leben auf einen konkreten Wert.
     */
    public void setLife(int life){
        this.life = life;
    }
}
