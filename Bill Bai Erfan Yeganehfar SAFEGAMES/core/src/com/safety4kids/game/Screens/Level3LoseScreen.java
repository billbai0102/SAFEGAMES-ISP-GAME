package com.safety4kids.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.safety4kids.game.Safety4Kids;


/**
 * This class is the lose screen for level 3.
 * <br>
 * Background of image borrowed from <a href="https://invisiblelightnetwork.com/2014/10/15/porter-robinson-worlds-tour-visuals/">source site</a> (2017-09-10) by artist: Porter Robinson
 * <br>
 *
 * <h2>Course info:</h2>
 * ICS4U with V. Krasteva
 *
 * @author Bill Bai, Erfan Yeganehfar
 * @version 1.2 06/09/19
 */
public class Level3LoseScreen implements Screen {

    private SpriteBatch batch;
    private Stage stage;
    private Safety4Kids game;
    private Texture bg;

    private Viewport gamePort;
    private OrthographicCamera gamecam;
    private BitmapFont font;

    private TextButton menuBtn;
    private TextButton restartLvl;

    private Skin skin;

    public Level3LoseScreen(Safety4Kids game) {
        this.game = game;
        skin = new Skin(Gdx.files.internal("skin/vhs/skin/vhs-ui.json"));
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), gamecam);

        Gdx.input.setInputProcessor(stage);

        bg = new Texture(Gdx.files.internal("Lv3Assets/Lv3Lose.png"));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/eight-bit-dragon.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 0.5f;
        font = generator.generateFont(parameter);
        generator.dispose();

        menuBtn = new TextButton(">>Go to Main Menu<<", skin);
        restartLvl = new TextButton(">>Restart level 3<<", skin);

        menuBtn.setPosition(Gdx.graphics.getWidth() / 2 - menuBtn.getWidth()/2, Gdx.graphics.getHeight() / 2 + 50);
        menuBtn.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Back to menu");
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(Level3LoseScreen.this.game));
                dispose();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        restartLvl.setPosition(Gdx.graphics.getWidth() / 2 - restartLvl.getWidth()/2, Gdx.graphics.getHeight()/2 + 130);
        restartLvl.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Restart lv 2");
                dispose();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Level3Screen(Level3LoseScreen.this.game));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        stage.addActor(menuBtn);
        stage.addActor(restartLvl);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
        bg.dispose();
        font.dispose();
        skin.dispose();
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

