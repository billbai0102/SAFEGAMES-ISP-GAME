package com.safety4kids.game.Entities;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;

public class BreakableTile extends InteractiveTile {

    public BreakableTile(World world, TiledMap map, Rectangle border) {
        super(world, map, border);
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
    }
}
