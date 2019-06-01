package com.safety4kids.game.Screens;

import com.badlogic.gdx.Screen;
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
import static com.safety4kids.game.Safety4Kids.PPM;

/**
 * This is class represents the second level of the game.
 *
 * @version 3 2019-05-30
 * @author Bill Bai, Erfan Yeganehfar
 * Ms. Krasteva
 *
 * Modifications:
 * 3.1 Bill Bai: (2019-05-30) Added the basics for the game such as the camera, viewports, hud, and renderer -- 2hrs
 */
public class Level2Screen implements Screen {
    private Safety4Kids game;
    private SpriteBatch batch;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private MyOrthogonalTiledMapRenderer tiledMapRenderer;

    private World world;
    private Box2DDebugRenderer b2dr;

    private MainPlayer player;

    public Level2Screen(Safety4Kids game) {
        this.game = game;

        batch = new SpriteBatch();

        gamecam = new OrthographicCamera();

        gamePort = new FitViewport(V_WIDTH / PPM, V_HEIGHT / PPM, gamecam);

        hud = new Hud(batch);

        map = new TmxMapLoader().load("core/assets/level2.tmx");
        tiledMapRenderer = new MyOrthogonalTiledMapRenderer(map, 1/PPM);
        //tiledMapLayer = (TiledMapTileLayer)map.getLayers().get(0);
        renderer = new OrthogonalTiledMapRenderer(map, 1/ PPM);

        //sets the view point of the Orthographic Camera to better use of the 4 quadrants within a 2d grid system
        gamecam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2,0);

        world = new World(new Vector2(0,CONST_GRAVITY),true);
        b2dr = new Box2DDebugRenderer();


    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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

    }
}
