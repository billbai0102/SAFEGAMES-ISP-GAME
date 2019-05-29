package com.safety4kids.game.Levels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.safety4kids.game.Safety4Kids;

/**
 *
 * @author Erfan Yeg, Bill Bai
 * @version 1.0
 * the Hud class displays current game information to the player using stages and Scene2D
 */
public class Hud {
    public Stage stage;
    private Viewport viewport;
    private  Integer worldTimer;
    private float timeCount;
    private Integer score;

    Label countdownLabel;
    Label scoreLabel;
    Label timeLebel;
    Label levelLabel;
    Label worlLabel;
    Label  playerLabel;

    /**
     * The constructor for the Hud initializes the Label values and constructs the viewport. Using Scene2D API such as Table,
     * the labels can be set properly with their sprites on the game screen.
     * @param sb the sprite batch passed into the hud for graphics
     */
    public Hud(SpriteBatch sb) {
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

        countdownLabel = new Label(worldTimer.toString(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(score.toString(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLebel= new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel= new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worlLabel= new Label( "WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        playerLabel= new Label("PLAYER1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        //adds Labels to the table, while equally dividing them
        table.add(playerLabel).expandX().padTop(1);
        table.add(worlLabel).expandX().padTop(1);
        table.add(timeLebel).expandX().padTop(1);
        //new row created
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        //adds table to the current stage
        stage.addActor(table);

    }

}
