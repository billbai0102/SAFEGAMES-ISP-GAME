package com.safety4kids.game.Entities;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.safety4kids.game.Safety4Kids;

public abstract class InteractiveTile {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle border;
    protected Body body;
    protected Fixture fixture;

    public InteractiveTile(World world, TiledMap map, Rectangle border) {
        this.world = world;
        this.map = map;
        this.border = border;
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((border.getX()+border.getWidth()/2)/ Safety4Kids.PPM, (border.getY() + border.getHeight() / 2)/ Safety4Kids.PPM);
        body = world.createBody(bdef);

        shape.setAsBox(border.getWidth()/2/ Safety4Kids.PPM,border.getHeight()/2/ Safety4Kids.PPM);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);
    }


    public abstract void onHatContact();
}
