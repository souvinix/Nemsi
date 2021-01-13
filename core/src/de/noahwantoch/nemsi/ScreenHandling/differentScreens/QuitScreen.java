package de.noahwantoch.nemsi.ScreenHandling.differentScreens;

import com.badlogic.gdx.Gdx;

import de.noahwantoch.nemsi.ScreenHandling.AbstractScreen;

public class QuitScreen extends AbstractScreen {

    public QuitScreen(){Gdx.app.debug("QUIT", "CALLED IT");}

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.app.exit();
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
    }
}
