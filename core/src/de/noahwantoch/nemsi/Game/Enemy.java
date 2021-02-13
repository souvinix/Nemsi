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
    private boolean finishedDrawing = false;

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
    public void fromFieldToGraveyard() {} //Bleibt leer

    @Override
    public void checkForInteraction() {
        super.checkForInteraction();

        ArrayList<Integer> selectedCards = new ArrayList<>(); //Indicies der selektierten Karten --> z.B. {0, 2, 5}

        for(int i = 0; i < CardGameInstances.enemy_fieldcards.size(); i++){ //Die selektierten Karten werden gesucht und in die Liste gepackt
            if(CardGameInstances.enemy_fieldcards.get(i).isSelected()) selectedCards.add(i);
        }

        for(int i = 0; i < fieldcards.size(); i++){
            if(touchDetector.isHoveredOverRectangle(fieldcards.get(i).getX(), fieldcards.get(i).getY(), GameSettings.cardWidth, GameSettings.cardHeight)){ //wenn man über eine Karte drüber "hovered"
                if(fieldcards.get(i).getSize() == 1f){
                    fieldcards.get(i).reinitialize(GameSettings.cardZoom);
                }
                if(Gdx.input.justTouched()){
                    if(fieldcards.get(i).isSelected()){ //i ist in fieldcards (als value, nicht als index)
                        fieldcards.get(i).deselect();
                    }else{
                        fieldcards.get(i).select();
                    }
                }
            }else{ //wenn man nicht mehr über eine Karte drüber "hovered"
                if(fieldcards.get(i).getSize() == GameSettings.cardZoom){
                    fieldcards.get(i).reinitialize(1f);
                }
            }
        }
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

        //Aktualisieren der Visualisierung der Anzahl der Deck- bzw. Friedhofkarten
        deckFont.setPosition(GameSettings.EnemyPositions.deckPosition.x + deckZone.getWidth() / 2 - GameSettings.drawingDeckOffset * deck.getSize(), GameSettings.EnemyPositions.deckPosition.y + deckZone.getHeight());
        graveyardFont.setPosition(GameSettings.EnemyPositions.graveyardPosition.x + graveyardZone.getWidth() / 2 - GameSettings.drawingDeckOffset * graveyard.getSize(), GameSettings.EnemyPositions.graveyardPosition.y + graveyardZone.getHeight());
        deckFont.setText(Integer.toString(deck.getSize() + currentDrawingCards.size()));
        graveyardFont.setText(Integer.toString(graveyard.getSize()));

        if(currentDrawingCards.size() == 0){ //Der Bot zieht erst zu Ende und spielt dann erst
            finishedDrawing = true;
        }else finishedDrawing = false;

        /** STATEMENTS FOR ARTIFICIAL INTELLIGENCE */
        if(finishedDrawing) actionCounter += delta;

        if(turn && actionCounter >= GameSettings.ai_secondsPerAction && finishedDrawing){
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
        }
    }
    @Override
    public void saveFieldcards(){
        CardGameInstances.enemy_fieldcards = fieldcards;
    }
    public boolean isFinished(){
        return isFinished;
    }
    private void finishTurn(){
        isFinished = true;
    }

    public void resetFinished(){
        isFinished = false;
    }

    /** FUNCTIONS FOR ARTIFICIAL INTELLIGENCE */

    private void summonBestCard(){
        summonCardByTribute(GameSettings.maxTributeLevel + 1); //Beschwört die beste Karte von der Anzahl an Tributen (rekursiv)
    }

    /**
     * @author Noah O. Wantoch
     * @param tributeValue Die Anzahl der benötigten Tribute
     */
    private void summonCardByTribute(int tributeValue){

//        for(Card card : handcards){
//            Gdx.app.debug("Gegner Handkarten: ", card.getName() + "(" + card.getTribute().getNeededCards() + ")," + "tributeValue: " + tributeValue + ", fieldcards.size(): " + fieldcards.size());
//        }

        if(tributeValue == 0){ //Wenn keine Tribute gefordert sind
            for(int i = 0; i < handcards.size(); i++){
                if(handcards.get(i).getTribute().getNeededCards() == tributeValue){
                    if(fieldcards.size() < GameSettings.maxFieldcards){
                        summonDirectly(i);
                    }else cardCounter += 1; //Damit keine Unendlichschleife entsteht
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

                            int elementCounter = 0; //Schaut, wie viele passende Tribute es geben WÜRDE (richtiges Element)
                            for(int x = 0; x < CardGameInstances.enemy_fieldcards.size(); x++){
                                if(CardGameInstances.enemy_fieldcards.get(x).getElement() == handcards.get(i).getTribute().getNeededElement() && CardGameInstances.enemy_fieldcards.get(x).getTribute().getNeededCards() < tributeValue){
                                    elementCounter += 1;
                                }
                            }

                            if(elementCounter >= handcards.get(i).getTribute().getNeededCards()){ //Wenn es genügend passende Tribute geben würde, darf diese Karte beschworen werden (Beachtung des Elements)
                                summon = true;
                                summonIndex = i;
                                break;
                            }

                            //Wenn eine Karten beschworen werden will, welche z.B. 2 Tribute benötigt, aber nur eine Karte auf dem Feld liegt, die mit einem Tribut beschworen wurde --> man muss abwiegen, ob der Effekt eventuell passend ist und/oder, ob die stats des Monsters besser sind
                        }else if(counter < handcards.get(i).getTribute().getNeededCards() || handcards.get(i).getTribute().getNeededCards() < fieldcards.size()){
                            //Es wird fürs Erste erst mal übersprungen
                            if(i == handcards.size() - 1){ //Wenn es bereits die letzte Karte ist (in der Iteration), die überprüft wird
                                cardCounter += 1;
                            }else continue;
                        }
                    }
                }
            }

            //Wenn jetzt bekannt ist, welche beschworen wird
            if(summon){ //Die niedrigsten Werte werden geopfert
                ArrayList<Integer> indicies = new ArrayList<>();
                for(int i = 0; i < handcards.get(summonIndex).getTribute().getNeededCards(); i++){ //Wird z.B. 2 Mal ausgeführt (weil 2 Karten geopfert werden)
                    int currentIndex = 0;
                    int currentLowestSum = 0; //Summe der ATK und DEF einer Karte --> Zum Vergleichen, welche nützlicher ist
                    for(int j = 0; j < valueSums.size(); j++){ //Summen vergleichen
                        if(currentLowestSum == 0){ //Erste Summe wird in currentLowestSum initialisiert
                            currentLowestSum = valueSums.get(j);
                        }

                        boolean exists = false;
                        for(int x : indicies){
                            if(x == j) exists = true; //Index existiert bereits in indicies
                        }

                        if(exists) continue;

                        if(currentLowestSum > valueSums.get(j) && !exists){
                            currentIndex = j;
                        }
                    }
                    indicies.add(currentIndex);
                    valueSums.remove(currentIndex);
                }

                for(int i : indicies){
                    fieldcards.get(i).destroy();
                }

                summonDirectly(summonIndex);

            }else if(!summon && tributeValue >= 0){summonCardByTribute(tributeValue - 1); }//Rekursionsverankerung --> tributeValue < 0 ist nicht gültig --> Rekursiver Aufruf
        }
    }

    /**
     * @author Noah O. Wantoch
     * Activates the effect, if the situation is good
     * */
    private void checkEffect(Effect effect){
        if(effect.getEffectModule() == EffectModule.DESTROY_N){ //Dieser Effekt soll nur auf den Spieler gehen
            if(CardGameInstances.player_fieldcards.size() > 0){ //Wenn der Spieler Feldkarten hat
                int currentSelectingIndex = 0;
                int atkLifeSum = 0; //Summe der Atk und life der verschiedenen Karten vom Spieler, um zu überprüfen, welche die beste Karte ist
                boolean destruction = false;

                for(int i = 0; i < CardGameInstances.player_fieldcards.size(); i++){ //Beste Karte vom Spieler soll rausgesucht und zerstört werden
                    if((CardGameInstances.player_fieldcards.get(i).getDamage() + CardGameInstances.player_fieldcards.get(i).getLife()) > atkLifeSum){
                        if(effect.getTarget() != Element.NO_ELEMENT){ //Wenn das Element nicht egal ist
                            if(effect.getTarget() == CardGameInstances.player_fieldcards.get(i).getElement()){
                                atkLifeSum = CardGameInstances.player_fieldcards.get(i).getDamage() + CardGameInstances.player_fieldcards.get(i).getLife();
                                currentSelectingIndex = i;
                                destruction = true;
                            }
                        }else{ //Wenn das Element egal ist
                            atkLifeSum = CardGameInstances.player_fieldcards.get(i).getDamage() + CardGameInstances.player_fieldcards.get(i).getLife();
                            currentSelectingIndex = i;
                        }
                    }
                }

                if(destruction) CardGameInstances.player_fieldcards.get(currentSelectingIndex).destroy();

            }else{} //Wenn er keine hat, soll der Effekt nicht aktiviert werden
        }

        else if(effect.getEffectModule() == EffectModule.DRAW_N){ //Zieh-Effekt für den Gegner
            if(effect.getTarget() == Element.NO_ELEMENT){
                drawCard(effect.getAmount());
            }else{
                drawSpecificElement(effect.getAmount(), effect.getTarget());
            }
        }

        else if(effect.getEffectModule() == EffectModule.HEAL_HERO){
            heal(effect.getAmount()); //Der Held wird um die bestimmte Menge geheilt
        }

        saveFieldcards();
    }

    private boolean checkPossibleSummon(){ //Überprüft, ob die AI etwas beschwören kann
        for(Card handcard : handcards){
            if(handcard.getTribute().getNeededCards() == 0){
                return true;
            }else{ // > 0
                if(handcard.getTribute().getNeededCards() <= fieldcards.size()){
                    return true;
                }
            }
        }
        return false;
    }

}
