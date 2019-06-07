package com.safety4kids.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


/**
 *
 * @version 3.0, 2019-05-28
 * @author Erfan Yeganehfar
 * @author Bill Bai
 *
 * Ms. Krasteva
 *
 * Modifications:
 *  Bill Bai: Completed entire class. Time spent: 1 hour.
 */
public class Instructions implements Screen {
    SpriteBatch batch;
    Texture backgroundImg;
    Skin skin;
    TextButton backBtn;

    Stage stage;
    Game game;

    public Instructions(Game aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();

        Gdx.input.setInputProcessor(stage);
        backgroundImg = new Texture(Gdx.files.internal("InstructionsImg.jpg"));
        skin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));

        //instructions btn
        backBtn = new TextButton("BACK", skin);
        backBtn.setSize(100, 100);
        backBtn.setPosition(Gdx.graphics.getWidth() / 2 - 10, 30);
        backBtn.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Instructions --> Main Menu");
                game.setScreen(new MainMenu(game));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        stage.addActor(backBtn);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    @SuppressWarnings("Duplicates")
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundImg, 0, 0);
        batch.end();

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        backgroundImg.dispose();
        stage.dispose();
        skin.dispose();
        batch.dispose();
    }
}
