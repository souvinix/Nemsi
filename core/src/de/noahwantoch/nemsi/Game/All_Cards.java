package de.noahwantoch.nemsi.Game;

import java.util.ArrayList;
import java.util.List;

//Sozusagen eine Datenbank für alle Karten
//Karten MÜSSEN mit Card.copy() kopiert werden
public class All_Cards{

    private static final String TAG = All_Cards.class.getSimpleName();
    private static All_Cards instance;

    //Alle Karten werden in eine Liste zusammengefügt
    private static List<Card> all_cards = new ArrayList<>();

    //Alle Feuerkarten
    public static Card[] fire_cards = {
            new Card("RA", 100, 200, Element.FIRE),
            new Card("Elementar-Kobold", 30, 120, Element.FIRE),
            new Card("Phoenix", 150, 300, Element.FIRE),
            new Card("Garodon", 300, 500, Element.FIRE),
            new Card("Sklave der Sonne", 30, 30, Element.FIRE)
    };

    public static Card[] wind_cards = {
            new Card("Sturmklinge", 200, 90, Element.WIND),
            new Card("Sky", 220, 140, Element.WIND),
            new Card("Sensei", 300, 300, Element.WIND),
            new Card("Balso-Diener", 30, 20, Element.WIND),
            new Card("Elisabeth", 120, 100, Element.WIND)
    };

    public static Card[] nature_cards = {
            new Card("Baum der Weisen", 20, 420, Element.NATURE),
            new Card("Der Gefallene", 70, 200, Element.NATURE),
            new Card("Blattklinge", 250, 600, Element.NATURE),
            new Card("Laub-Diener", 10, 40, Element.NATURE),
            new Card("Eichkaiser", 100, 1000, Element.NATURE)
    };

    public static Card[] antimatter_cards = {
            new Card("Finsternis", 300, 300, Element.ANTIMATTER),
            new Card("Dunkeles Fünkchen", 40, 40, Element.ANTIMATTER),
            new Card("Verlorener Berserker", 180, 100, Element.ANTIMATTER),
            new Card("Dunkler Lord", 800, 500, Element.ANTIMATTER),
            new Card("N E M E S I S", 1400, 2000, Element.ANTIMATTER)
    };

    public static Card[] ice_cards = {
            new Card("Schneekönigin", 90, 310, Element.ICE),
            new Card("Eis-Vikinger", 200, 400, Element.ICE),
            new Card("Wächterin Eris", 100, 800, Element.ICE),
            new Card("Gefallener Drache", 700, 1200, Element.ICE),
            new Card("Königin Emilia", 1000, 2500, Element.ICE)
    };


    private All_Cards(){}
    public static void initialize(){
        instance = new All_Cards();

        for(Card fireCard : fire_cards){
            all_cards.add(fireCard);
        }
        for(Card windCard : wind_cards){
            all_cards.add(windCard);
        }
        for(Card natureCard : nature_cards){
            all_cards.add(natureCard);
        }
        for(Card antimatterCard : antimatter_cards){
            all_cards.add(antimatterCard);
        }
        for(Card iceCard : ice_cards){
            all_cards.add(iceCard);
        }
    }

    //Erstellt ein zufälliges Deck aus allen Karten mit cardNumber Karten
    public static Deck getRandomDeck(int cardNumber){
        Deck deck = new Deck();
        for(int i = 0; i < cardNumber; i++){
            int random = (int) Math.round(Math.random() * (all_cards.size() - 1));
            deck.addCard(all_cards.get(random).getCopy());
        }

        return deck;
    }
}
