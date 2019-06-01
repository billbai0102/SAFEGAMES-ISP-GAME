package com.safety4kids.game.Screens;

<<<<<<< HEAD
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.safety4kids.game.Entities.MainPlayer;
import com.safety4kids.game.Levels.Hud;
import com.safety4kids.game.Safety4Kids;
import com.safety4kids.game.Utils.Box2DCollisionCreator;
import com.safety4kids.game.Utils.MyOrthogonalTiledMapRenderer;

import static com.safety4kids.game.Safety4Kids.*;

/**
 * This Class represents the first level of the game where it is based on an interactive learning platformer.
 *
 * @version 3.4 2019-05-30
 * @author Erfan Yeganehfar, Bill Bai
 * Ms. Krasteva
 *
 * Modifications:
 * 3.1 Erfan Yeg: (2019-05-28) Added the basics for the game such as the camera, viewports, hud, and renderer -- 2hrs
 * 3.2 Erfan Yeg: (2019-05-29) created + added the basic tile map and created the tilemap renderer -- 1hr
 * 3.3 Erfan Yeg: (2019-05-29) Created box2d bodies and fixtures and added them to the box2d world, aka collision detection,
 * Added the main player body to the world as well as input handling. -- 2hr
 * 3.4 Bill Bai: (2019-05-30) Cleaned up code, by removing unused variables. -- 15mins
 * 3.5 Bill Bai: (2019-05-30) Edited the TiledMap for the game, to better suit the constricts. -- 0.5hr
 */
public class GameScreen implements Screen {

    //
    private Safety4Kids game;
    private SpriteBatch batch;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    //Tile map Instance variables
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private MyOrthogonalTiledMapRenderer tiledMapRenderer;


    //Box2d collision detection instance variables
    private World world;
    private Box2DDebugRenderer b2dr;

    //Instance of the main character
    private MainPlayer player;
    /**
     * The constructor creates all the necessary components of the platformer. This includes the actual game,
     * the sprite batches, game camera, viewport, tiled map, box2d world + it's collision detection,
     * and finally the main player.
     * @param game The Safety4Kids Game that this level screen is displayed on
     */
    public GameScreen(Safety4Kids game){
        this.game = game;

        batch = new SpriteBatch();

        gamecam = new OrthographicCamera();

        gamePort = new FitViewport(V_WIDTH / PPM, V_HEIGHT / PPM, gamecam);

        hud = new Hud(batch);
        //mapLoader = new TmxMapLoader();
        //map = mapLoader.load("core/assets/level1.tmx");

        map = new TmxMapLoader().load("core/assets/level1.tmx");
        tiledMapRenderer = new MyOrthogonalTiledMapRenderer(map, 1/PPM);
        //tiledMapLayer = (TiledMapTileLayer)map.getLayers().get(0);
        renderer = new OrthogonalTiledMapRenderer(map, 1/ PPM);

        //sets the view point of the Orthographic Camera to better use of the 4 quadrants within a 2d grid system
        gamecam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2,0);

        world = new World(new Vector2(0,CONST_GRAVITY),true);
        b2dr = new Box2DDebugRenderer();

        //Generates the Box2D world for the objects within the Tile Map
        new Box2DCollisionCreator(world, map);

        //The player is created inside of the Box2D world
        player = new MainPlayer(world);

    }

    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= MAX_VELOCITY)
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0),player.b2body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= MIN_VELOCITY)
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0),player.b2body.getWorldCenter(), true);
    }

    public void update(){

        //user input handler
        handleInput();

        world.step(STEP, 6, 2);

        gamecam.position.x = (float) Math.round(player.b2body.getPosition().x * 100f) / 100f;
        //gamecam.position.y = player.b2body.getPosition().y;


        //update the gamecam with the player whenever they move
        gamecam.update();

        //sets the view of the renderer to the games orthographic camera and renders teh tilemap
        renderer.setView(gamecam);
        tiledMapRenderer.setView(gamecam);
        tiledMapRenderer.render();
    }

    /**
     * The renderer method upadtes and displays new graphical/technical changes to the game screen based on the game camera
     * This includes the Tiled Map, the box2d debugger, the camera position, and the onscreen Hud.
     * @param delta
     */
    @Override
    public void render(float delta) {
        //update is separated from the render logic
        update();
        Gdx.graphics.setTitle(TITLE + " -- FPS: " + Gdx.graphics.getFramesPerSecond());

        //Clears the game screen
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Renders the Game map
        renderer.render();

        //Box2D Debug renderer
        b2dr.render(world,gamecam.combined);

        //shows the screen based on the Camera with the hud
        batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    /**
     * Based on the screen size, the viewport of the game is positioned to correctly display the game screen
     * @param width the world width to be displayed
     * @param height the world height to be displayed
     */
    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    /**
     * Used for efficiency, disposes of game assets
     */
    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }

    @Override
    public void show() {

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

=======
public class GameScreen {
>>>>>>> 5d1b76d2899eb1a8ae468a71376bdb6880b8d1af
}
