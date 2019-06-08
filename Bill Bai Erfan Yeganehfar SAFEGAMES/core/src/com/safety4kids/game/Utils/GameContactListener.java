package com.safety4kids.game.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.safety4kids.game.Entities.*;

public class GameContactListener implements ContactListener {
    private MainPlayer player;

    public GameContactListener(MainPlayer player) {
        this.player = player;
    }

    @Override
    public void beginContact(Contact contact) {
        //The two fixtures that are to be colliding
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureA();

        int collide = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;


//        switch(collide){
//            case B2DConstants.BIT_PLAYER_HAT | B2DConstants.BIT_BREAKABLE_BLOCK:
//            case B2DConstants.BIT_PLAYER_HAT | B2DConstants.BIT_COIN:
//                Gdx.app.log("obj", "collide");
//                if(fixA.getFilterData().categoryBits == B2DConstants.BIT_PLAYER_HAT) {
//                    Gdx.app.log("obj", "collide");
//                    ((InteractiveTile) fixB.getUserData()).onHatContact((MainPlayer) fixA.getUserData());
//                }else
//                    ((InteractiveTile) fixA.getUserData()).onHatContact((MainPlayer) fixB.getUserData());
//
//                break;
//            case B2DConstants.BIT_HAZARD | B2DConstants.BIT_OBJECT:
//            if(fixA.getFilterData().categoryBits == B2DConstants.BIT_HAZARD){
//                Gdx.app.log("obj", "collide");
//                ((Hazard)fixA.getUserData()).reverseVelocity(true, false);}
//            else
//                ((Hazard)fixB.getUserData()).reverseVelocity(true, false);
//            break;
//        }

        //If one of the fixtures is the players hat
        if (fixA.getUserData() != null && fixB.getUserData() != null) {
            if ((fixA.getUserData().equals("hat") || fixB.getUserData().equals("hat") && (fixA.getUserData().equals("breakable") || fixB.getUserData().equals("breakable")))) {

                if (fixA.getFilterData().categoryBits == B2DConstants.BIT_PLAYER_HAT) {

                    InteractiveTile.onHatContact(player);
                } else
                    InteractiveTile.onHatContact(player);
            }
        }
        Gdx.app.log("obj1", fixB.getUserData()+"");
        Gdx.app.log("obj2", fixA.getUserData()+"");
        if (fixA.getUserData() != null && fixB.getUserData() != null) {
            if ((fixA.getUserData() instanceof Hazard || fixA.getUserData()instanceof Hazard)) {
                Gdx.app.log("obj", "collide");
                if (fixA.getFilterData().categoryBits == B2DConstants.BIT_HAZARD) {
                    ((Hazard) fixA.getUserData()).reverseVelocity(true, false);
                }
            } else if  (fixB.getFilterData().categoryBits == B2DConstants.BIT_HAZARD)
                ((Hazard) fixB.getUserData()).reverseVelocity(true, false);
        }



    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
