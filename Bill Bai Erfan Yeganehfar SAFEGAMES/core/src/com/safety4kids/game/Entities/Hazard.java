package com.safety4kids.game.Entities;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.safety4kids.game.Screens.GameScreen;

/**
 * This Class represents the base hazard within the game. It is skeleton for all hazards providing its basic abstraction.
 *
 *  <h2>Course Info:</h2>
 *
 * @version 4.0 2019-06-02
 * @author Erfan Yeganehfar
 * @author Bill Bai
 *
 * Ms. Krasteva
 *
 * Modifications:
 * 4.0 finished the class. Finised the constructor, abstract methods, and instance vars
 */
public abstract class Hazard extends Sprite {
    /** The world that the hazard box 2d body is located in**/
    protected World world;

    /** The screen on which the hazard is displayed on**/
    protected Screen screen;

    /** The hazards box 2d body**/
    public Body b2body;

    /**
     * The Hazrads constructor, where the world, screen, position, and box2d body is defined
     * @param screen The screen that implements the hazard
     * @param x the x location of the hazard
     * @param y the y location of the hazard
     */
    public Hazard (GameScreen screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x,y);
        createHazard();
    }

    /**
     * Defines the hazards box2d body
     */
    protected abstract void createHazard();

    /**
     * update method used to update the hazards position and texture region
     * @param dt uses delta time derived from the the cpu's speed indicating the frames of the game
     */
    public abstract void update(float dt);

    /**
     * Used to draw the sprite batch onto the screen
     * @param batch the batch to be drawn
     */
    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }

}
