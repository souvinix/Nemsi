package de.noahwantoch.nemsi.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import de.noahwantoch.nemsi.TextureHandling.TextureEnum;
import de.noahwantoch.nemsi.UI.Font;
import de.noahwantoch.nemsi.Utility.FontEnum;

public class MessageBox {
    public String message;
    public Sprite sprite;
    public boolean state;
    public int size;
    public Vector2 position;
    public boolean active;
    public Font messageFont;

    public MessageBox(String message, int size){
        this.message = message;
        sprite = new Sprite(new Texture(TextureEnum.MESSAGE_BOX.getPath()));
        sprite.setSize(sprite.getWidth() * size, sprite.getHeight() * size);

        position.set(Gdx.graphics.getWidth() / 2f - sprite.getWidth() / 2f, Gdx.graphics.getHeight() / 2f - sprite.getHeight() / 2f);
        //messageFont = new Font(FontEnum.Adamina_Regular.getFontDataName());
    }

    public boolean getState(){
        return state;
    }

    public void setPosition(float x, float y){
        position.set(x, y);
    }

    private void exit(){

    }

    public boolean isActive(){
        return active;
    }

    public void draw(SpriteBatch batch){

    }
}
