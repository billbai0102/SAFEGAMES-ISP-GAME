package com.safety4kids.game.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.safety4kids.game.OverLays.Hud;
import com.safety4kids.game.Screens.GameScreen;
import com.safety4kids.game.Utils.B2DConstants;

public class BreakableTile extends InteractiveTile {

    public BreakableTile(GameScreen screen, MapObject mapObj) {
        super(screen, mapObj);
        fixture.setUserData(this);
        setCatFilter(B2DConstants.BIT_BREAKABLE_BLOCK);

    }

    @Override
    public void onHatContact(MainPlayer player) {
        Gdx.app.log("bruh", "Collision");
        setCatFilter(B2DConstants.BIT_DESTROYED);
        getCell().setTile(null);
        Hud.addPoints(100);
    }
}
