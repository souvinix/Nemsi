package de.noahwantoch.nemsi.Game;

import java.util.ArrayList;
import java.util.List;

import de.noahwantoch.nemsi.EffectModule;

//Sozusagen eine Datenbank für alle Karten
//Karten MÜSSEN mit Card.copy() kopiert werden
public class All_Cards{

    private static final String TAG = All_Cards.class.getSimpleName();

    //Alle Karten werden in eine Liste zusammengefügt
    private static List<Card> all_cards = new ArrayList<>();

    //Alle Feuerkarten
    public static Card[] fire_cards = {
            new Card("RA", 100, 200, Element.FIRE, new Effect("Zerstöre eine Feuer-Karte.", EffectModule.DESTROY_N, 1, Element.FIRE)),
            new Card("Elementar-Kobold", 30, 120, Element.FIRE, new Effect("Ziehe eine Karte.", EffectModule.DRAW_N, 1)),
            new Card("Phoenix", 150, 300, Element.FIRE, new Effect("Verursache 200 Schaden (Nur Karten).", EffectModule.DAMAGE_N, 200)),
            new Card("Garodon", 300, 500, Element.FIRE, new Effect("Zerstöre 2 Eis-Karten.", EffectModule.DESTROY_N, 2, Element.ICE), new Tribute(Element.NO_ELEMENT, 1)),
            new Card("Sklave der Sonne", 30, 30, Element.FIRE, new Effect("Heile dich um 200.", EffectModule.HEAL_HERO, 200))
    };

    public static Card[] wind_cards = {
            new Card("Sturmklinge", 200, 90, Element.WIND, new Effect("Zerstöre eine Karte.", EffectModule.DESTROY_N, 1)),
            new Card("Sky", 220, 140, Element.WIND, new Effect("Ziehe eine Wind-Karte.", EffectModule.DRAW_N, 1, Element.WIND)),
            new Card("Sensei", 300, 300, Element.WIND, new Effect("Zerstöre 2 Wind-Karten", EffectModule.DESTROY_N, 2, Element.WIND), new Tribute(Element.NO_ELEMENT, 1)),
            new Card("Balso-Diener", 30, 20, Element.WIND, new Effect("Ziehe eine Karte.", EffectModule.DRAW_N, 1)),
            new Card("Elisabeth", 120, 100, Element.WIND, new Effect("Ziehe 3 Karten.", EffectModule.DRAW_N, 3))
    };

    public static Card[] nature_cards = {
            new Card("Baum der Weisen", 20, 500, Element.NATURE, new Effect("Heile dich um 300", EffectModule.HEAL_HERO, 300), new Tribute(Element.FIRE, 1)),
            new Card("Der Gefallene", 70, 200, Element.NATURE, new Effect("Ziehe eine Wind-Karte.", EffectModule.DRAW_N, 1, Element.WIND)),
            new Card("Blattklinge", 250, 600, Element.NATURE, new Effect("Heile dich um 200.", EffectModule.HEAL_HERO, 200), new Tribute(Element.NATURE, 1)),
            new Card("Laub-Diener", 10, 40, Element.NATURE, new Effect("Ziehe eine Karte.", EffectModule.DRAW_N, 1)),
            new Card("Eichkaiser", 100, 1000, Element.NATURE, new Effect("Heile dich um 1000.", EffectModule.HEAL_HERO, 1000), new Tribute(Element.NATURE, 2))
    };

    public static Card[] antimatter_cards = {
            new Card("Finsternis", 300, 300, Element.ANTIMATTER, new Effect("Zerstöre eine Natur-Karte.", EffectModule.DESTROY_N, 1, Element.NATURE)),
            new Card("Dunkeles Fünkchen", 40, 40, Element.ANTIMATTER, new Effect("Ziehe eine Antimatierie-Karte.", EffectModule.DRAW_N, 1, Element.ANTIMATTER)),
            new Card("Verlorener Berserker", 180, 100, Element.ANTIMATTER, new Effect("Heile dich um 100.", EffectModule.HEAL_HERO, 100)),
            new Card("Dunkler Lord", 800, 500, Element.ANTIMATTER, new Effect("Zerstöre 2 Feuer-Karten.", EffectModule.DESTROY_N, 2, Element.FIRE), new Tribute(Element.ANTIMATTER, 2)),
            new Card("NEMESIS", 1400, 2000, Element.ANTIMATTER, new Effect("Zerstöre alle anderen Karten", EffectModule.DESTROY_ALL_OTHER, 0), new Tribute(Element.ANTIMATTER, 3))
    };

    public static Card[] ice_cards = {
            new Card("Schneekönigin", 90, 310, Element.ICE, new Effect("Heile dich um 400.", EffectModule.HEAL_HERO, 400)),
            new Card("Eis-Vikinger", 200, 400, Element.ICE, new Effect("Zerstöre eine Antimatierie-Karte.", EffectModule.DESTROY_N, 1, Element.ANTIMATTER)),
            new Card("Wächterin Eris", 100, 800, Element.ICE, new Effect("Heile eine Karte um 800.", EffectModule.HEAL_N, 800), new Tribute(Element.ICE, 1)),
            new Card("Gefallener Drache", 700, 1200, Element.ICE, new Effect("Heile eine Wind-Karte um 1500.", EffectModule.HEAL_N, 1500, Element.WIND), new Tribute(Element.ICE, 2)),
            new Card("Königin Emilia", 1000, 1500, Element.ICE, new Effect("Heile alle verbündeten Karten um 1000.", EffectModule.HEAL_TEAM, 1000), new Tribute(Element.NATURE, 2))
    };


    private All_Cards(){}
    public static void initialize(){
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
