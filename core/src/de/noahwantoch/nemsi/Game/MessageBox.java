package de.noahwantoch.nemsi.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import de.noahwantoch.nemsi.TextureHandling.TextureEnum;
import de.noahwantoch.nemsi.UI.Button;
import de.noahwantoch.nemsi.UI.Font;
import de.noahwantoch.nemsi.Utility.FontEnum;

public class MessageBox {
    private String message;
    private Sprite sprite;
    private int size;
    private Vector2 position;
    private Font messageFont;
    private MessageBoxType type;

    private Button buttonTrue; //Yes / okay --> return true
    private Button buttonFalse; //No --> return false

    private boolean state = false;
    private boolean result = false;

    private int wrap = 20;

    public MessageBox(int size, MessageBoxType type){
        this.sprite = new Sprite(new Texture(TextureEnum.MESSAGE_BOX.getPath()));
        this.type = type;
        sprite.setSize(sprite.getWidth() * size, sprite.getHeight() * size);
        position = new Vector2(Gdx.graphics.getWidth() / 2f - sprite.getWidth() / 2f, Gdx.graphics.getHeight() / 2f - sprite.getHeight() / 2f);
        sprite.setPosition(position.x, position.y);
        messageFont = new Font(FontEnum.Adamina_Regular.getFontDataName(), size * 4, message);
        messageFont.setColor(0f, 0f, 0f, 1f);
        messageFont.setPosition(position.x + sprite.getWidth() / 2f, position.y + sprite.getHeight() * 0.8f);
        messageFont.wrapText(wrap);

        float buttonHeight = position.y + sprite.getHeight() * 0.3f;
        float buttonSize = size / 2f;

        //Both Buttons needed
        if(type == MessageBoxType.YES_NO_MESSAGE_BOX){
            buttonTrue = new Button(position.x + sprite.getWidth() * 0.4f, buttonHeight, buttonSize, "Ja", false);
            buttonFalse = new Button(position.x + sprite.getWidth() - buttonTrue.getWidth(), buttonHeight, buttonSize, "Nein", false);
        }
        //Just one Button needed
        else if(type == MessageBoxType.OKAY_MESSAGE_BOX){
            buttonTrue = new Button(position.x + sprite.getWidth() / 2f, buttonHeight, buttonSize, "Okay", false);
            buttonFalse = new Button(0, 0, 0, "", false);
        }
    }

    public void draw(SpriteBatch batch, float delta){
        if(state){
            sprite.draw(batch);
            messageFont.draw(batch);
            buttonTrue.draw(delta);

            if(type == MessageBoxType.YES_NO_MESSAGE_BOX){
                buttonFalse.draw(delta);
            }

            if(buttonTrue.isPressedDelayed()){
                result = true;
                state = false;
            }

            if(buttonFalse.isPressedDelayed()){
                state = false;
                result = false;
            }
        }
    }

    public void reset(){
        result = false;
    }

    public boolean getState(){ return state; }

    public boolean getResult(){ return result; }

    public void showMessage(String message){
        result = false;
        state = true;
        buttonFalse.reset();
        buttonTrue.reset();
        this.message = message;
        messageFont.setText(message);
        messageFont.wrapText(wrap);
    }

    public void showMessage(){
        state = true;
    }

    public enum MessageBoxType{
        YES_NO_MESSAGE_BOX,
        OKAY_MESSAGE_BOX
    }
}
