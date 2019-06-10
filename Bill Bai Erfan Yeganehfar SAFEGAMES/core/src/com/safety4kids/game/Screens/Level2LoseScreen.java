package com.safety4kids.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
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
 * This class is called when the user loses level 2. It draws a Lose screen.
 * <br>
 * Background of image borrowed from <a href="https://wallup.net/sunset-16-bit-pixel-art-pokemon/">source site</a> (2017-03-29) by unknown artist.
 * <br>
 * <h2>Course info:</h2>
 * ICS4U with V. Krasteva
 *
 * @author Bill Bai, Erfan Yeganehfar
 * @version 1.6 05/02/19
 */
public class Level2LoseScreen implements Screen {

    /**
     * SpriteBatch to be drawn onto.
     */
    private SpriteBatch batch;
    /**
     * Stage where buttons will be drawn on.
     */
    private Stage stage;
    /**
     * Game to be drawn onto.
     */
    private Safety4Kids game;
    /**
     * Background texture.
     */
    private Texture bg;
    /**
     * GamePort that conducts the user's view of the screen.
     */
    private Viewport gamePort;
    /**
     * Orthographic Camera instance used with gamePort.
     */
    private OrthographicCamera gamecam;
    /**
     * Font used in this level.
     */
    private BitmapFont font;
    /**
     * FontGlyph instance that controls first line of text onscreen.
     */
    private GlyphLayout fontGlyph = new GlyphLayout();
    /**
     * FontGlyph instance that controls second line of text onscreen.
     */
    private GlyphLayout fontGlyph2 = new GlyphLayout();
    /**
     * TextButton that leads to menu.
     */
    private TextButton menuBtn;
    /**
     * TextButton that leads to lv3
     */
    private TextButton nextLvl;
    /**
     * TextButton that restarts lv2
     */
    private TextButton restartLvl;
    /**
     * Button skin.
     */
    private Skin skin;
    /**
     * The score at which the user lost at.
     */
    private int loseScore;

    /**
     * This is the constructor. It initializes the global variables and adds listeners to the different
     * TextButton instances.
     * @param game The game to be drawn on.
     * @param loseScore The score at which the user lost on.
     */
    public Level2LoseScreen(Safety4Kids game, int loseScore) {
        this.game = game;
        this.loseScore = loseScore;
        skin = new Skin(Gdx.files.internal("skin/vhs/skin/vhs-ui.json"));
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), gamecam);
        //Sets input processor to this stage
        Gdx.input.setInputProcessor(stage);
        bg = new Texture(Gdx.files.internal("Lv2Assets/LoseScreenBg.png"));
        //Creates fonts
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/eight-bit-dragon.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 0.5f;
        font = generator.generateFont(parameter);
        generator.dispose();
        //Initializes the text buttons
        menuBtn = new TextButton(">>Go to Main Menu<<", skin);
        nextLvl = new TextButton(">>Skip to level 3<<", skin);
        restartLvl = new TextButton(">>Restart level 2<<", skin);
        //Adds listener to menuBtn
        menuBtn.setPosition(Gdx.graphics.getWidth() / 2 - menuBtn.getWidth() / 2, Gdx.graphics.getHeight() / 2 + 50);
        menuBtn.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Back to menu");
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(Level2LoseScreen.this.game));
                dispose();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        //Adds listener to nextLvl
        nextLvl.setPosition(Gdx.graphics.getWidth() / 2 - nextLvl.getWidth() / 2, Gdx.graphics.getHeight() / 2 - 30);
        nextLvl.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Go to lv3");
                dispose();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Level3IntroScreen(Level2LoseScreen.this.game));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        //Adds listener to restartLvl
        restartLvl.setPosition(Gdx.graphics.getWidth() / 2 - restartLvl.getWidth() / 2, Gdx.graphics.getHeight() / 2 + 130);
        restartLvl.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Restart lv 2");
                dispose();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Level2Screen(Level2LoseScreen.this.game));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        //Adds buttons to the stage
        stage.addActor(menuBtn);
        stage.addActor(nextLvl);
        stage.addActor(restartLvl);
    }

    /**
     * This is the render methood. It draws the background and losing text onto the screen. It the draws the
     * button over the image and text.
     *
     * @param delta The current frame.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        batch.draw(bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        fontGlyph.setText(font, "Unfortunately, you lost at question " + loseScore + ".");
        font.draw(batch, fontGlyph, (Gdx.graphics.getWidth() - fontGlyph.width) / 2, Gdx.graphics.getHeight() - fontGlyph.height);

        fontGlyph2.setText(font, "Would you like to...");
        font.draw(batch, fontGlyph2, (Gdx.graphics.getWidth() - fontGlyph2.width) / 2, Gdx.graphics.getHeight() - fontGlyph.height - fontGlyph2.height - 20);

        batch.end();

        stage.draw();
    }

    /**
     * This method disposes of objects created in this class to free up memory.
     */
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

