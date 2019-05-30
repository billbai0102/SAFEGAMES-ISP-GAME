package com.safety4kids.game.Entities;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.safety4kids.game.Safety4Kids;

import java.awt.geom.RectangularShape;

public class MainPlayer extends Sprite {
    public World world;
    public Body b2body;


    public MainPlayer(World world){
        this.world = world;
        definePlayer();
    }

    public void definePlayer(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(500 / Safety4Kids.PPM,340/ Safety4Kids.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10/ Safety4Kids.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }



}
