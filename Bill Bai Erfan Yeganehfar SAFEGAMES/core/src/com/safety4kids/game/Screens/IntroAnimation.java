package com.safety4kids.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.safety4kids.game.Utils.SplashScreenLogo;

/**
 * This class serves as the splash screen to the program. It displays the company logo.
 *
 * @author Bill Bai, Erfan Yeganehfar
 * Ms. Krasteva
 * <p>
 * Modifications:
 * Bill Bai: (2019-05-28) Completed entire class. Time spent: 1 hour.
 * @version 3.0, 2019-05-28
 */
public class IntroAnimation implements Screen {
    /**
     * This variable is type SpriteBatch. It holds a batch of sprites to be drawn on screen.
     */
    private SpriteBatch batch;

    /**
     * This is the stage that is being drawn on
     */
    private Stage stage;

    /**
     * This is the Game that is currently being used to draw on.
     */
    private Game game;

    private SplashScreenLogo logo;

    /**
     * This is the constructor. It initiates the global variables.
     *
     * @param aGame The Game that is currently being drawn on.
     */
    public IntroAnimation(Game aGame) {
        super();
        game = aGame;
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();

        logo = new SplashScreenLogo();
        logo.setPosition(-400,Gdx.graphics.getHeight()/2f-200);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        logo.draw(batch);
        batch.end();

        if(logo.getX() < Gdx.graphics.getWidth()/2 - 200) {
            logo.moveRight(Gdx.graphics.getDeltaTime());
        }else if (logo.getX() > Gdx.graphics.getWidth()/2 - 200 && logo.getAlpha() >= 0) {
            logo.fade();
        }else{
            game.setScreen(new MainMenu(game));
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    /**
     * This method has no implementation, since it is not used. It is only added since the class implements Screen.
     */
    @Override
    public void show() {
    }

    /**
     * This method has no implementation, since it is not used. It is only added since the class implements Screen.
     */
    @Override
    public void resize(int width, int height) { }

    /**
     * This method has no implementation, since it is not used. It is only added since the class implements Screen.
     */
    @Override
    public void pause() {

    }

    /**
     * This method has no implementation, since it is not used. It is only added since the class implements Screen.
     */
    @Override
    public void resume() { }

    /**
     * This method has no implementation, since it is not used. It is only added since the class implements Screen.
     */
    @Override
    public void hide() { }
}
