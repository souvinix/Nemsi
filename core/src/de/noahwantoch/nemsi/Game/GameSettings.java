package de.noahwantoch.nemsi.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import de.noahwantoch.nemsi.TextureHandling.TextureEnum;

public interface GameSettings {
    //Werden für die Höhen und Breiten der Texturen genutzt
    Texture exampleCard = new Texture(de.noahwantoch.nemsi.TextureHandling.TextureEnum.CARD.getPath());
    Texture exampleElement = new Texture(de.noahwantoch.nemsi.TextureHandling.TextureEnum.FIRE_SYMBOL.getPath());
    Texture exampleSword = new Texture(de.noahwantoch.nemsi.TextureHandling.TextureEnum.SWORD_SYMBOL.getPath());
    Texture exampleShield = new Texture(TextureEnum.SHIELD_SYMBOL.getPath());

    int playerStartHealth = 0; //Startzahl des Lebens von dem Spieler nach einer Initialisierung eines Kartenspiels
    int maxCardsPerDeck = 40; //Maximale Kartenanzahl pro Deck
    int handcardsStartNumber = 5; //Handkarten-Startanzahl
    int maxHealth = 10000; //Maximales Leben eines PlayingPossibilities-Objekt
    int messageBoxSize = 5; //Die Größe der Nachrichtenbox
    int cardsPerTurn = 2; //Maximale Beschwörungen pro Runde
    int lifeFontSize = 60; //Die Visualisierung des Lebens der PlayingPossibilities-Objects (Spieler und Enemy)

    float cardSize = 2.5f; //Kartengröße
    float symbolSize = 3f; //Symbol-Größe auf den Karten
    float cardFontSize = 0.1f; //Größe der Schrift auf den Karten
    float atkAndDefFontSIze = 0.12f; //Größe der ATK- und DEF-Zahl auf den Karten
    float cardWidth = exampleCard.getWidth() * cardSize; //Kartenbreite in px
    float cardHeight = exampleCard.getHeight() * cardSize; //Kartenhöhe in px
    float elementWidth = exampleElement.getWidth() * symbolSize; //Symbolbreite des Elements auf der Karte in px
    float elementHeight = exampleElement.getHeight() * symbolSize; //Symbolhöhe des Elements auf der Karte in px
    float swordWidth = exampleSword.getWidth() * symbolSize; //Symbolbreite des Schwerts auf der Karte in px
    float swordHeight = exampleSword.getHeight() * symbolSize; //Symbolhöhe des Schwerts auf der Karte in px
    float shieldWidth = exampleShield.getWidth() * symbolSize; //Symbolbreite des Schildes auf der Karte in px
    float shieldHeight = exampleShield.getHeight() * symbolSize; //Symbolhöhe des Schildes auf der Karte in px
    float widthOffset = Gdx.graphics.getWidth() / 25f * Gdx.graphics.getDensity(); //Kleiner Abstand relativ zur Bildschirmbreite
    float heightOffset = Gdx.graphics.getHeight() / 15f * Gdx.graphics.getDensity(); //Kleiner Abstand relativ zur Bildschirmhöhe
    float fieldcardOffset = cardWidth * 1.2f; //Abstand zwischen den Feldkarten
    float handcardOffset = cardWidth * Gdx.graphics.getDensity() * 0.1f; //Abstand zwischen den Handkarten
    float drawCardSeconds = 3.0f; //Maximale Zeit (in Sekunden), die man für das Ziehen benötigt (automatisches abschließen)
    float drawSpeed = 1000f; //(* Gdx.graphics.getDeltaTime())
    float drawingDeckOffset = cardWidth / 300f; //Für den drei-dimensionalen Effekt des Decks (jede Karte kriegt diesen Abstand * index der Karte im Deck)
    float fieldcardHeightOffset = Gdx.graphics.getDensity() * 20f; //Ein kleiner Abstand von der Mitte zum Spieler oder Gegner, um eine Kollision der Feldkarten vom Spieler und des Gegners zu vermeiden
    float cardZoom = 1.1f; //Wenn man über die Feldkarten drüber "hovered" wird reingezommt
    float descriptionSize = 10f; //Größe der Beschreibung auf der Karte


    //Farben --> mit Color.cpy() initialisieren, ansonsten wird der gleiche Wert referenziert
    Color color_greenNormal = new Color(0.7f, 1,0.7f,0.7f);
    Color color_greenDamaged = new Color();
    Color color_greenHealed = new Color(0f, 1f,0f,1f);
    Color color_atkNormal = new Color(1, 0.5f,0.5f,0.8f);
    Color color_cardName = new Color(0, 0, 0, 1);
    Color color_cardDescription = new Color(0, 0, 0, 1);
    Color color_cardNameSelected = new Color(1f, 1f, 1f, 1f);
    Color color_tributeFontColor = new Color(0f, 0f, 0f, 0.5f);

    interface PlayerPositions{
        Vector2 deckPosition = new Vector2(Gdx.graphics.getWidth() - cardWidth - widthOffset, heightOffset); //Die Position des Decks
        Vector2 graveyardPosition = new Vector2(Gdx.graphics.getWidth() - cardWidth - widthOffset, heightOffset * 2f + cardHeight); //Die Position des Friedhofs
        Vector2 banishedCardsPosition = new Vector2(widthOffset, heightOffset); //Die Position des Stapels der verbannten Karten
        Vector2 handPosition = new Vector2(Gdx.graphics.getWidth() / 2f, heightOffset); //Die Position der Mitte der Handkarten
        Vector2 lifePosition = new Vector2(Gdx.graphics.getWidth() / 5f, heightOffset * 1.5f);
        float fieldcardY = Gdx.graphics.getHeight() / 2f - cardHeight - fieldcardHeightOffset; //Die Höhe der Feldkarten
    }

    interface EnemyPositions{
        Vector2 deckPosition = new Vector2(widthOffset, Gdx.graphics.getHeight() - cardHeight - heightOffset); //Die Position des Decks
        Vector2 graveyardPosition = new Vector2(widthOffset, Gdx.graphics.getHeight() - (cardHeight * 2f) - (heightOffset * 2f)); //Die Position des Friedhofs
        Vector2 banishedCardsPosition = new Vector2(Gdx.graphics.getWidth() - widthOffset - cardWidth, Gdx.graphics.getHeight() - cardHeight - heightOffset); //Die Position des Stapels der verbannten Karten
        Vector2 handPosition = new Vector2(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() - cardHeight - heightOffset); //Die Position der Mitte der Handkarten
        Vector2 lifePosition = new Vector2(Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 5f, Gdx.graphics.getHeight() - heightOffset);
        float fieldcardY = Gdx.graphics.getHeight() / 2f + fieldcardHeightOffset;; //Die Höhe der Feldkarten
    }
}
