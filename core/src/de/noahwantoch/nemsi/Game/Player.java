package de.noahwantoch.nemsi.Game;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

/**
 * @author Noah O. Wantoch
 * Der Spieler.
 * @see de.noahwantoch.nemsi.Game.PlayingPossibilities
 */
public class Player extends PlayingPossibilities{

    // private static final String TAG = PlayingPossibilities.class.getSimpleName(); //For debugging

    public Player(){
        super();

        //Deck, Friedhof und verbannten Karten auf die vor-definierten Positionen setzen
        deck.setPosition(GameSettings.PlayerPositions.deckPosition);
        graveyard.setPosition(GameSettings.PlayerPositions.graveyardPosition);
        banishedCards.setPosition(GameSettings.PlayerPositions.banishedCardsPosition);
        deckZone.setPosition(GameSettings.PlayerPositions.deckPosition.x, GameSettings.PlayerPositions.deckPosition.y);
        graveyardZone.setPosition(GameSettings.PlayerPositions.graveyardPosition.x, GameSettings.PlayerPositions.graveyardPosition.y);
        banishedCardsZone.setPosition(GameSettings.PlayerPositions.banishedCardsPosition.x, GameSettings.PlayerPositions.banishedCardsPosition.y);

        deckFont.setPosition(GameSettings.PlayerPositions.deckPosition.x + deckZone.getWidth() / 2, GameSettings.PlayerPositions.deckPosition.y + deckZone.getHeight());
        graveyardFont.setPosition(GameSettings.PlayerPositions.graveyardPosition.x + graveyardZone.getWidth() / 2, GameSettings.PlayerPositions.graveyardPosition.y + graveyardZone.getHeight());

        //Beschreibt die Mitte (x) und offset nach oben (y = GameSettings.heightOffset)
        handPosition.set(GameSettings.PlayerPositions.handPosition);

        //Das Deck und der Friedhof liegen rechts, die verbannten Karten links
        super.deckDrawingOffset = GameSettings.drawingDeckOffset;
        super.banishedCardsDrawingOffset = -GameSettings.drawingDeckOffset;
        super.graveyardDrawingOffset = GameSettings.drawingDeckOffset;
        super.drawSpeed = GameSettings.drawSpeed;
        super.heightOffset = GameSettings.heightOffset;
        super.hoveringOffset = heightOffset;

        updateLife();
    }

    @Override
    public void fromFieldToGraveyard() { //Die selektierten Karten auf den Friedhof schicken
        super.fromFieldToGraveyard();

        for(Card card : fieldcards){
            if(card.isSelected()) card.destroy();
        }

        saveFieldcards();
    }

    @Override
    public void checkForInteraction() {
        super.checkForInteraction();

        ArrayList<Integer> selectedCards = new ArrayList<>(); //Indicies der selektierten Karten --> z.B. {0, 2, 5}

        for(int i = 0; i < CardGameInstances.player_fieldcards.size(); i++){ //Die selektierten Karten werden gesucht und in die Liste gepackt
            if(CardGameInstances.player_fieldcards.get(i).isSelected()) selectedCards.add(i);
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
    public void updateLife() {
        super.updateLife();
        lifeFont.setPosition(GameSettings.PlayerPositions.lifePosition.x, GameSettings.PlayerPositions.lifePosition.y);
    }

    @Override
    public void getDamage(int value) {
        super.getDamage(value);
        lifeFont.setPosition(GameSettings.PlayerPositions.lifePosition.x, GameSettings.PlayerPositions.lifePosition.y);
    }

    @Override
    public void heal(int value) {
        super.heal(value);
        lifeFont.setPosition(GameSettings.PlayerPositions.lifePosition.x, GameSettings.PlayerPositions.lifePosition.y);
    }

    @Override
    public void cardInteraction(int index) {
        super.cardInteraction(index);
        super.playCard(index);
    }

    @Override
    public void setDeck(Deck deck){
        deck.setPosition(GameSettings.PlayerPositions.deckPosition);
        super.setDeck(deck);
        deckFont.setText(Integer.toString(deck.getSize()));
    }

    @Override
    public void draw(float delta){
        super.draw(delta);
        super.updateFieldcards(GameSettings.PlayerPositions.fieldcardY); //Die Position der Feldkarten wird aktualisiert
        super.moveCardsAnimation(GameSettings.PlayerPositions.handPosition);
        super.updateCardAnimation(delta, GameSettings.PlayerPositions.deckPosition, true);

        //Aktualisieren der Visualisierung der Anzahl der Deck- bzw. Friedhofkarten
        deckFont.setPosition(GameSettings.PlayerPositions.deckPosition.x + deckZone.getWidth() / 2 + GameSettings.drawingDeckOffset * deck.getSize(), GameSettings.PlayerPositions.deckPosition.y + deckZone.getHeight());
        graveyardFont.setPosition(GameSettings.PlayerPositions.graveyardPosition.x + graveyardZone.getWidth() / 2 + GameSettings.drawingDeckOffset * graveyard.getSize(), GameSettings.PlayerPositions.graveyardPosition.y + graveyardZone.getHeight());
        deckFont.setText(Integer.toString(deck.getSize() + currentDrawingCards.size()));
        graveyardFont.setText(Integer.toString(graveyard.getSize()));

        ArrayList<Integer> selectedCards = new ArrayList<>(); //Indicies der selektierten Karten --> z.B. {0, 2, 5}

        for(int i = 0; i < CardGameInstances.player_fieldcards.size(); i++){ //Die selektierten Karten werden gesucht und in die Liste gepackt
            if(CardGameInstances.player_fieldcards.get(i).isSelected()) selectedCards.add(i);
        }

        if(!CardGame.effectMessageBox.getState()){
            if(CardGame.effectMessageBox.getResult()){ //Wenn "Ja" gedrückt wurde, nachdem etwas gedrückt wurde
                activateEffect(currentEffect);
            }
            CardGame.effectMessageBox.reset(); //result wird zurückgesetzt, damit es nur einmal abgefragt wird
        }

        //Der aktuell auslösende Effekt
        if(currentExecutingEffect != null){
            if(healing){ //single healing
                if(currentExecutingEffect.getTarget() == Element.NO_ELEMENT){ //Wenn es kein bestimmtes Element sein muss
                    if(selectedCards.size() > 0){ //Wenn Karten ausgewählt wurden
                        fieldcards.get(selectedCards.get(0)).heal(currentExecutingEffect.getAmount());
                        fieldcards.get(selectedCards.get(0)).updateValues();
                        deselectCards(); //Hebt die Auswahl der Feldkarten auf
                        healing = false;
                        currentExecutingEffect = null;
                    }
                }else{ //Wenn die Zielkarte ein bestimmtes Element haben muss
                    boolean healedCard = false;
                    boolean healingPossible = false;

                    for(Card card : fieldcards){ //Überprüfen, ob überhaupt eine Karte geheilt werden kann
                        if(card.getElement() == currentExecutingEffect.getTarget()){
                            healingPossible = true;
                        }
                    }

                    if(healingPossible){
                        for(int index : selectedCards){ //Die ausgewählten Karten heilen (eine).
                            if(fieldcards.get(index).getElement() == currentExecutingEffect.getTarget()){ //Wenn man beim Beschwören schon eine Karte ausgewählt hat
                                fieldcards.get(index).heal(currentExecutingEffect.getAmount());
                                fieldcards.get(index).updateValues(); //Schnelles Updaten der Lebenszahl
                                healedCard = true;
                                break; //Keine weiteren Karten heilen
                            }
                        }
                    }

                    if(!healingPossible){ //Wenn das Heilen nicht möglich ist
                        CardGame.okayMessageBox.showMessage("Leider konnte keine Karte geheilt werden.");
                        healing = false;
                        currentExecutingEffect = null;
                    }

                    if(healedCard){ //Wenn eine Karte geheilt wurde
                        healing = false;
                        currentExecutingEffect = null;
                        saveFieldcards(); //Das Spielfeld des PlayingPossibilities-Object wird gespeichert
                    }
                }

            }else if(damaging){

            }else if(destroying){

            }
        }

        if(!selectingTributes){ //Wenn man gerade keine Karten für eine Tributbeschwörung auswählt
            //Eine Karte wird beschworen
            if(!CardGame.yesNoMessageBox.getState() && !CardGame.okayMessageBox.getState()){
                if(CardGame.yesNoMessageBox.getResult()){ // Wenn "Ja" gedrückt wurde
                    if(handcards.get(currentSummonIndex).getTribute().getNeededCards() > 0){ //Wenn ein oder mehrere Tribute erforderlich sind
                        CardGame.okayMessageBox.showMessage("Wähle jetzt Tribute aus.");
                        selectingTributes = true;
                    }else{
                        summonDirectly(currentSummonIndex);
                    }
                    CardGame.yesNoMessageBox.reset();
                    CardGame.okayMessageBox.reset();
                }
            }
        }else{
            if(selectedCards.size() == handcards.get(currentSummonIndex).getTribute().getNeededCards()){ //Wenn man soviele Karten wie benötigt, ausgewählt hat
                if(handcards.get(currentSummonIndex).getTribute().getNeededElement() == Element.NO_ELEMENT){ //Wenn kein spezifisches Element benötigt wird
                    fromFieldToGraveyard(); //Ausgewählten Karten auf den Friedhof schicken
                    summonDirectly(currentSummonIndex);
                    selectingTributes = false;
                    
                }else{
                    for(int i = 0; i < selectedCards.size(); i++) {
                        if(fieldcards.get(selectedCards.get(i)).getElement() != handcards.get(currentSummonIndex).getTribute().getNeededElement()){
                            CardGame.okayMessageBox.showMessage("Es wird leider ein anderes Element benötigt: " + handcards.get(currentSummonIndex).getTribute().getNeededElement());
                            CardGame.okayMessageBox.reset();
                            break;
                        }else{
                            fromFieldToGraveyard(); //Ausgewählten Karten auf den Friedhof schicken
                            summonDirectly(currentSummonIndex);
                            selectingTributes = false;
                        }
                    }
                }
            }
        }
    }

    /**
     * @author Noah. O. Wantoch
     * Es werden number Karten gezogen, um die obere Mechanik in Gang zu setzen
     * @param number Wie viele Karten gezogen werden sollen
     */
    public void drawCard(int number){
        super.drawCard(number);
    }

    @Override
    public void checkDrawingCondition() {
        super.checkDrawingCondition();
        if(currentDrawingCards.get(currentDrawingCards.size() - 1).getX() < (Gdx.graphics.getWidth() / 2f) + handcards.size() * (GameSettings.cardWidth / 2f + GameSettings.handcardOffset)){
            super.drawCardCounter = GameSettings.drawCardSeconds;
        }
    }

    @Override
    public void setTurn(boolean bool) {
        super.setTurn(bool);
    }

    @Override
    public void saveFieldcards() {
        CardGameInstances.player_fieldcards = fieldcards;
    }

    public void deselectCards(){ //fieldcards
        for(Card card : fieldcards){
            if(card.isSelected()) card.deselect();
        }

        saveFieldcards();
    }
}
