package com.safety4kids.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.safety4kids.game.Utils.SplashScreenLogo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class serves as the splash screen to the program. It displays the company logo.
 *
 * @author Erfan Yeganehfar
 * @author Bill Bai
 * <p>
 * Ms. Krasteva
 * <p>
 * Modifications:
 * Bill Bai: (2019-05-28) Completed entire class. Time spent: 1 hour.
 * @version 3.0, 2019-05-28
 */
public class IntroAnimation implements Screen {
    /**
     * This is the stage that is being drawn on
     */
    private Stage stage;

    /**
     * This is the Game that is currently being used to draw on.
     */
    private Game game;

    private SplashScreenLogo logo;

    /**
     * This variable is type SpriteBatch. It holds a batch of sprites to be drawn on screen.
     */
    public SpriteBatch batch;

    /**
     * This is the constructor. It initiates the global variables.
     *
     * @param aGame The Game that is currently being drawn on.
     */
    public IntroAnimation(Game aGame) {
        super();
        game = aGame;
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();

        logo = new SplashScreenLogo();
        logo.setPosition(-400, Gdx.graphics.getHeight() / 2f - 200);

        loadLevel2Questions();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        logo.draw(batch);
        batch.end();

        game.setScreen(new MainMenu(game));

//        if(logo.getX() < Gdx.graphics.getWidth()/2 - 200) {
//            logo.moveRight(Gdx.graphics.getDeltaTime());
//        }else if (logo.getX() > Gdx.graphics.getWidth()/2 - 200 && logo.getAlpha() >= 0) {
//            logo.fade();
//        }else{
//            game.setScreen(new MainMenu(game));
//        }
    }

    static TextureAtlas playerTexture = new TextureAtlas(Gdx.files.internal("Lv2Assets/Lv2Sprites.atlas"));

    public static TextureAtlas getPlayerTexture() {
        return playerTexture;
    }

    static List<String> questions = new ArrayList<String>();
    static List<ArrayList<String>> answers = new ArrayList<ArrayList<String>>(20);
    static List<String> questionHelp = new ArrayList<String>();

    public static List<String> getQuestions() {
        return questions;
    }

    public static List<ArrayList<String>> getAnswers() {
        return answers;
    }

    public static List<String> getQuestionHelp() {
        return questionHelp;
    }

    public void loadLevel2Questions() {
        try {
            //TODO change to allow jar work
           // InputStream in = getClass().getResourceAsStream("Level2Questions.txt");
           // BufferedReader br = new BufferedReader(new InputStreamReader(in));

           // BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("~/core/assets/Lv2Assets/Level2Questions.txt")));

            BufferedReader br = new BufferedReader(new FileReader("Lv2Assets/Level2Questions.txt"));
            for (int x = 0; x < 20; x++) {
                ArrayList<String> txtAnswer = new ArrayList<String>();
                questions.add(br.readLine());

                for (int y = 0; y < 4; y++) {
                    txtAnswer.add(y, br.readLine());
                }
                answers.add(x, txtAnswer);
            }

            br = new BufferedReader(new FileReader("Lv2Assets/Level2QuestionHelp.txt"));
            for (int x = 0; x < 20; x++) {
                questionHelp.add(br.readLine());
            }

            br.close();
            //in.close();
        } catch (IOException e) {
            System.out.println("file not found");
        }
    }

    public SpriteBatch getBatch(){
        return batch;
    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
        logo.dispose();
    }

    /**
     * This method has no implementation, since it is not used. It is only added since the class implements Screen.
     */
    @Override
    public void show() {
    }

    /**
     * This method has no implementation, since it is not used. It is only added since the class implements Screen.
     */
    @Override
    public void resize(int width, int height) {
    }

    /**
     * This method has no implementation, since it is not used. It is only added since the class implements Screen.
     */
    @Override
    public void pause() {

    }

    /**
     * This method has no implementation, since it is not used. It is only added since the class implements Screen.
     */
    @Override
    public void resume() {
    }

    /**
     * This method has no implementation, since it is not used. It is only added since the class implements Screen.
     */
    @Override
    public void hide() {
    }

}
