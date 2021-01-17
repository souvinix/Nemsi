package de.noahwantoch.nemsi.Game;

import com.badlogic.gdx.Gdx;

/**
 * @author Noah O. Wantoch
 * Ein Gegner.
 * @see PlayingPossibilities
 */
public class Enemy extends PlayingPossibilities{

    public Enemy(){
        super();
        deck.setPosition(GameSettings.EnemyPositions.deckPosition);
        graveyard.setPosition(GameSettings.EnemyPositions.graveyardPosition);
        banishedCards.setPosition(GameSettings.EnemyPositions.banishedCardsPosition);

        deckZone.setPosition(GameSettings.EnemyPositions.deckPosition.x, GameSettings.EnemyPositions.deckPosition.y);
        graveyardZone.setPosition(GameSettings.EnemyPositions.graveyardPosition.x, GameSettings.EnemyPositions.graveyardPosition.y);
        banishedCardsZone.setPosition(GameSettings.EnemyPositions.banishedCardsPosition.x, GameSettings.EnemyPositions.banishedCardsPosition.y);

        //Beschreibt die Mitte (x) und offset nach oben (y = GameSettings.heightOffset)
        handPosition.set(GameSettings.EnemyPositions.handPosition);

        //Das Deck und der Friedhof liegen rechts, die verbannten Karten links
        super.deckDrawingOffset = -GameSettings.drawingDeckOffset;
        super.banishedCardsDrawingOffset = GameSettings.drawingDeckOffset;
        super.graveyardDrawingOffset = -GameSettings.drawingDeckOffset;

        super.drawSpeed = -GameSettings.drawSpeed;
        super.heightOffset = Gdx.graphics.getHeight() * 0.9f - GameSettings.heightOffset;
        super.hoveringOffset = -GameSettings.heightOffset;
    }

    @Override
    public void setDeck(Deck deck) {
        deck.setPosition(GameSettings.EnemyPositions.deckPosition);
        Gdx.app.debug("Enemy", "Deck: " + deck.getSize());
        super.setDeck(deck);
    }

    /**
     * @author Noah O. Wantoch
     * @param number Wie viele Karten gezogen werden sollen
     */
    public void drawCard(int number) {
        super.drawCard(number, GameSettings.EnemyPositions.deckPosition);
    }

    @Override
    public void draw(float delta){
        super.draw(delta);

        super.updateFieldcards(GameSettings.EnemyPositions.fieldcardY); //Die Position der Feldkarten wird aktualisiert
        super.moveCardsAnimation(GameSettings.EnemyPositions.handPosition);
        super.updateCardAnimation(delta, GameSettings.EnemyPositions.deckPosition, false);
    }

    @Override
    public void checkDrawingCondition() {
        super.checkDrawingCondition();
        if(currentDrawingCards.get(currentDrawingCards.size() - 1).getX() > (Gdx.graphics.getWidth() / 2f) + handcards.size() * (GameSettings.cardWidth / 2f + GameSettings.handcardOffset)){
            super.drawCardCounter = GameSettings.drawCardSeconds;
        }
    }
}
