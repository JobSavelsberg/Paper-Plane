package com.jobsavelsberg.paperplane.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jobsavelsberg.paperplane.MainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Paper Plane";
		config.width = 1280;
		config.height =720;
		config.fullscreen = false;
		config.vSyncEnabled = true;
		new LwjglApplication(new MainGame(), config);
	}
}
