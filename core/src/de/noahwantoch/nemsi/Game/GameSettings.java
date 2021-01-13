package de.noahwantoch.nemsi.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import de.noahwantoch.nemsi.TextureHandling.TextureEnum;

public interface GameSettings {
    Texture exampleCard = new Texture(de.noahwantoch.nemsi.TextureHandling.TextureEnum.CARD.getPath());
    Texture exampleElement = new Texture(de.noahwantoch.nemsi.TextureHandling.TextureEnum.FIRE_SYMBOL.getPath());
    Texture exampleSword = new Texture(de.noahwantoch.nemsi.TextureHandling.TextureEnum.SWORD_SYMBOL.getPath());
    Texture exampleShield = new Texture(TextureEnum.SHIELD_SYMBOL.getPath());

    int playerStartHealth = 0;
    int maxCardsPerDeck = 40;
    int handcardsStartNumber = 3;
    float cardSize = 3f;
    float symbolSize = 3.5f;

    float cardWidth = exampleCard.getWidth() * cardSize;
    float cardHeight = exampleCard.getHeight() * cardSize;
    float elementWidth = exampleElement.getWidth() * symbolSize;
    float elementHeight = exampleElement.getHeight() * symbolSize;
    float swordWidth = exampleSword.getWidth() * symbolSize;
    float swordHeight = exampleSword.getHeight() * symbolSize;
    float shieldWidth = exampleShield.getWidth() * symbolSize;
    float shieldHeight = exampleShield.getHeight() * symbolSize;
    float widthOffset = Gdx.graphics.getWidth() / 25f * Gdx.graphics.getDensity();
    float heightOffset = Gdx.graphics.getHeight() / 15f * Gdx.graphics.getDensity();
    float fieldcardOffset = cardWidth * Gdx.graphics.getDensity() * 3f;
    float handcardOffset = cardWidth * Gdx.graphics.getDensity() * 0.1f;
    float drawCardSeconds = 3.0f;
    float drawSpeed = 10f;
    float drawingDeckOffset = cardWidth / 300f;

    interface PlayerPositions{
        Vector2 deckPosition = new Vector2(Gdx.graphics.getWidth() - cardWidth - widthOffset, heightOffset);
        Vector2 graveyardPosition = new Vector2(Gdx.graphics.getWidth() - cardWidth - widthOffset, heightOffset * 2f + cardHeight);
        Vector2 banishedCardsPosition = new Vector2(widthOffset, heightOffset);
        Vector2 handPosition = new Vector2(Gdx.graphics.getWidth() / 2f, heightOffset);
    }

    interface EnemyPositions{
        Vector2 deckPosition = new Vector2(widthOffset, Gdx.graphics.getHeight() - cardHeight - heightOffset);
        Vector2 graveyardPosition = new Vector2(widthOffset, Gdx.graphics.getHeight() - (cardHeight * 2f) - (heightOffset * 2f));
        Vector2 banishedCardsPosition = new Vector2(Gdx.graphics.getWidth() - widthOffset - cardWidth, Gdx.graphics.getHeight() - cardHeight - heightOffset);
        Vector2 handPosition = new Vector2(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() - cardHeight - heightOffset);
    }
}
