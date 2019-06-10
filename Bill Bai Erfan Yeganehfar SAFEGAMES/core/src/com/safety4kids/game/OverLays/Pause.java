package com.safety4kids.game.OverLays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.safety4kids.game.Safety4Kids;
import com.safety4kids.game.Screens.GameScreen;

import static com.safety4kids.game.Screens.GameScreen.GameState.RETURN;

/**
 * A pause screen that pauses the game and allows the user to return to the main menu.
 *
 * @author Erfan Yeganehfar
 * @author Bill Bai
 *
 * @version 4.1 2019-06-04
 * 4.1 Erfan Yeg: (2019-06-04) Created the pause stage and window styles + interactions 2hrs
 */
public class Pause implements Disposable {

    //stage that displays the labels on
    public Stage stage;

    //the games viewport, allows hud to follow player
    private Viewport viewport;

    //The skin for the SceneU elements
    private Skin skin;

    //Window that displays buttons
    private final Window pause;

    //Bitmap font used to show the title
    private BitmapFont font;

    //Buttons used to continue and exit the level
    public TextButton cont;
    public TextButton exit;

    //title label
    private Label title;

    /**
     * The constructor for the Hud initializes the Label values and constructs the viewport. Using Scene2D API such as Table,
     * the labels can be set properly with their sprites on the game screen.
     * @param sb the sprite batch passed into the hud for graphics
     */
    public Pause(SpriteBatch sb, final GameScreen game) {
        viewport = new FitViewport(Safety4Kids.V_WIDTH * Safety4Kids.SCALE, Safety4Kids.V_HEIGHT * Safety4Kids.SCALE, new OrthographicCamera());
        SpriteDrawable bgDrawble = new SpriteDrawable(new Sprite(new Texture("purp.jpg")));
        SpriteDrawable background = new SpriteDrawable(new Sprite(new Texture("transp.png")));

        //window style
        Window.WindowStyle windowStyle= new Window.WindowStyle(new BitmapFont(), Color.BLACK, bgDrawble);
        windowStyle.stageBackground = background;
        pause = new Window("", windowStyle);

        //font generator
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/eight-bit-dragon.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 0.9f;
        font = generator.generateFont(parameter);
        generator.dispose();

        stage = new Stage(viewport, sb);
        skin = new Skin(Gdx.files.internal("skin/craftacular/skin/craftacular-ui.json"));

        pause.top();

        //Button controls onClick return to game
        cont = new TextButton("Continue", skin);
        cont.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.resume();
            }
        });

        //Button controls onClick return to menu
        exit = new TextButton("Main Menu", skin);
        exit.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.state = RETURN;
            }
        });

        title = new Label("Pause Menu", new Label.LabelStyle(font, Color.WHITE));


        cont.setSize(20, 20);
        exit.setSize(20, 20);

        //sets up the styling of the window
        pause.add(title).expandX().padTop(20).row();
        pause.add(cont).padTop(40);
        pause.row();
        pause.add(exit).padTop(20);
        pause.setSize(500,300);
        pause.setPosition(stage.getWidth()/2-pause.getWidth()/2,stage.getHeight()/2-pause.getHeight()/2);


        //adds table to the current stage
        stage.addActor(pause);
        //sets the input prcessor as this stage
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Disposes of assets
     */
    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
    }
}
