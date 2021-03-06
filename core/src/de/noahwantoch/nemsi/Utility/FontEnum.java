package de.noahwantoch.nemsi.Utility;

public enum FontEnum {
    PrinceValiant {
        @Override
        public String getFontDataName() {
            return "PrinceValiant.ttf";
        }
    },

    Retganon {
        @Override
        public String getFontDataName() {
            return "retganon.ttf";
        }
    },

    Adamina_Regular{
        @Override
        public String getFontDataName() {
            return "Adamina-Regular.ttf";
        }
    };

    public abstract String getFontDataName();
    public static String getMainFontDataName(){ return PrinceValiant.getFontDataName(); }
}
