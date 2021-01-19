package de.noahwantoch.nemsi.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import de.noahwantoch.nemsi.MainGameClass;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = 1706;
		config.height = 960;
//		config.width = 1920;
//		config.height = 1080;
		config.useGL30 = true;
		config.title = "N E M S I";
//		config.fullscreen = true;
		config.resizable = false;
		config.foregroundFPS = 144;
		config.y = 0;

		new LwjglApplication(new MainGameClass(), config);
	}
}
