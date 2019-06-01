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
        FALLING, JUMPING, IDLE, RUNNING}
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
        for(int i = 2; i < 12; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("sprite"), 78 + i * 16, 4, 14, 24));
        frames.clear();

        playerRun = new Animation(0.1f, frames);
        playerIdle = new TextureRegion(screen.getAtlas().findRegion("idle"), 10, 4, 14, 24);
        playerJump = new TextureRegion(screen.getAtlas().findRegion("jump"), 44, 4, 14, 24);

        definePlayer();

        setBounds(500 / Safety4Kids.PPM,300/ Safety4Kids.PPM, 14 / Safety4Kids.PPM, 24 / Safety4Kids.PPM);
        setRegion(playerIdle);
    }

    public void definePlayer(){

        BodyDef bdef = new BodyDef();
        bdef.position.set(500 / Safety4Kids.PPM,300/ Safety4Kids.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();

        PolygonShape shape = new PolygonShape();

        shape.setAsBox(7f/Safety4Kids.PPM,13f/Safety4Kids.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

    public Vector2 getPosition() { return b2body.getPosition(); }

    public void jump(){
        if ( currState != State.JUMPING ) {
            b2body.applyLinearImpulse(new Vector2(0, 3.8f), b2body.getWorldCenter(), true);
            currState = State.JUMPING;
        }
    }

    public void update(float dt) {

        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);

        setRegion(getFrame(dt));


    }

        public State getState(){
        if((b2body.getLinearVelocity().y > 0 && currState == State.JUMPING) || (b2body.getLinearVelocity().y < 0 && prevState == State.JUMPING))
            return State.JUMPING;
            //if there is a negative value in the y Axis
        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;
            //if the player is running at a positive or negative value in the x axis
        else if(b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
            //returns the state as idle if no other state is present
        else
            return State.IDLE;
    }

    public TextureRegion getFrame(float dt) {
        //get marios current state. ie. jumping, running, standing...
        currState = getState();

        TextureRegion region;

        //depending on the state, get corresponding animation keyFrame.
        switch (currState) {
            case JUMPING:
                region = playerJump;
                break;
           /* case RUNNING:
                region = playerRun.getKeyFrame(timer,timer);
                break;*/
            case FALLING:
            case IDLE:
            default:
                region = playerIdle;
                break;
        }

        //if mario is running left and the texture isnt facing left... flip it.
        if((b2body.getLinearVelocity().x < 0 || !isRight) && !region.isFlipX()){
            region.flip(true, false);
            isRight = false;
        }

        //if mario is running right and the texture isnt facing right... flip it.
        else if((b2body.getLinearVelocity().x > 0 || isRight) && region.isFlipX()){
            region.flip(true, false);
            isRight = true;
        }

        //if the current state is the same as the previous state increase the state timer.
        //otherwise the state has changed and we need to reset timer.
        timer = currState == prevState ? timer + dt : 0;
        //update previous state
        prevState = currState;
        //return our final adjusted frame
        return region;
    }

}
