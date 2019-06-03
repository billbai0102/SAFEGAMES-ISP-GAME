package com.safety4kids.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.safety4kids.game.Safety4Kids;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.safety4kids.game.Screens.GameScreen.GameState.RETURN;
import static com.safety4kids.game.Screens.GameScreen.GameState.RUN;


/**
 * This is class represents the second level of the game.
 *
 * @author Erfan Yeganehfar
 * @author Bill Bai
 *
 * Ms. Krasteva
 * <p>
 * Modifications:
 * 3.1 Bill Bai: (2019-05-30) Added the basics for the game such as the camera, viewports, hud, and renderer -- 2hrs
 * 3.2 Added File IO to read into game -- 30min
 * 3.3 Added Infinite map and animated sprites -- 1.5hrs
 * 3.4 Added shuffling questions and answers, and input to game - 2hrs
 * 3.5 Bill Bai: Added external library manually. Had to edit build.gradle and manually insert the JAR files. -- 1.5hrs
 * @version 6 2019-06-03
 */
@SuppressWarnings("Duplicates")
public class Level2Screen extends GameScreen implements Screen {
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

    private boolean win = false;

    private Input answer;

    private BitmapFont font;

    private int lives = 3;

    List<String> questions = new ArrayList<String>();
    List<String[]> answers = new ArrayList<String[]>();
    List<Integer> answerKey = new ArrayList<Integer>();
    int curQuestionIndex = 0;

    public Level2Screen(Safety4Kids game) {
        this.game = game;
        game.batch = new SpriteBatch();
        batch = game.batch;

        bg = new Texture("core/assets/Lv2Assets/Level2Background.png");
        bg.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.ClampToEdge);
        bgSprite = new Sprite(bg);

        player = new TextureAtlas(Gdx.files.internal("core/assets/Lv2Assets/Lv2Sprites.atlas"));
        playerAnimation = new Animation<TextureRegion>(1 / 12f, player.getRegions());

        warning = new TextureAtlas(Gdx.files.internal("core/assets/Lv2Assets/Lv2Warning.atlas"));
        warningAnimation = new Animation<TextureRegion>(1 / 5f, warning.getRegions());

        loadQuestions();

        //LOAD FONT
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("core/assets/Fonts/VCR_OSD_MONO_1.001.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 1;
        font = generator.generateFont(parameter);
        generator.dispose();
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            state = RETURN;

        switch (state) {
            case RUN:
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

                drawQuestions(batch);
                batch.end();


                if (win) {
                    System.out.println("Progressing to Level 3 ...");
                    //((Game)Gdx.app.getApplicationListener()).setScreen(new Level3Screen(game));
                }

                //    if win -->
                //    state = NEXT_LEVEL;
                break;
            case NEXT_LEVEL:
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Level3Screen(game));
                dispose();
                break;
            case RESUME:
                state = RUN;
                break;
            case RETURN:
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(game));
                dispose();
                break;
            default:
                break;
        }

    }

    public void shuffleAnswers() {
        String[] tempAnswers = answers.get(0);
        Random r = new Random();

        for (int x = 0; x < answers.size(); x++) {
            tempAnswers = answers.get(x);
            for (int y = tempAnswers.length - 1; y > 0; y--) {
                // swap
                int k = r.nextInt(y);
                String temp = tempAnswers[k];
                tempAnswers[k] = tempAnswers[y];
                tempAnswers[y] = temp;
            }
            for (int y = 0; y < tempAnswers.length; y++) {
                if (tempAnswers[y].charAt(0) == '4') {
                    answerKey.add(y + 1);
                    break;
                }
            }
            for (int y = 0; y < tempAnswers.length; y++) {
                System.out.println(tempAnswers[y]);
                System.out.println();
            }
            System.out.println("Answer at: " + answerKey.get(x));
            System.out.println();
        }
    }

    public void drawQuestions(SpriteBatch batch) {
        boolean correct = false;
        GlyphLayout questionGlyph = new GlyphLayout();
        String q = "Q".concat(String.valueOf(curQuestionIndex + 1).concat(questions.get(curQuestionIndex).substring(2)));
        questionGlyph.setText(font, q);

        GlyphLayout a1Layout = new GlyphLayout();
          //String a1 = "A)".concat(answers.get(curQuestionIndex).substring(1));
          //questionGlyph.setText(font, a1);

        font.draw(batch, questionGlyph, (Gdx.graphics.getWidth() - questionGlyph.width) / 2, Gdx.graphics.getHeight() - questionGlyph.height);
        // font.draw(batch, )

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            System.out.println("hi");
        }


        if (lives == 0) {
            System.out.println("You've lost!");
        }

    }

    public void loadQuestions() {
        String[] txtAnswer = new String[4];
        try {
            BufferedReader br = new BufferedReader(new FileReader("core/assets/Lv2Assets/Level2Questions.txt"));
            for (int x = 0; x < 20; x++) {
                questions.add(br.readLine());

                for (int y = 0; y < 4; y++) {
                    txtAnswer[y] = br.readLine();
                }
                answers.add(txtAnswer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        shuffleAnswers();
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


    @Override
    public void update(float dt) {

    }
}
