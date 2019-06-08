package com.safety4kids.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
 * This class displays the main menu which has buttons to allow the user to visit different stages such as Instructions,
 * first level, or exit.
 *
 * @version 3.0, 2019-05-28
 * @author Erfan Yeganehfar
 * @author Bill Bai
 * Ms. Krasteva
 *
 * Modifications:
 * 3.1 Bill Bai: (2019-05-30) Recreated background, rearranged and restyled buttons. -- 0.5hrs
 */
public class MainMenu implements Screen {

    public static SpriteBatch batch;
    private Texture backgroundImg;
    private TextButton startBtn;
    private Skin skin;
    private TextButton instructionsBtn;
    private TextButton exitBtn;
    private Viewport gamePort;
    private OrthographicCamera gamecam;

    private Stage stage;
    private Game game;

    public MainMenu(Game aGame) {
        this.game = aGame;
        stage = new Stage(new ScreenViewport());
        gamecam = new OrthographicCamera();

        gamePort = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), gamecam);

        batch = new SpriteBatch();
        backgroundImg = new Texture(Gdx.files.internal("MainMenuBg.png"));
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skin/flat_earth/flat-earth-ui.json"));
        //start btn
        startBtn = new TextButton("START", skin);
        startBtn.setSize(100, 50);
        startBtn.setPosition(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 + 200);
        startBtn.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Starting level 1...");

                ((Game)Gdx.app.getApplicationListener()).setScreen(new Level1IntroScreen(new Safety4Kids()));
                dispose();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        //exit btn
        exitBtn = new TextButton("EXIT", skin);
        exitBtn.setSize(100, 50);
        exitBtn.setPosition(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 + 50);
        exitBtn.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Lights out!");
                Gdx.app.exit();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        //instructions btn
        instructionsBtn = new TextButton("HELP", skin);
        instructionsBtn.setSize(100, 50);
        instructionsBtn.setPosition(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 + 125);
        instructionsBtn.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Starting level 2...");

                ((Game)Gdx.app.getApplicationListener()).setScreen(new Level2IntroScreen(new Safety4Kids()));
                dispose();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        stage.addActor(startBtn);
        stage.addActor(exitBtn);
        stage.addActor(instructionsBtn);

    }

    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(backgroundImg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.draw();
    }


    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);

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
        skin.dispose();
        stage.dispose();
        backgroundImg.dispose();
        batch.dispose();
    }
}
