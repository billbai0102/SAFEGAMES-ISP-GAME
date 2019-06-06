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
//created class on 06-06 bill 2hrs
public class LoseScreen implements Screen {

    private SpriteBatch batch;
    private Stage stage;
    private Safety4Kids game;
    private Texture bg;

    private Viewport gamePort;
    private OrthographicCamera gamecam;
    private BitmapFont font;

    private TextButton menuBtn;
    private TextButton nextLvl;
    private TextButton restartLvl;

    private Skin skin;

    private int loseScore;

    public LoseScreen(Safety4Kids game, int loseScore) {
        this.game = game;
        this.loseScore = loseScore;
        skin = new Skin(Gdx.files.internal("skin/flat_earth/flat-earth-ui.json"));
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), gamecam);

        Gdx.input.setInputProcessor(stage);

        bg = new Texture(Gdx.files.internal("Lv2Assets/LoseScreenBg.png"));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/eight-bit-dragon.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 0.5f;
        font = generator.generateFont(parameter);
        generator.dispose();

        menuBtn = new TextButton("Go to Main Menu", skin);
        nextLvl = new TextButton("Skip to level 3", skin);
        restartLvl = new TextButton("Restart level 2", skin);

        menuBtn.setPosition(Gdx.graphics.getWidth() / 2 - menuBtn.getWidth()/2, Gdx.graphics.getHeight() / 2);
        menuBtn.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Back to menu");
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(LoseScreen.this.game));
                dispose();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        nextLvl.setPosition(Gdx.graphics.getWidth() / 2 - menuBtn.getWidth()/2 - 30 - nextLvl.getWidth(), Gdx.graphics.getHeight()/2);
        nextLvl.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Go to lv3");
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Level3Screen(LoseScreen.this.game));
                dispose();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        restartLvl.setPosition(Gdx.graphics.getWidth() / 2 + menuBtn.getWidth()/2 + 30, Gdx.graphics.getHeight()/2);
        restartLvl.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Restart lv 2");
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Level2Screen(LoseScreen.this.game));
                dispose();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        batch.draw(bg, 0, 0);

        GlyphLayout fontGlyph = new GlyphLayout();
        fontGlyph.setText(font, "Unfortunately, you lost at level " + loseScore + ".");
        font.draw(batch, fontGlyph, (Gdx.graphics.getWidth() - fontGlyph.width) / 2, Gdx.graphics.getHeight() - fontGlyph.height);

        GlyphLayout fontGlyph2 = new GlyphLayout();
        fontGlyph2.setText(font, "Would you like to...");
        font.draw(batch, fontGlyph2, (Gdx.graphics.getWidth() - fontGlyph2.width) / 2, Gdx.graphics.getHeight() - fontGlyph.height - fontGlyph2.height - 20);

        batch.end();

        stage.addActor(menuBtn);
        stage.addActor(nextLvl);
        stage.addActor(restartLvl);
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

