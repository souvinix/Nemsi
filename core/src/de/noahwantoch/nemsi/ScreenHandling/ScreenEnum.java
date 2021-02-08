package de.noahwantoch.nemsi.ScreenHandling;

import com.badlogic.gdx.Screen;

import de.noahwantoch.nemsi.ScreenHandling.differentScreens.GameScreen;
import de.noahwantoch.nemsi.ScreenHandling.differentScreens.MenuScreen;
import de.noahwantoch.nemsi.ScreenHandling.differentScreens.OptionsScreen;
import de.noahwantoch.nemsi.ScreenHandling.differentScreens.QuitScreen;
import de.noahwantoch.nemsi.ScreenHandling.differentScreens.ShopScreen;

public enum ScreenEnum {
    MENU_SCREEN {
        public Screen getScreen() {
            return new MenuScreen();
        }
    },

    GAME_SCREEN{
        public Screen getScreen(){ return new GameScreen(); }
    },

    //Applikation soll verlassen werden
    QUIT_SCREEN{
        public Screen getScreen(){ return new QuitScreen(); }
    },

    OPTIONS_SCREEN{
        public Screen getScreen(){ return new OptionsScreen(); }
    },

    SHOP_SCREEN{
        public Screen getScreen(){ return new ShopScreen(); }
    };

    public abstract Screen getScreen();
}
