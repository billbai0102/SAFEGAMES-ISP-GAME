package com.safety4kids.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;

/**
 * This class is the entry point to the game. It is called by DesktopLauncher, and first plays a splash screen before
 * starting the main menu.
 *
 * @version 3.0 2019-05-28
 * @author Bill Bai, Erfan Yeganehfar
 * Ms. Krasteva
 *
 * Modifications:
 *  Bill Bai: Completed entire class. Time spent: 1 hour
 */
public class Start extends Game {
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

        final long START_TIME = System.currentTimeMillis();
        new Thread(new Runnable() {
            @Override
            public void run() {
                /*
                Load all images, music, etc here.
                 */

                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {

                        long splash_elapsed_time = System.currentTimeMillis() - START_TIME;
                        if (splash_elapsed_time < Start.MIN_TIME) {
                            Timer.schedule(
                                    new Timer.Task() {
                                        @Override
                                        public void run() {
                                            Start.this.setScreen(new MainMenu(Start.this));
                                        }
                                    }, (float)(Start.MIN_TIME - splash_elapsed_time) / 1000f);
                        } else {
                            Start.this.setScreen(new MainMenu(Start.this));
                        }
                    }
                });
            }
        }).start();
    }

    /**
     * This method calls the superclass render() method, which renders the scene to display it.
     */
    @Override
    public void render () {
        super.render();
    }
}
