package com.safety4kids.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;

/**
 * This class is the entry point to the game. It is called by DesktopLauncher, and first plays a splash screen before
 * starting the main menu.
 *
 * @author Bill Bai, Erfan Yeganehfar
 * Ms. Krasteva
 * Modifications:
 * Bill Bai: Completed entire class. Time spent: 1 hour
 * @version 3.0 2019-05-28
 */
public class Start extends Game implements ApplicationListener {
    /**
     * This variable represents the minimum time taken to startup the game, before going to the main menu.
     */
    private static long MIN_TIME = 3000;

    /**
     * This is the constructor. It calls the superclass constructor, which is of Game.
     */
    public Start(){
        super();
    }

    /**
     * This method loads the splash screen, and keeps it on until necessary elements are loaded in. It serves as a buffer
     * to prevent the game from lagging. It is called by the constructor.
     */
    @Override
    public void create () {
        setScreen(new IntroAnimation(this));
    }

    /**
     * This method calls the superclass render() method, which renders the scene to display it.
     */
    @Override @SuppressWarnings("Duplicates")
    public void render () {
        super.render();
    }
}
