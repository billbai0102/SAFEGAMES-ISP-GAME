package com.safety4kids.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.safety4kids.game.Safety4Kids;


//created june 6 2hrs
public class Level2IntroScreen implements Screen {

    private SpriteBatch batch;
    private Safety4Kids game;
    private Texture bg;
    private Sprite bgSprite;

    private float alpha = 0;
    private boolean fadeIn = true;
    private boolean fadeOut = false;

    public Level2IntroScreen(Safety4Kids game){
        this.game = game;
        batch = new SpriteBatch();
        bg = new Texture(Gdx.files.internal("Lv2Assets/Lv2Intro.png"));
        bgSprite = new Sprite(bg);
        bgSprite.setAlpha(alpha);
    }

    @Override
    public void render(float delta) {
        batch.begin();
        bgSprite.draw(batch);
        batch.end();

        if(fadeIn){
            alpha += (1f / 60f) / 3;
            if(alpha >= 1) {
                fadeIn = false;
                System.out.println("done");
            }
        }

//        if(fadeOut){
//            alpha -= (1f / 60f) / 3;
//            bgSprite.setAlpha(alpha);
//            if(alpha <= 0.01){
//                ((Game)Gdx.app.getApplicationListener()).setScreen(new Level2Screen(new Safety4Kids()));
//                dispose();
//            }
//        }

        bgSprite.setAlpha(alpha);

        if(Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)){
            ((Game) Gdx.app.getApplicationListener()).setScreen(new Level2Screen(new Safety4Kids()));
            dispose();
        }
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

    @Override
    public void dispose() {
        batch.dispose();
        bg.dispose();
    }
}
