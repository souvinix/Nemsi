package de.noahwantoch.nemsi.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;

public class Font {

    private static final String TAG = Font.class.getSimpleName();
    private static final String FONT_DIRECTORY = "Fonts/";

    private Vector3 position;

    private float size;
    private FileHandle fontFile;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    private BitmapFont textFont;

    private boolean isDisposed = false;

    private String path;

    public Font(String fontDataName, float size, int shadowYOffset){
        this.size = size;
        position = new Vector3();

        path = FONT_DIRECTORY + fontDataName;
        fontFile = Gdx.files.internal(path);
        generator = new FreeTypeFontGenerator(fontFile);
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) size;
        parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS;
        parameter.shadowOffsetY = shadowYOffset;
        parameter.shadowOffsetX = shadowYOffset;
        textFont = generator.generateFont(parameter);

        generator.dispose();
    }

    public void draw(SpriteBatch batch, String text){
        if(!isDisposed()){
            if(text.length() < 5){
                text = " " + text + " ";
            }
            textFont.draw(batch, text, position.x - getSize() / 2, position.y, 1, text.length(), getSize(), 1, true, text);
        }
    }

    public void debug(){
        Gdx.app.debug(TAG, "size: " + size + ", x: " + position.x + ", y: " + position.y);
    }

    public float getSize(){
        return size;
    }

    public void setPosition(float x, float y){
        position.set(x, y,0);
    }

    public void setColor(float r, float g, float b, float a){
        textFont.setColor(r, g, b, a);
    }

    public String getPath(){ return path; }

    public void dispose(){
        if(!isDisposed()){
            textFont.dispose();

            isDisposed = true;
        }
    }

    public boolean isDisposed(){
        return isDisposed;
    }
}
