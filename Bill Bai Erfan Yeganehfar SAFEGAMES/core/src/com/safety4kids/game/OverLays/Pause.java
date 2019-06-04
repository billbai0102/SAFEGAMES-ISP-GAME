package com.safety4kids.game.OverLays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.safety4kids.game.Safety4Kids;

public class Pause implements Disposable {

    public Stage stage;
    private Viewport viewport;
    private Skin skin;

    private Label countdownLabel;

    /**
     * The constructor for the Hud initializes the Label values and constructs the viewport. Using Scene2D API such as Table,
     * the labels can be set properly with their sprites on the game screen.
     * @param sb the sprite batch passed into the hud for graphics
     */
    public Pause(SpriteBatch sb) {
        viewport = new FitViewport(Safety4Kids.V_WIDTH * Safety4Kids.SCALE, Safety4Kids.V_HEIGHT * Safety4Kids.SCALE, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        skin = new Skin(Gdx.files.internal("core/skin/flat-earth-ui.json"));
        Window pause= new Window("PAUSE",skin);
        pause.padTop(10);
        TextButton cont = new TextButton("Continue", skin);
        TextButton exit = new TextButton("Main Menu", skin);
        cont.setSize(100, 50);
        exit.setSize(100, 50);
        pause.add(cont);
        pause.row();
        pause.add(exit).padTop(20);
        pause.setSize(200,200);
        pause.setPosition(stage.getWidth()/2-pause.getWidth()/2,stage.getHeight()/2-pause.getHeight()/2);



        //adds table to the current stage
        stage.addActor(pause);

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
