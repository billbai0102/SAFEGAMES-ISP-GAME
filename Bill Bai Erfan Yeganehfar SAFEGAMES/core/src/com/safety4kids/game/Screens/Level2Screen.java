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

import static com.safety4kids.game.Screens.GameScreen.GameState.*;


/**
 * This is class represents the second level of the game.
 *
 * @author Erfan Yeganehfar
 * @author Bill Bai
 * <p>
 * Ms. Krasteva
 * <p>
 * Modifications:
 * 3.1 Bill Bai: (2019-05-30) Added the basics for the game such as the camera, viewports, hud, and renderer -- 2hrs
 * 3.2 Added File IO to read into game -- 30min
 * 3.3 Added Infinite map and animated sprites -- 1.5hrs
 * 3.4 Added shuffling questions and answers, and input to game - 2hrs
 * 3.5 Bill Bai: Added external library manually. Had to edit build.gradle and manually insert the JAR files. -- 1.5hrs
 * 3.6 Bill Bai: Added algorithm to correctly display text without going over edges. -- 2hrs
 * @version 6 2019-06-03
 */
@SuppressWarnings("Duplicates")
public class Level2Screen extends GameScreen implements Screen {
    private Safety4Kids game;
    private SpriteBatch batch;

    private Texture bg;
    private Sprite bgSprite;
    private float scrollTime = 0;

    private TextureAtlas player;
    private Animation<TextureRegion> playerAnimation;
    private float timePassed = 0;

    private TextureAtlas warning;
    private Animation<TextureRegion> warningAnimation;
    float warningLocation = 0;

    private boolean win = false;

    private BitmapFont font;
    private BitmapFont answerFont;

    private int lives = 4;

    private int questionNumber = 1;

    private List<String> questions = new ArrayList<String>();
    private List<String[]> answers = new ArrayList<String[]>();
    private List<Integer> answerKey = new ArrayList<Integer>();
    private List<String> questionHelp = new ArrayList<String>();
    private int curQuestionIndex = 0;

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
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("core/assets/Fonts/eight-bit-dragon.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 0.5f;
        font = generator.generateFont(parameter);
        //SECOND FONT
        parameter.size = 20;
        answerFont = generator.generateFont(parameter);
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


                displayQuestionHelp(Gdx.graphics.getDeltaTime());
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

    private void shuffleAnswers() {
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
        }
    }

    String[] toPrint;

    private void drawQuestions(SpriteBatch batch) {
        int guess;

        toPrint = answers.get(curQuestionIndex);

        if (questions.get(curQuestionIndex).contains("snow")) {

            GlyphLayout qGlyphPart1 = new GlyphLayout();
            GlyphLayout qGlyphPart2 = new GlyphLayout();

            String part1 = "Q" + questionNumber + questions.get(curQuestionIndex).substring(2, 44);
            String part2 = questions.get(curQuestionIndex).substring(44);

            qGlyphPart1.setText(font, part1);
            qGlyphPart2.setText(font, part2);

            font.draw(batch, qGlyphPart1, (Gdx.graphics.getWidth() - qGlyphPart1.width) / 2, Gdx.graphics.getHeight() - qGlyphPart1.height);
            font.draw(batch, qGlyphPart2, (Gdx.graphics.getWidth() - qGlyphPart2.width) / 2, Gdx.graphics.getHeight() - qGlyphPart2.height - qGlyphPart1.height - 20);

        } else if (questions.get(curQuestionIndex).length() > 35) {

            GlyphLayout qGlyphPart1 = new GlyphLayout();
            GlyphLayout qGlyphPart2 = new GlyphLayout();

            int firstWord = questions.get(curQuestionIndex).substring(2).indexOf(' ', 35);

            String part1 = "Q" + questionNumber + questions.get(curQuestionIndex).substring(2, firstWord + 3);
            String part2 = questions.get(curQuestionIndex).substring(firstWord + 3);

            qGlyphPart1.setText(font, part1);
            qGlyphPart2.setText(font, part2);

            font.draw(batch, qGlyphPart1, (Gdx.graphics.getWidth() - qGlyphPart1.width) / 2, Gdx.graphics.getHeight() - qGlyphPart1.height);
            font.draw(batch, qGlyphPart2, (Gdx.graphics.getWidth() - qGlyphPart2.width) / 2, Gdx.graphics.getHeight() - qGlyphPart2.height - qGlyphPart1.height - 20);

        } else {

            GlyphLayout questionGlyph = new GlyphLayout();

            String q = "Q".concat(String.valueOf(questionNumber).concat(questions.get(curQuestionIndex).substring(2)));

            questionGlyph.setText(font, q);

            font.draw(batch, questionGlyph, (Gdx.graphics.getWidth() - questionGlyph.width) / 2, Gdx.graphics.getHeight() - questionGlyph.height);
        }

        //Format first answer
        //toPrint[0].substring(1)
        GlyphLayout a1Glyph = new GlyphLayout();
        String a1 = "A)".concat(toPrint[0].substring(1));
        a1Glyph.setText(answerFont, a1);
        //Draw answer
        answerFont.draw(batch, a1Glyph, 5, 750);

        //Format second answer
        GlyphLayout a2Glyph = new GlyphLayout();
        String a2 = "B)".concat(toPrint[1].substring(1));
        a2Glyph.setText(answerFont, a2);
        //Draw answer
        answerFont.draw(batch, a2Glyph, 5, 700);

        //Format third answer
        GlyphLayout a3Glyph = new GlyphLayout();
        String a3 = "C)".concat(toPrint[2].substring(1));
        a3Glyph.setText(answerFont, a3);
        //Draw answer
        answerFont.draw(batch, a3Glyph, 5, 650);

        //Format fourth answer
        GlyphLayout a4Glyph = new GlyphLayout();
        String a4 = "D)".concat(toPrint[3].substring(1));
        a4Glyph.setText(answerFont, a4);
        //Draw answer
        answerFont.draw(batch, a4Glyph, 5, 600);


        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            guess = 1;
            if (guess == answerKey.get(curQuestionIndex)) {
                System.out.println("nice");
            } else {
                lose();
            }
            inputPressed();
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            guess = 2;
            if (guess == answerKey.get(curQuestionIndex)) {
                System.out.println("nice");
            } else {
                lose();
            }
            inputPressed();
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            guess = 3;
            if (guess == answerKey.get(curQuestionIndex)) {
                System.out.println("nice");
            } else {
                lose();
            }
            inputPressed();
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
            guess = 4;
            if (guess == answerKey.get(curQuestionIndex)) {
                System.out.println("nice");
            } else {
                lose();
            }
            inputPressed();
        }


        if (questionNumber == 16) {
            state = NEXT_LEVEL;
        }


        if (lives == 0) {
            System.out.println("You've lost!");
            // state = RETURN;
        }

    }

    private void lose() {
        lives--;
        warningLocation += (500f / 4f) - 18f;
        System.out.println("Lost life.");
        //displayQuestionHelp();
    }

    private void inputPressed() {
        questions.remove(curQuestionIndex);
        answers.remove(curQuestionIndex);
        answerKey.remove(curQuestionIndex);
        questionHelp.remove(curQuestionIndex);
        curQuestionIndex = (int) (Math.random() * questions.size() - 1);
        questionNumber++;
    }

    private final float ANIMATION_LIMIT = 600;

    private void displayQuestionHelp(float delta) {
        int other = 0;
        other += delta;
        if (delta < ANIMATION_LIMIT)
            if (questionHelp.get(curQuestionIndex).length() > 35) {
                //Format question
                GlyphLayout qGlyphPart1 = new GlyphLayout();
                GlyphLayout qGlyphPart2 = new GlyphLayout();
                int firstWord = questionHelp.get(curQuestionIndex).substring(2).indexOf(' ', 35);

                String part1 = questionHelp.get(curQuestionIndex).substring(0, firstWord + 2);
                String part2 = questionHelp.get(curQuestionIndex).substring(firstWord + 2);

                qGlyphPart1.setText(answerFont, part1);
                qGlyphPart2.setText(answerFont, part2);

                answerFont.draw(batch, qGlyphPart1, (Gdx.graphics.getWidth() - qGlyphPart1.width) / 2, 120);
                answerFont.draw(batch, qGlyphPart2, (Gdx.graphics.getWidth() - qGlyphPart2.width) / 2, 50);
            } else {
                //Format question
                GlyphLayout glyphLayout = new GlyphLayout();
                String q = questionHelp.get(curQuestionIndex);
                glyphLayout.setText(answerFont, q);
                //Draw question
                answerFont.draw(batch, glyphLayout, (Gdx.graphics.getWidth() - glyphLayout.width) / 2, 120);
            }
    }

    private void loadQuestions() {
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
            br = new BufferedReader(new FileReader("core/assets/Lv2Assets/Level2QuestionHelp.txt"));
            for (int x = 0; x < 20; x++) {
                questionHelp.add(br.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Loaded " + questions.size() + " questions, and " + answers.size() + " answer sets.");

        curQuestionIndex = (int) (Math.random() * questions.size() - 1);
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
