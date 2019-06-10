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
 * This class plays the second level. It is essentially a safety quiz where the user has 4 lives. They lose lives for getting
 * incorrect answers, and gains lives for getting the correct answer.
 * <br>
 * Background borrowed from <a href="https://www.pinterest.ca/pin/728527677197576963/">source site</a> (2017-09-10) by artist: Jatashankar Kumar
 * <br>
 *
 * <h2>Course info:</h2>
 * ICS4U with V. Krasteva
 *
 * @author Bill Bai, Erfan Yeganehfar
 * @version 6.5 06/09/19
 */
public class Level2Screen extends GameScreen implements Screen {
    /**
     * Game to be drawn onto.
     */
    private Safety4Kids game;
    /**
     * SpriteBatch to be drawn onto.
     */
    private SpriteBatch batch;
    /**
     * Background of map.
     */
    private Texture bg;
    /**
     * bg converted into a Sprite, so that it can be altered.
     */
    private Sprite bgSprite;
    /**
     * Scroll time of the map. Controls animation speed.
     */
    private float scrollTime = 0;
    /**
     * TextureAtlas that contains animation images for the player.
     */
    public TextureAtlas player;
    /**
     * Aniamtion of the player object.
     */
    private Animation<TextureRegion> playerAnimation;
    /**
     * Float that counts time passed since run.
     */
    private float timePassed = 0;
    /**
     * TextureAtlas for warning label.
     */
    private TextureAtlas warning;
    /**
     * Animation for warning object.
     */
    private Animation<TextureRegion> warningAnimation;
    /**
     * Float that controls the warning label's location.
     */
    float warningLocation = 0;
    /**
     * Default font.
     */
    private BitmapFont font;
    /**
     * Font for answers.
     */
    private BitmapFont answerFont;
    /**
     * Font for wrong answers.
     */
    private BitmapFont wrongFont;
    /**
     * Font for correct answers.
     */
    private BitmapFont correctFont;
    /**
     * Glyph layout for the question.
     */
    private GlyphLayout fontGlyph = new GlyphLayout();
    /**
     * GlyphLayout for the "continue" text.
     */
    private GlyphLayout continueGlyph = new GlyphLayout();
    /**
     * GlyphLayout for first part of the question.
     */
    private GlyphLayout qGlyphPart1 = new GlyphLayout();
    /**
     * GlyphLayout for second part of the question.
     */
    private GlyphLayout qGlyphPart2 = new GlyphLayout();
    /**
     * GlyphLayout for the question if it's a single line.
     */
    private GlyphLayout questionGlyph = new GlyphLayout();
    /**
     * GlyphLayout for first answer.
     */
    private GlyphLayout a1Glyph = new GlyphLayout();
    /**
     * GlyphLayout for second answer.
     */
    private GlyphLayout a2Glyph = new GlyphLayout();
    /**
     * GlyphLayout for third answer.
     */
    private GlyphLayout a3Glyph = new GlyphLayout();
    /**
     * GlyphLayout for fourth answer.
     */
    private GlyphLayout a4Glyph = new GlyphLayout();
    /**
     * GlyphLayout for question help.
     */
    private GlyphLayout glyphLayout = new GlyphLayout();
    /**
     * User's lives.
     */
    private int lives = 4;
    /**
     * Current question number.
     */
    private int questionNumber = 1;
    /**
     * Questions of the quiz
     */
    private List<String> questions = new ArrayList<String>();
    /**
     * Answers that correspond with the questions.
     */
    private List<ArrayList<String>> answers = new ArrayList<ArrayList<String>>();
    /**
     * Answer key that stores the correct answer numbers.
     */
    private List<Integer> answerKey = new ArrayList<Integer>();
    /**
     * List that stores answer help.
     */
    private List<String> questionHelp = new ArrayList<String>();
    /**
     * Current question index used for randomization.
     */
    private int curQuestionIndex;
    /**
     * True when program needs to be paused.
     */
    private boolean pauseProgram = false;
    /**
     * TextButton to return to main menu.
     */
    private TextButton exitButton;
    /**
     * Skin for button.
     */
    private Skin skin;
    /**
     * Stage to draw buttons on
     */
    Stage stage;
    /**
     * Boolean that is true if user gets question correct.
     */
    private boolean correct;

    /**
     * This is the constructor.
     *
     * @param game The game to be drawn onto.
     */
    public Level2Screen(Safety4Kids game) {
        //Sets game to be drawn on
        this.game = game;

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skin/flat_earth/flat-earth-ui.json"));
        exitButton = new TextButton("MENU", skin);
        exitButton.setPosition(Gdx.graphics.getWidth() - exitButton.getWidth() - 10, 10);
        exitButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                curQuestionIndex--;
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(Level2Screen.this.game));
                dispose();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });


        //Instantiates SpriteBatch
        //Sets this sprite batch to game's sprite batch
        batch = new SpriteBatch();
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

        stage.addActor(exitButton);

    }

    /**
     * This method loads all questions, answers, and questionhelp into the respective lists,
     */
    public void loadQuestions() {
        for (int x = 0; x < 20; x++) {
            this.questions.add(IntroAnimation.getQuestions().get(x));
            this.answers.add(IntroAnimation.getAnswers().get(x));
            this.questionHelp.add(IntroAnimation.getQuestionHelp().get(x));
        }
    }

    /**
     * Thisis the render method. It uses a switch statement to decide which part of the program to run.
     * <br> <b>Case RUN:</b> Runs the quiz normally.
     * <br> <b>Case NEXT_LEVEL:</b> Calls dispose() and sets scene to next level.
     * <br> <b>Case RESUME:</b> Pauses the program to display question help. Then resumes the program when user presses
     * space.
     * <br> <b>Case LOSE:</b> Sets scene to lose scene.
     * <br> <b>Case RETURN:</b> Sets scene to main menu
     *
     * @param delta The target frame rate minus the time taken to complete this frame is called the delta time, used to
     *              keep the frames consistant across platforms.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //Sets state to next level when user wins
        if (questionNumber == 16) {
            state = NEXT_LEVEL;
        }
        switch (state) {
            case RUN:
                //Dictates how fast background scrolls
                scrollTime += 0.0007f;
                if (scrollTime >= 1.0f)
                    scrollTime = 0.0f;
                timePassed += Gdx.graphics.getDeltaTime();
                //Draws background and sprites with animations
                batch.begin();
                bgSprite.setU(scrollTime);
                bgSprite.setU2(scrollTime + 0.80f);
                bgSprite.draw(batch);
                batch.draw(playerAnimation.getKeyFrame(timePassed, true), 500, 150);
                batch.draw(warningAnimation.getKeyFrame(timePassed, true), warningLocation, 130);
                drawQuestions(batch); //Draws questions on screen
                batch.end();
                //Draws buttons
                stage.draw();
                break;
            case NEXT_LEVEL:
                //Switches to lv3
                dispose();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Level2WinScreen(game));
                break;
            case RESUME:
                //Displays question help until space is pressed.
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

                //Draws scrolling background and sprites
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
                //Displays "Correct" or "False" based on whether they got question correct or not.
                if (correct) {
                    fontGlyph.setText(correctFont, "CORRECT!");
                    //Draw font
                    answerFont.draw(batch, fontGlyph, (Gdx.graphics.getWidth() - fontGlyph.width) / 2, (Gdx.graphics.getHeight() - fontGlyph.height) / 2);
                } else {
                    fontGlyph.setText(wrongFont, "WRONG!");
                    //Draw font
                    answerFont.draw(batch, fontGlyph, (Gdx.graphics.getWidth() - fontGlyph.width) / 2, (Gdx.graphics.getHeight() - fontGlyph.height) / 2);
                }
                continueGlyph.setText(answerFont, "Press space to continue...");
                answerFont.draw(batch, continueGlyph, (Gdx.graphics.getWidth() - continueGlyph.width) / 2, (Gdx.graphics.getHeight() - fontGlyph.height) / 2 - fontGlyph.height * 2);
                batch.end();
                //Draws buttons onto screen
                stage.draw();
                break;
            case RETURN:
                //Sets scene to MainMenu
                dispose();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(game));
                break;
            case LOSE:
                //Sets scene to Lose screen
                dispose();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Level2LoseScreen(new Safety4Kids(), questionNumber));
                break;
            default:
                break;
        }

    }

    /**
     * This method uses <b>SEARCHING</b>. It implements a sequential search to find the correct answers, after
     * being shuffled.
     */
    private void shuffleAnswers() {
        //Shuffles answers
        for (int x = 0; x < answers.size(); x++) {
            Collections.shuffle(answers.get(x));
        }
        //Finds correct answer using sequential search and stores in answerKey
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

    /**
     * This method draws questions onto the screen.
     *
     * @param batch The SpriteBatch to be drawn on.
     */
    private void drawQuestions(SpriteBatch batch) {
        //User's guess
        int guess;
        //This if statement is an algorithm to fit all characters onto screen.
        if (questions.get(curQuestionIndex).contains("snow")) {
            String part1 = "Q" + questionNumber + ":" + questions.get(curQuestionIndex).substring(2, 44);
            String part2 = questions.get(curQuestionIndex).substring(44);

            qGlyphPart1.setText(font, part1);
            qGlyphPart2.setText(font, part2);

            font.draw(batch, qGlyphPart1, (Gdx.graphics.getWidth() - qGlyphPart1.width) / 2, Gdx.graphics.getHeight() - qGlyphPart1.height);
            font.draw(batch, qGlyphPart2, (Gdx.graphics.getWidth() - qGlyphPart2.width) / 2, Gdx.graphics.getHeight() - qGlyphPart2.height - qGlyphPart1.height - 20);

        } else if (questions.get(curQuestionIndex).length() > 35) {
            int firstWord = questions.get(curQuestionIndex).substring(2).indexOf(' ', 35);

            String part1 = "Q" + questionNumber + ":" + questions.get(curQuestionIndex).substring(2, firstWord + 3);
            String part2 = questions.get(curQuestionIndex).substring(firstWord + 3);

            qGlyphPart1.setText(font, part1);
            qGlyphPart2.setText(font, part2);

            font.draw(batch, qGlyphPart1, (Gdx.graphics.getWidth() - qGlyphPart1.width) / 2, Gdx.graphics.getHeight() - qGlyphPart1.height);
            font.draw(batch, qGlyphPart2, (Gdx.graphics.getWidth() - qGlyphPart2.width) / 2, Gdx.graphics.getHeight() - qGlyphPart2.height - qGlyphPart1.height - 20);
        } else {
            String q = "Q".concat(String.valueOf(questionNumber).concat(":").concat(questions.get(curQuestionIndex).substring(2)));

            questionGlyph.setText(font, q);

            font.draw(batch, questionGlyph, (Gdx.graphics.getWidth() - questionGlyph.width) / 2, Gdx.graphics.getHeight() - questionGlyph.height);
        }
        //List of answers to be printed on screen.
        ArrayList<String> toPrint;
        toPrint = answers.get(curQuestionIndex);

        //Format first answer
        String a1 = "1)".concat(toPrint.get(0).substring(1));
        a1Glyph.setText(answerFont, a1);
        //Draw answer
        answerFont.draw(batch, a1Glyph, 15, Gdx.graphics.getHeight() - 150);

        //Format second answer
        String a2 = "2)".concat(toPrint.get(1).substring(1));
        a2Glyph.setText(answerFont, a2);
        //Draw answer
        answerFont.draw(batch, a2Glyph, 15, Gdx.graphics.getHeight() - 200);

        //Format third answer
        String a3 = "3)".concat(toPrint.get(2).substring(1));
        a3Glyph.setText(answerFont, a3);
        //Draw answer
        answerFont.draw(batch, a3Glyph, 15, Gdx.graphics.getHeight() - 250);

        //Format fourth answer
        String a4 = "4)".concat(toPrint.get(3).substring(1));
        a4Glyph.setText(answerFont, a4);
        //Draw answer
        answerFont.draw(batch, a4Glyph, 15, Gdx.graphics.getHeight() - 300);

        //If program isn't paused
        if (pauseProgram == false) {
            //User input to get user's guess, and checks whether it is correct or not.
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
        //Sets state to LOSE if lives is 0
        if (lives == 0) {
            state = LOSE;
        }

    }

    /**
     * This method is called when the user gets the correct answer. It adds a life and draws CORRECT onto the screen
     */
    private void win() {
        if (lives < 4) {
            lives++;
            warningLocation -= (500f / 4f);
        }

        state = RESUME;
        correct = true;
        pauseProgram = true;
    }

    /**
     * This method is called when the user gets the wrong answer. It decreases a life and draws WRONG onto the screen
     */
    private void lose() {
        lives--;
        warningLocation += (500f / 4f);
        System.out.println("Lost life.");
        state = RESUME;

        correct = false;
        pauseProgram = true;
    }

    /**
     * This method is only called during pause. It display's the question's tips.
     */
    private void displayQuestionHelp() {
        if (questionHelp.get(curQuestionIndex).length() > 55) {
            //Format question
            int firstWord = questionHelp.get(curQuestionIndex).substring(2).indexOf(' ', 35);

            String part1 = questionHelp.get(curQuestionIndex).substring(0, firstWord + 2);
            String part2 = questionHelp.get(curQuestionIndex).substring(firstWord + 2);

            qGlyphPart1.setText(answerFont, part1);
            qGlyphPart2.setText(answerFont, part2);

            answerFont.draw(batch, qGlyphPart1, (Gdx.graphics.getWidth() - qGlyphPart1.width) / 2, 120);
            answerFont.draw(batch, qGlyphPart2, (Gdx.graphics.getWidth() - qGlyphPart2.width) / 2, 50);
        } else {
            //Format question
            String q = questionHelp.get(curQuestionIndex);
            glyphLayout.setText(answerFont, q);
            //Draw question
            answerFont.draw(batch, glyphLayout, (Gdx.graphics.getWidth() - glyphLayout.width) / 2, 120);
        }
    }

    /**
     * This method disposes the level's objects when it's done running, to save memory usage.
     */
    @Override
    public void dispose() {
        game.dispose();
        warning.dispose();
        bg.dispose();
        stage.dispose();
        answerFont.dispose();
        font.dispose();
        batch.dispose();
        wrongFont.dispose();
        correctFont.dispose();
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

    @Override
    public void update(float dt) {

    }
}
