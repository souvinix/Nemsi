package de.noahwantoch.nemsi.Utility;

public enum FontEnum {
    RETGANON{
        @Override
        public String getFontDataName() {
            return "retganon.ttf";
        }
    };

    public abstract String getFontDataName();
    public static String getMainFontDataName(){ return RETGANON.getFontDataName(); }
}
