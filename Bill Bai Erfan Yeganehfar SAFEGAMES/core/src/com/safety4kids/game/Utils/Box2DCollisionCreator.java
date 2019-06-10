package com.safety4kids.game.Utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.safety4kids.game.Entities.HazardSprite;
import com.safety4kids.game.Safety4Kids;
import com.safety4kids.game.Screens.GameScreen;
import com.safety4kids.game.Screens.Level1Screen;
import com.safety4kids.game.Screens.Level3Screen;


/**
 * This method creates a Box2DCollisionCreator object, which takes in World and TiledMap information to create
 * boundaries, bodies, and fixture variables in the GameScreen.
 *
 * @author Erfan Yeganehfar
 * @author Bill Bai
 */
public class Box2DCollisionCreator {
    /**
     * World instance, which information is taken from to create boundaries.
     */
    private World world;
    /**
     * TiledMap instance, which information is taken from to create boundaries.
     */
    private TiledMap map;

    /**
     * This is the constructor. World and TiledMap instances of the GameScreen class are accessed, so that information
     * from the World and TiledMap, can be used to create boundaries, bodies, and fixture variables.
     *
     * @param screen The GameScreen object, where World and TiledMap instances are accessed for information.
     */
    public Box2DCollisionCreator(GameScreen screen) {
        world = screen.getWorld(); //Sets world to the GameScreen's World instance.


        //Sets map to the correct TiledMap instance, based on whether the Box2D boundaries are being created for
        // Level1 or Level2
        if (screen instanceof Level1Screen)
            map = ((Level1Screen) screen).getMap();
        else
            map = ((Level3Screen) screen).getMap();

        System.out.println(map);
        //Creates body and fixture variables which assign Objects their states within the world
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //Sets body and fixture variables to it's respective values based on the TiledMap's MapObject instance's information.
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Safety4Kids.PPM, (rect.getY() + rect.getHeight() / 2) / Safety4Kids.PPM);
            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Safety4Kids.PPM, rect.getHeight() / 2 / Safety4Kids.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

    }

}
