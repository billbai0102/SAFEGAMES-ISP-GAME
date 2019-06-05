package com.safety4kids.game.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.safety4kids.game.Utils.B2DConstants;

public class BreakableTile extends InteractiveTile {

    public BreakableTile(World world, TiledMap map, Rectangle border) {
        super(world, map, border);
        fixture.setUserData(this);
        setCatFilter(B2DConstants.BIT_BREAKABLE_BLOCK);

    }

    @Override
    public void onHatContact() {
        Gdx.app.log("bruh", "Collision");
        setCatFilter(B2DConstants.BIT_DESTROYED);
        getCell().setTile(null);
    }
}
