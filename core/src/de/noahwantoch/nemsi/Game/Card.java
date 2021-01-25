package de.noahwantoch.nemsi.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import de.noahwantoch.nemsi.EffectModule;
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
    private int rowLength = 15;

    private Effect effect;
    private Tribute tribute;
    private Font tributeFont; //could be null after initialization
    private Sprite tributeSprite; //could be null after initialization

    private Font descriptionFont;
    private boolean selected;

    private Color currentNameColor;
    private Color currentLifeColor;

    public Card(String name, int damage, int life, Element element){
        this.effect = new Effect("Kein Effekt.", EffectModule.NO_EFFECT, 0);
        this.tribute = new Tribute(Element.NO_ELEMENT, 0);
        init(name, damage, life, element);
    }

    public Card(String name, int damage, int life, Element element, Effect effect){
        this.effect = effect;
        this.tribute = new Tribute(Element.NO_ELEMENT, 0);
        init(name, damage, life, element);
    }

    public Card(String name, int damage, int life, Element element, Effect effect, Tribute tribute){
        this.effect = effect;
        this.tribute = tribute;
        init(name, damage, life, element);
    }

    private void init(String name, int damage, int life, Element element){
        cardSprite = new Sprite(new Texture(TextureEnum.CARD.getPath()));
        elementSprite = new Sprite(new Texture(element.getElementPath()));
        swordSprite = new Sprite(new Texture(TextureEnum.SWORD_SYMBOL.getPath()));
        shieldSprite = new Sprite(new Texture(TextureEnum.SHIELD_SYMBOL.getPath()));
        cardBack = new Sprite(new Texture(TextureEnum.Card_Back.getPath())); //Kartenrücken
        tributeSprite = new Sprite(new Texture(tribute.getNeededElement().getElementPath()));

        this.name = name;
        this.damage = damage;
        this.life = life;
        this.element = element;

        this.currentNameColor = GameSettings.color_cardName.cpy();
        this.currentLifeColor = GameSettings.color_greenNormal.cpy();

        this.size = 2f;
        reinitialize(1f); //Der Name, der Schaden und das Leben der Karte können hiermit visualisiert werden
    }

    public void draw(float delta){
        //covered = "umgedreht" --> man sieht den Kartenrücken
        if(!covered){
            cardSprite.draw(BatchInstance.batch);
            textFont.draw(BatchInstance.batch);
            elementSprite.draw(BatchInstance.batch);
            swordSprite.draw(BatchInstance.batch);
            shieldSprite.draw(BatchInstance.batch);
            damageFont.draw(BatchInstance.batch);
            lifeFont.draw(BatchInstance.batch);
            descriptionFont.draw(BatchInstance.batch);

            if(tribute.getNeededCards() > 0){
                tributeSprite.draw(BatchInstance.batch);
                tributeFont.draw(BatchInstance.batch);
            }
        }else cardBack.draw(BatchInstance.batch);
    }

    public Tribute getTribute(){ return tribute; }

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
        descriptionFont.setPosition(x + cardSprite.getWidth() / 2f, y + cardSprite.getHeight() / 3f);

        tributeSprite.setPosition(x + cardSprite.getWidth() * 0.84f, y + cardSprite.getHeight() * 0.41f);
        tributeFont.setPosition(x + cardSprite.getWidth() * 0.78f, y + cardSprite.getHeight() * 0.47f);
    }

    public boolean isSelected(){ return selected; }

    public void select(){
        selected = true;
        currentNameColor.set(GameSettings.color_cardNameSelected);
        textFont.setColor(GameSettings.color_cardNameSelected.r, GameSettings.color_cardNameSelected.g, GameSettings.color_cardNameSelected.b, GameSettings.color_cardNameSelected.a); //rgba
    }

    public void deselect(){
        selected = false;
        currentNameColor.set(GameSettings.color_cardName);
        textFont.setColor(GameSettings.color_cardName.r, GameSettings.color_cardName.g, GameSettings.color_cardName.b, GameSettings.color_cardName.a); //rgba
    }


    /**
     * @author Noah O. Wantoch
     * Aktualisiert die Visualisierung der Zahlen auf der Karte (effizienter als bei reinitialize())
     */
    public void updateValues(){
        damageFont = new Font(FontEnum.getMainFontDataName(), (int) (de.noahwantoch.nemsi.Game.GameSettings.cardWidth * Gdx.graphics.getDensity() * de.noahwantoch.nemsi.Game.GameSettings.cardSize * GameSettings.atkAndDefFontSIze * size), Integer.toString(damage));
        damageFont.setColor(GameSettings.color_atkNormal.r, GameSettings.color_atkNormal.g, GameSettings.color_atkNormal.b, GameSettings.color_atkNormal.a); //rgba
        lifeFont = new Font(FontEnum.getMainFontDataName(), (int) (de.noahwantoch.nemsi.Game.GameSettings.cardWidth * Gdx.graphics.getDensity() * de.noahwantoch.nemsi.Game.GameSettings.cardSize * GameSettings.atkAndDefFontSIze * size), Integer.toString(life));
        lifeFont.setColor(currentLifeColor.r, currentLifeColor.g, currentLifeColor.b, currentLifeColor.a);
    }

    /**
     * @author Noah O. Wantoch
     * @param size Die Größe der Karte
     * Die Karte wird mit einer anderen Größe neu-initialisiert
     */
    public void reinitialize(float size){
        if(this.size != size){
            this.size = size;

            cardSprite.setSize(de.noahwantoch.nemsi.Game.GameSettings.cardWidth * size, de.noahwantoch.nemsi.Game.GameSettings.cardHeight * size);
            elementSprite.setSize(de.noahwantoch.nemsi.Game.GameSettings.elementWidth * size, de.noahwantoch.nemsi.Game.GameSettings.elementHeight * size);
            swordSprite.setSize(de.noahwantoch.nemsi.Game.GameSettings.swordWidth * size, de.noahwantoch.nemsi.Game.GameSettings.swordHeight * size);
            shieldSprite.setSize(de.noahwantoch.nemsi.Game.GameSettings.shieldWidth * size, de.noahwantoch.nemsi.Game.GameSettings.shieldHeight * size);
            cardBack.setSize(de.noahwantoch.nemsi.Game.GameSettings.cardWidth * size, de.noahwantoch.nemsi.Game.GameSettings.cardHeight * size);
            tributeSprite.setSize((GameSettings.elementWidth / 3f) * size, (GameSettings.elementHeight / 3f)* size);

            textFont = new Font(FontEnum.getMainFontDataName(), (int) (de.noahwantoch.nemsi.Game.GameSettings.cardWidth * Gdx.graphics.getDensity() * de.noahwantoch.nemsi.Game.GameSettings.cardSize * GameSettings.cardFontSize * size), name);
            textFont.setColor(currentNameColor.r, currentNameColor.g, currentNameColor.b, currentNameColor.a);

            damageFont = new Font(FontEnum.getMainFontDataName(), (int) (de.noahwantoch.nemsi.Game.GameSettings.cardWidth * Gdx.graphics.getDensity() * de.noahwantoch.nemsi.Game.GameSettings.cardSize * GameSettings.atkAndDefFontSIze * size), Integer.toString(damage));
            damageFont.setColor(GameSettings.color_atkNormal.r, GameSettings.color_atkNormal.g, GameSettings.color_atkNormal.b, GameSettings.color_atkNormal.a);
            lifeFont = new Font(FontEnum.getMainFontDataName(), (int) (de.noahwantoch.nemsi.Game.GameSettings.cardWidth * Gdx.graphics.getDensity() * de.noahwantoch.nemsi.Game.GameSettings.cardSize * GameSettings.atkAndDefFontSIze * size), Integer.toString(life));
            lifeFont.setColor(currentLifeColor.r, currentLifeColor.g, currentLifeColor.b, currentLifeColor.a);
            descriptionFont = new Font(FontEnum.Adamina_Regular.getFontDataName(), (int) GameSettings.descriptionSize, effect.getDescription());
            descriptionFont.wrapText(rowLength);
            descriptionFont.setColor(GameSettings.color_cardDescription.r, GameSettings.color_cardDescription.g, GameSettings.color_cardDescription.b, GameSettings.color_cardDescription.a); //rgba
            tributeFont = new Font(FontEnum.Adamina_Regular.getFontDataName(), (int) (GameSettings.descriptionSize * size), tribute.getNeededCards() + "x");
            tributeFont.setColor(GameSettings.color_tributeFontColor.r, GameSettings.color_tributeFontColor.g, GameSettings.color_tributeFontColor.b, GameSettings.color_tributeFontColor.a); //rgba

            setPosition(getX(), getY());
        }
    }

    public void heal(int amount){
        this.life += amount;
        currentLifeColor.set(GameSettings.color_greenHealed.cpy());
    }

    public Effect getEffect(){ return effect; }

    public Element getElement(){ return element; }

    public float getWidth(){
        return cardSprite.getWidth();
    }

    public float getSize(){ return size; }

    //Erstellt eine Kopie dieser Karte, um auszuschließen, dass man auf die selbe Instanz referenziert
    public Card getCopy(){
        return new Card(name, damage, life, element, effect, tribute);
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
