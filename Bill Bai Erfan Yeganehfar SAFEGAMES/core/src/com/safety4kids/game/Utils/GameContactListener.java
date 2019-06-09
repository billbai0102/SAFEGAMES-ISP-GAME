package com.safety4kids.game.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.safety4kids.game.Entities.Hazard;
import com.safety4kids.game.Entities.InteractiveTile;
import com.safety4kids.game.Entities.MainPlayer;

/**
 * This class creates a <i>GameContactListener</i> object.
 * <br> The <i>GameContactListener</i> instance will create different outputs in the game based on what Contact object
 * a <i>MainPlayer</i> object collides with.
 *
 * @author Erfan Yeganehfar, Bill Bai
 * @version 3.3 06/09/19
 */
public class GameContactListener implements ContactListener {
    /**
     * The instance of the <i>MainPlayer</i>, that will be
     */
    private MainPlayer player;

    /**
     * The constructor of the class. Sets the <i>MainPlayer</i> passed in the parameters to the <i>MainPlayer</i> instance
     * of the class.
     *
     * @param player Instance of the <i>MainPlayer</i>
     */
    public GameContactListener(MainPlayer player) {
        this.player = player;
    }

    /**
     * This method is called when the <i>MainPlayer</i> object makes contact with a Contact object.
     *
     * @param contact The Contact object that is being made contact with.
     */
    @Override
    public void beginContact(Contact contact) {
        //The two fixtures that are to be colliding
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureA();

        // bitwise or to define the type of collision between 2 collision types
        int collide = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;


        switch(collide){
            case B2DConstants.BIT_PLAYER_HAT | B2DConstants.BIT_BREAKABLE_BLOCK:
            case B2DConstants.BIT_PLAYER_HAT | B2DConstants.BIT_COIN:
                Gdx.app.log("obj", "collide");
                if(fixA.getFilterData().categoryBits == B2DConstants.BIT_PLAYER_HAT) {
                    Gdx.app.log("obj", "collide");
                    ((InteractiveTile) fixB.getUserData()).onHatContact((MainPlayer) fixA.getUserData());
                }else
                    ((InteractiveTile) fixA.getUserData()).onHatContact((MainPlayer) fixB.getUserData());

                break;
            case B2DConstants.BIT_HAZARD | B2DConstants.BIT_OBJECT:
            if(fixA.getFilterData().categoryBits == B2DConstants.BIT_HAZARD){
                Gdx.app.log("obj", "collide");
                ((Hazard)fixA.getUserData()).reverseVelocity(true, false);}
            else
                ((Hazard)fixB.getUserData()).reverseVelocity(true, false);
            break;
        }

     /*   //If one of the fixtures is the players hat
        if (fixA.getUserData() != null && fixB.getUserData() != null) {
            if ((fixA.getUserData().equals("hat") || fixB.getUserData().equals("hat") && (fixA.getUserData().equals("breakable") || fixB.getUserData().equals("breakable")))) {

                if (fixA.getFilterData().categoryBits == B2DConstants.BIT_PLAYER_HAT) {

                    InteractiveTile.onHatContact(player);
                } else
                    InteractiveTile.onHatContact(player);
            }
        }
        Gdx.app.log("obj1", fixB.getUserData() + "");
        Gdx.app.log("obj2", fixA.getUserData() + "");
        if (fixA.getUserData() != null && fixB.getUserData() != null) {
            if ((fixA.getUserData() instanceof Hazard || fixA.getUserData() instanceof Hazard)) {
                Gdx.app.log("obj", "collide");
                if (fixA.getFilterData().categoryBits == B2DConstants.BIT_HAZARD) {
                    ((Hazard) fixA.getUserData()).reverseVelocity(true, false);
                }
            } else if (fixB.getFilterData().categoryBits == B2DConstants.BIT_HAZARD)
                ((Hazard) fixB.getUserData()).reverseVelocity(true, false);
        }*/


    }

    /**
     * This method is called when contact between player and a Contact object ends.
     *
     * @param contact The Contact object that is being contacted with.
     */
    @Override
    public void endContact(Contact contact) {

    }

    /**
     * This method has no use. It is only implemented since the class implements ContactListener.
     */
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    /**
     * This method has no use. It is only implemented since the class implements ContactListener.
     */
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
