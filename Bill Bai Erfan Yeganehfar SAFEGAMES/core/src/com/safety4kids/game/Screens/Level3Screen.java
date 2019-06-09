package com.safety4kids.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.safety4kids.game.Entities.MainPlayer;
import com.safety4kids.game.OverLays.Hud;
import com.safety4kids.game.Safety4Kids;
import com.safety4kids.game.Utils.Box2DCollisionCreator;
import com.safety4kids.game.Utils.GameContactListener;
import com.safety4kids.game.Utils.InputHandler;
import com.safety4kids.game.Utils.CustomMapRenderer;

import static com.safety4kids.game.Safety4Kids.PPM;
import static com.safety4kids.game.Safety4Kids.STEP;
import static com.safety4kids.game.Screens.GameScreen.GameState.*;
@SuppressWarnings("Duplicates")

public class Level3Screen extends GameScreen {

    //Tile map Instance variables
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private CustomMapRenderer tiledMapRenderer;

    private InputHandler input;

    //Instance of the main character
    private MainPlayer player;

    /**
     * The constructor creates all the necessary components for this specific platformer. This includes the actual game,
     * the sprite batches, game camera, viewport, box2d world through the gameState.
     * and the collision detection, main player, tileMaps, and Hud in this constructor.
     *
     * @param game The Safety4Kids Game that this level screen is displayed on
     */
    public Level3Screen(Safety4Kids game) {
        super();
        //Sets the hud for this level
        hud = new Hud(batch, true, 3);

        //Loads, fixes (added padding), and creates the renderer for the TileMap for level 1
        map = new TmxMapLoader().load("MapAssets/level3.tmx");
        tiledMapRenderer = new CustomMapRenderer(map, 1 / PPM);
        renderer = new OrthogonalTiledMapRenderer(map, 1 / PPM);

        //Generates the Box2D world for the objects within the Tile Map
        creator = new Box2DCollisionCreator(this);

        //The player is created inside of the Box2D world
        player = new MainPlayer(this, 400, 200);


        //Processes input for the player
        input = new InputHandler(player);

    }

    public void update(float dt) {
        if (!isPaused) {
            //user input handler
            input.inputProcess();

            world.step(STEP, 6, 2);
            player.update(dt);

            if (player.b2body.getPosition().x > 2.5 && player.b2body.getPosition().x < 35)
                gameCam.position.x = (player.b2body.getPosition().x);

            //update the gameCam with the player whenever they move
            gameCam.update();

            //sets the view of the renderer to the games orthographic camera and renders teh tilemap
            renderer.setView(gameCam);
            tiledMapRenderer.setView(gameCam);
            tiledMapRenderer.render();
        }
    }

    /**
     * The renderer method updates and displays new graphical/technical changes to the game screen based on the game camera
     * This includes the Tiled Map, the box2d debugger, the camera position, and the onscreen Hud.
     *
     * @param delta
     */
    @Override
    public void render(float delta) {
        if (isPaused) {
            if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY))
                state = RUN;
        }
        switch (state) {
            case RUN:
                if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                    if (!isPaused)
                        isPaused = true;
                    else if (isPaused)
                        isPaused = false;
                }

                if(!isPaused)
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
                //b2dr.render(world, gameCam.combined);

                //shows the screen based on the Camera with the hud
                game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
                hud.stage.draw();
                if(isPaused)
                    pause.stage.draw();


                if (player.b2body.getPosition().x > 37.5)
                    state = RETURN;
                break;
            case RETURN:
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(game));
                dispose();
                break;
            default:
                break;
        }
    }

    /**
     * Based on the screen size, the viewport of the game is positioned to correctly display the game screen
     *
     * @param width  the world width to be displayed
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
        pause.dispose();
        hud.dispose();
        atlas.dispose();
        batch.dispose();
        game.dispose();

    }

    public TiledMap getMap(){
        return map;
    }
}
