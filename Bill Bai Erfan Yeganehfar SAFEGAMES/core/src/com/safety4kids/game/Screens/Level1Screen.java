package com.safety4kids.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
import com.safety4kids.game.Utils.InputProcessor;
import com.safety4kids.game.Utils.MyOrthogonalTiledMapRenderer;

import static com.safety4kids.game.Safety4Kids.*;

/**
 * This Class represents the first level of the game where it is based on an interactive learning platformer.
 *
 * @version 3.4 2019-05-30
 * @author Erfan Yeganehfar
 * Ms. Krasteva
 *
 * Modifications:
 * 3.1 Erfan Yeg: (2019-05-28) Added the basics for the game such as the camera, viewports, hud, and renderer -- 2hrs
 * 3.2 Erfan Yeg: (2019-05-29) created + added the basic tile map and created the tilemap renderer -- 1hr
 * 3.3 Erfan Yeg: (2019-05-29) Created box2d bodies and fixtures and added them to the box2d world, aka collision detection,
 * Added the main player body to the world as well as input handling. -- 2hr
 * 3.4 Erfan Yeg: (2019-05-30) Cleaned code up, made a new class for loading in objects and fixed tilemap bleeding as well
 * as better mouvment. -- 1.5hrs
 */
@SuppressWarnings("Duplicates")
public class Level1Screen extends GameScreen {

    private Hud hud;

    //Tile map Instance variables
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private MyOrthogonalTiledMapRenderer tiledMapRenderer;
    private TextureAtlas atlas;

    private InputProcessor input;

    //Instance of the main character
    private MainPlayer player;

    /**
     * The constructor creates all the necessary components for this specific platformer. This includes the actual game,
     * the sprite batches, game camera, viewport, box2d world through the gameState.
     * and the collision detection, main player, tileMaps, and Hud in this constructor.
     * @param game The Safety4Kids Game that this level screen is displayed on
     */
    public Level1Screen(Safety4Kids game){
        super();
        atlas = new TextureAtlas("core/assets/MainPlayerAssets/MainPlayer.pack");

        //Sets the hud for this level
        hud = new Hud(batch, false, 1);

        //Loads, fixes (added padding), and creates the renderer for the TileMap for level 1
        map = new TmxMapLoader().load("core/assets/MapAssets/level1.tmx");
        tiledMapRenderer = new MyOrthogonalTiledMapRenderer(map, 1/PPM);
        renderer = new OrthogonalTiledMapRenderer(map, 1/ PPM);

        //Generates the Box2D world for the objects within the Tile Map
        new Box2DCollisionCreator(world, map);

        //The player is created inside of the Box2D world
        player = new MainPlayer(this, 400, 300);

        //Processes input for the player
        input = new InputProcessor(player);

    }

    public void update(float dt){
        //user input handler
        InputProcessor.inputProcess();

        world.step(STEP, 6, 2);
        player.update(dt);

        if(player.b2body.getPosition().x >  2.5 && player.b2body.getPosition().x < 36 )
        gameCam.position.x = (float) Math.round(player.b2body.getPosition().x * 100f) / 100f;

        //update the gameCam with the player whenever they move
        gameCam.update();

        //sets the view of the renderer to the games orthographic camera and renders teh tilemap
        renderer.setView(gameCam);
        tiledMapRenderer.setView(gameCam);
        tiledMapRenderer.render();
    }

    /**
     * The renderer method updates and displays new graphical/technical changes to the game screen based on the game camera
     * This includes the Tiled Map, the box2d debugger, the camera position, and the onscreen Hud.
     * @param delta
     */
    @Override
    public void render(float delta) {
        //update is separated from the render logic
        update(delta);
        //Clears the game screen
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Renders the Game map
        renderer.render();

        //Draws the sprites to the game screen based on the cam
        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

        //Box2D Debug renderer
        b2dr.render(world,gameCam.combined);

        //shows the screen based on the Camera with the hud
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }


    public World getWorld() {
        return world;
    }

    public TextureAtlas getAtlas() {
        return atlas;
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
     * Used for memory efficiency, disposes of game assets
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

}
