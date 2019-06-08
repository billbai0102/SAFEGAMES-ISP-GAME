package com.safety4kids.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.safety4kids.game.Safety4Kids;

//TODO remove supresswarnings
@SuppressWarnings("Duplicates")
public class Level1IntroScreen implements Screen {

    private SpriteBatch batch;
    private Safety4Kids game;
    private Texture bg;
    private Sprite bgSprite;

    private float alpha = 0;
    private boolean fadeIn = true;

    public Level1IntroScreen(Safety4Kids game) {
        batch = new SpriteBatch();
        this.game = game;
        bg = new Texture(Gdx.files.internal("Lv1Intro.png"));
        bgSprite = new Sprite(bg);
        bgSprite.setAlpha(alpha);
        bgSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.begin();
        bgSprite.draw(batch);
        batch.end();

        if (fadeIn) {
            alpha += (1f / 60f) / 7;
            if (alpha >= 1) {
                fadeIn = false;
                System.out.println("done");
            }
        }

        bgSprite.setAlpha(alpha);
        if (alpha > 0.4) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
                dispose();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Level1Screen(new Safety4Kids()));
            }
        }
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
        batch.dispose();
        game.dispose();
        bg.dispose();
    }
}
