package com.safety4kids.game.Entities;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.safety4kids.game.Safety4Kids;
import com.safety4kids.game.Screens.GameScreen;
import com.safety4kids.game.Utils.B2DConstants;
import com.safety4kids.game.Utils.InputHandler;

import static com.safety4kids.game.Safety4Kids.MAX_VELOCITY;
import static com.safety4kids.game.Safety4Kids.MIN_VELOCITY;


/**
 * This class represents the MainPlayer, it creates and instantiates the location of the player, the box2d body, the sprites, and the animation for them.
 * It is the control center for the main player.
 *
 * <h2>Course Info:</h2>
 * @version 4 2019-05-29
 * @author Erfan Yeganehfar
 * @author Bill Bai
 *
 * Ms. Krasteva
 *
 * Modifications:
 * 3.1 Erfan Yeg: (2019-05-29) Created the Box2d body and fixture for the main player, allowing for collsiion detection. -- 30 mins
 * 3.2 Erfan Yeg: (2019-05-31) created texture regions for the main players sprite animation states.
 * Allowing the Box2d body to bind with the sprite animations. -- 1hr
 * 3.3 Erfan Yeg: (2019-06-01) Binded the player sprite to the box2d body and also animated the running animation,
 * added the different states and fixed the infinite jumping. -- 3hr
 * 4.0 Finished the Player, fixed the box2d body position
 */
public class MainPlayer extends Sprite{

    /** The world that the player box 2d body is located in**/
    public World world;

    /** The players box 2d body**/
    public Body b2body;

    /**the x start position of the player**/
    private float startPosX;

    /**the y start position of the player**/
    private float startPosY;

    /** The idle texture region for the player**/
    private TextureRegion playerIdle;

    /** The running texture region animation for the player**/
    private Animation<TextureRegion> playerRun;

    /** The jumping texture region for the player**/
    private TextureRegion playerJump;

    /**
     * Enum that represents the states of the player
     */
    public enum State {
        FALLING, JUMPING, IDLE, RUNNING };

    /**the current state of the player**/
    public State currState;

    /**The previous state of the player**/
    public State prevState;

    /**The animation timer for the player**/
    private float timer;

    /**Determines if the player is facing right**/
    private boolean isRight;

    /** The screen on which the player is displayed on**/
    private GameScreen screen;

    /**The input handler for the player**/
    private InputHandler input;

    /**
     * The constructor for the MainPLayer class, it instantiates the screen and position of the sprite, as well as defines the box2d body,
     * and the texture animation for it
     *
     * @param screen the screen in which the player is drawn on
     * @param posX the x position of the player
     * @param posY the y position of the player
     */
    public MainPlayer(GameScreen screen, float posX, float posY){
        this.screen = screen;
        this.world = screen.getWorld();
        startPosX = posX;
        startPosY = posY;
        //sets the states to idle
        currState = State.IDLE;
        prevState = State.IDLE;
        timer = 0;
        isRight = true;

        input = new InputHandler(this);

        Array<TextureRegion> runAnimation = new Array<TextureRegion>();

        //get run animation frames and add them to marioRun Animation
        for(int i = 0; i < 10; i++) {
            runAnimation.add(new TextureRegion(screen.getAtlas().findRegion("running"), 10+(i * 32), 4, 14, 24));
        }
        playerRun = new Animation(0.075f, runAnimation);

        runAnimation.clear();

        playerIdle = new TextureRegion(screen.getAtlas().findRegion("idle"), 10, 4, 14, 24);
        playerJump = new TextureRegion(screen.getAtlas().findRegion("jump"), 9, 4, 15, 24);

        //creates and bounds the box2d body for the player
        creatBox2D();
        setBounds(startPosX / Safety4Kids.PPM,startPosY / Safety4Kids.PPM , 17f / Safety4Kids.PPM, 30f / Safety4Kids.PPM);
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

        //Now a fixture is made for collisions
        FixtureDef fdef = new FixtureDef();

        //The type of shape is assigned and defined
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(5f/Safety4Kids.PPM,13f/Safety4Kids.PPM);

        //Sets the filtering bits of the body as the Player bit category
        fdef.filter.categoryBits = B2DConstants.BIT_PLAYER;

        //Defines what the player can collide with
        fdef.filter.maskBits = B2DConstants.PLATFORM_BIT | B2DConstants.BIT_COIN
                | B2DConstants.BIT_BREAKABLE_BLOCK | B2DConstants.BIT_HAZARD | B2DConstants.BIT_OBJECT;

        //the shape is bound to the fixture, and the fixture to the body
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);


    }

    /**
     * Returns the Box2D body position in the Box2D world
     * @return the Box2D body position
     */
    public Vector2 getPosition() { return b2body.getPosition(); }

    /**
     * Gets the box 2ds body position
     * @return the box 2d body position
     */
    public float getXPos(){
        return b2body.getPosition().x;
    }

    /**
     * Applies an impulse to the player from the y coordinate allowing it to simulate jumping if the current state was not jumping/falling before
     */
    public void jump(){
        if ( currState != State.JUMPING && currState!= State.FALLING) {
            b2body.applyLinearImpulse(new Vector2(0, 3.8f), b2body.getWorldCenter(), true);
            currState = State.JUMPING;
        }
    }

    /**
     * Update used to update the position of the hazard as well as the current animation
     * @param dt uses delta time derived from the the cpu's speed indicating the frames of the game
     */
    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    /**
     * Based on the current state of the body (in the form of coordinates) the state of the body is returned
     * @return the state of the players body
     */
        public State getState(){
            //if the player is above the ground and the current state is jumping or if the players y value is falling and the state is jumping
        if((b2body.getLinearVelocity().y > 0 && currState == State.JUMPING) || (b2body.getLinearVelocity().y < 0 && prevState == State.JUMPING))
            return State.JUMPING;
            //if there is a negative value in the y axis
        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;
            //if the player is running at a positive or negative value in the x axis
        else if(b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
            //returns the state as idle if no other state is present
        else
            return State.IDLE;
    }

    /**
     * Gets the sprite frame of the player depending on the state a different animation key is returned.
     * @param dt uses delta time derived from the the cpu's speed indicating the frames of the game
     * @return returns the texture region that shoudl be displayed at the specified frame
     */
    public TextureRegion getFrame(float dt) {
        //get player current state. ie. jumping, running, standing...
        currState = getState();

        TextureRegion region;

        //depending on the state, get corresponding animation keyFrame.
        switch (currState) {
            case JUMPING:
                region = playerJump;
                if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) && b2body.getLinearVelocity().x <= MAX_VELOCITY)
                    input.moveRight();
                if ((Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) && b2body.getLinearVelocity().x >= MIN_VELOCITY)
                    input.moveLeft();
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

        //if player is running left and the texture isnt facing left... flip it.
        if((b2body.getLinearVelocity().x < 0 || !isRight) && !region.isFlipX()){
            region.flip(true, false);
            isRight = false;
        }

        //if player is running right and the texture isnt facing right... flip it.
        else if((b2body.getLinearVelocity().x > 0 || isRight) && region.isFlipX()){
            region.flip(true, false);
            isRight = true;
        }

        //if the current state is the same as the previous state increase the state timer
        //otherwise the state has changed and the timer is reset
        timer = currState == prevState ? timer + dt : 0;
        //update previous state
        prevState = currState;

        //the texture region is returned
        return region;
    }

    /**
     * gets the timers value
     * @return the value of the timer
     */
    public float getTimer(){
        return timer;
    }


}
