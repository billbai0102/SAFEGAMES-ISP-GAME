package com.safety4kids.game.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.safety4kids.game.Entities.MainPlayer;

import static com.safety4kids.game.Safety4Kids.MAX_VELOCITY;
import static com.safety4kids.game.Safety4Kids.MIN_VELOCITY;

/**
 * This class allows you create an instance of an <i>InputHandler</i> object, which allows the program to accept inputs
 * to alter a <i>MainPlayer</i> object's state, which consists of it's location on the map.
 *
 * @author Erfan Yeganehfar, Bill Bai
 * @version 3.6 06/07/19
 */
public class InputHandler{

    /**
     * This is the instance of the <i>MainPlayer</i> object, that will be altered by the <i>InputHandler</i> object.
     */
    private static MainPlayer player;

    /**
     * Constructor of the class. Sets the <i>MainPlayer</i> to be moved, to player.
     *
     * @param player The player, which the InputHandler object will alter.
     */
    public InputHandler(MainPlayer player) {
        this.player = player;
    }

    /**
     * This method processes inputs.
     * <br>If the up key or W key is pressed, it will call the <i>MainPlayer</i> object's
     * <i>jump</i> method.
     * <br>If the right key or D key is pressed, it will call the moveRight method of this class, which
     * will shift the location of the <i>MainPlayer</i> object right.
     * <br> If the left key or A key is pressed, it will call the moveLeft method of this class, which
     * will shift the location of the <i>MainPlayer</i> object left.
     */
    public void inputProcess() {
        //If up or W key is pressed
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W))
            player.jump(); //Call player jump method
        //If right or D key is pressed
        if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) && player.b2body.getLinearVelocity().x <= MAX_VELOCITY) {
            moveRight(); //Call moveRight method
            /*if (keyUp(Input.Keys.RIGHT) || keyUp(Input.Keys.D))
                player.b2body.setLinearVelocity(0f, 0f);*/
        }
        //If left or A key is pressed
        if ((Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) && player.b2body.getLinearVelocity().x >= MIN_VELOCITY) {
            moveLeft(); //Call moveLeft method
            /*if (keyUp(Input.Keys.LEFT) || keyUp(Input.Keys.A))
            player.b2body.setLinearVelocity(0f, 0f);*/
        }


    }

    /**
     * Shifts the location of player to the right
     */
    public void moveRight() {
        player.b2body.applyLinearImpulse(new Vector2(0.07f, 0), player.b2body.getWorldCenter(), true);

    }

    /**
     * Shifts the location of player to the left
     */
    public void moveLeft() {
        player.b2body.applyLinearImpulse(new Vector2(-0.07f, 0), player.b2body.getWorldCenter(), true);
    }
}
