package de.noahwantoch.nemsi.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import de.noahwantoch.nemsi.EffectModule;
import de.noahwantoch.nemsi.TextureHandling.TextureEnum;
import de.noahwantoch.nemsi.UI.Font;
import de.noahwantoch.nemsi.Utility.BatchInstance;
import de.noahwantoch.nemsi.Utility.FontEnum;
import de.noahwantoch.nemsi.Utility.TouchDetector;

/**
 * @author Noah. O. Wantoch
 * Diese Klasse liefert die Vorraussetzungen für die Tochterklassen Enemy und Player
 * @see Enemy
 * @see Player
 */
public class PlayingPossibilities {
    public int life;
    public Font lifeFont;
    public Vector2 lifePosition;

    protected Font deckFont; //Anzahl der Deckkarten
    protected Font graveyardFont; //Anzahl der Friedhoskarten

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

    public int cardsPerTurn = GameSettings.cardsPerTurn;
    public int cardCounter;

    public Effect currentEffect;

    public boolean summonCard = false;
    public int currentSummonIndex = 0;
    public boolean selectingTributes = false;

    public TributeHandler tributeHandler;

    public Effect currentExecutingEffect;

    public boolean healing = false;
    public boolean destroying = false;
    public boolean damaging = false;

    public boolean turn;
    protected boolean interaction = true; //Ob die Karten sich bewegen sollen, wenn man drüber "hovered"

    public PlayingPossibilities(){
        deck = new de.noahwantoch.nemsi.Game.Deck();
        graveyard = new de.noahwantoch.nemsi.Game.Deck();
        banishedCards = new de.noahwantoch.nemsi.Game.Deck();

        deckFont = new Font(FontEnum.Retganon.getFontDataName(), GameSettings.numberFontSize, Integer.toString(deck.getSize()));
        graveyardFont = new Font(FontEnum.Retganon.getFontDataName(), GameSettings.numberFontSize, Integer.toString(graveyard.getSize()));
        deckFont.setColor(GameSettings.color_numberVisualization.r, GameSettings.color_numberVisualization.g, GameSettings.color_numberVisualization.b, GameSettings.color_numberVisualization.a);
        graveyardFont.setColor(GameSettings.color_numberVisualization.r, GameSettings.color_numberVisualization.g, GameSettings.color_numberVisualization.b, GameSettings.color_numberVisualization.a);

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

        currentEffect = null;

        this.tributeHandler = new TributeHandler();
        lifePosition = new Vector2();
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

    public void heal(int value){
        life += value;
        updateLife();
    }

    public void getDamage(int value){
        life -= value;
        updateLife();
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

        int currentDestroyedCard = 0;
        boolean destroyedCard = false;
        for(int i = 0; i < fieldcards.size(); i++){
            if(fieldcards.get(i).isDestroyed()){
                currentDestroyedCard = i;
                destroyedCard = true;
            }
        }

        if(destroyedCard) destroyCard(currentDestroyedCard);

        //Die oberste Karten (die gezogen wird), wird gemalt
        if(currentDrawingCards.size() > 0){
            currentDrawingCards.get(currentDrawingCards.size() - 1).draw(delta);
        }

        //Feldkarten werden gemalt
        for(Card fieldcard : fieldcards){
            fieldcard.draw(delta);
        }

        interaction = false;
        //Touch-Detection wird erkannt
        if(!CardGame.okayMessageBox.getState() && !CardGame.yesNoMessageBox.getState() && !CardGame.effectMessageBox.getState()){
            interaction = true; //Wird in checkForInteraction benutzt
        }
        checkForInteraction();

        //Handkarten werden gemalt
        for(Card handcard : handcards){
            handcard.draw(delta);
        }

        lifeFont.draw(BatchInstance.batch); //Die Visualisierung der Lebenszahl
        deckFont.draw(BatchInstance.batch);
        graveyardFont.draw(BatchInstance.batch);
    }

    /**
     * @author Noah O. Wantoch
     * Schickt die Karten von dem Feld auf den Friedhof
     */
    public void fromFieldToGraveyard(){
        //Tochterklassen überschreiben diese Funktion
    }

    /**
     * @author Noah O. Wantoch
     * Überprüft, ob der Benutzer über eine Karte "hovered". Wenn ja: die Karte wird ein kleines Stück nach oben bzw. unten bewegt
     */
    public void checkForInteraction(){
        if(interaction){
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
        }else{
            for(int i = 0; i < handcards.size(); i++){
                handcards.get(i).setPosition(handcards.get(i).getX(), heightOffset);
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
     */
    public void drawCardAnimation(Vector2 deckPosition, boolean openCards){
        currentDrawingCards.get(currentDrawingCards.size() - 1).setPosition(currentDrawingCards.get(currentDrawingCards.size() - 1).getX() - drawSpeed * Gdx.graphics.getDeltaTime(), deckPosition.y);
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
     */
    public void drawCard(int number){
        drawCardCounter = 0;
        for(int i = 0; i < number; i++){
            if(deck.getSize() > 0){
                Card card = deck.pop();
                card.toOpen();
                card.setPosition(deck.getPosition().x, deck.getPosition().y);
                currentDrawingCards.add(card); //Eine Liste, welche dafür verantwortlich ist, die Karten-Zieh-Animation der darin enthaltenen Karten zu malen
            }
        }
    }

    public void destroyCard(int fieldcardIndex){
        Card card = fieldcards.get(fieldcardIndex);
        fieldcards.remove(fieldcardIndex);
        graveyard.addCard(card);

        //Friedhof wird aktualisiert --> Sie sind dann nicht mehr selektiert oder noch groß (weil drüber "gehovered" wurde)
        for(int i = 0; i < graveyard.getSize(); i++){
            graveyard.getCard(i).reinitialize(1f);
            graveyard.getCard(i).deselect();
        }
    }

    /**
     * @author Noah O. Wantoch
     * @param index index in handcards
     * Bringt die Karte handcards.get(index) auf das Spielfeld
     */
    public void playCard(int index){
        if(turn) {
            boolean summon = false;
            Card card = handcards.get(index);

            if(card.getTribute().getNeededCards() > fieldcards.size()) { //Wenn (erst) kein Tribut möglich ist, weil die Feldkarten zu klein sind
                CardGame.okayMessageBox.showMessage("Du brauchst mindestens: " + card.getTribute().getNeededCards() + " Karten auf dem Spielfeld zum opfern."); return;
            }

            if(fieldcards.size() >= GameSettings.maxFieldcards && handcards.get(index).getTribute().getNeededCards() == 0){ //Es kann nur eine bestimmte Anzahl an Karten auf dem Feld liegen (pro Spieler)
                CardGame.okayMessageBox.showMessage("Du kannst keine weitere Karte mehr beschwören!"); return;
            }

            if(card.getTribute().getNeededCards() <= fieldcards.size()) { //Wenn genug Feldkarten existieren
                if (card.getTribute().getNeededElement() != Element.NO_ELEMENT) { //Wenn das Element eine Rolle spielt bei der Tributbeschwörung
                    int counter = 0; //Zähler der gültigen Elemente
                    for (Card fieldcard : fieldcards) {
                        if (fieldcard.getElement() == card.getTribute().getNeededElement()) { //Wie viele Feldkarten haben das gewünschte Zielelement
                            counter += 1;
                        }
                    }
                    if (counter >= card.getTribute().getNeededCards()) { //Erfolgreiche Beschwörung
                        summon = true;
                    } else {
                        Gdx.app.debug("PlayingPossibilities_BUG(1): ", "state: " + CardGame.okayMessageBox.getState() + ", result: " + CardGame.okayMessageBox.getResult());
                        CardGame.okayMessageBox.showMessage("Du brauchst mindestens: " + card.getTribute().getNeededCards() + " Karten auf dem Spielfeld von dem Element: " + card.getTribute().getNeededElement().toString());
                        CardGame.okayMessageBox.reset();
                        Gdx.app.debug("PlayingPossibilities_BUG(2): ", "state: " + CardGame.okayMessageBox.getState() + ", result: " + CardGame.okayMessageBox.getResult());
                    }
                } else { //Wenn das Element (erst) keine Rolle spielt bei der Beschwörung
                    summon = true;
                }

                if (cardCounter == cardsPerTurn) {
                    CardGame.okayMessageBox.showMessage("Du hast diese Runde leider schon " + cardCounter + " Karten gespielt :(.");
                    CardGame.okayMessageBox.reset();
                    summon = false;
                }

                if (summon) {
                    CardGame.yesNoMessageBox.showMessage("Möchtest du die Karte: " + card.getName() + " beschwören?");
                    CardGame.yesNoMessageBox.reset();
                    summonCard = true;
                    currentSummonIndex = index;
                }
            }
        }else{ //Wenn man nicht am Zug ist
            CardGame.okayMessageBox.showMessage("Du bist leider nicht am Zug!");
        }
    }

    public void summonDirectly(int handcardIndex){
        if(summonCard){ //Wenn eine Beschwörung erlaubt ist
            if(handcardIndex < handcards.size()){ //Wenn es den Index in handcards gibt
                Card card = handcards.get(handcardIndex);
                cardCounter += 1;
                fieldcards.add(card);
                handcards.remove(handcardIndex);
            }
        }

        if(fieldcards.get(fieldcards.size() - 1).getEffect().getEffectModule() != EffectModule.NO_EFFECT){ //Wenn es ein Effekt hat
            CardGame.effectMessageBox.showMessage("Willst du den Effekt aktivieren?: " + fieldcards.get(fieldcards.size() - 1).getEffect().getDescription());
            currentEffect = fieldcards.get(fieldcards.size() - 1).getEffect();
            Gdx.app.debug("Beschwörung", "currentEffect: " + currentEffect.getEffectModule().toString());
        }

        currentSummonIndex = 0;
        summonCard = false;
        currentExecutingEffect = null; //Wenn man eine Karte beschwört, aber gerade eigentlich einen Effekt aktiviert, darf der Effekt nach der Beschwörung nicht mehr aktiviert werden

        saveFieldcards();
    }

    /**
     * @author Noah O. Wantoch
     * @param effect Der Effekt, der (sicher) ausgelöst wird.
     */
    protected void activateEffect(Effect effect){
        if(effect != null){
            /**
             * ZIEHEN
             */
            if(effect.getEffectModule() == EffectModule.DRAW_N){ //Wenn man eine Karte ziehen soll
                if(effect.getTarget() == Element.NO_ELEMENT){ //Wenn das Element egal ist
                    drawCard(effect.getAmount());
                }else{
                    drawSpecificElement(effect.getAmount(), effect.getTarget()); //Die nächste/-n Karte/-n des gesuchten Elements wird/werden gezogen
                }
            }

            /**
             * HEILEN
             */
            else if(effect.getEffectModule() == EffectModule.HEAL_N){ //Heilen einer anderen Karte (Regel: nur das eigene Team steht zur Auswahl)
                currentExecutingEffect = effect;
                healing = true;
            }

            else if(effect.getEffectModule() == EffectModule.HEAL_HERO){ //Heilen des eigenen Helden (des Spielers)
                heal(effect.getAmount()); //Das Element spielt keine Rolle, deswegen wird kriegt der Spieler einfach das Leben (over-heal)
            }

            else if(effect.getEffectModule() == EffectModule.HEAL_TEAM){ //Heilen von allen ANDEREN Karten (im Team) außer sich selbst
                if(fieldcards.size() > 1){ //Wenn es mehr Karten als die bereits beschworene gibt
                    for(int i = 0; i < fieldcards.size() - 1; i++){ //Es wird über allen außer der letzten Karte iteriert
                        if(effect.getTarget() == Element.NO_ELEMENT){ //Wenn das Element der anderen Karten egal ist
                            fieldcards.get(i).heal(effect.getAmount());
                        }else{
                            if(effect.getTarget() == fieldcards.get(i).getElement()){ //Wenn das Element nicht egal ist
                                fieldcards.get(i).heal(effect.getAmount());
                            }
                        }
                    }
                }

            }

            else if(effect.getEffectModule() == EffectModule.HEAL_ALL){ //Heilen von allen Karten auf dem Spielfeld (auch Gegner)
                if(effect.getTarget() == Element.NO_ELEMENT){ //Wenn das Element egal ist

                }else{ //Wenn das Element eine Rolle spielt

                }
            }

            else if(effect.getEffectModule() == EffectModule.HEAL_ALL_OTHER){ //Heilen des eigenen Team (alle Spielfeldkarten)
                if(effect.getTarget() == Element.NO_ELEMENT){ //Wenn das Element egal ist

                }else{ //Wenn das Element eine Rolle spielt

                }
            }
            /**
             * ZERSTÖREN
             */
            else if(effect.getEffectModule() == EffectModule.DESTROY_N) { //Wenn man eine Karte zerstören soll
                if (effect.getTarget() == Element.NO_ELEMENT) { //Wenn das Element egal ist

                } else { //Wenn das Element eine Rolle spielt

                }
            }

            else if(effect.getEffectModule() == EffectModule.DESTROY_ALL) { //Wenn man alle Karten zerstören will
                if (effect.getTarget() == Element.NO_ELEMENT) { //Wenn das Element egal ist

                } else { //Wenn das Element eine Rolle spielt

                }
            }

            else if(effect.getEffectModule() == EffectModule.DESTROY_ALL_OTHER) { //Wenn man alle Karten außer sich selbst zerstören will
                if (effect.getTarget() == Element.NO_ELEMENT) { //Wenn das Element egal ist

                } else { //Wenn das Element eine Rolle spielt

                }
            }

            /**
             * SCHADEN
             */
            else if(effect.getEffectModule() == EffectModule.DAMAGE_N) { //Wenn man einer Karte schaden will
                if (effect.getTarget() == Element.NO_ELEMENT) { //Wenn das Element egal ist

                } else { //Wenn das Element eine Rolle spielt

                }
            }

            else if(effect.getEffectModule() == EffectModule.DAMAGE_ALL) { //Wenn man allen Karten schaden will
                if (effect.getTarget() == Element.NO_ELEMENT) { //Wenn das Element egal ist

                } else { //Wenn das Element eine Rolle spielt

                }
            }

            else if(effect.getEffectModule() == EffectModule.DAMAGE_HERO) { //Wenn man dem gegnerischen Helden Schaden zufügen will
                if (effect.getTarget() == Element.NO_ELEMENT) { //Wenn das Element egal ist

                } else { //Wenn das Element eine Rolle spielt

                }
            }

            /**
             * SUCHEN
             */
            else if(effect.getEffectModule() == EffectModule.DRAW_SPECIFIC_CARD) { //Wenn man eine Karte sucht (von dem Deck der Hand hinzufügen)
                if (effect.getTarget() == Element.NO_ELEMENT) { //Wenn das Element egal ist

                } else { //Wenn das Element eine Rolle spielt

                }
            }

            currentEffect = null; //wird am Ende wieder auf null gesetzt
        }

        saveFieldcards();

    }

    public void drawSpecificElement(int number, Element element){
        for(int i = 0; i < number; i++){
            if(deck.getSize() > 0){
                int value = deck.putSpecificElementAbove(number, element); //Tut number Karten oben auf das Deck
                drawCard(value); //So viele werden dann gezogen
            }
        }
    }

    /**
     * @author Noah O. Wantoch
     * @param life Das Leben des Objects
     * Setzt das Leben auf einen konkreten Wert.
     */
    public void setLife(int life){
        this.life = life;
        updateLife();
    }

    public void updateLife(){
        lifeFont = new Font(FontEnum.Retganon.getFontDataName(), GameSettings.lifeFontSize, Integer.toString(life));
        lifeFont.setColor(GameSettings.color_lifeFontColor.r, GameSettings.color_lifeFontColor.g, GameSettings.color_lifeFontColor.b, GameSettings.color_lifeFontColor.a);
    }

    public void setTurn(boolean bool){
        turn = bool;
        saveFieldcards();
        cardCounter = 0;
        if(turn) drawCard(1);
    }

    public void saveFieldcards(){}

    public boolean getTurn(){ return turn; }
}
