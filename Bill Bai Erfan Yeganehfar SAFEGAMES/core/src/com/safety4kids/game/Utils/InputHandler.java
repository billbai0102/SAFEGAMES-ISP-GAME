package com.safety4kids.game.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.safety4kids.game.Entities.MainPlayer;

import static com.safety4kids.game.Safety4Kids.MAX_VELOCITY;
import static com.safety4kids.game.Safety4Kids.MIN_VELOCITY;


public class InputHandler implements InputProcessor {


    //Instance of the main character
    private static MainPlayer player;

    public InputHandler(MainPlayer player){
        this.player = player;
    }

    public void inputProcess() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W))
            player.jump();
        if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) && player.b2body.getLinearVelocity().x <= MAX_VELOCITY) {
            moveRight();
            /*if (keyUp(Input.Keys.RIGHT) || keyUp(Input.Keys.D))
                player.b2body.setLinearVelocity(0f, 0f);*/
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) && player.b2body.getLinearVelocity().x >= MIN_VELOCITY){
            moveLeft();
            /*if (keyUp(Input.Keys.LEFT) || keyUp(Input.Keys.A))
            player.b2body.setLinearVelocity(0f, 0f);*/
        }


    }

    public void moveRight(){
        player.b2body.applyLinearImpulse(new Vector2(0.07f, 0),player.b2body.getWorldCenter(), true);

    }
    public void moveLeft(){
        player.b2body.applyLinearImpulse(new Vector2(-0.07f, 0),player.b2body.getWorldCenter(), true);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return Gdx.input.isKeyPressed(keycode) ? false : true;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }
}
