package de.noahwantoch.nemsi.TextureHandling;

public enum TextureEnum {
    BUTTON{
        public String getPath(){ return "button.png"; }
    },

    START_BUTTON{
        public String getPath(){ return "startButton.png"; }
    },

    TITLE{
        public String getPath(){ return "title.png"; }
    },

    BACKGROUND{
        public String getPath(){ return "background.png"; }
    },

    CARD{
        public String getPath(){ return "card.png"; }
    },

    Card_Back{
        public String getPath(){ return "card_back.png"; }
    },

    FIRE_SYMBOL{
        public String getPath(){ return "fire_symbol.png"; }
    },

    ICE_SYMBOL{
        public String getPath(){ return "ice_symbol.png"; }
    },

    NATURE_SYMBOL{
        public String getPath(){ return "nature_symbol.png"; }
    },

    ANTIMATTER_SYMBOL{
        public String getPath(){ return "antimatter_symbol.png"; }
    },

    WIND_SYMBOL{
        public String getPath(){ return "wind_symbol.png"; }
    },

    SWORD_SYMBOL{
        public String getPath(){ return "sword_symbol.png"; }
    },

    SHIELD_SYMBOL{
        public String getPath(){ return "shield_symbol.png"; }
    },

    BOARD{
        public String getPath(){ return "spielfeld.png"; }
    },

    STAPEL_ZONE{
        public String getPath(){ return "stapel_zone.png"; }
    };

    public abstract String getPath();

}
