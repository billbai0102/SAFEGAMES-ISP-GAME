package com.safety4kids.game.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.safety4kids.game.Safety4Kids;
import static com.safety4kids.game.Safety4Kids.*;

/**
 * This Class represents the states that both level 1 and 2 inherit from.
 * This includes the base game functionalitiy like the gameCam, the sprite batches, the viewPorts and the Box2d World
 *
 * @version 3.1 2019-05-31
 * @author Erfan Yeganehfar
 * Ms. Krasteva
 *
 * Modifications:
 * 3.1 Erfan Yeg: (2019-05-31) Created this Class for ease of use/ reasuability in the levels. -- 30mins
 * 3.2 Erfan Yeg: (2019-05-2) Added states to the levels, allowing the user to exit to the main menu -- 15mis
 */
public abstract class GameScreen implements Screen {

    public enum GameState
    {
        PAUSE,
        NEXT_LEVEL,
        RUN,
        RESUME,
        RETURN
    }
    public GameState state;
    protected Safety4Kids game;
    protected SpriteBatch batch;
    protected OrthographicCamera gameCam;
    protected Viewport gamePort;
    //Box2d collision detection instance variables
    protected World world;
    protected Box2DDebugRenderer b2dr;

    /**
     * The constructor creates all the base necessary components of the platformer. Which is the actual game,
     * the sprite batches, game camera, viewport, and a Box2d world
     */
    public GameScreen(){
        game = new Safety4Kids();
        game.batch = new SpriteBatch();
        batch =  game.batch;
        state = GameState.RUN;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(V_WIDTH / PPM, V_HEIGHT / PPM, gameCam);

        //sets the view point of the Orthographic Camera to better use of the 4 quadrants within a 2d grid system
        gameCam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2,0);

        world = new World(new Vector2(0,CONST_GRAVITY),true);
        b2dr = new Box2DDebugRenderer();
    }

    public abstract void update(float dt);
    public abstract void render(float delta);
    public abstract void resize(int width, int height);
    public abstract void dispose();

}
