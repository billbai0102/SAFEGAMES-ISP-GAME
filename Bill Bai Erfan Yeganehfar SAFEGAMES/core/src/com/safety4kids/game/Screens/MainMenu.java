package com.safety4kids.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.safety4kids.game.Safety4Kids;

/**
 * This class displays the main menu which has buttons to allow the user to visit different stages such as Instructions,
 * first level, or exit.
 *
 * @version 3.0, 2019-05-28
 * @author Bill Bai, Erfan Yeganehfar
 * Ms. Krasteva
 *
 * Modifications:
 * 3.1 Bill Bai: (2019-05-30) Recreated background, rearranged buttons. -- 0.5hrs
 */
public class MainMenu implements Screen {

    SpriteBatch batch;
    Texture img;
    Texture backgroundImg;
    TextButton startBtn;
    Skin skin;
    TextButton instructionsBtn;
    TextButton exitBtn;

    Stage stage;
    Game game;

    public MainMenu(Game aGame) {
        this.game = aGame;
        stage = new Stage(new ScreenViewport());

        batch = new SpriteBatch();
        img = new Texture("core/assets/badlogic.jpg");
        backgroundImg = new Texture("core/assets/MainMenuBg.png");
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("core/skin/flat-earth-ui.json"));

        //start btn
        startBtn = new TextButton("START", skin);
        startBtn.setSize(100, 50);
        startBtn.setPosition(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 + 200);
        startBtn.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Starting level 1...");

                ((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen(new Safety4Kids()));
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
                System.out.println("Main Menu --> Instructions");
                ((Game)Gdx.app.getApplicationListener()).setScreen(new Level2Screen(new Safety4Kids()));
               // game.setScreen(new Instructions(game));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
    }

    @Override
    @SuppressWarnings("Duplicates")
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch = new SpriteBatch();
        batch.begin();
        batch.draw(backgroundImg, 0, 0);
        batch.end();

        stage.addActor(startBtn);
        stage.addActor(exitBtn);
        stage.addActor(instructionsBtn);
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
        stage.dispose();
    }
}
