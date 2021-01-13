package de.noahwantoch.nemsi.Game;

import com.badlogic.gdx.Gdx;

public class Player extends PlayingPossibilities{

    private static final String TAG = PlayingPossibilities.class.getSimpleName();
    private float drawCardCounter = 0.0f;

    public Player(){
        super();

        deck.setPosition(GameSettings.PlayerPositions.deckPosition);
        graveyard.setPosition(GameSettings.PlayerPositions.graveyardPosition);
        banishedCards.setPosition(GameSettings.PlayerPositions.banishedCardsPosition);

        deckZone.setPosition(GameSettings.PlayerPositions.deckPosition.x, GameSettings.PlayerPositions.deckPosition.y);
        graveyardZone.setPosition(GameSettings.PlayerPositions.graveyardPosition.x, GameSettings.PlayerPositions.graveyardPosition.y);
        banishedCardsZone.setPosition(GameSettings.PlayerPositions.banishedCardsPosition.x, GameSettings.PlayerPositions.banishedCardsPosition.y);

        //Beschreibt die Mitte (x) und offset nach oben (y = GameSettings.heightOffset)
        handPosition.set(GameSettings.PlayerPositions.handPosition);
    }

    @Override
    public void setDeck(Deck deck){
        deck.setPosition(GameSettings.PlayerPositions.deckPosition);
        super.setDeck(deck);
    }

    @Override
    public void draw(float delta){
        super.draw(delta);

        //Zieh-Animation --> Kopien werden auf die Hand geslided
        for(int i = 0; i < handcards.size(); i++){
            handcards.get(i).setPosition(GameSettings.PlayerPositions.handPosition.x - (GameSettings.cardWidth / 2f) * handcards.size() + i * (GameSettings.cardWidth + GameSettings.handcardOffset) - GameSettings.handcardOffset,
                    GameSettings.PlayerPositions.handPosition.y);

            //wenn man über eine Karte drüber "hovered"
            if(touchDetector.isHoveredOverRectangle(handcards.get(i).getX(), handcards.get(i).getY(), GameSettings.cardWidth, GameSettings.cardHeight)){
                handcards.get(i).setPosition(handcards.get(i).getX(), handcards.get(i).getY() + GameSettings.heightOffset);
            }else{
                handcards.get(i).setPosition(handcards.get(i).getX(), GameSettings.heightOffset);
            }
            handcards.get(i).draw(delta);
        }

        //Oberes passiert innerhalb einer bestimmen Zeit bzw. bis zu einer bestimmten Position
        //Zieh-Animation: Die Karten wechseln sichtbar ihre X-Koordinate
        drawCardCounter += delta;
        if(currentDrawingCards.size() > 0 && drawCardCounter < GameSettings.drawCardSeconds){
            drawCardAnimation();
            if(currentDrawingCards.get(currentDrawingCards.size() - 1).getX() < (Gdx.graphics.getWidth() / 2f) + handcards.size() * (GameSettings.cardWidth / 2f + GameSettings.handcardOffset)){
                drawCardCounter = GameSettings.drawCardSeconds;
            }
        }else if(currentDrawingCards.size() > 0 && drawCardCounter >= GameSettings.drawCardSeconds){
            drawCardCounter = 0.0f;
            handcards.add(currentDrawingCards.get(currentDrawingCards.size() - 1));
            currentDrawingCards.remove(currentDrawingCards.size() - 1);
        }
    }

    //Die Animation des Ziehens wird hier umgesetzt (Positionen verändern sich pro render(delta))
    public void drawCardAnimation(){
        currentDrawingCards.get(currentDrawingCards.size() - 1).toOpen();
        currentDrawingCards.get(currentDrawingCards.size() - 1).setPosition(currentDrawingCards.get(currentDrawingCards.size() - 1).getX() - GameSettings.drawSpeed,
                                    GameSettings.PlayerPositions.deckPosition.y);
    }

    //Es werden number Karten gezogen, um die obere Mechanik in Gang zu setzen
    @Override
    public void drawCard(int number){
        super.drawCard(number);

        for(int i = 0; i < number; i++){
            if(deck.getSize() > 0){
                Card card = deck.pop();
                card.setPosition(GameSettings.PlayerPositions.deckPosition.x, GameSettings.PlayerPositions.deckPosition.y);
                currentDrawingCards.add(card);
            }
        }
    }
}
