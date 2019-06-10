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
 * This class is the instructions screen. It displays an image with instructions on how the game runs.
 * <br>
 *
 * <h2>Course info:</h2>
 * ICS4U with V. Krasteva
 *
 * @author Bill Bai, Erfan Yeganehfar
 * @version 3.5 06/07/19
 */
public class Instructions implements Screen {

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
    private float alpha = 0;
    /**
     * boolean value to dictate whether the sprite should fade in or not.
     */
    private boolean fadeIn = true;

    /**
     * This is the constructor. It initializes variables, and sets the position and adds a listener to contBtn.
     *
     * @param game The game to be drawn onto
     */
    public Instructions(Safety4Kids game) {
        this.game = game;
        batch = new SpriteBatch();
        bg = new Texture(Gdx.files.internal("InstructionsImg.jpg"));
        bgSprite = new Sprite(bg);
        bgSprite.setAlpha(alpha);
        bgSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        skin = new Skin(Gdx.files.internal("skin/vhs/skin/vhs-ui.json"));
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        contBtn = new TextButton(">Back<", skin);
        contBtn.setColor(Color.BLACK);
        contBtn.setPosition(Gdx.graphics.getWidth() / 2 - contBtn.getWidth() / 2, contBtn.getHeight() + 10);
        //Adds listener to contBtn
        contBtn.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(Instructions.this.game));
                dispose();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        stage.addActor(contBtn);
    }

    /**
     * Renders the screen. It draws bgSprite and the button, and causes the bgSprite to fade in.
     *
     * @param delta The current frame.
     */
    @Override
    public void render(float delta) {
        //Draws bgSprite
        batch.begin();
        bgSprite.draw(batch);
        batch.end();

        //Fades in bgSprite by increasing it's alpha value.
        if (fadeIn) {
            alpha += (1f / 60f) / 7;
            //Stops fading in when alpha is 1 (max)
            if (alpha >= 1) {
                fadeIn = false;
            }
        }
        //Sets alpha value of bgSprite to alpha
        bgSprite.setAlpha(alpha);
        //Draws the button when alpha is atleast .15
        if(alpha > 0.15) {
            stage.draw();
        }
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
