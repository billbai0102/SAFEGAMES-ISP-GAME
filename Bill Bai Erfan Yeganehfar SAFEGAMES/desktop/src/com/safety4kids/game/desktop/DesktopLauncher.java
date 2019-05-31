package com.safety4kids.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.safety4kids.game.Safety4Kids;

/**
 * @version 3.0, 2019-05-28
 * @author Bill Bai, Erfan Yeganehfar
 * Ms. Krasteva
 *
 * Modifications:
 *  Bill Bai: Completed entire class. Time spent: 7 minutes.
 */
public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = Safety4Kids.TITLE; //This is the title of the output window.
		config.height = Safety4Kids.V_HEIGHT * Safety4Kids.SCALE; //This is the height of the output window.
		config.width = Safety4Kids.V_WIDTH * Safety4Kids.SCALE; //This is the width of the output window.
		config.forceExit = false; //This allows the application to fully close, without running in background when closed.
		new LwjglApplication(new Safety4Kids(), config); //Starts the game.
	}
}
