package de.noahwantoch.nemsi.Game;

public class TributeHandler {

    public TributeHandler(){}

    public void draw(){

    }

    /**
     * @author Noah O. Wantoch
     * @param tribute Das Tribut
     * @see Tribute
     * @param cards Die ausgewählten Karten
     * @see Card
     * @return Ob die Tributbeschwörung mit den ausgewählten Karten möglich ist
     */
    public boolean checkTribute(Tribute tribute, Card... cards){
        if(cards.length < tribute.getNeededCards()) return false;

        int counter = 0;
        if(tribute.getNeededElement() != Element.NO_ELEMENT){ //Wenn das Element kein spezifisches Element als Tribut braucht
            if(cards.length == tribute.getNeededCards()) return true; //Wenn die Anzahl der ausgewählten Karten der Anzahl der benötigten Karten entsprechen
            else return false; //Wenn das nicht so ist
        }else{ //Wenn das Element des Tributs spezifisch ist
            for(Card card : cards){
                if(card.getElement() == tribute.getNeededElement()){
                    counter += 1; //Zählervariable für die Anzahl der in cards enthaltenen Karten, die das richtige Element haben
                }else return false; //Wenn eins dabei ist, welches nicht das gesuchte Element hat/ist
            }

            if(counter == tribute.getNeededCards()) return true; //Wenn alles stimmt
        }
        return false;
    }
}
