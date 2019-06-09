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


//created june 6 2hrs
public class Level2IntroScreen implements Screen {

    private SpriteBatch batch;
    private Safety4Kids game;
    private Texture bg;
    private Sprite bgSprite;

    private float alpha = 0;
    private boolean fadeIn = true;
    private boolean fadeOut = false;

    private Skin skin;
    private Stage stage;
    private TextButton contBtn;

    public Level2IntroScreen(Safety4Kids game) {
        this.game = game;
        batch = new SpriteBatch();
        bg = new Texture(Gdx.files.internal("Lv2Assets/Lv2Intro.png"));
        bgSprite = new Sprite(bg);
        bgSprite.setAlpha(alpha);
        bgSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        skin = new Skin(Gdx.files.internal("skin/vhs/skin/vhs-ui.json"));
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        contBtn = new TextButton(">Press to continue...<",skin);
        contBtn.setColor(Color.BLACK);
        contBtn.setPosition(Gdx.graphics.getWidth() / 2 - contBtn.getWidth()/2, contBtn.getHeight() + 10);
        contBtn.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Continue...");
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Level2Screen(Level2IntroScreen.this.game));
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
    public void render(float delta) {
        batch.begin();
        bgSprite.draw(batch);
        batch.end();

        if (fadeIn) {
            alpha += (1f / 60f) / 5;
            if (alpha >= 1) {
                fadeIn = false;
                System.out.println("done");
            }
        }

        bgSprite.setAlpha(alpha);

        stage.draw();
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
        game.dispose();
        bg.dispose();
        stage.dispose();
        skin.dispose();
    }
}
