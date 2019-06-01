package com.safety4kids.game.Entities;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.safety4kids.game.Safety4Kids;
import com.safety4kids.game.Screens.Level1Screen;


/**
 *
 * @version 3.2 2019-05-29
 * @author Erfan Yeganehfar
 * Ms. Krasteva
 *
 * Modifications:
 * 3.1 Erfan Yeg: (2019-05-29) Created the Box2d body and fixture for the main player, allowing for collsiion detection. -- 30 mins
 * 3.2 Erfan Yeg: (2019-05-31) created texture regions for the main players sprite animation states.
 * Allowing the Box2d body to bind with the sprite animations. -- 1hr
 */
public class MainPlayer extends Sprite {
    public World world;
    public Body b2body;

    private TextureRegion playerIdle;
    private Animation<TextureRegion> playerRun;
    private TextureRegion playerJump;
    public enum State {
        FALLING, JUMPING, IDLE, RUNNING,
    };
    public State currState;
    public State prevState;

    private float timer;
    private boolean isRight;
    private Level1Screen screen;

    public MainPlayer(Level1Screen screen){
        this.screen = screen;
        this.world = screen.getWorld();
        currState = State.IDLE;
        prevState = State.IDLE;
        timer = 0;
        isRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();


        //get run animation frames and add them to marioRun Animation
        for(int i = 1; i < 4; i++)
//            frames.add(new TextureRegion(screen.getAtlas().findRegion("MainPlayer"), i * 16, 0, 16, 16));
        playerRun = new Animation(0.1f, frames);

        frames.clear();
        definePlayer();
    }

    public void definePlayer(){

        BodyDef bdef = new BodyDef();
        bdef.position.set(500 / Safety4Kids.PPM,340/ Safety4Kids.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();

        PolygonShape shape = new PolygonShape();

        shape.setAsBox(7f/Safety4Kids.PPM,12f/Safety4Kids.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

    public Vector2 getPosition() { return b2body.getPosition(); }

    public void jump(){
        if ( currState != State.JUMPING ) {
            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
            currState = State.JUMPING;
        }
    }

}
