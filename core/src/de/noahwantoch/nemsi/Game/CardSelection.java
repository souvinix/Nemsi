package de.noahwantoch.nemsi.Game;

import java.util.ArrayList;

public class CardSelection {
    private static CardSelection instance;

    private ArrayList<Integer> indicies;
    private ArrayList<Card> cards;

    private CardSelection(){
        this.indicies = new ArrayList<>();
        this.cards = new ArrayList<>();
    }

    private CardSelection getInstance(){
        if(instance == null) instance = new CardSelection();
        return instance;
    }

    private void clean(){
        indicies = new ArrayList<>();
        cards = new ArrayList<>();
    }
}
