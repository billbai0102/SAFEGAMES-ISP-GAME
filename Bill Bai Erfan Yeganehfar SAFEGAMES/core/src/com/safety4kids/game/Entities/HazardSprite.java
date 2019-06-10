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
/**
 * This Class represents the main hazard type, it defines the textures, box2d body, and updates the position and texture regions of the hazard
 *
 * <h2>Course Info:</h2>
 *
 * @version 4.2 2019-06-02
 * @author Erfan Yeganehfar
 * @author Bill Bai
 *
 * Ms. Krasteva
 *
 * Modifications:
 * 4.0 Finised the constructor added instance variables, and defined the hazrad in box2d
 * 4.1 Added the texture region of the hazard
 * 4.2 Added the different types of hazards and their texture regions
 */
public class HazardSprite extends Hazard {

    /** The current amount of time in which the hazards animation is taking**/
    private float time;

    /** The texture region array used to read the textures from the .pack file **/
    private Array<TextureRegion> frames;

    /**The actual animation set of texture regions used to animate the sprite **/
    private Animation<TextureRegion> move;

    /**The type of hazard that this class represents**/
    private int type;

    /**
     * The constructor for the HazardSprite class, it instantiates the screen and position of the sprite, as well as defines the box2d body,
     * and the texture animation for it
     *
     * @param screen the screen in which the hazard is drawn on
     * @param x the x location of the hazard
     * @param y the y loaction of the hazard
     * @param type the type of hazard
     */
    public HazardSprite(GameScreen screen, float x, float y, int type) {
        super(screen, x, y);
        this.type = type;

        //Switch used to draw the different types of hazards
        switch (type) {
            case 1:
            default:
                frames = new Array<TextureRegion>();
                for (int i = 0; i < 6; i++)
                    frames.add(new TextureRegion(screen.getAtlas().findRegion("Safety4Kids Stranger Sprite"), 10 + (i * 32), 4, 14, 24));
                move = new Animation(0.8f, frames);
                setBounds(x / Safety4Kids.PPM, y / Safety4Kids.PPM, 17 / Safety4Kids.PPM, 30 / Safety4Kids.PPM);
                break;
            case 2:
                frames = new Array<TextureRegion>();
                for (int i = 0; i < 3; i++)
                    frames.add(new TextureRegion(screen.getAtlas().findRegion("SAFEGAMES Fire Animation"), 1 + (i * 32), 5, 18, 18));
                move = new Animation(0.07f, frames);
                setBounds(x / Safety4Kids.PPM, y / Safety4Kids.PPM, 20 / Safety4Kids.PPM, 22 / Safety4Kids.PPM);
                break;
            case 3:
                frames = new Array<TextureRegion>();
                for (int i = 0; i < 7; i++)
                    frames.add(new TextureRegion(screen.getAtlas().findRegion("PoisonSprite"), 9 + (i * 32), 9, 9, 24));
                move = new Animation(0.07f, frames);
                setBounds(x / Safety4Kids.PPM, y / Safety4Kids.PPM, 9 / Safety4Kids.PPM, 24 / Safety4Kids.PPM);
                break;
            case 4:
                frames = new Array<TextureRegion>();
                for (int i = 0; i < 7; i++)
                    frames.add(new TextureRegion(screen.getAtlas().findRegion("SAFEGAMES Lightning Sprite"), 5 + (i * 32), 17, 19, 15));
                move = new Animation(0.07f, frames);
                setBounds(x / Safety4Kids.PPM, y / Safety4Kids.PPM, 19 / Safety4Kids.PPM, 15 / Safety4Kids.PPM);
                break;
            case 5:
                frames = new Array<TextureRegion>();
                for (int i = 0; i < 15; i++)
                    frames.add(new TextureRegion(screen.getAtlas().findRegion("KnifeSprite"), 11 + (i * 32), 8, 14, 17));
                move = new Animation(0.07f, frames);
                setBounds(x / Safety4Kids.PPM, y / Safety4Kids.PPM, 14 / Safety4Kids.PPM, 17 / Safety4Kids.PPM);
                break;
            case 6:
                frames = new Array<TextureRegion>();
                for (int i = 0; i < 5; i++)
                    frames.add(new TextureRegion(screen.getAtlas().findRegion("NeedleSprite"), 15 + (i * 32), 5, 5, 23));
                move = new Animation(0.1f, frames);
                setBounds(x / Safety4Kids.PPM, y / Safety4Kids.PPM, 5 / Safety4Kids.PPM, 23 / Safety4Kids.PPM);
                break;
            case 7:
                frames = new Array<TextureRegion>();
                for (int i = 0; i < 6; i++)
                    frames.add(new TextureRegion(screen.getAtlas().findRegion("SkullSprite"), 8 + (i * 32), 7, 14, 18));
                move = new Animation(0.1f, frames);
                setBounds(x / Safety4Kids.PPM, y / Safety4Kids.PPM, 13 / Safety4Kids.PPM, 17 / Safety4Kids.PPM);
                break;
            case 8:
                frames = new Array<TextureRegion>();
                for (int i = 0; i < 12; i++)
                    frames.add(new TextureRegion(screen.getAtlas().findRegion("PowerOutletSprite"), 8 + (i * 32), 2, 17, 27));
                move = new Animation(0.07f, frames);
                setBounds(x / Safety4Kids.PPM, y / Safety4Kids.PPM, 20 / Safety4Kids.PPM, 27 / Safety4Kids.PPM);
                break;

        }
        time = 0;

    }

    /**
     * Update used to update the position of the hazard as well as the current animation
     * @param dt uses delta time derived from the the cpu's speed indicating the frames of the game
     */
    public void update(float dt) {
        time += dt;
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(move.getKeyFrame(time, true));

    }

    /**
     * Creates the hazards box2d body inside the screen box2d world
     */
    @Override
    protected void createHazard() {
        //Defined Body
        BodyDef bdef = new BodyDef();
        //Position of the body
        bdef.position.set(getX() / Safety4Kids.PPM, getY() / Safety4Kids.PPM);

        //the type of Body is dynamic (therefore it can move)
        bdef.type = BodyDef.BodyType.DynamicBody;

        //The body is created into the Box2D world
        b2body = world.createBody(bdef);

        //Now a fixture is made for collisions
        FixtureDef fdef = new FixtureDef();

        //The type of shape is assigned and defined
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(11f / Safety4Kids.PPM, 10f / Safety4Kids.PPM);

        //Sets the filtering bits of the body as the hazards bit category
        fdef.filter.categoryBits = B2DConstants.BIT_HAZARD;
        //Defines what the hazards can collide with
        fdef.filter.maskBits = B2DConstants.PLATFORM_BIT | B2DConstants.BIT_COIN |
                B2DConstants.BIT_BREAKABLE_BLOCK | B2DConstants.BIT_HAZARD |
                B2DConstants.BIT_OBJECT | B2DConstants.BIT_PLAYER;

        //the shape is bound to the fixture, and the fixture to the body
        fdef.shape = shape;
        b2body.createFixture(fdef);


    }

    /**
     * The batch to be drawn to the screen
     * @param batch the batch to be drawn
     */
    public void draw(Batch batch) {
        super.draw(batch);
    }
}
