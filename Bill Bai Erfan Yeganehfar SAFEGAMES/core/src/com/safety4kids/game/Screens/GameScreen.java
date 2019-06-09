package com.safety4kids.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.safety4kids.game.Entities.MainPlayer;
import com.safety4kids.game.OverLays.Hud;
import com.safety4kids.game.OverLays.Pause;
import com.safety4kids.game.Safety4Kids;
import com.safety4kids.game.Utils.Box2DCollisionCreator;
import com.safety4kids.game.Utils.GameContactListener;
import com.safety4kids.game.Utils.InputHandler;
import com.safety4kids.game.Utils.CustomMapRenderer;
import static com.safety4kids.game.Safety4Kids.*;


/**
 * This Class represents the states that both level 1 and 3 inherit from.
 * This includes the base game functionality like the gameCam, the sprite batches, the viewPorts and the Box2d World
 *
 * @version 4.0 2019-05-31
 * @author Erfan Yeganehfar
 * @author Bill Bai
 *
 * Ms. Krasteva
 *
 * Modifications:
 * 3.1 Erfan Yeg: (2019-05-31) Created this Class for ease of use/ reasuability in the levels. -- 30mins
 * 3.2 Erfan Yeg: (2019-05-2) Added states to the levels, allowing the user to exit to the main menu -- 15mins
 * 4.0 Added states variables enabling pause options, also added some get methods
 */
public abstract class GameScreen implements Screen {

    /**
     * Different enums representing th States of the game
     */
    public enum GameState
    {
        NEXT_LEVEL,
        RUN,
        RESUME,
        RETURN,
        LOSE
    }

    //Overlay stages
    protected Pause pause;
    protected Hud hud;

    //game state
    public GameState state;

    //Basic functions fo the game, game cam, view ports, input handler, and sprites
    protected Safety4Kids game;
    protected SpriteBatch batch;
    protected OrthographicCamera gameCam;
    protected Viewport gamePort;
    protected InputHandler input;

    //Tile map Instance variables
    protected TiledMap map;
    protected OrthogonalTiledMapRenderer renderer;
    protected CustomMapRenderer tiledMapRenderer;

    //Box2d collision detection instance variables
    protected World world;
    protected Box2DDebugRenderer b2dr;
    protected TextureAtlas atlas;
    protected Box2DCollisionCreator creator;

    //Instance of the main character
    protected MainPlayer player;

    //Paused state
    protected boolean isPaused;

    /**
     * The constructor of the GameScreen, creates all the base necessary components of the platformer. Which is the actual game,
     * the sprite batches, game camera, viewport, and a Box2d world and contact listeners
     */
    public GameScreen(){
        game = new Safety4Kids();
        game.batch = new SpriteBatch();
        batch =  game.batch;
        state = GameState.RUN;

        atlas = new TextureAtlas(Gdx.files.internal("SpriteAssets/SpriteAssets.pack"));


        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(V_WIDTH / PPM, V_HEIGHT / PPM, gameCam);

        pause = new Pause(batch, this);

        //sets the view point of the Orthographic Camera to better use of the 4 quadrants within a 2d grid system
        gameCam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2,0);

        world = new World(new Vector2(0,CONST_GRAVITY),true);
        b2dr = new Box2DDebugRenderer();


        world.setContactListener(new GameContactListener(player));

    }

    /**
     * gets the current box2d game world
     * @return the box2d game world
     */
    public World getWorld() {
        return world;
    }

    /**
     * gets the texture atlas to be used for the sprites
     * @return the texture atlas
     */
    public TextureAtlas getAtlas() {
        return atlas;
    }

    /**
     * each render tick has an update execution to change the game application based on inputs and events
     * @param dt The target frame rate minus the time taken to complete this frame is called the delta time, used to keep the frames consistant across platforms
     */
    public abstract void update(float dt);

    /**
     * Rendering allows the game to be drawn to the screen while still updating the game, basically a dynamic drawing method
     * @param delta The target frame rate minus the time taken to complete this frame is called the delta time, used to keep the frames consistant across platforms
     */
    public abstract void render(float delta);

    /**
     * Resizes the viewport of the game to better fit the moniter
     * @param width the games width
     * @param height the games height
     */
    public abstract void resize(int width, int height);
    public abstract void dispose();

    /**
     * Hides the game when the window is not active
     */
    @Override
    public void hide() {
        pause();
        isPaused = true;
    }

    /**
     * displays the game when the window is active
     */
    @Override
    public void show() {

    }

    /**
     * puases th game when invoked
     */
    @Override
    public void pause() {
        isPaused = true;

    }

    /**
     * resumes the game after a pause when invoked
     */
    @Override
    public void resume() {
        isPaused = false;

    }

}
