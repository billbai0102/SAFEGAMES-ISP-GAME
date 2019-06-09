package com.safety4kids.game.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.safety4kids.game.OverLays.Hud;
import com.safety4kids.game.Safety4Kids;
import com.safety4kids.game.Screens.GameScreen;
import com.safety4kids.game.Screens.Level1Screen;
import com.safety4kids.game.Screens.Level3Screen;
import com.safety4kids.game.Utils.B2DConstants;

public abstract class InteractiveTile {
    protected World world;
    protected static TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle border;
    protected static Body body;
    protected static Fixture fixture;
    protected MapObject object;
    protected GameScreen screen;

    public InteractiveTile(GameScreen screen, MapObject object) {
        this.object = object;
        this.screen = screen;
        this.world = screen.getWorld();
        if (screen instanceof Level1Screen)
            map = ((Level1Screen )screen ).getMap();
        else
            map = ((Level3Screen)screen ).getMap();
        this.border = ((RectangleMapObject) object).getRectangle();;

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

    /**
     * This Method invokes an action to be done based on the object the players hat collided with
     */
    public abstract void onHatContact(MainPlayer player);

    /**
     * Sets the filter bit for a box2d fixture
     * @param bit the filter bit to be set as the fixtures filter
     */
    public static void setCatFilter(short bit){
        Filter filter = new Filter();
        filter.categoryBits = bit;
        fixture.setFilterData(filter);

    }

    /**
     * Gets the tile cell of the players box2d body
     * @return The players cell location
     */
    public static TiledMapTileLayer.Cell getCell(){
        //Type casts the map layer to a TiledMapTileLayer to gain access to the specified tile
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);

        //using the PPM and tile size the cell position of the player is returned
        return  layer.getCell((int)(body.getPosition().x * Safety4Kids.PPM / 16),
                (int)(body.getPosition().y * Safety4Kids.PPM / 16));
    }
}
