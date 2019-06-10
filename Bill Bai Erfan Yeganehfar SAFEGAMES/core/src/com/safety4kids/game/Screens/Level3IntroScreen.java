package com.safety4kids.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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
 * This class is the introductory screen for level 3.
 * <br>
 *
 * <h2>Course info:</h2>
 * ICS4U with V. Krasteva
 *
 * @author Bill Bai, Erfan Yeganehfar
 * @version 3.4 06/09/19
 */
public class Level3IntroScreen implements Screen {

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
     * @param game The game to be drawn onto.
     */
    public Level3IntroScreen(Safety4Kids game) {
        this.game = game;
        batch = new SpriteBatch();
        bg = new Texture(Gdx.files.internal("Lv3Assets/Lv3Intro.png"));
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
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Level3Screen(new Safety4Kids()));
                dispose();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        //Adds contBtn to the stage
        stage.addActor(contBtn);
    }

    /**
     * The render method draws the button and the image background.
     *
     * @param delta The current frame
     */
    @Override
    public void render(float delta) {
        batch.begin();
        bgSprite.draw(batch);
        batch.end();
        stage.draw();
    }

    /**
     * This method disposes of objects created in this class to free up memory.
     */
    @Override
    public void dispose() {
        batch.dispose();
        game.dispose();
        bg.dispose();
        stage.dispose();
        skin.dispose();
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
