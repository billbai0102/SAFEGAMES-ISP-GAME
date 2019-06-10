package com.safety4kids.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.safety4kids.game.Entities.HazardSprite;
import com.safety4kids.game.Entities.MainPlayer;
import com.safety4kids.game.OverLays.Hud;
import com.safety4kids.game.Safety4Kids;
import com.safety4kids.game.Utils.Box2DCollisionCreator;
import com.safety4kids.game.Utils.GameContactListener;
import com.safety4kids.game.Utils.InputHandler;
import com.safety4kids.game.Utils.CustomMapRenderer;
import sun.applet.Main;


import static com.safety4kids.game.Safety4Kids.PPM;
import static com.safety4kids.game.Safety4Kids.STEP;
import static com.safety4kids.game.Screens.GameScreen.GameState.*;
@SuppressWarnings("Duplicates")
/**
 * This Class represents the 3rd level of the game where it is based on an interactive learning platformer.
 * In this level the user must avoid hazards and unsafe situations to final escape their adversity.
 * Using Box2d bodies, tile maps, cameras, viewports, game loops, and game states
 * to create a cohesive platformer meant to make the user learn about safety
 *
 * Most of the documentation for the methods used can be found at
 * <a href="https://github.com/libgdx/libgdx">libgdx</a>
 * alongside the documentation used to make this game
 * tilemap resources from <a href="https://ansimuz.itch.io/sunny-land-pixel-game-art ">by ansimuz</a>
 * <b>Sprite resources are original from Erfan.Y and Bill.B</b>
 *
 * <h2>Course info:</h2>
 * ICS4U with V. Krasteva
 *
 * @author Erfan Yeganehfar, Bill Bai
 * @version 4.1 06/09/19
 */
public class Level3Screen extends GameScreen {

    /**
     * Custom TiledMap renderer to fix bleeding.
     */
    private TiledMap map;

    /**
     * Used to render the TiledMap.
     */
    private OrthogonalTiledMapRenderer renderer;

    /**
     * Custom TiledMap renderer to fix bleeding.
     */
    private CustomMapRenderer tiledMapRenderer;

    private InputHandler input;

    /**
     * Creates instance of MainPlayer which is player to be controlled.
     */
    private MainPlayer player;

    /**
     * An Arraylist of hazards that are to be drawn to the game world
     */
    Array<HazardSprite> hazards;

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
        player = new MainPlayer(this, 240, 200);


        //Processes input for the player
        input = new InputHandler(player);

        world.setContactListener(new GameContactListener(this));

        //create the hazards onto the screen
        createHazards();
    }

    /**
     * The update method used to update the locations and states of the game, it also changes how the camera is relative to the player
     * @param dt The target frame rate minus the time taken to complete this frame is called the delta time, used to keep the frames consistant across platforms
     */
    public void update(float dt) {
        if (!isPaused) {
            //user input handler
            input.inputProcess();

            world.step(STEP, 6, 2);
            player.update(dt);
            for(HazardSprite hazard : hazards) {
                hazard.update(dt);
                /*if(hazard.getX() < player.getX() + 224 / Safety4Kids.PPM)
                    hazard.b2body.setActive(true);*/
            }

            hud.update(dt);
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
     * This includes the Tiled Map, the box2d debugger, the camera position, and the onscreen Hud.  makes sure end conditions are met.
     *
     * @param delta The target frame rate minus the time taken to complete this frame is called the delta time, used to keep the frames consistant across platforms
     */
    @Override
    public void render(float delta) {

        //if the game is paused
        if (isPaused) {
            //resumes the game
            if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY))
                state = RUN;
        }
        switch (state) {
            case RUN:
                //if the esc is pressed then pauses or un-pauses the game
                if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                    if (!isPaused)
                        isPaused = true;
                    else if (isPaused)
                        isPaused = false;
                }

                if(!isPaused)
                    //If the timer is up
                    if(hud.isEndGame())
                        state = LOSE;
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
                for (HazardSprite hazard : hazards)
                    hazard.draw(game.batch);
                game.batch.end();

                //shows the screen based on the Camera with the hud
                game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
                hud.stage.draw();
                if(isPaused)
                    pause.stage.draw();


                if (player.b2body.getPosition().x > 37.5)
                    state = NEXT_LEVEL;
                break;
            case RETURN:
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(game));
                dispose();
                break;
            case NEXT_LEVEL:
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Level3WinScreen(game));
                dispose();
                break;
            case LOSE:
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Level3LoseScreen(game));
                dispose();
                break;
            default:
                break;
        }
    }

    /**
     * A method that creates all the hazards within the level
     */
    public void createHazards(){
        //create all hazards
        hazards = new Array<HazardSprite>();

        //Adds all stranger hazards
        hazards.add(new HazardSprite(this, 1400, 300, 1));
        hazards.add(new HazardSprite(this, 3200, 400, 1));
        //Adds all fire hazards
        hazards.add(new HazardSprite(this, 500, 200, 2));
        hazards.add(new HazardSprite(this, 1597, 200, 2));
        hazards.add(new HazardSprite(this, 1700, 200, 2));

        //Adds  lightning hazards
        hazards.add(new HazardSprite(this, 2147, 100, 2));
        hazards.add(new HazardSprite(this, 2469, 100, 1));
        hazards.add(new HazardSprite(this, 3450, 200, 3));

        hazards.add(new HazardSprite(this, 780, 200, 5));
        hazards.add(new HazardSprite(this, 1920, 270, 7));
        hazards.add(new HazardSprite(this, 3000, 400, 1));
        //Adds all knife hazards
        hazards.add(new HazardSprite(this, 930, 200, 5));
        hazards.add(new HazardSprite(this, 1800, 200, 5));
        hazards.add(new HazardSprite(this, 2780, 200, 5));
        //Adds all needle hazards
        hazards.add(new HazardSprite(this, 1500, 360, 6));
        hazards.add(new HazardSprite(this, 2300, 150, 6));
        hazards.add(new HazardSprite(this, 300, 400, 6));

        //Adds all skull hazards
        hazards.add(new HazardSprite(this, 880, 200, 7));
        hazards.add(new HazardSprite(this, 1967, 200, 7));
        //Adds all outlet hazards
        hazards.add(new HazardSprite(this, 1550, 380, 8));
        hazards.add(new HazardSprite(this, 2658, 200, 8));

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
        tiledMapRenderer.dispose();
        pause.dispose();
        hud.dispose();
        atlas.dispose();
        game.batch.dispose();
        game.dispose();
        hazards.clear();
    }

    /**
     * Returns the level's tilemap
     *
     * @return the tilemap to be returned.
     */
    public TiledMap getMap(){
        return map;
    }
}
