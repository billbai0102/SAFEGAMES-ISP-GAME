package com.safety4kids.game.Utils;

import com.badlogic.gdx.physics.box2d.*;
import com.safety4kids.game.Entities.InteractiveTile;
import com.sun.javafx.tk.FileChooserType;

public class GameContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        //The two fixtures that are to be colliding
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureA();

        //If one of the fixtures is the players hat
        if (fixA.getUserData() == "hat" || fixB.getUserData() == "hat" ){
            //Determining which fixture is the hat fixture
            Fixture hat = fixA.getUserData() == "hat" ? fixA : fixB;
            //Which fixture is the collided object with the hat
            Fixture obj = hat == fixA ? fixB : fixA;
            //Determines if the Fixture object is an Interactive Tile object by seeing if the user data is assigned from the Interactive Tile
            if (obj.getUserData() != null && InteractiveTile.class.isAssignableFrom(obj.getUserData().getClass())){
                //Executes the hat contact method if the object is indeed an interactive tile
                ((InteractiveTile) obj.getUserData()).onHatContact();
            }
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
