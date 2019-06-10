package com.safety4kids.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.safety4kids.game.Utils.SplashScreenLogo;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates an instance of SplashScreenLogo and alter's it's alpha and x-position to draw a splash screen.
 *
 * <h2>Course info:</h2>
 * ICS4U with V. Krasteva
 *
 * @author Bill Bai, Erfan Yeganehfar
 * @version 1.4 06/05/19
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

    /**
     * Instance of SplashScreenLogo to be drawn onto screen.
     */
    private SplashScreenLogo logo;

    /**
     * This variable is type SpriteBatch. It holds a batch of sprites to be drawn on screen.
     */
    private SpriteBatch batch;

    /**
     * This is the TextureAtlas for the second level. It is loaded in the splash screen to save time
     * when switching to level 2.
     */
    private static TextureAtlas playerTexture = new TextureAtlas(Gdx.files.internal("Lv2Assets/Lv2Sprites.atlas"));
    /**
     * This is the questions List for the second level. It is loaded in the splash screen to save time
     * when switching to level 2.
     */
    private static List<String> questions = new ArrayList<String>();
    /**
     * This is the answers List for the second level. It is loaded in the splash screen to save time
     * when switching to level 2.
     */
    private static List<ArrayList<String>> answers = new ArrayList<ArrayList<String>>(20);
    /**
     * This is the questionHelp List for the second level. It is loaded in the splash screen to save time
     * when switching to level 2.
     */
    private static List<String> questionHelp = new ArrayList<String>();

    /**
     * This is the constructor. It initiates the global variables and loads level2 questions, which saves time
     * when level2 is loading.
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

    /**
     * This is the render method. It draws the logo on the screen while shifting it's alpha and x-location.
     *
     * @param delta The current frame.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Draws logo
        batch.begin();
        logo.draw(batch);
        batch.end();

        //Controls when to shift alpha, location, and change to menu screen.
        if (logo.getX() < Gdx.graphics.getWidth() / 2 - 200) {
            logo.moveRight(Gdx.graphics.getDeltaTime());
        } else if (logo.getX() > Gdx.graphics.getWidth() / 2 - 200 && logo.getAlpha() >= 0) {
            logo.fade();
        } else {
            game.setScreen(new MainMenu(game));
            dispose();
        }

    }

    /**
     * Returns the player TextureAtlas.
     *
     * @return playerTexture which is a texture atlas for the player animation.
     */
    public static TextureAtlas getPlayerTexture() {
        return playerTexture;
    }

    /**
     * Returns the questions List.
     *
     * @return questions List which holds questions.
     */
    public static List<String> getQuestions() {
        return questions;
    }

    /**
     * Returns the answers List.
     *
     * @return answers List which holds answers to questions.
     * */
    public static List<ArrayList<String>> getAnswers() {
        return answers;
    }

    /**
     * Returns questionHelp List.
     *
     * @return questionHelp List which carries question's tips.
     */
    public static List<String> getQuestionHelp() {
        return questionHelp;
    }

    /**
     * This method loads the second level Lists: <i>questions</i>, <i>answers</i>, and <i>questionHelp</i>. It initializes
     * those Lists, so that they can be returned. It implements <b>FileIO</b>. It reads from two files,
     * stores the Strings into it's respected Lists, then uses a sequential search to find the answer of each question.
     */
    private void loadLevel2Questions() {
        try {
            //The first file to be loaded
            FileHandle file = Gdx.files.internal("Lv2Assets/Level2Questions.txt");
            BufferedReader br = new BufferedReader(file.reader());
            //Loads txt file into questions and answers List
            for (int x = 0; x < 20; x++) {
                ArrayList<String> txtAnswer = new ArrayList<String>();
                questions.add(br.readLine());
                for (int y = 0; y < 4; y++) {
                    txtAnswer.add(y, br.readLine());
                }
                answers.add(x, txtAnswer);
            }
            //Second file to be loaded
            FileHandle file2 = Gdx.files.internal("Lv2Assets/Level2QuestionHelp.txt");
            br = new BufferedReader(file2.reader());
            //Loads txt file into questionHelp List.
            for (int x = 0; x < 20; x++) {
                questionHelp.add(br.readLine());
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method returns the SpriteBatch of this class.
     *
     * @return batch.
     */
    public SpriteBatch getBatch() {
        return batch;
    }

    /**
     * This method disposes of objects created in the class to save memory.
     */
    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
        logo.dispose();
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
