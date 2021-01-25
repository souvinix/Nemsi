package de.noahwantoch.nemsi.Game;

import com.badlogic.gdx.Gdx;

/**
 * @author Noah O. Wantoch
 * Der Spieler.
 * @see de.noahwantoch.nemsi.Game.PlayingPossibilities
 */
public class Player extends PlayingPossibilities{

    // private static final String TAG = PlayingPossibilities.class.getSimpleName(); //For debugging

    public Player(){
        super();

        //Deck, Friedhof und verbannten Karten auf die vor-definierten Positionen setzen
        deck.setPosition(GameSettings.PlayerPositions.deckPosition);
        graveyard.setPosition(GameSettings.PlayerPositions.graveyardPosition);
        banishedCards.setPosition(GameSettings.PlayerPositions.banishedCardsPosition);
        deckZone.setPosition(GameSettings.PlayerPositions.deckPosition.x, GameSettings.PlayerPositions.deckPosition.y);
        graveyardZone.setPosition(GameSettings.PlayerPositions.graveyardPosition.x, GameSettings.PlayerPositions.graveyardPosition.y);
        banishedCardsZone.setPosition(GameSettings.PlayerPositions.banishedCardsPosition.x, GameSettings.PlayerPositions.banishedCardsPosition.y);

        //Beschreibt die Mitte (x) und offset nach oben (y = GameSettings.heightOffset)
        handPosition.set(GameSettings.PlayerPositions.handPosition);

        //Das Deck und der Friedhof liegen rechts, die verbannten Karten links
        super.deckDrawingOffset = GameSettings.drawingDeckOffset;
        super.banishedCardsDrawingOffset = -GameSettings.drawingDeckOffset;
        super.graveyardDrawingOffset = GameSettings.drawingDeckOffset;
        super.drawSpeed = GameSettings.drawSpeed;
        super.heightOffset = GameSettings.heightOffset;
        super.hoveringOffset = heightOffset;

        updateLife();
    }

    @Override
    public void updateLife() {
        super.updateLife();
        lifeFont.setPosition(GameSettings.PlayerPositions.lifePosition.x, GameSettings.PlayerPositions.lifePosition.y);
    }

    @Override
    public void getDamage(int value) {
        super.getDamage(value);
        lifeFont.setPosition(GameSettings.PlayerPositions.lifePosition.x, GameSettings.PlayerPositions.lifePosition.y);
    }

    @Override
    public void heal(int value) {
        super.heal(value);
        lifeFont.setPosition(GameSettings.PlayerPositions.lifePosition.x, GameSettings.PlayerPositions.lifePosition.y);
    }

    @Override
    public void cardInteraction(int index) {
        super.cardInteraction(index);
        Gdx.app.debug("PlayingPossibilities", "index: " + index + ", handcards.size(): " + handcards.size());
        super.playCard(index);
    }

    @Override
    public void setDeck(Deck deck){
        deck.setPosition(GameSettings.PlayerPositions.deckPosition);
        super.setDeck(deck);
    }

    @Override
    public void draw(float delta){
        super.draw(delta);

        super.updateFieldcards(GameSettings.PlayerPositions.fieldcardY); //Die Position der Feldkarten wird aktualisiert
        super.moveCardsAnimation(GameSettings.PlayerPositions.handPosition);
        super.updateCardAnimation(delta, GameSettings.PlayerPositions.deckPosition, true);
    }

    /**
     * @author Noah. O. Wantoch
     * Es werden number Karten gezogen, um die obere Mechanik in Gang zu setzen
     * @param number Wie viele Karten gezogen werden sollen
     */
    public void drawCard(int number){
        super.drawCard(number);
    }

    @Override
    public void checkDrawingCondition() {
        super.checkDrawingCondition();
        if(currentDrawingCards.get(currentDrawingCards.size() - 1).getX() < (Gdx.graphics.getWidth() / 2f) + handcards.size() * (GameSettings.cardWidth / 2f + GameSettings.handcardOffset)){
            super.drawCardCounter = GameSettings.drawCardSeconds;
        }
    }
}
