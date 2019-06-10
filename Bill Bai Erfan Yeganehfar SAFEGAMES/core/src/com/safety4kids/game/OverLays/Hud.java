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
 * the Hud class displays current game information to the player using stages and Scene2D
 *
 * <h2>Course Info:</h2>
 * @author Erfan Yeg
 * @author Bill Bai
 * Ms.Krasteva
 *
 * @version 4.1 2019-05-24
 * 4.1 Finished labels and stage, added new font
 *
 */
public class Hud implements Disposable {

    //stage that displays the labels on
    public Stage stage;

    //the games viewport, allows hud to follow player
    private Viewport viewport;
    //The timer
    private  Integer worldTimer;

    //timer that is used for calculating the timer
    private float timer;

    //dictates what labels to show
    private boolean showStats;

    //int that displays current level
    private int level;

    //The bitmap font used
    private BitmapFont font;

    //determines if the game should end
    private boolean endGame;

    //various labels used to display information
    private Label countdownLabel;
    private Label timeLabel;
    private Label levelLabel;
    private Label worlLabel;

    /**
     * The constructor for the Hud initializes the Label values and constructs the viewport. Using Scene2D API such as Table,
     * the labels can be set properly with their sprites on the game screen.
     * @param sb the sprite batch passed into the hud for graphics
     */
    public Hud(SpriteBatch sb, boolean showStats, int level) {
        this.level = level;
        this.showStats = showStats;
        worldTimer = 200;
        timer = 0;
        endGame= false;
        viewport = new FitViewport(Safety4Kids.V_WIDTH, Safety4Kids.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();

        //Sets assets on top of the stage
        table.top();

        //scales to the stage
        table.setFillParent(true);

        //font generator
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/eight-bit-dragon.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 10;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 0.9f;
        font = generator.generateFont(parameter);
        generator.dispose();

        //label styles
        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(font, Color.WHITE));
        timeLabel= new Label("TIME", new Label.LabelStyle(font, Color.WHITE));
        levelLabel= new Label(level+"", new Label.LabelStyle(font, Color.WHITE));
        worlLabel= new Label( "LEVEL", new Label.LabelStyle(font, Color.WHITE));
        //adds Labels to the table, while equally dividing them

        if (showStats){
            table.add(worlLabel).expandX().padTop(10);
            table.add(timeLabel).expandX().padTop(10);
            table.row();
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

    /**
     * updates the time based on the amount of seconds that have passed
     * @param delta uses delta time derived from the the cpu's speed indicating the frames of the game
     */
    public void update(float delta) {
        timer += delta;
        if(timer >= 1){
            if (worldTimer > 0) {
                worldTimer--;
            } else {
                endGame = true;
            }
            countdownLabel.setText(String.format("%03d", worldTimer));
            timer = 0;
        }
    }

    /**
     * Returns if the game should en d
     * @return returns the boolean endGame
     */
    public boolean isEndGame(){
        return endGame;
    }

    /**
     * disposes of assets
     */
    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
    }
}
