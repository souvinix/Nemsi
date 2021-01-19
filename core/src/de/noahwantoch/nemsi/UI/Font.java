package de.noahwantoch.nemsi.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;

import de.noahwantoch.nemsi.Game.GameSettings;
import de.noahwantoch.nemsi.Utility.FontEnum;

public class Font {

    private static final String TAG = Font.class.getSimpleName();
    private static final String FONT_DIRECTORY = "Fonts/";

    private Vector3 position;

    private float size;
    private FileHandle fontFile;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont textFont;
    private String path;
    private String text;

    private float offset;

    private boolean isDisposed = false;

    public Font(String fontDataName, int size, String text){
        this.size = size;
        this.text = text;

        position = new Vector3();

        path = FONT_DIRECTORY + fontDataName;
        fontFile = Gdx.files.internal(path);
        generator = new FreeTypeFontGenerator(fontFile);
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS;
        textFont = generator.generateFont(parameter);
        generator.dispose();
    }

    public void wrapText(int rowLength){
        offset = 0;
        ArrayList<String> words = new ArrayList<>();
        String currentWord = "";

        for(int i = 0; i < text.length(); i++){
            if(text.charAt(i) == ' ' || text.charAt(i) == '-'){
                words.add(currentWord);
                currentWord = "";
            }else{
                currentWord += Character.toString(text.charAt(i));
            }
        }

        words.add(currentWord);

        String newText = "";
        int counter = 0;
        for(int i = 0; i < words.size(); i++){
            if(counter + words.get(i).length() < rowLength){
                newText += words.get(i);
                counter += words.get(i).length();
            }else{
                newText += "\n" + words.get(i);
                counter = 0;
                offset += size * Gdx.graphics.getDensity();
            }
            if(i != words.size() - 1){
                if(i + 1 < words.size()){
                    if(counter + words.get(i + 1).length() <= rowLength){
                        newText += " ";
                    }
                }
            }
        }

        text = newText;
    }

    public void wrapTextByCut(int cut){
        offset = 0;
        int counter = 0;
        int wordCounter = 0;
        String newText = "";

        for(int i = 0; i < text.length(); i++){
            newText += Character.toString(text.charAt(i));
            if(text.charAt(i) == ' ' || text.charAt(i) == '-'){
                counter += 1;
            }
            if(counter >= cut){
                counter = 0;
                newText += "\n";
                offset += size * Gdx.graphics.getDensity();
            }
        }

        text = newText;
    }

    public void setText(String text){
        this.text = text;
    }

    public void draw(SpriteBatch batch){
        if(!isDisposed()){
            textFont.draw(batch, text, position.x, position.y + offset, 0, 1, true);
        }
    }

    public void debug(){
        Gdx.app.debug(TAG, "size: " + size + ", x: " + position.x + ", y: " + position.y);
    }

    public void setPosition(float x, float y){
        position.set(x, y,0);
    }

    public void setColor(float r, float g, float b, float a){
        textFont.setColor(r, g, b, a);
    }

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
