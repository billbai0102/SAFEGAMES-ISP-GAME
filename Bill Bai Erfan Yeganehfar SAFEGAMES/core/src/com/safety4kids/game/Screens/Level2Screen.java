package com.safety4kids.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.safety4kids.game.Safety4Kids;

/**
 * This is class represents the second level of the game.
 *
 * @author Bill Bai, Erfan Yeganehfar
 * Ms. Krasteva
 * <p>
 * Modifications:
 * 3.1 Bill Bai: (2019-05-30) Added the basics for the game such as the camera, viewports, hud, and renderer -- 2hrs
 * @version 3 2019-05-30
 */
public class Level2Screen implements Screen {
    private Safety4Kids game;
    private SpriteBatch batch;

    private Texture bg;
    private Sprite bgSprite;
    float scrollTime = 0;

    private TextureAtlas player;
    private Animation<TextureRegion> playerAnimation;
    private float timePassed = 0;

    private TextureAtlas warning;
    private Animation<TextureRegion> warningAnimation;
    float warningLocation = 0;

    public Level2Screen(Safety4Kids game) {
        this.game = game;
        game.batch = new SpriteBatch();
        batch = game.batch;
        bg = new Texture("core/assets/Lv2Assets/Level2Background.png");
        bg.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.ClampToEdge);
        bgSprite = new Sprite(bg);

        player = new TextureAtlas(Gdx.files.internal("core/assets/Lv2Assets/Lv2Sprites.atlas"));
        playerAnimation = new Animation<TextureRegion>(1 / 17f, player.getRegions());

        warning = new TextureAtlas(Gdx.files.internal("core/assets/Lv2Assets/Lv2Warning.atlas"));
        warningAnimation = new Animation<TextureRegion>(1 / 5f, warning.getRegions());
    }

    @Override
    public void render(float delta) {
        scrollTime += 0.0007f;
        if (scrollTime >= 1.0f)
            scrollTime = 0.0f;

        timePassed += Gdx.graphics.getDeltaTime();

        batch.begin();
        bgSprite.setU(scrollTime);
        bgSprite.setU2(scrollTime + 0.80f);
        bgSprite.draw(batch);
        batch.draw(playerAnimation.getKeyFrame(timePassed, true), 500, 150);
        batch.draw(warningAnimation.getKeyFrame(timePassed, true), warningLocation, 130);
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
        game.dispose();
        batch.dispose();
        bg.dispose();
        player.dispose();
        warning.dispose();
    }
}
