package com.safety4kids.game;
import com.badlogic.gdx.Game;
import com.safety4kids.game.Screens.GameScreen;

/**
 * This class is the entry point to the game. It is called by DesktopLauncher, and first plays a splash screen before
 * starting the main menu.
 *
 * @version 3.1 2019-05-28
 * @author Bill Bai, Erfan Yeganehfar
 * Ms. Krasteva
 *
 * Modifications:
 *  Bill Bai: Completed entire class. Time spent: 1 hour 2019-05-28
 *  Erfan Yeganehfar: Added overrided methods and components within the constructor: 1.5hrs 2019-05-28
 */
public class Safety4Kids extends Game {
    public static final int V_WIDTH = 450;
    public static final int V_HEIGHT = 450;
    public static final float PPM = 100f;

    /**
     * This variable represents the minimum time taken to startup the game, before going to the main menu.
     */
    private static long MIN_TIME = 3000;

    /**
     * This is the constructor. It calls the superclass constructor, which is of Game.
     */
    public Safety4Kids(){
        super();
    }

    /**
     * This method loads the splash screen, and keeps it on until necessary elements are loaded in. It serves as a buffer
     * to prevent the game from lagging. It is called by the constructor.
     */
    @Override
    public void create () {
        setScreen(new GameScreen(this));


    }

    /**
     * This method calls the superclass render() method, which renders the active screen to display it.
     */
    @Override
    public void render () {
        super.render();
    }

    /**
     * This method calls the superclass dispose() method, which disposes screen assets
     */
    @Override
    public void dispose() {
        super.dispose();
    }

    /**
     * This method calls the superclass pause() method, used to pause the game
     */
    @Override
    public void pause() {
        super.pause();
    }

    /**
     * This method calls the superclass resume() method, used to resume the game
     */
    @Override
    public void resume() {
        super.resume();
    }

    /**
     * This method calls the superclass resize() method, which resizes the window
     */
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }
}
