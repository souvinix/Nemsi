package de.noahwantoch.nemsi.ScreenHandling.differentScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import de.noahwantoch.nemsi.TextureHandling.TextureEnum;
import de.noahwantoch.nemsi.UI.Button;
import de.noahwantoch.nemsi.ScreenHandling.AbstractScreen;
import de.noahwantoch.nemsi.ScreenHandling.ScreenEnum;
import de.noahwantoch.nemsi.Utility.BatchInstance;

public class MenuScreen extends AbstractScreen implements Screen {
    private static final String TAG = MenuScreen.class.getSimpleName();
    private de.noahwantoch.nemsi.UI.Button startButton;
    private de.noahwantoch.nemsi.UI.Button quitButton;
    private de.noahwantoch.nemsi.UI.Button optionsButton;
    private de.noahwantoch.nemsi.UI.Button shopButton;

    private Texture titleTexture;
    private Sprite titleSprite;
    private Texture backgroundTexture;
    private Sprite backgroundSprite;

    private Vector2 buttonPosition;
    private float offset = Gdx.graphics.getHeight() * 1/200f * Gdx.graphics.getDensity();
    private float buttonSize = 5f;
    private float startButton_offset = offset * 8f;

    public MenuScreen(){
        super();
        buttonPosition = new Vector2(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() * 1/8f);

        backgroundTexture = new Texture(de.noahwantoch.nemsi.TextureHandling.TextureEnum.BACKGROUND.getPath());
        backgroundSprite = new Sprite(backgroundTexture);

        backgroundSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        backgroundSprite.setScale(3f);

        Texture texture = new Texture(de.noahwantoch.nemsi.TextureHandling.TextureEnum.BUTTON.getPath());

        float i = 0f;
        quitButton = new de.noahwantoch.nemsi.UI.Button(buttonPosition.x, buttonPosition.y + offset * i, buttonSize, "BEENDEN");
        quitButton.setScreen(ScreenEnum.QUIT_SCREEN); i++;
        optionsButton = new de.noahwantoch.nemsi.UI.Button(buttonPosition.x, buttonPosition.y + texture.getHeight() * buttonSize * i + offset * i, buttonSize, "OPTIONEN");
        optionsButton.setScreen(ScreenEnum.OPTIONS_SCREEN); i++;
        shopButton = new de.noahwantoch.nemsi.UI.Button(buttonPosition.x, buttonPosition.y + texture.getHeight() * buttonSize  * i + offset * i, buttonSize, "SHOP");
        shopButton.setScreen(ScreenEnum.SHOP_SCREEN); i++;
        startButton = new Button(de.noahwantoch.nemsi.TextureHandling.TextureEnum.START_BUTTON, buttonPosition.x, buttonPosition.y + texture.getHeight() * buttonSize * i + startButton_offset * i, buttonSize - 1, "");
        startButton.setScreen(ScreenEnum.GAME_SCREEN); i++;

        optionsButton.setFunction(false);
        shopButton.setFunction(false);

        titleTexture = new Texture(TextureEnum.TITLE.getPath());
        titleSprite = new Sprite(titleTexture);
        int titleSize = 6;
        titleSprite.setSize(titleSprite.getWidth() * titleSize, titleSprite.getHeight() * titleSize);
        titleSprite.setPosition(Gdx.graphics.getWidth() / 2f - titleSprite.getWidth() / 2f, Gdx.graphics.getHeight() - titleSprite.getHeight() - titleSprite.getHeight() * 1/20f);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        BatchInstance.batch.begin();
        backgroundSprite.draw(BatchInstance.batch);
        startButton.draw(delta);
        optionsButton.draw(delta);
        shopButton.draw(delta);
        quitButton.draw(delta);

        titleSprite.draw(BatchInstance.batch);
        BatchInstance.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
        super.dispose();

        startButton.dispose();
        quitButton.dispose();
        titleTexture.dispose();
    }

    @Override
    public void show() {
        super.show();
    }
}
