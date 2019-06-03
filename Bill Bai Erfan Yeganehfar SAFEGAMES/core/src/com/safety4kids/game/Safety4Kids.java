package com.safety4kids.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.safety4kids.game.Screens.IntroAnimation;

/**
 * This class is the entry point to the game. It is called by DesktopLauncher, and first plays a splash screen before
 * starting the main menu.
 *
 * @version 3.1 2019-05-28
 * @author Bill Bai, Erfan Yeganehfar
 * Ms. Krasteva
 *
 * Modifications:
 *  3.0 Bill Bai: (2019-05-28) Completed entire class. Time spent: 1 hour
 *  3.1 Erfan Yeganehfar: (2019-05-28) Added overrided methods and components within the constructor to create the virtual game: 1.5hrs
 */
public class Safety4Kids extends Game {

    public static final String TITLE = "Safety4Kids";
    public static final int V_WIDTH = 450;
    public static final int V_HEIGHT = 450;
    public static final float PPM = 101f;
    public static final int SCALE = 2;
    public static final float CONST_GRAVITY = -9.8f;
    public static final float MAX_VELOCITY = 2f;
    public static final float MIN_VELOCITY = -2f;
    public static final float STEP = 1 / 60f;
    public SpriteBatch batch;

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
        setScreen(new IntroAnimation(this));
    }

    /**
     * This method calls the superclass render() method, which renders the active screen to display it.
     */
    @Override
    public void render () {
        super.render();
        Gdx.graphics.setTitle(TITLE + " -- FPS: " + Gdx.graphics.getFramesPerSecond());
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
