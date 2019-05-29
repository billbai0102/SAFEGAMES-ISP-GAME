package com.safety4kids.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * This class serves as the splash screen to the program. It displays the company logo.
 * @author Bill Bai, Erfan Yeganehfar
 * Ms. Krasteva
 * <p>
 * Modifications:
 * Bill Bai: Completed entire class. Time spent: 1 hour.
 * @version 3.0, 2019-05-28
 */
public class IntroAnimation implements Screen {
    /**
     * This variable is type SpriteBatch. It holds a batch of sprites to be drawn on screen.
     */
    SpriteBatch batch;
    Texture spriteTexture;

    Stage stage;
    Game game;

    public IntroAnimation(Game aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(stage);
        spriteTexture = new Texture("core/assets/SAFEGAMES_Logo.png");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(spriteTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void show() { }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }
}
