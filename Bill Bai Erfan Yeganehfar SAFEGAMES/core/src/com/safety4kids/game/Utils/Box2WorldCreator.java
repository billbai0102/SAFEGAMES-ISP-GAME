package com.safety4kids.game.Utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.safety4kids.game.Safety4Kids;

public class Box2WorldCreator {

    public Box2WorldCreator(World world, TiledMap map) {
        //creates body and fixture variables which assign Objects their states within the world
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/ Safety4Kids.PPM, (rect.getY() + rect.getHeight() / 2)/ Safety4Kids.PPM);
            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/2/ Safety4Kids.PPM,rect.getHeight()/2/ Safety4Kids.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/ Safety4Kids.PPM, (rect.getY() + rect.getHeight() / 2)/ Safety4Kids.PPM);
            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/2/ Safety4Kids.PPM,rect.getHeight()/2/ Safety4Kids.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/ Safety4Kids.PPM, (rect.getY() + rect.getHeight() / 2)/ Safety4Kids.PPM);
            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/2/ Safety4Kids.PPM,rect.getHeight()/2/ Safety4Kids.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

    }
}
