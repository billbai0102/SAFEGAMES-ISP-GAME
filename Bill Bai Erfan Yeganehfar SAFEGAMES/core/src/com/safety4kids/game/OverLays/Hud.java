package com.safety4kids.game.OverLays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.safety4kids.game.Safety4Kids;

/**
 *
 * @author Erfan Yeg, Bill Bai
 * @version 1.0
 * the Hud class displays current game information to the player using stages and Scene2D
 */
public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;
    private  Integer worldTimer;
    private float timeCount;
    private Integer score;
    private boolean showStats;
    private int level;
    private BitmapFont font;

    private Label countdownLabel;
    private Label scoreLabel;
    private Label timeLabel;
    private Label levelLabel;
    private Label worlLabel;
    private Label  playerLabel;

    /**
     * The constructor for the Hud initializes the Label values and constructs the viewport. Using Scene2D API such as Table,
     * the labels can be set properly with their sprites on the game screen.
     * @param sb the sprite batch passed into the hud for graphics
     */
    public Hud(SpriteBatch sb, boolean showStats, int level) {
        this.level = level;
        this.showStats = showStats;
        worldTimer = 300;
        timeCount = 0;
        score = 0;
        viewport = new FitViewport(Safety4Kids.V_WIDTH, Safety4Kids.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();

        //Sets assets on top of the stage
        table.top();

        //scales to the stage
        table.setFillParent(true);


        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("core/assets/Fonts/eight-bit-dragon.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 10;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 0.9f;
        font = generator.generateFont(parameter);
        generator.dispose();

        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(font, Color.WHITE));
        scoreLabel =new Label(String.format("%06d", score), new Label.LabelStyle(font, Color.WHITE));
        timeLabel= new Label("TIME", new Label.LabelStyle(font, Color.WHITE));
        levelLabel= new Label(level+"", new Label.LabelStyle(font, Color.WHITE));
        worlLabel= new Label( "LEVEL", new Label.LabelStyle(font, Color.WHITE));
        playerLabel= new Label("SCORE", new Label.LabelStyle(font, Color.WHITE));
        //adds Labels to the table, while equally dividing them

        if (showStats){
            table.add(playerLabel).expandX().padTop(10);
            table.add(worlLabel).expandX().padTop(10);
            table.add(timeLabel).expandX().padTop(10);
            table.row();
            table.add(scoreLabel).expandX().padTop(10);
            table.add(levelLabel).expandX().padTop(10);
            table.add(countdownLabel).expandX().padTop(10);

        }
        else{
            table.add(worlLabel).expandX().padTop(10);
            table.row();
            table.add(levelLabel).expandX().padTop(10);
        }

        //adds table to the current stage
        stage.addActor(table);

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
