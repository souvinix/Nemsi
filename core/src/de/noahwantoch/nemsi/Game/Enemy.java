package de.noahwantoch.nemsi.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import de.noahwantoch.nemsi.EffectModule;

/**
 * @author Noah O. Wantoch
 * Ein Gegner.
 * @see PlayingPossibilities
 */
public class Enemy extends PlayingPossibilities{

    private float actionCounter = 0f;
    private boolean isFinished = false;

    public Enemy(){
        super();
        deck.setPosition(GameSettings.EnemyPositions.deckPosition);
        graveyard.setPosition(GameSettings.EnemyPositions.graveyardPosition);
        banishedCards.setPosition(GameSettings.EnemyPositions.banishedCardsPosition);

        deckZone.setPosition(GameSettings.EnemyPositions.deckPosition.x, GameSettings.EnemyPositions.deckPosition.y);
        graveyardZone.setPosition(GameSettings.EnemyPositions.graveyardPosition.x, GameSettings.EnemyPositions.graveyardPosition.y);
        banishedCardsZone.setPosition(GameSettings.EnemyPositions.banishedCardsPosition.x, GameSettings.EnemyPositions.banishedCardsPosition.y);

        //Beschreibt die Mitte (x) und offset nach oben (y = GameSettings.heightOffset)
        handPosition.set(GameSettings.EnemyPositions.handPosition);

        //Das Deck und der Friedhof liegen rechts, die verbannten Karten links
        super.deckDrawingOffset = -GameSettings.drawingDeckOffset;
        super.banishedCardsDrawingOffset = GameSettings.drawingDeckOffset;
        super.graveyardDrawingOffset = -GameSettings.drawingDeckOffset;

        super.drawSpeed = -GameSettings.drawSpeed;
        super.heightOffset = Gdx.graphics.getHeight() * 0.9f - GameSettings.heightOffset;
        super.hoveringOffset = -GameSettings.heightOffset;

        updateLife();
    }

    @Override
    public void updateLife() {
        super.updateLife();
        lifeFont.setPosition(GameSettings.EnemyPositions.lifePosition.x, GameSettings.EnemyPositions.lifePosition.y);
    }

    @Override
    public void getDamage(int value) {
        super.getDamage(value);
        lifeFont.setPosition(GameSettings.EnemyPositions.lifePosition.x, GameSettings.EnemyPositions.lifePosition.y);
    }

    @Override
    public void heal(int value) {
        super.heal(value);
        lifeFont.setPosition(GameSettings.EnemyPositions.lifePosition.x, GameSettings.EnemyPositions.lifePosition.y);
    }

    @Override
    public void setDeck(Deck deck) {
        deck.setPosition(GameSettings.EnemyPositions.deckPosition);
        Gdx.app.debug("Enemy", "Deck: " + deck.getSize());
        super.setDeck(deck);
    }

    /**
     * @author Noah O. Wantoch
     * @param number Wie viele Karten gezogen werden sollen
     */
    @Override
    public void drawCard(int number) {
        for(int i = 0; i < number; i++){
            if(deck.getSize() > 0){
                Card card = deck.pop();
                card.setPosition(deck.getPosition().x, deck.getPosition().y);
                currentDrawingCards.add(card); //Eine Liste, welche dafür verantwortlich ist, die Karten-Zieh-Animation der darin enthaltenen Karten zu malen
            }
        }
    }

    @Override
    public void moveCardsAnimation(Vector2 handPosition) {
        super.moveCardsAnimation(handPosition);
    }

    @Override
    public void updateFieldcards(float fieldcardY) {
        super.updateFieldcards(fieldcardY);
    }

    @Override
    public void updateCardAnimation(float delta, Vector2 deckPosition, boolean openCards) {
        super.updateCardAnimation(delta, deckPosition, openCards);
    }

    @Override
    public void fromFieldToGraveyard() {



    }

    @Override
    public void checkForInteraction() {
        super.checkForInteraction();
    }

    @Override
    public void cardInteraction(int index) {
        super.cardInteraction(index);
    }

    @Override
    public void drawCardAnimation(Vector2 deckPosition, boolean openCards) {
        super.drawCardAnimation(deckPosition, openCards);
    }

    @Override
    public void playCard(int index) {


    }

    @Override
    public void summonDirectly(int handcardIndex) {
        if(handcardIndex < handcards.size()){
            Card card = handcards.get(handcardIndex);
            card.toOpen();
            cardCounter += 1;
            fieldcards.add(card);
            handcards.remove(handcardIndex);

            if(fieldcards.get(fieldcards.size() - 1).getEffect().getEffectModule() != EffectModule.NO_EFFECT){ //Wenn es ein Effekt hat
                currentEffect = fieldcards.get(fieldcards.size() - 1).getEffect();
                checkEffect(currentEffect); //Der Effekt wird von der AI überprüft
            }

            saveFieldcards();
        }else {
            Gdx.app.debug("Gegner", "Gescheiterte Beschwörung: " + handcardIndex + ", name: " + handcards.get(handcardIndex).getName() + ", Tribute: " + handcards.get(handcardIndex).getTribute().getNeededCards() + " von dem Element: " + handcards.get(handcardIndex).getTribute().getNeededElement());
            cardCounter += 1;
        }
    }

    @Override
    public void setLife(int life) {
        super.setLife(life);
    }

    @Override
    public void checkDrawingCondition() {
        super.checkDrawingCondition();
        if(currentDrawingCards.get(currentDrawingCards.size() - 1).getX() > (Gdx.graphics.getWidth() / 2f) + handcards.size() * (GameSettings.cardWidth / 2f + GameSettings.handcardOffset)){
            super.drawCardCounter = GameSettings.drawCardSeconds;
        }
    }

    @Override
    public void draw(float delta){
        super.draw(delta);
        super.updateFieldcards(GameSettings.EnemyPositions.fieldcardY); //Die Position der Feldkarten wird aktualisiert
        super.moveCardsAnimation(GameSettings.EnemyPositions.handPosition);
        super.updateCardAnimation(delta, GameSettings.EnemyPositions.deckPosition, false);

        /** STATEMENTS FOR ARTIFICIAL INTELLIGENCE */
        actionCounter += delta;

        if(turn && actionCounter >= GameSettings.ai_secondsPerAction){
            if(cardCounter >= GameSettings.cardsPerTurn){ //Zug beenden, wenn die AI keine Karte mehr beschwören kann (UND nicht mehr angreifen kann)
                finishTurn();
            }else{
                summonBestCard();
                actionCounter = 0;

                if(!checkPossibleSummon()){
                    finishTurn();
                }
            }
        }
    }

    @Override
    public void setTurn(boolean bool){
        super.setTurn(bool);
        if(turn){
            isFinished = false;
            actionCounter = 0;
            cardCounter = 0;
        }
        else if(!turn) saveFieldcards();
    }
    @Override
    public void saveFieldcards(){
        Gdx.app.debug("Gegner", "Spielfeldkarten wurden aktualisiert!");
        CardGameInstances.enemyFieldcards = fieldcards;
    }
    public boolean isFinished(){
        return isFinished;
    }
    private void finishTurn(){
        isFinished = true;
        Gdx.app.debug("Gegner", "Gegner beendet den Zug.");
    }

    public void resetFinished(){
        isFinished = false;
    }

    /** FUNCTIONS FOR ARTIFICIAL INTELLIGENCE */

    private void summonBestCard(){
        summonCardByTribute(GameSettings.maxTributes + 1); //Beschwört die beste Karte von der Anzahl an Tributen (rekursiv)
    }


    private void summonCardByTribute(int tributeValue){
        if(tributeValue == 0){ //Wenn keine Tribute gefordert sind
            for(int i = 0; i < handcards.size(); i++){
                if(handcards.get(i).getTribute().getNeededCards() == tributeValue){
                    summonDirectly(i);
                    break;
                }
            }
        }else if(tributeValue > 0){ //Wenn mindestens ein Tribut benötigt wird
            ArrayList<Integer> valueSums = new ArrayList<>(); //ATK und DEF werden für jede Karte addiert und für den jeweiligen Index temporär eingespeichert
            boolean summon = false;
            int summonIndex = 0;
            for(int i = 0; i < handcards.size(); i++){
                if(handcards.get(i).getTribute().getNeededCards() == tributeValue){ //Sucht Karten in der Hand nach der gewünschten Anzahl der geforderten Tributen
                    if(fieldcards.size() >= tributeValue){ //Wenn eine Tributbeschwörung möglich wäre
                        int counter = 0; // Zählt die Karten mit einem schwächeren Tribut, als das zu beschworene (tributValue - 1)
                        for(int j = 0; j < fieldcards.size(); j++){ //Überprüft, ob sich die Tributbeschwörung denn lohnen würde
                            int sum = fieldcards.get(j).getDamage() + fieldcards.get(j).getLife();

                            if(fieldcards.get(j).getTribute().getNeededCards() < tributeValue){
                                counter += 1;
                            }
                            valueSums.add(sum);
                        }

                        if(counter >= handcards.get(i).getTribute().getNeededCards()){
                            summon = true;
                            summonIndex = i;
                            break;
                        }
                    }
                }
            }

            if(summon){ //Die niedrigsten Werte werden geopfert
                ArrayList<Integer> indicies = new ArrayList<>();
                for(int i = 0; i < handcards.get(summonIndex).getTribute().getNeededCards(); i++){ //Wird z.B. 2 Mal ausgeführt (weil 2 Karten geopfert werden)
                    int currentIndex = 0;
                    int currentLowest = 0;
                    for(int j = 0; j < valueSums.size(); j++){ //Summen vergleichen
                        if(currentLowest == 0){ //Erste Summe wird in currentLowest initialisiert
                            currentLowest = valueSums.get(j);
                        }
                        if(currentLowest > valueSums.get(j)){
                            currentIndex = j;
                        }
                    }
                    indicies.add(currentIndex);
                    valueSums.remove(currentIndex);
                }

                Gdx.app.debug("BUG", "indicies: " + indicies.toString() + ", Beschwörung: " + handcards.get(summonIndex).getName());
                for(int i : indicies){
                    fieldcards.get(i).destroy();
                }

                summonDirectly(summonIndex);

            }else if(!summon && tributeValue >= 0){summonCardByTribute(tributeValue - 1); }//Rekursionsverankerung --> tributeValue < 0 ist nicht gültig
        }
    }

    /**
     * @author Noah O. Wantoch
     * Activates the effect, if the situation is good
     * */
    private void checkEffect(Effect effect){
        if(effect.getEffectModule() == EffectModule.DESTROY_N){ //Dieser Effekt soll nur auf den Spieler gehen
            if(CardGameInstances.playerFieldcards.size() > 0){ //Wenn der Spieler Feldkarten hat
                int currentSelectingIndex = 0;
                int atkLifeSum = 0; //Summe der Atk und life der verschiedenen Karten vom Spieler, um zu überprüfen, welche die beste Karte ist

                for(int i = 0; i < CardGameInstances.playerFieldcards.size(); i++){ //Beste Karte vom Spieler soll rausgesucht und zerstört werden
                    if((CardGameInstances.playerFieldcards.get(i).getDamage() + CardGameInstances.playerFieldcards.get(i).getLife()) > atkLifeSum){
                        atkLifeSum = CardGameInstances.playerFieldcards.get(i).getDamage() + CardGameInstances.playerFieldcards.get(i).getLife();
                        currentSelectingIndex = i;
                    }
                }

                CardGameInstances.playerFieldcards.get(currentSelectingIndex).destroy();

            }else{} //Wenn er keine hat, soll der Effekt nicht aktiviert werden
        }

        saveFieldcards();
    }

    private boolean checkPossibleSummon(){ //Überprüft, ob die AI etwas beschwören kann
        boolean result = false;

        for(Card handcard : handcards){
            if(handcard.getTribute().getNeededCards() == 0){
                result = true;
            }else{ // > 0
                if(handcard.getTribute().getNeededCards() <= fieldcards.size()){
                    result = true;
                }
            }
        }

        return result;
    }

}
