package com.safety4kids.game.Entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.safety4kids.game.Safety4Kids;
import com.safety4kids.game.Screens.GameScreen;
import com.safety4kids.game.Utils.B2DConstants;

public class HazardSprite extends Hazard {

    private float time;
    private Array<TextureRegion> frames;
    private Animation<TextureRegion> move;
    private float x;
    private float y;
    private GameScreen screen;
    private String type;


    public HazardSprite(GameScreen screen, float x, float y, String type) {
        super(screen, x, y);
        this.x = x;
        this.y = y;
        this.screen = screen;
        //TODO Add animation.
        frames = new Array<TextureRegion>();
        for(int i = 0; i < 10; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("running"), 10+(i * 32), 4, 14, 24));
        }
        move = new Animation(0.075f, frames);
        time = 0;

        setBounds(getX(), getY(), 17 / Safety4Kids.PPM, 30 / Safety4Kids.PPM);
    }

    public void update(float dt){
        time += dt;
        b2body.setLinearVelocity(velocity);
        setPosition(b2body.getPosition().x - getWidth() / 2,b2body.getPosition().y - getHeight() / 2 );
        setRegion(move.getKeyFrame(time, true));

    }
    @Override
    protected void createHazard() {
        //Defined Body
        BodyDef bdef = new BodyDef();
        //Position of the body
        bdef.position.set(getX() / Safety4Kids.PPM, getY()/Safety4Kids.PPM);

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
        fdef.filter.categoryBits = B2DConstants.BIT_HAZARD;
        //Defines what the player can
        fdef.filter.maskBits = B2DConstants.PLATFORM_BIT | B2DConstants.BIT_COIN |
               B2DConstants.BIT_BREAKABLE_BLOCK | B2DConstants.BIT_HAZARD |
                B2DConstants.BIT_OBJECT | B2DConstants.BIT_PLAYER;

        //the shape is bound to the fixture, and the fixture to the body
        fdef.shape = shape;
        b2body.createFixture(fdef);

        PolygonShape sensor = new PolygonShape();
        sensor.setAsBox(10f/Safety4Kids.PPM,-10f/Safety4Kids.PPM);
        fdef.filter.categoryBits = B2DConstants.BIT_HAZARD;
        fdef.filter.maskBits = B2DConstants.PLATFORM_BIT | B2DConstants.BIT_COIN |
                B2DConstants.BIT_BREAKABLE_BLOCK | B2DConstants.BIT_HAZARD |
                B2DConstants.BIT_OBJECT | B2DConstants.BIT_PLAYER;
        fdef.shape = sensor;
        fdef.restitution = 0.5f;
        fdef.isSensor = false;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void draw(Batch batch){
        super.draw(batch);
    }
}
