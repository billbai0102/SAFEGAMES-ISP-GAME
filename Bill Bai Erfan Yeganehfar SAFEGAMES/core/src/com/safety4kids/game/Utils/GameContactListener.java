package com.safety4kids.game.Utils;

import com.badlogic.gdx.physics.box2d.*;
import com.safety4kids.game.Entities.MainPlayer;
import com.safety4kids.game.Screens.GameScreen;

/**
 * This class creates a <i>GameContactListener</i> object.
 * <br> The <i>GameContactListener</i> instance will create different outputs in the game based on what Contact object
 * a <i>MainPlayer</i> object collides with.
 *
 * @author Erfan Yeganehfar
 * @author Bill Bai
 * @version 4.2 06/09/19
 * Modifications:
 * 4.0
 * 4.1
 */
public class GameContactListener implements ContactListener {

    /**
     * The instance of the <i>GameScreen</i>
     */
    private GameScreen screen;

    /**
     * The constructor of the class. Sets the <i>GameScreen</i> passed in the parameters to the <i>GameScreen</i> instance
     * of the class.
     *
     * @param screen Instance of the <i>GameScreen</i>
     */
    public GameContactListener(GameScreen screen) {
        this.screen = screen;
    }

    /**
     * This method is called when any object makes contact with a Contact object.
     *
     * @param contact The Contact object that is being made contact with.
     */
    @Override
    public void beginContact(Contact contact) {
        //The two fixtures that are to be colliding
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureA();

       System.out.println(fixB.getFilterData().categoryBits);
        System.out.println(fixA.getFilterData().categoryBits);

        if ((fixA.getFilterData().categoryBits != 32 && fixB.getFilterData().categoryBits != 32)) {
            if ((fixA.getFilterData().categoryBits == 2 && fixB.getFilterData().categoryBits == 2)) {
                screen.state = GameScreen.GameState.LOSE;
            }
        }




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
