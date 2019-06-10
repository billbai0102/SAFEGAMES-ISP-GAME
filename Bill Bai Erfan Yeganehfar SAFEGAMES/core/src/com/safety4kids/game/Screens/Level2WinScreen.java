package com.safety4kids.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.safety4kids.game.Safety4Kids;

/**
 * This class is the win screen for level 2.
 * <br>
 * Background of image borrowed from <a href="https://www.behance.net/gallery/65290819/Pixel-Art-Backgrounds-Tutorial-Skip">
 * source site</a> (2018-05-06) by artist: Fez Escalante
 * <br>
 *
 * <h2>Course info:</h2>
 * ICS4U with V. Krasteva
 *
 * @author Bill Bai, Erfan Yeganehfar
 * @version 2.6 06/09/19
 */
public class Level2WinScreen implements Screen {

    /**
     * SpriteBatch to be drawn onto.
     */
    private SpriteBatch batch;
    /**
     * Game to be drawn onto.
     */
    private Safety4Kids game;
    /**
     * Background of level.
     */
    private Texture bg;
    /**
     * Sprite to convert bg Texture to sprite, therefore it can be altered.
     */
    private Sprite bgSprite;
    /**
     * Button to allow user to continue
     */
    private TextButton contBtn;
    /**
     * Stage that button will be drawn onto
     */
    private Stage stage;
    /**
     * Skin for buttons
     */
    private Skin skin;
    /**
     * Starting alpha value of bgSprite
     */
    private float alpha = 1;

    /**
     * This is the constructor. It initializes variables, and sets the position and adds a listener to contBtn.
     *
     * @param game The game to be drawn onto
     */
    public Level2WinScreen(Safety4Kids game) {
        this.game = game;
        batch = new SpriteBatch();
        bg = new Texture(Gdx.files.internal("Lv2Assets/WinScreenBg.png"));
        bgSprite = new Sprite(bg);
        bgSprite.setAlpha(alpha);
        bgSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        skin = new Skin(Gdx.files.internal("skin/vhs/skin/vhs-ui.json"));
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        contBtn = new TextButton(">Press to continue...<", skin);
        contBtn.setColor(Color.BLACK);
        contBtn.setPosition(Gdx.graphics.getWidth() / 2 - contBtn.getWidth() / 2, contBtn.getHeight() + 10);
        //Adds listener to contBtn
        contBtn.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Continue...");
                dispose();
                Gdx.gl.glClearColor(255, 255, 255, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Level3IntroScreen(Level2WinScreen.this.game));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        stage.addActor(contBtn);
    }

    /**
     * The render method draws the button and the image background.
     *
     * @param delta The current frame
     */
    @Override
    public void render(float delta) {
        //Draw bgSprite
        batch.begin();
        bgSprite.draw(batch);
        batch.end();
        //Draw button
        stage.draw();
    }

    /**
     * This method disposes of objects created in this class to free up memory.
     */
    @Override
    public void dispose() {
        batch.dispose();
        bg.dispose();
        skin.dispose();
        stage.draw();
        game.dispose();
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
