package de.noahwantoch.nemsi.Game;

public class Enemy extends PlayingPossibilities{

    public Enemy(){
        super();
        deck.setPosition(GameSettings.EnemyPositions.deckPosition);
        graveyard.setPosition(GameSettings.EnemyPositions.graveyardPosition);
        banishedCards.setPosition(GameSettings.EnemyPositions.banishedCardsPosition);

        deckZone.setPosition(GameSettings.EnemyPositions.deckPosition.x, GameSettings.EnemyPositions.deckPosition.y);
        graveyardZone.setPosition(GameSettings.EnemyPositions.graveyardPosition.x, GameSettings.EnemyPositions.graveyardPosition.y);
        banishedCardsZone.setPosition(GameSettings.EnemyPositions.banishedCardsPosition.x, GameSettings.EnemyPositions.banishedCardsPosition.y);
    }

    @Override
    public void setDeck(Deck deck) {
        super.setDeck(deck);
    }

    @Override
    public void drawCard(int number) {
        super.drawCard(number);
    }

    @Override
    public void draw(float delta){
        super.draw(delta);
    }
}
