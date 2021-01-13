package de.noahwantoch.nemsi.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import de.noahwantoch.nemsi.TextureHandling.TextureEnum;
import de.noahwantoch.nemsi.UI.Font;
import de.noahwantoch.nemsi.Utility.BatchInstance;
import de.noahwantoch.nemsi.Utility.FontEnum;

public class Card {
    private static final String TAG = Card.class.getSimpleName();

    private String name;
    private int life;
    private int damage;
    private Element element;
    private String description;

    private Sprite cardSprite;
    private Sprite elementSprite;
    private Sprite swordSprite;
    private Sprite shieldSprite;

    private float symbolOffset = Gdx.graphics.getDensity() * de.noahwantoch.nemsi.Game.GameSettings.cardWidth / 10f;
    private Font textFont;

    private Font damageFont;
    private Font lifeFont;

    private Sprite cardBack;
    private boolean covered = true;

    public Card(String name, int damage, int life, Element element){
        cardSprite = new Sprite(new Texture(TextureEnum.CARD.getPath()));
        cardSprite.setSize(de.noahwantoch.nemsi.Game.GameSettings.cardWidth, de.noahwantoch.nemsi.Game.GameSettings.cardHeight);
        elementSprite = new Sprite(new Texture(element.getElementPath()));
        elementSprite.setSize(de.noahwantoch.nemsi.Game.GameSettings.elementWidth, de.noahwantoch.nemsi.Game.GameSettings.elementHeight);
        swordSprite = new Sprite(new Texture(TextureEnum.SWORD_SYMBOL.getPath()));
        swordSprite.setSize(de.noahwantoch.nemsi.Game.GameSettings.swordWidth, de.noahwantoch.nemsi.Game.GameSettings.swordHeight);
        shieldSprite = new Sprite(new Texture(TextureEnum.SHIELD_SYMBOL.getPath()));
        shieldSprite.setSize(de.noahwantoch.nemsi.Game.GameSettings.shieldWidth, de.noahwantoch.nemsi.Game.GameSettings.shieldHeight);

        //Der Name, der Schaden und das Leben der Karte können hiermit visualisiert werden
        textFont = new Font(FontEnum.getMainFontDataName(), de.noahwantoch.nemsi.Game.GameSettings.cardWidth * Gdx.graphics.getDensity() * de.noahwantoch.nemsi.Game.GameSettings.cardSize * 0.09f, 1);
        textFont.setColor(0, 0,0,1);
        damageFont = new Font(FontEnum.getMainFontDataName(), de.noahwantoch.nemsi.Game.GameSettings.cardWidth * Gdx.graphics.getDensity() * de.noahwantoch.nemsi.Game.GameSettings.cardSize * 0.09f, 1);
        damageFont.setColor(1, 0.5f,0.5f,0.4f);
        lifeFont = new Font(FontEnum.getMainFontDataName(), de.noahwantoch.nemsi.Game.GameSettings.cardWidth * Gdx.graphics.getDensity() * de.noahwantoch.nemsi.Game.GameSettings.cardSize * 0.09f, 1);
        lifeFont.setColor(0.5f, 1,0.5f,0.4f);

        this.name = name;
        this.damage = damage;
        this.life = life;
        this.element = element;

        //Kartenrücken
        cardBack = new Sprite(new Texture(TextureEnum.Card_Back.getPath()));
        cardBack.setSize(de.noahwantoch.nemsi.Game.GameSettings.cardWidth, de.noahwantoch.nemsi.Game.GameSettings.cardHeight);
    }

    public void draw(float delta){
        //covered = "umgedreht" --> man sieht den Kartenrücken
        if(!covered){
            cardSprite.draw(BatchInstance.batch);
            textFont.draw(BatchInstance.batch, name);
            elementSprite.draw(BatchInstance.batch);
            swordSprite.draw(BatchInstance.batch);
            shieldSprite.draw(BatchInstance.batch);
            damageFont.draw(BatchInstance.batch, Integer.toString(damage));
            lifeFont.draw(BatchInstance.batch, Integer.toString(life));
        }else cardBack.draw(BatchInstance.batch);
    }

    //Die Position der Karte und der dazugehörigen Objekte werden relativ zur Karte gesetzt/verschoben
    public void setPosition(float x, float y){
        float symbolY = y + symbolOffset;
        float symbolOffset_ = de.noahwantoch.nemsi.Game.GameSettings.cardWidth * Gdx.graphics.getDensity() * 0.3f;

        cardSprite.setPosition(x, y);
        cardBack.setPosition(x, y);
        textFont.setPosition(x + de.noahwantoch.nemsi.Game.GameSettings.cardWidth / 2f, cardSprite.getY() + de.noahwantoch.nemsi.Game.GameSettings.cardHeight * 0.96f);
        damageFont.setPosition(x + symbolOffset_, symbolY + symbolOffset_);
        lifeFont.setPosition(x + de.noahwantoch.nemsi.Game.GameSettings.cardWidth - symbolOffset_ , symbolY + symbolOffset_);
        elementSprite.setPosition(x + de.noahwantoch.nemsi.Game.GameSettings.cardWidth / 2f - elementSprite.getWidth() / 2f, symbolY);
        swordSprite.setPosition(x + symbolOffset, symbolY);
        shieldSprite.setPosition(x + de.noahwantoch.nemsi.Game.GameSettings.cardWidth - GameSettings.shieldWidth - symbolOffset * 1.5f, symbolY);
    }

    //Erstellt eine Kopie dieser Karte, um auszuschließen, dass man auf die selbe Instanz referenziert
    public Card getCopy(){
        return new Card(name, damage, life, element);
    }

    public String getName(){ return name; }
    public void toOpen(){
        covered = false;
    } //Karte wird umgedreht
    public float getX(){
        return cardSprite.getX();
    }
    public float getY(){
        return cardSprite.getY();
    }
    
}
