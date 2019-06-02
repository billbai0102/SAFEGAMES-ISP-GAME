package com.safety4kids.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.safety4kids.game.Safety4Kids;

/**
 * This is class represents the second level of the game.
 *
 * @version 3 2019-05-30
 * @author Bill Bai, Erfan Yeganehfar
 * Ms. Krasteva
 *
 * Modifications:
 * 3.1 Bill Bai: (2019-05-30) Added the basics for the game such as the camera, viewports, hud, and renderer -- 2hrs
 */
public class Level2Screen implements Screen {
    private Safety4Kids game;
    private SpriteBatch batch;

    private Texture bg;

    private TextureAtlas player;
    private Animation<TextureRegion> animation;
    private float timePassed = 0;

    public Level2Screen(Safety4Kids game) {
        this.game = game;
        batch = new SpriteBatch();
        bg = new Texture("core/assets/Level2Background.png");
        bg.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);


        player = new TextureAtlas(Gdx.files.internal("core/assets/Lv2Assets/Lv2Sprites.atlas"));
        animation = new Animation<TextureRegion>(1/17f,player.getRegions());


    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(bg,0,0,900,900);
        timePassed += Gdx.graphics.getDeltaTime();
        batch.draw(animation.getKeyFrame(timePassed, true),500,150);
        batch.end();
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

    }
}
