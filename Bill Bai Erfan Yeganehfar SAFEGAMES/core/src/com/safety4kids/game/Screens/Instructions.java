package com.safety4kids.game.Screens;

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
import com.safety4kids.game.Safety4Kids;


/**
 * This class is the instructions screen for the game, which display's the game's instructions.
 *
 * <h2>Course info:</h2>
 * ICS4U with V. Krasteva
 *
 * @author Bill Bai, Erfan Yeganehfar
 * @version 1.5 05/30/19
 */
public class Instructions implements Screen {
    /**
     * SpriteBatch to be drawn on
     */
    SpriteBatch batch;
    /**
     * Image containing instructions
     */
    Texture backgroundImg;
    /**
     * Skin for the buttons.
     */
    Skin skin;
    /**
     * Button that leads to MainMenu
     */
    TextButton backBtn;
    /**
     * Stage, where buttons are added on to be drawn
     */
    Stage stage;
    /**
     * Game screen to be drawn on.
     */
    Safety4Kids game;

    /**
     * This is the constructor. It sets initializes variables and adds a listener to backBtn.
     *
     * @param game Safety4Kids game object to be drawn on.
     */
    public Instructions(Safety4Kids game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(stage);
        backgroundImg = new Texture(Gdx.files.internal("InstructionsImg.jpg"));
        skin = new Skin(Gdx.files.internal("skin/flat_earth/flat-earth-ui.json"));

        //Instructions button
        backBtn = new TextButton("BACK", skin);
        backBtn.setPosition(Gdx.graphics.getWidth() / 2 - backBtn.getWidth() / 2, 30);
        //Adds listener
        backBtn.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Instructions --> Main Menu");
                Instructions.this.game.setScreen(new MainMenu(Instructions.this.game));
                dispose();
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(backBtn);
    }

    /**
     * This is the render method. It draws the instructions image and button.
     *
     * @param delta The current frame.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundImg, 0, 0);
        batch.end();

        stage.draw();
    }

    /**
     * Disposes of objects created in the class to free up memory.
     */
    @Override
    public void dispose() {
        backgroundImg.dispose();
        stage.dispose();
        game.dispose();
        skin.dispose();
        batch.dispose();
    }

    @Override
    public void show() {
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
}
