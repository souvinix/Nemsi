package de.noahwantoch.nemsi.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.noahwantoch.nemsi.ScreenHandling.ScreenEnum;
import de.noahwantoch.nemsi.ScreenHandling.ScreenHandler;
import de.noahwantoch.nemsi.TextureHandling.TextureEnum;
import de.noahwantoch.nemsi.Utility.BatchInstance;
import de.noahwantoch.nemsi.Utility.FontEnum;
import de.noahwantoch.nemsi.Utility.TouchDetector;

public class Button {
    private static final String TAG = Button.class.getSimpleName();
    private float secondsToLoad = 0.15f; //Damit man sehen kann, wie man den Knopf vorher runterdrückt

    private float x;
    private float y;
    private int state = 0;
    private float size;

    private Texture texture;
    private TextureRegion[] textureRegions = new TextureRegion[2];

    private float width;
    private float height;

    private String fontDataName = FontEnum.Retganon.getFontDataName(); //Fonts / FontDataname.ttf
    private de.noahwantoch.nemsi.UI.Font textFont;
    private String text;

    private float offsetX;
    private float offsetY;

    private boolean isPressed;
    private ScreenEnum currentScreen;

    private TouchDetector touchDetector;

    private boolean isDisposed;
    private TextureEnum textureEnum = TextureEnum.BUTTON;

    private boolean functionEnabled = true;
    private boolean screenCalling = true;
    private boolean isPressedDelayed = false;

    public Button(TextureEnum textureEnum, float x, float y, float size, String text){
        this.textureEnum = textureEnum;
        setSize(size);
        setPosition(x, y);
        setText(text);
        initialize();
    }

    //texture 0 or 1 represents the state of the button
    public Button(float x, float y, float size, String text){
        setSize(size);
        setPosition(x, y);
        setText(text);
        initialize();
    }

    public Button(float x, float y, float size, String text, boolean screenCalling){
        this.screenCalling = screenCalling;
        setSize(size);
        setPosition(x, y);
        setText(text);
        initialize();
    }

    public void setFunction(boolean bool){
        functionEnabled = bool;
    }

    float counter = 0;
    public void draw(float delta){
        if(!isDisposed){
            BatchInstance.batch.draw(textureRegions[state], offsetX, offsetY, 0, 0, width, height, size, size, 0);

            if(!text.isEmpty()) {
                textFont.draw(BatchInstance.batch);
            }

            if(isPressed){
                counter += delta;
            }

            if(counter >= secondsToLoad){
                if(functionEnabled) function();
                isPressedDelayed = true;
            }

            //Wenn der Button gedrückt wird
            if(touchDetector.isRectangleTouched(offsetX, offsetY, width * size, height * size)){
                state = 1;
                textFont.setPosition(x, y + texture.getHeight() * Gdx.graphics.getDensity() * 1.2f);

                isPressed = true;
                return;
            }
            textFont.setPosition(x, y + texture.getHeight());
            state = 0;
        }else Gdx.app.debug(TAG, "DISPOSED");
    }

    public void setScreen(ScreenEnum screenEnum){
        currentScreen = screenEnum;
    }

    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void function(){
        isPressed = false;

        if(screenCalling){
            ScreenHandler.getInstance().setCurrentScreen(currentScreen);
        }
    }

    public void reset(){
        isPressedDelayed = false;
        isPressed = false;
        counter = 0;
    }

    public void setSize(float value){
        size = value;
    }

    public void setText(String string){
        text = string;
    }

    public float getWidth(){ return width * size; }
    public float getHeight(){ return height * size; }

    private void initialize(){
        texture = new Texture(Gdx.files.internal(textureEnum.getPath()));
        textureRegions[0] = new TextureRegion(texture, 0, 0, texture.getWidth() / 2, texture.getHeight());
        textureRegions[1] = new TextureRegion(texture, texture.getWidth() / 2, 0, texture.getWidth() / 2, texture.getHeight());

        width = texture.getWidth() / 2f;
        height = texture.getHeight();

        textFont = new Font(fontDataName, (int) (size * 10f), text);
        textFont.setPosition(x, y + texture.getHeight());
        textFont.setColor(0f, 0f, 0f, 1f);

        touchDetector = new TouchDetector();

        offsetX = x - ((width / 2f) * size);
        offsetY = y - ((height / 2f) * size);

        isDisposed = false;
    }

    //Instant detection
    public boolean isPressed(){
        return isPressed;
    }

    //Delayed detection
    public boolean isPressedDelayed(){
        return isPressedDelayed;
    }

    public void dispose(){
        isDisposed = true;
    }
}
