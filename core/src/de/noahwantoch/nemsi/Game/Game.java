package de.noahwantoch.nemsi.Game;

//Die Daten werden dann später einfach aus einer DB gezogen
public class Game {

    private static Player player = new Player();
    private static int gold = 0;
    private static int level;
    //...

    private Game(){

    }

    public static Player getPlayer(){
        player.setDeck(All_Cards.getRandomDeck(40));
        return player;
    }


}
