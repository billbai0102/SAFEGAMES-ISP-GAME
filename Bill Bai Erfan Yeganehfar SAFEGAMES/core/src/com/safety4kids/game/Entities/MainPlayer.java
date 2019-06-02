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
 * @version 3.3 2019-05-29
 * @author Erfan Yeganehfar
 * Ms. Krasteva
 *
 * Modifications:
 * 3.1 Erfan Yeg: (2019-05-29) Created the Box2d body and fixture for the main player, allowing for collsiion detection. -- 30 mins
 * 3.2 Erfan Yeg: (2019-05-31) created texture regions for the main players sprite animation states.
 * Allowing the Box2d body to bind with the sprite animations. -- 1hr
 * 3.3 Erfan Yeg: (2019-06-01) Binded the player sprite to the box2d body and also animated the running animation,
 * added the different states and fixed the infinite jumping. -- 3hr
 */
public class MainPlayer extends Sprite {
    public World world;
    public Body b2body;
    private float startPosX;
    private float startPosY;

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

    public MainPlayer(Level1Screen screen, float posX, float posY){
        this.screen = screen;
        this.world = screen.getWorld();
        startPosX = posX;
        startPosY = posY;
        currState = State.IDLE;
        prevState = State.IDLE;
        timer = 0;
        isRight = true;

        Array<TextureRegion> runAnimation = new Array<TextureRegion>();

        //get run animation frames and add them to marioRun Animation
        for(int i = 0; i < 10; i++) {
            runAnimation.add(new TextureRegion(screen.getAtlas().findRegion("running"), 10+(i * 32), 4, 14, 24));
        }
        playerRun = new Animation(0.1f, runAnimation);

        runAnimation.clear();

        playerIdle = new TextureRegion(screen.getAtlas().findRegion("idle"), 10, 4, 14, 24);
        playerJump = new TextureRegion(screen.getAtlas().findRegion("jump"), 9, 4, 15, 24);

        creatBox2D();

        setBounds(startPosX / Safety4Kids.PPM,startPosY/ Safety4Kids.PPM, 17 / Safety4Kids.PPM, 30 / Safety4Kids.PPM);
        setRegion(playerIdle);
    }


    /**
     * Creates the Box2d Fixture and Body for the player, assigns the location, the shape, and the type of Body.
     */
    public void creatBox2D(){

        //Defined Body
        BodyDef bdef = new BodyDef();
        //Position of the body
        bdef.position.set(startPosX / Safety4Kids.PPM,startPosY/ Safety4Kids.PPM);

        //the type of Body is dynamic (therefore it can move)
        bdef.type = BodyDef.BodyType.DynamicBody;

        //The body is created into the Box2D world
        b2body = world.createBody(bdef);

        //Now a fixure is made for collisions
        FixtureDef fdef = new FixtureDef();

        //The type of shape is assigned and defined
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(7f/Safety4Kids.PPM,13f/Safety4Kids.PPM);

        //the shape is bound to the fixture, and the fixture to the body
        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

    /**
     * Returns the Box2D body position in the Box2D world
     * @return the Box2D body position
     */
    public Vector2 getPosition() { return b2body.getPosition(); }

    public void jump(){
        if ( currState != State.JUMPING && currState!= State.FALLING) {
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
            case RUNNING:
               region = playerRun.getKeyFrame(timer, true);
                break;
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

    public float Timer(){
        return timer;
    }

}
