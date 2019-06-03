package com.safety4kids.game.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.safety4kids.game.Entities.MainPlayer;
import com.safety4kids.game.Screens.GameScreen;

import static com.safety4kids.game.Safety4Kids.MAX_VELOCITY;
import static com.safety4kids.game.Safety4Kids.MIN_VELOCITY;


public class InputProcessor {


    //Instance of the main character
    private static MainPlayer player;

    public InputProcessor (MainPlayer player){
        this.player = player;
    }

    public static void inputProcess() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W))
            player.jump();
        if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) && player.b2body.getLinearVelocity().x <= MAX_VELOCITY)
            moveRight();
        if ((Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) && player.b2body.getLinearVelocity().x >= MIN_VELOCITY)
            moveLeft();
    }

    public static void moveRight(){
        player.b2body.applyLinearImpulse(new Vector2(0.1f, 0),player.b2body.getWorldCenter(), true);
    }
    public static void moveLeft(){
        player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0),player.b2body.getWorldCenter(), true);
    }
}
