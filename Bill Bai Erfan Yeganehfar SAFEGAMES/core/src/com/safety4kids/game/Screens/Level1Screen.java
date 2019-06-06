package com.safety4kids.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.safety4kids.game.Entities.MainPlayer;
import com.safety4kids.game.Entities.MovingHazard;
import com.safety4kids.game.OverLays.Hud;
import com.safety4kids.game.Safety4Kids;
import com.safety4kids.game.Utils.Box2DCollisionCreator;
import com.safety4kids.game.Utils.GameContactListener;
import com.safety4kids.game.Utils.InputHandler;
import com.safety4kids.game.Utils.MyOrthogonalTiledMapRenderer;

//import java.awt.*;

import static com.safety4kids.game.Safety4Kids.*;
import static com.safety4kids.game.Screens.GameScreen.GameState.*;

/**
 * This Class represents the first level of the game where it is based on an interactive learning platformer.
 *
 * @author Erfan Yeganehfar
 * @author Bill Bai
 * <p>
 * Ms. Krasteva
 * <p>
 * Modifications:
 * 3.1 Erfan Yeg: (2019-05-28) Added the basics for the game such as the camera, viewports, hud, and renderer -- 2hrs
 * 3.2 Erfan Yeg: (2019-05-29) created + added the basic tile map and created the tilemap renderer -- 2hrs
 * 3.3 Erfan Yeg: (2019-05-29) Created box2d bodies and fixtures and added them to the box2d world, aka collision detection,
 * Added the main player body to the world as well as input handling. -- 2hrs
 * 3.4 Erfan Yeg: (2019-05-30) Cleaned code up, made a new class for loading in objects and fixed tilemap bleeding as well
 * as better movement. -- 1.5hrs
 * 3.5 Erfan Yeg: (2019-06-01) Added a way of transitioning from the current level to the next -- 30mins
 * 3.6 Erfan Yeg: (2019-06-02) Added different states for the game that control the state of the game -- 1hr
 * @version 3.6 2019-05-30
 */
@SuppressWarnings("Duplicates")
public class Level1Screen extends GameScreen {

    //Tile map Instance variables
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private MyOrthogonalTiledMapRenderer tiledMapRenderer;

    private InputHandler input;
    private MovingHazard hazard;

    //Instance of the main character
    private MainPlayer player;

    /**
     * The constructor creates all the necessary components for this specific platformer. This includes the actual game,
     * the sprite batches, game camera, viewport, box2d world through the gameState.
     * and the collision detection, main player, tileMaps, and Hud in this constructor.
     *
     * @param game The Safety4Kids Game that this level screen is displayed on
     */
    public Level1Screen(Safety4Kids game) {
        super();
        //Sets the hud for this level
        hud = new Hud(batch, true, 1);

        //Loads, fixes (added padding), and creates the renderer for the TileMap for level 1
        map = new TmxMapLoader().load("core/assets/MapAssets/level1a.tmx");
        tiledMapRenderer = new MyOrthogonalTiledMapRenderer(map, 1 / PPM);
        renderer = new OrthogonalTiledMapRenderer(map, 1 / PPM);

        //Generates the Box2D world for the objects within the Tile Map
        new Box2DCollisionCreator(world, map);

        //The player is created inside of the Box2D world
        player = new MainPlayer(this, 400, 200);
        hazard = new MovingHazard(this, 500, 200);

        //Processes input for the player
        input = new InputHandler(player);
    }

    public void update(float dt) {
        if (!isPaused) {
            //user input handler
            input.inputProcess();

            world.step(STEP, 6, 2);
            player.update(dt);
            hud.update(dt);
            hazard.update(dt);
            //Sets the min and max bounds if the camera following the player
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
                System.out.println(game.batch);
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
                    System.out.println(hazard);

                    game.batch.begin();
                    player.draw(game.batch);
                    hazard.draw(game.batch);
                    game.batch.end();

                //Box2D Debug renderer
                 b2dr.render(world, gameCam.combined);
                hud.stage.draw();

                //shows the screen based on the Camera with the hud
                game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
                if(isPaused)
                pause.stage.draw();


                if (player.b2body.getPosition().x > 37.5)
                    state = NEXT_LEVEL;
                break;
            case NEXT_LEVEL:
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Level2Screen(game));
                dispose();
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
    }

}
