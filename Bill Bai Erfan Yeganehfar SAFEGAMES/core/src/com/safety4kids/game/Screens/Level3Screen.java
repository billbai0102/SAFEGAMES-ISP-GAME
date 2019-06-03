package com.safety4kids.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.safety4kids.game.Entities.MainPlayer;
import com.safety4kids.game.Levels.Hud;
import com.safety4kids.game.Safety4Kids;
import com.safety4kids.game.Utils.Box2DCollisionCreator;
import com.safety4kids.game.Utils.InputProcessor;
import com.safety4kids.game.Utils.MyOrthogonalTiledMapRenderer;

import static com.safety4kids.game.Safety4Kids.PPM;
import static com.safety4kids.game.Safety4Kids.STEP;
import static com.safety4kids.game.Screens.GameScreen.GameState.*;
@SuppressWarnings("Duplicates")

public class Level3Screen extends GameScreen {


    /**
     * The constructor creates all the necessary components for this specific platformer. This includes the actual game,
     * the sprite batches, game camera, viewport, box2d world through the gameState.
     * and the collision detection, main player, tileMaps, and Hud in this constructor.
     * @param game The Safety4Kids Game that this level screen is displayed on
     */
    public Level3Screen(Safety4Kids game){
        super();
        atlas = new TextureAtlas("core/assets/MainPlayerAssets/MainPlayer.pack");

        //Sets the hud for this level
        hud = new Hud(batch, true, 3);
        //Loads, fixes (added padding), and creates the renderer for the TileMap for level 1
        map = new TmxMapLoader().load("core/assets/MapAssets/level1.tmx");
        tiledMapRenderer = new MyOrthogonalTiledMapRenderer(map, 1/PPM);
        renderer = new OrthogonalTiledMapRenderer(map, 1/ PPM);

        //Generates the Box2D world for the objects within the Tile Map
        new Box2DCollisionCreator(world, map);

        //The player is created inside of the Box2D world
        player = new MainPlayer(this, 400, 250);

        //Processes input for the player
        input = new InputProcessor(player);

    }

    public void update(float dt){

        //user input handler
        InputProcessor.inputProcess();

        world.step(STEP, 6, 2);
        player.update(dt);

        if(player.b2body.getPosition().x >  2.5 && player.b2body.getPosition().x < 35 )
            gameCam.position.x = (float) Math.round(player.b2body.getPosition().x * 1000f) / 1000f;

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
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            state = RETURN;

        switch (state) {
            case RUN:
                //update is separated from the render logic
                update(delta);
                //Clears the game screen
                Gdx.gl.glClearColor(0, 0, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

                //Renders the Game map
                renderer.render();

                //Draws the sprites to the game screen based on the cam
                game.batch.setProjectionMatrix(gameCam.combined);
                game.batch.begin();
                player.draw(game.batch);
                game.batch.end();

                //Box2D Debug renderer
                b2dr.render(world, gameCam.combined);

                //shows the screen based on the Camera with the hud
                game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
                hud.stage.draw();

                if (player.b2body.getPosition().x > 37.5)
                    state = NEXT_LEVEL;
                break;
            case NEXT_LEVEL:
                toMainMenu();
                break;
            case PAUSE:
                break;
            case RESUME:
                state = RUN;
                break;
            case RETURN:
                state = NEXT_LEVEL;
                break;
            default:
                break;
        }
    }

    private void toMainMenu(){
        ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(game));
        dispose();
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
        tiledMapRenderer.dispose();
    }

    @Override
    public void show() {

    }

    @Override
    public void pause() {
        this.state = PAUSE;

    }

    @Override
    public void resume() {
        this.state = RESUME;

    }

    @Override
    public void hide() {

    }

}