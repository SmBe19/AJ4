package com.smeanox.games.aj4.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.smeanox.games.aj4.AJ4;
import com.smeanox.games.aj4.Consts;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Consts.WIDTH;
		config.height = Consts.HEIGHT;
		new LwjglApplication(new AJ4(), config);
	}
}
