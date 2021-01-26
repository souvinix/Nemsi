package de.noahwantoch.nemsi.Game;

import java.util.ArrayList;

public class CardGameInstances {
    //Player
    public static ArrayList<Card> playerFieldcards = new ArrayList<>();
    public static int playerLife = 0;

    //Enemy
    public static ArrayList<Card> enemyFieldcards = new ArrayList<>();
    public static int enemyLife = 0;

    private CardGameInstances(){}

    public static void clear(){
        playerFieldcards.clear();
        enemyFieldcards.clear();
    }
}
