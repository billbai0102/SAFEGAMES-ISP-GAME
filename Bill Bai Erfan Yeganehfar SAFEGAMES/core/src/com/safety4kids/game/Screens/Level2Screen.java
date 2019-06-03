package com.safety4kids.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.safety4kids.game.Safety4Kids;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This is class represents the second level of the game.
 *
 * @author Bill Bai, Erfan Yeganehfar
 * Ms. Krasteva
 * <p>
 * Modifications:
 * 3.1 Bill Bai: (2019-05-30) Added the basics for the game such as the camera, viewports, hud, and renderer -- 2hrs
 * @version 3 2019-05-30
 */
public class Level2Screen implements Screen {
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

        font = new BitmapFont(Gdx.files.internal("core/assets/Lv2Assets/segoe.fnt"));

        loadQuestions();
    }

    @Override
    public void render(float delta) {
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

    }

    public void shuffleAnswers() {
        String[] tempAnswers;
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
            //for(int y = 0; y < tempAnswers.length; x++){
            //     if(tempAnswers[y].charAt(0) == '4'){
            //        answerKey.add(y+1);
            //       break;
            //     }
            // }
            // for(int y = 0; y< tempAnswers.length; y++){
            //     System.out.println(tempAnswers[y]);
            // }
            // System.out.println("Answer at: " + answerKey.get(x));
        }
    }

    public void drawQuestions(SpriteBatch batch) {
        boolean correct = false;
        GlyphLayout questionGlyph = new GlyphLayout();
        String q = "Q".concat(String.valueOf(curQuestionIndex + 1).concat(questions.get(curQuestionIndex).substring(2)));
        questionGlyph.setText(font, q);

        GlyphLayout a1Layout = new GlyphLayout();
        //  String a1 = "A)".concat(answers.get(curQuestionIndex).substring(1));
        //   questionGlyph.setText(font, a1);

        font.draw(batch, questionGlyph, (Gdx.graphics.getWidth() - questionGlyph.width) / 2, Gdx.graphics.getHeight() - questionGlyph.height);
        // font.draw(batch, )

        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)){
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
        System.out.println("Got to loadQuestions()");
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
}
