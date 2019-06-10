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
 * This class is the win screen for level 3.
 * <br>
 * Background of image borrowed from <a href="https://imgur.com/gallery/6hSG6">source site</a> (2017-09-10) by artist: Spintronic
 * <br>
 *
 * <h2>Course info:</h2>
 * ICS4U with V. Krasteva
 *
 * @author Bill Bai, Erfan Yeganehfar
 * @version 1.2 06/09/19
 */
public class Level3WinScreen implements Screen {

    private SpriteBatch batch;
    private Safety4Kids game;
    private Texture bg;
    private Sprite bgSprite;

    private float alpha = 1;

    private Skin skin;
    private Stage stage;
    private TextButton contBtn;

    public Level3WinScreen(Safety4Kids game) {
        this.game = game;
        batch = new SpriteBatch();
        bg = new Texture(Gdx.files.internal("Lv3Assets/Lv3Win.png"));
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
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(Level3WinScreen.this.game));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        stage.addActor(contBtn);
    }

    /**
     * This method renders the screen. It causes the image to fade in, then draws a button that leads to MainMenu.
     *
     * @param delta The current frame.
     */
    @Override
    public void render(float delta) {
        batch.begin();
        bgSprite.draw(batch); //Draw bgSprite
        batch.end();

        //Draws button after alpha is greater than .15
        if (alpha > 0.15) {
            stage.draw();
        }
    }

    /**
     * This method disposes of objects created in this class. This frees up memory.
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
