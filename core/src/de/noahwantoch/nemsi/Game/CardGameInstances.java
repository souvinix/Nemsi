package de.noahwantoch.nemsi.Game;

import java.util.ArrayList;

public class CardGameInstances {
    //Player
    public static ArrayList<Card> player_fieldcards = new ArrayList<>();
    public static int playerLife = 0; //Hier muss sich @CardGame das Leben des Spielers holen, hier kann es auch verändert werden

    //Enemy
    public static ArrayList<Card> enemy_fieldcards = new ArrayList<>();
    public static int enemyLife = 0; //Hier muss sich @CardGame das Leben des jeweiligen Gegners holen, hier kann es auch verändert werden

    private CardGameInstances(){}
}
