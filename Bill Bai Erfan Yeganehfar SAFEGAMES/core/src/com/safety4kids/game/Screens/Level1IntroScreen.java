package com.safety4kids.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.safety4kids.game.Safety4Kids;

//TODO remove supresswarnings
@SuppressWarnings("Duplicates")
public class Level1IntroScreen implements Screen {

    private SpriteBatch batch;
    private Safety4Kids game;
    private Texture bg;
    private Sprite bgSprite;

    private TextButton contBtn;
    private Stage stage;
    private Skin skin;

    private float alpha = 0;
    private boolean fadeIn = true;

    public Level1IntroScreen(Safety4Kids game) {
        batch = new SpriteBatch();
        this.game = game;
        bg = new Texture(Gdx.files.internal("Lv1Intro.png"));
        bgSprite = new Sprite(bg);
        bgSprite.setAlpha(alpha);
        bgSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        skin = new Skin(Gdx.files.internal("skin/vhs/skin/vhs-ui.json"));
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        contBtn = new TextButton(">Press to continue...<",skin);
        contBtn.setPosition(Gdx.graphics.getWidth() / 2 - contBtn.getWidth()/2, contBtn.getHeight() + 10);
        contBtn.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Continue...");
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Level1Screen(Level1IntroScreen.this.game));
                dispose();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        stage.addActor(contBtn);
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
        batch.dispose();
        game.dispose();
        bg.dispose();
        stage.dispose();
        skin.dispose();
    }
}
