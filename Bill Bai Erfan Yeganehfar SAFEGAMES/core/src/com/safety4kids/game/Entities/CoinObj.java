package com.safety4kids.game.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.safety4kids.game.Screens.GameScreen;
import com.safety4kids.game.Utils.B2DConstants;

public class CoinObj extends InteractiveTile {

    public CoinObj(GameScreen screen, MapObject mapObj) {
        super(screen, mapObj);
        fixture.setUserData("coin");
        setCatFilter(B2DConstants.BIT_COIN);
    }

    @Override
    public void onHatContact(MainPlayer player) {
        Gdx.app.log("coin", "Collision");
    }
}
