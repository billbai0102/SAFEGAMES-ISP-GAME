package com.safety4kids.game.Entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
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
    private int type;


    public HazardSprite(GameScreen screen, float x, float y, int type) {
        super(screen, x, y);
        this.x = x;
        this.y = y;
        this.screen = screen;
        System.out.println("b" + type);
        switch (type){
            case 1:
                default:
                frames = new Array<TextureRegion>();
                for (int i = 0; i < 6; i++)
                    frames.add(new TextureRegion(screen.getAtlas().findRegion("Safety4Kids Stranger Sprite"), 10 + (i * 32), 4, 14, 24));
                move = new Animation(1f, frames);
                setBounds(x / Safety4Kids.PPM, y / Safety4Kids.PPM, 17 / Safety4Kids.PPM, 30 / Safety4Kids.PPM);
                break;
            case 2:
                frames = new Array<TextureRegion>();
                for (int i = 0; i < 3; i++)
                    frames.add(new TextureRegion(screen.getAtlas().findRegion("SAFEGAMES Fire Animation"),  1+ (i * 32), 5, 18, 18));
                move = new Animation(0.07f, frames);
                setBounds(x / Safety4Kids.PPM, y / Safety4Kids.PPM, 20 / Safety4Kids.PPM, 22/ Safety4Kids.PPM);
                break;
            case 3:
                frames = new Array<TextureRegion>();
                for (int i = 0; i < 7; i++)
                    frames.add(new TextureRegion(screen.getAtlas().findRegion("PoisonSprite"),  9 + (i * 32), 3, 9, 24));
                move = new Animation(0.07f, frames);
                setBounds(x - 20/ Safety4Kids.PPM, y / Safety4Kids.PPM, 9 / Safety4Kids.PPM, 24 / Safety4Kids.PPM);
                break;
            case 4:
                frames = new Array<TextureRegion>();
                for (int i = 0; i < 7; i++)
                    frames.add(new TextureRegion(screen.getAtlas().findRegion("SAFEGAMES Lightning Sprite"),  5 + (i * 32), 17, 19, 15));
                move = new Animation(0.07f, frames);
                setBounds(x - 20/ Safety4Kids.PPM, y / Safety4Kids.PPM, 19 / Safety4Kids.PPM, 15 / Safety4Kids.PPM);
                break;
            case 5:
                frames = new Array<TextureRegion>();
                for(int i = 0; i < 15; i++)
                    frames.add(new TextureRegion(screen.getAtlas().findRegion("KnifeSprite.png"), 11 + (i*32), 8, 14 ,17));
                break;
            case 6:
                break;

        }
        time = 0;

    }

    public void update(float dt){
        time += dt;
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

        switch(type) {
            case 1:
                default:
                shape.setAsBox(5f / Safety4Kids.PPM, 13f / Safety4Kids.PPM);
                break;
            case 2:
            case 3:
                shape.setAsBox(9f / Safety4Kids.PPM, 9f / Safety4Kids.PPM);
                break;
        }


        //Sets the filtering bits of the body as the Player bit category
        fdef.filter.categoryBits = B2DConstants.BIT_HAZARD;
        //Defines what the player can
        fdef.filter.maskBits = B2DConstants.PLATFORM_BIT | B2DConstants.BIT_COIN |
               B2DConstants.BIT_BREAKABLE_BLOCK | B2DConstants.BIT_HAZARD |
                B2DConstants.BIT_OBJECT | B2DConstants.BIT_PLAYER;

        //the shape is bound to the fixture, and the fixture to the body
        fdef.shape = shape;
        b2body.createFixture(fdef);


    }

    public void draw(Batch batch){
        super.draw(batch);
    }
}
