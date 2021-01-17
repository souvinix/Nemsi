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
    //private String description;

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
    private float size = 1f;

    public Card(String name, int damage, int life, Element element){
        cardSprite = new Sprite(new Texture(TextureEnum.CARD.getPath()));
        elementSprite = new Sprite(new Texture(element.getElementPath()));
        swordSprite = new Sprite(new Texture(TextureEnum.SWORD_SYMBOL.getPath()));
        shieldSprite = new Sprite(new Texture(TextureEnum.SHIELD_SYMBOL.getPath()));
        cardBack = new Sprite(new Texture(TextureEnum.Card_Back.getPath())); //Kartenrücken

        reinitialize(1f); //Der Name, der Schaden und das Leben der Karte können hiermit visualisiert werden

        this.name = name;
        this.damage = damage;
        this.life = life;
        this.element = element;
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
        float symbolY = y + symbolOffset * size;
        float symbolOffset_ = (cardSprite.getWidth() * Gdx.graphics.getDensity() * 0.3f) * size;

        cardSprite.setPosition(x, y);
        cardBack.setPosition(x, y);
        textFont.setPosition(x + cardSprite.getWidth() / 2f, cardSprite.getY() + cardSprite.getHeight() * 0.96f);
        damageFont.setPosition(x + symbolOffset_, symbolY + symbolOffset_);
        lifeFont.setPosition(x + cardSprite.getWidth() - symbolOffset_ , symbolY + symbolOffset_);
        elementSprite.setPosition(x + cardSprite.getWidth() / 2f - elementSprite.getWidth() / 2f, symbolY);
        swordSprite.setPosition(x + symbolOffset, symbolY);
        shieldSprite.setPosition(x + cardSprite.getWidth() - shieldSprite.getWidth() - symbolOffset * 1.5f, symbolY);
    }

    /**
     * @author Noah O. Wantoch
     * @param size Die Größe der Karte
     * Die Karte wird mit einer anderen Größe neu-initialisiert
     */
    public void reinitialize(float size){
        this.size = size;

        cardSprite.setSize(de.noahwantoch.nemsi.Game.GameSettings.cardWidth * size, de.noahwantoch.nemsi.Game.GameSettings.cardHeight * size);
        elementSprite.setSize(de.noahwantoch.nemsi.Game.GameSettings.elementWidth * size, de.noahwantoch.nemsi.Game.GameSettings.elementHeight * size);
        swordSprite.setSize(de.noahwantoch.nemsi.Game.GameSettings.swordWidth * size, de.noahwantoch.nemsi.Game.GameSettings.swordHeight * size);
        shieldSprite.setSize(de.noahwantoch.nemsi.Game.GameSettings.shieldWidth * size, de.noahwantoch.nemsi.Game.GameSettings.shieldHeight * size);
        cardBack.setSize(de.noahwantoch.nemsi.Game.GameSettings.cardWidth * size, de.noahwantoch.nemsi.Game.GameSettings.cardHeight * size);

        textFont = new Font(FontEnum.getMainFontDataName(), de.noahwantoch.nemsi.Game.GameSettings.cardWidth * Gdx.graphics.getDensity() * de.noahwantoch.nemsi.Game.GameSettings.cardSize * GameSettings.cardFontSize * size, 0);
        textFont.setColor(0, 0,0,1);
        damageFont = new Font(FontEnum.getMainFontDataName(), de.noahwantoch.nemsi.Game.GameSettings.cardWidth * Gdx.graphics.getDensity() * de.noahwantoch.nemsi.Game.GameSettings.cardSize * GameSettings.atkAndDefFontSIze * size, 0);
        damageFont.setColor(1, 0.5f,0.5f,0.8f);
        lifeFont = new Font(FontEnum.getMainFontDataName(), de.noahwantoch.nemsi.Game.GameSettings.cardWidth * Gdx.graphics.getDensity() * de.noahwantoch.nemsi.Game.GameSettings.cardSize * GameSettings.atkAndDefFontSIze * size, 0);
        lifeFont.setColor(0.5f, 1,0.5f,0.8f);

        setPosition(getX(), getY());
    }

    public float getWidth(){
        return cardSprite.getWidth();
    }

    public float getSize(){ return size; }

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
