package com.safety4kids.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.safety4kids.game.Safety4Kids;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public TextureAtlas player;
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
    private List<ArrayList<String>> answers = new ArrayList<ArrayList<String>>();
    private List<Integer> answerKey = new ArrayList<Integer>();
    private List<String> questionHelp = new ArrayList<String>();
    private int curQuestionIndex;

    private boolean pauseProgram = false;

    private TextButton exitButton;
    private Skin skin;

    Stage stage;

    public Level2Screen(Safety4Kids game) {
        //Sets game to be drawn on
        this.game = game;
        //TODO Remove later

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skin/flat_earth/flat-earth-ui.json"));
        exitButton = new TextButton("MENU",skin);
        exitButton.setPosition(Gdx.graphics.getWidth() - exitButton.getWidth()-10, 10);
        exitButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Starting level 1...");
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu(new Safety4Kids()));
                dispose();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });


        //Instantiates SpriteBatch
        game.batch = new SpriteBatch();
        //Sets this sprite batch to game's sprite batch
        batch = game.batch;
        //Sets up ScreenViewport
        gamePort = new ScreenViewport();
        //Instantiates background
        bg = new Texture(Gdx.files.internal("Lv2Assets/Level2Background.png"));
        bg.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.ClampToEdge);
        bgSprite = new Sprite(bg);

        player = IntroAnimation.getPlayerTexture();
        playerAnimation = new Animation<TextureRegion>(1 / 12f, player.getRegions());

        warning = new TextureAtlas(Gdx.files.internal("Lv2Assets/Lv2Warning.atlas"));
        warningAnimation = new Animation<TextureRegion>(1 / 5f, warning.getRegions());

        loadQuestions();

        curQuestionIndex = (int) (Math.random() * questions.size() - 1);
        shuffleAnswers();

        //LOAD FONT
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/eight-bit-dragon.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 0.5f;
        font = generator.generateFont(parameter);
        //SECOND FONT
        parameter.size = 20;
        answerFont = generator.generateFont(parameter);

        //FONT FOR WRONG ANSWER
        wrongFont = generator.generateFont(parameter);
        wrongFont.setColor(Color.RED);

        //FONT FOR CORRECT ANSWER
        correctFont = generator.generateFont(parameter);
        correctFont.setColor(Color.GREEN);

        generator.dispose();
    }

    BitmapFont wrongFont;
    BitmapFont correctFont;

    public void loadQuestions() {
        for (int x = 0; x < 20; x++) {
            this.questions.add(IntroAnimation.getQuestions().get(x));
            this.answers.add(IntroAnimation.getAnswers().get(x));
            this.questionHelp.add(IntroAnimation.getQuestionHelp().get(x));
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            state = RETURN;

        if (questionNumber == 16) {
            state = NEXT_LEVEL;
        }


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

                stage.addActor(exitButton);
                stage.draw();
                break;
            case NEXT_LEVEL:
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Level3Screen(game));
                dispose();
                break;
            case RESUME: //when they lose (will change)

                float delay = 2.0f;

                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    if (pauseProgram) {
                        questions.remove(curQuestionIndex);
                        answers.remove(curQuestionIndex);
                        answerKey.remove(curQuestionIndex);
                        questionHelp.remove(curQuestionIndex);
                        curQuestionIndex = (int) (Math.random() * questions.size() - 1);
                        questionNumber++;
                    }
                    pauseProgram = false;

                    state = RUN;
                }

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
                if (pauseProgram)
                    displayQuestionHelp();

                GlyphLayout fontGlyph = new GlyphLayout();
                if (correct) {
                    fontGlyph.setText(correctFont, "CORRECT!");
                    //Draw font
                    answerFont.draw(batch, fontGlyph, (Gdx.graphics.getWidth() - fontGlyph.width) / 2, (Gdx.graphics.getHeight() - fontGlyph.height) / 2);
                } else {
                    fontGlyph.setText(wrongFont, "WRONG!");
                    //Draw font
                    answerFont.draw(batch, fontGlyph, (Gdx.graphics.getWidth() - fontGlyph.width) / 2, (Gdx.graphics.getHeight() - fontGlyph.height) / 2);
                }
                GlyphLayout continueGlyph = new GlyphLayout();
                continueGlyph.setText(answerFont, "Press space to continue...");
                answerFont.draw(batch, continueGlyph, (Gdx.graphics.getWidth() - continueGlyph.width) / 2, (Gdx.graphics.getHeight() - fontGlyph.height) / 2 - fontGlyph.height * 2);

                batch.end();

                stage.addActor(exitButton);
                stage.draw();

                break;
            case RETURN:
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(game));
                dispose();
                break;
            case LOSE:
                ((Game) Gdx.app.getApplicationListener()).setScreen(new LoseScreen(new Safety4Kids(), questionNumber));
                dispose();
                break;
            default:
                break;
        }

    }

    boolean correct;

    private void shuffleAnswers() {
        for (int x = 0; x < answers.size(); x++) {
            if(x == 4)
                x++;
            Collections.shuffle(answers.get(x));
        }


        for (int x = 0; x < answers.size(); x++) {
            Collections.shuffle(answers.get(x));
            for (int y = 0; y < answers.get(x).size(); y++) {
                if (answers.get(x).get(y).charAt(0) == '4') {
                    answerKey.add(y + 1);
                    break;
                }
            }
        }
    }

    private void drawQuestions(SpriteBatch batch) {
        int guess;

        if (questions.get(curQuestionIndex).contains("snow")) {

            GlyphLayout qGlyphPart1 = new GlyphLayout();
            GlyphLayout qGlyphPart2 = new GlyphLayout();

            String part1 = "Q" + questionNumber + ":" + questions.get(curQuestionIndex).substring(2, 44);
            String part2 = questions.get(curQuestionIndex).substring(44);

            qGlyphPart1.setText(font, part1);
            qGlyphPart2.setText(font, part2);

            font.draw(batch, qGlyphPart1, (Gdx.graphics.getWidth() - qGlyphPart1.width) / 2, Gdx.graphics.getHeight() - qGlyphPart1.height);
            font.draw(batch, qGlyphPart2, (Gdx.graphics.getWidth() - qGlyphPart2.width) / 2, Gdx.graphics.getHeight() - qGlyphPart2.height - qGlyphPart1.height - 20);

        } else if (questions.get(curQuestionIndex).length() > 35) {

            GlyphLayout qGlyphPart1 = new GlyphLayout();
            GlyphLayout qGlyphPart2 = new GlyphLayout();

            int firstWord = questions.get(curQuestionIndex).substring(2).indexOf(' ', 35);

            String part1 = "Q" + questionNumber + ":" + questions.get(curQuestionIndex).substring(2, firstWord + 3);
            String part2 = questions.get(curQuestionIndex).substring(firstWord + 3);

            qGlyphPart1.setText(font, part1);
            qGlyphPart2.setText(font, part2);

            font.draw(batch, qGlyphPart1, (Gdx.graphics.getWidth() - qGlyphPart1.width) / 2, Gdx.graphics.getHeight() - qGlyphPart1.height);
            font.draw(batch, qGlyphPart2, (Gdx.graphics.getWidth() - qGlyphPart2.width) / 2, Gdx.graphics.getHeight() - qGlyphPart2.height - qGlyphPart1.height - 20);

        } else {

            GlyphLayout questionGlyph = new GlyphLayout();

            String q = "Q".concat(String.valueOf(questionNumber).concat(":").concat(questions.get(curQuestionIndex).substring(2)));

            questionGlyph.setText(font, q);

            font.draw(batch, questionGlyph, (Gdx.graphics.getWidth() - questionGlyph.width) / 2, Gdx.graphics.getHeight() - questionGlyph.height);
        }

        ArrayList<String> toPrint;
        toPrint = answers.get(curQuestionIndex);

        //Format first answer
        GlyphLayout a1Glyph = new GlyphLayout();
        String a1 = "1)".concat(toPrint.get(0).substring(1));
        a1Glyph.setText(answerFont, a1);
        //Draw answer
        answerFont.draw(batch, a1Glyph, 15, Gdx.graphics.getHeight() - 150);

        //Format second answer
        GlyphLayout a2Glyph = new GlyphLayout();
        String a2 = "2)".concat(toPrint.get(1).substring(1));
        a2Glyph.setText(answerFont, a2);
        //Draw answer
        answerFont.draw(batch, a2Glyph, 15, Gdx.graphics.getHeight() - 200);

        //Format third answer
        GlyphLayout a3Glyph = new GlyphLayout();
        String a3 = "3)".concat(toPrint.get(2).substring(1));
        a3Glyph.setText(answerFont, a3);
        //Draw answer
        answerFont.draw(batch, a3Glyph, 15, Gdx.graphics.getHeight() - 250);

        //Format fourth answer
        GlyphLayout a4Glyph = new GlyphLayout();
        String a4 = "4)".concat(toPrint.get(3).substring(1));
        a4Glyph.setText(answerFont, a4);
        //Draw answer
        answerFont.draw(batch, a4Glyph, 15, Gdx.graphics.getHeight() - 300);

        if (pauseProgram == false) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
                guess = 1;
                if (guess == answerKey.get(curQuestionIndex)) {
                    win();
                } else {
                    lose();
                }
                pauseProgram = true;
            }


            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
                guess = 2;
                if (guess == answerKey.get(curQuestionIndex)) {
                    win();
                } else {
                    lose();
                }
                pauseProgram = true;
            }


            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
                guess = 3;
                if (guess == answerKey.get(curQuestionIndex)) {
                    win();
                } else {
                    lose();
                }
                pauseProgram = true;
            }


            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
                guess = 4;
                if (guess == answerKey.get(curQuestionIndex)) {
                    win();
                } else {
                    lose();
                }
                System.out.println(pauseProgram);
            }

        }

        if (lives == 0) {
            System.out.println("You've lost!");
            state = LOSE;
        }

    }

    private void win() {
        System.out.println("correct answer");
        if (lives < 4) {
            lives++;
            warningLocation -= (500f / 4f);
        }

        state = RESUME;
        correct = true;
        pauseProgram = true;
    }

    private void lose() {
        lives--;
        warningLocation += (500f / 4f);
        System.out.println("Lost life.");
        state = RESUME;

        correct = false;
        pauseProgram = true;
    }


    private void displayQuestionHelp() {
        if (questionHelp.get(curQuestionIndex).length() > 55) {
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

    /**
     * This method disposes the level's objects when it's done running, to save memory usage.
     */
    @Override
    public void dispose() {
        game.dispose();
        batch.dispose();
        warning.dispose();
        bg.dispose();
        warning.dispose();
        stage.dispose();
    }

    /**
     * This method is not used, and only implemented to
     */
    @Override
    public void update(float dt) {

    }
}
