package com.safety4kids.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.safety4kids.game.Safety4Kids;

/**
 * @version 3.1, 2019-05-28
 * @author Bill Bai, Erfan Yeganehfar
 * Ms. Krasteva
 *
 * Modifications:
 *  3.0 Bill Bai: Completed entire class. Time spent: -- 7 mins.
 *  3.1 Erfan Yeg: (2019-05-29) Better naming conventions/ programming organization with static finals -- 5 mins
 *  3.2 Bill Bai: (2019-06-03) Changed sizing settings in window -- 7 mins.
 */
public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = Safety4Kids.TITLE; //This is the title of the output window.
		config.height = Safety4Kids.V_HEIGHT * Safety4Kids.SCALE; //This is the height of the output window.
		config.width = Safety4Kids.V_WIDTH * Safety4Kids.SCALE; //This is the width of the output window.
		config.forceExit = false; //This allows the application to fully close, without running in background when closed.
		config.resizable = false; //Disables resizing.
        new LwjglApplication(new Safety4Kids(), config); //Starts the game.
	}
}
