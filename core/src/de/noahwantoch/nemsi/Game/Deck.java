package de.noahwantoch.nemsi.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    private static final String TAG = Deck.class.getSimpleName();
    private List<Card> cards;

    private Vector2 drawingPosition;

    public Deck(){
        drawingPosition = new Vector2(0, 0);
        init();
    }

    private void init(){
        cards = new ArrayList<>();
    }

    public void draw(float delta){
        for(int i = 0; i < cards.size(); i++){
            float offset = i * GameSettings.drawingDeckOffset;
            cards.get(i).setPosition(drawingPosition.x + offset, drawingPosition.y + offset / 2f);
            cards.get(i).draw(delta);
        }
    }

    public void addCard(Card ... cards){
        for(Card card : cards){
            this.cards.add(card);
        }
    }

    public Card pop(){
        Gdx.app.debug(TAG, "cards.size(): " + cards.size());
        Card card = cards.get(cards.size() - 1);
        cards.remove(cards.size() - 1);
        return card;
    }

    public void setPosition(Vector2 position){
        drawingPosition = position;
    }

    public void sortDeck(){
        for(int x = 0; x < cards.size() - 1; x++){
            for(int i = 0; i < cards.size() - 1; i++){

                //Zweiter bleibt Zweiter
                if(cards.get(i).getName().compareTo((cards.get(i + 1).getName())) < 0){
                    continue;
                }

                //Nichts geschieht
                if(cards.get(i).getName().compareTo((cards.get(i + 1).getName())) == 0){
                    continue;
                }

                //Zweiter wird Erster
                else if(cards.get(i).getName().compareTo((cards.get(i + 1).getName())) > 0){
                    Card card = cards.get(i);
                    cards.set(i, cards.get(i + 1));
                    cards.set(i + 1, card);
                    continue;
                }
            }
        }
    }

    public void printValues(){
        Gdx.app.debug(TAG, "-----DECK-----");
        Gdx.app.debug(TAG, "Size: " + cards.size());
        for(Card card : cards){
            Gdx.app.debug(TAG, card.getName());
        }

        Gdx.app.debug(TAG, "--------------");
    }

    public Card getCard(int i){
        return cards.get(i);
    }

    public int getSize(){
        return cards.size();
    }
}
