package de.noahwantoch.nemsi.Game;

/**
 * @author Noah O. Wantoch
 * Jede Karte besitzt eine Instanz dieser Klasse, um sog. Tributbeschwörungen vollziehen zu können
 */
public class Tribute {

    private Element element; //Das Element, welches gebraucht wird. Bei Element.NO_ELEMENT wird kein spezielles gebraucht
    private int number; //Die Zahl der Karten, die für das Tribut benötigt werden

    /**
     * @author Noah O. Wantoch
     * @param element Die Tribute sollen dieses Element haben
     * @param number Die (genaue) Anzahl der Tribute
     */
    public Tribute(Element element, int number){
        this.element = element;
        this.number = number;
    }

    /**
     * @author Noah O. Wantoch
     * @param cards Array von Karten, welche für das Tribut eingereicht werden
     * @return ob die eingereichten Karten den Anforderungen des Tributs entsprechen
     */
    public boolean tryExecute(Card... cards){
        int counter = 0;
        if(cards.length == number){
            if(element == Element.NO_ELEMENT) return true;
            for(Card card : cards){
                if(card.getElement() == element){
                    counter += 1;
                    continue;
                }
                return false;
            }
        }

        return counter == number;
    }

    public Element getNeededElement(){ return element; }
    public int getNeededCards(){ return number; }
}
