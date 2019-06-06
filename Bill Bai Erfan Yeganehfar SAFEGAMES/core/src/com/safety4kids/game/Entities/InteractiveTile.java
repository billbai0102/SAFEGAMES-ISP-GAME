package com.safety4kids.game.Entities;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
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

    /**
     * This Method invokes an action to be done based on the object the players hat collided with
     */
    public abstract void onHatContact();

    /**
     * Sets the filter bit for a box2d fixture
     * @param bit the filter bit to be set as the fixtures filter
     */
    public void setCatFilter(short bit){
        Filter filter = new Filter();
        filter.categoryBits = bit;
        fixture.setFilterData(filter);

    }

    /**
     * Gets the tile cell of the players box2d body
     * @return The players cell location
     */
    public TiledMapTileLayer.Cell getCell(){
        //Type casts the map layer to a TiledMapTileLayer to gain access to the specified tile
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);

        //using the PPM and tile size the cell position of the player is returned
        return  layer.getCell((int)(body.getPosition().x * Safety4Kids.PPM / 16),
                (int)(body.getPosition().y * Safety4Kids.PPM / 16));
    }
}
