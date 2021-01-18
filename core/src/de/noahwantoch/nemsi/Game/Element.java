package de.noahwantoch.nemsi.Game;

import com.badlogic.gdx.graphics.Color;

import de.noahwantoch.nemsi.TextureHandling.TextureEnum;

public enum Element {

    FIRE{
        Color color = Color.RED;
        @Override
        public String getElementPath() {
            return TextureEnum.FIRE_SYMBOL.getPath();
        }
    },

    ANTIMATTER{
        Color color = Color.BLACK;
        @Override
        public String getElementPath() {
            return TextureEnum.ANTIMATTER_SYMBOL.getPath();
        }
    },

    ICE{
        Color color = Color.BLUE;
        @Override
        public String getElementPath() {
            return TextureEnum.ICE_SYMBOL.getPath();
        }
    },

    NATURE{
        Color color = Color.GREEN;
        @Override
        public String getElementPath() {
            return TextureEnum.NATURE_SYMBOL.getPath();
        }
    },

    WIND{
        Color color = Color.YELLOW;
        @Override
        public String getElementPath() {
            return TextureEnum.WIND_SYMBOL.getPath();
        }
    },

    NO_ELEMENT{
        @Override
        public String getElementPath() {
            return TextureEnum.NO_ELEMENT.getPath();
        }
    };

    public abstract String getElementPath();
}
