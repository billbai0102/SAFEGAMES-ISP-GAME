package com.safety4kids.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.safety4kids.game.Entities.MainPlayer;
import com.safety4kids.game.Levels.Hud;
import com.safety4kids.game.Safety4Kids;
import sun.applet.Main;

public class GameScreen implements Screen {
    private Safety4Kids game;
    private OrthographicCamera gamecam;
    //
    private Viewport gamePort;
    private Hud hud;
    //Tile map Instance variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d collision detection instance variables
    private World world;
    private Box2DDebugRenderer b2dr;

    private MainPlayer player;

    public GameScreen(Safety4Kids game){
        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(Safety4Kids.V_WIDTH / Safety4Kids.PPM, Safety4Kids.V_HEIGHT / Safety4Kids.PPM, gamecam);
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("core/assets/level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1/ Safety4Kids.PPM);
        //sets the view point of the Orthographic Camera to better use of the 4 quadrants within a 2d grid system
        gamecam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2,0);

        world = new World(new Vector2(0,-10),true);
        b2dr = new Box2DDebugRenderer();
        player = new MainPlayer(world);

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/ Safety4Kids.PPM, (rect.getY() + rect.getHeight() / 2)/ Safety4Kids.PPM);
            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/2/ Safety4Kids.PPM,rect.getHeight()/2/ Safety4Kids.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/ Safety4Kids.PPM, (rect.getY() + rect.getHeight() / 2)/ Safety4Kids.PPM);
            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/2/ Safety4Kids.PPM,rect.getHeight()/2/ Safety4Kids.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/ Safety4Kids.PPM, (rect.getY() + rect.getHeight() / 2)/ Safety4Kids.PPM);
            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/2/ Safety4Kids.PPM,rect.getHeight()/2/ Safety4Kids.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

    }
    @Override
    public void show() {

    }

    public void handleInput(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
            player.b2body.applyLinearImpulse(new Vector2(0, 3.5f), player.b2body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0),player.b2body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0),player.b2body.getWorldCenter(), true);
    }

    public void update(float dt){
        //user input handler
        handleInput(dt);

        world.step(1/60f, 6, 2);

        gamecam.position.x = player.b2body.getPosition().x;
        //gamecam.position.y = player.b2body.getPosition().y;


        //update the gamecam with the player whenever they move
        gamecam.update();
        //sets the view of the renderer to the games orthographic camera
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {
        //update is separated from the render logic
        update(delta);
        //Clears the game screen
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Renders the Game map
        renderer.render();

        //Box2D Debug renderer
        b2dr.render(world,gamecam.combined);

        //shows the screen based on the Camera with the hud
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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
