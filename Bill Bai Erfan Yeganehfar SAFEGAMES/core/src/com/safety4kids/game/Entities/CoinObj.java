package com.safety4kids.game.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;

public class CoinObj extends InteractiveTile {

    public CoinObj(World world, TiledMap map, Rectangle border) {
        super(world, map, border);
        fixture.setUserData(this);

    }

    @Override
    public void onHatContact() {
        Gdx.app.log("coin", "Collision");
    }
}
