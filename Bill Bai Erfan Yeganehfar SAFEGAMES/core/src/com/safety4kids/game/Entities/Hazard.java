package com.safety4kids.game.Entities;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.safety4kids.game.Screens.GameScreen;

public abstract class Hazard extends Sprite {
    protected World world;
    protected Screen screen;

    public Body b2body;
    public Vector2 velocity;


    public Hazard (GameScreen screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x,y);
        createHazard();
        velocity = new Vector2(-1, -2);
    }

    protected abstract void createHazard();
    public abstract void update(float dt);

    public void reverseVelocity(boolean x, boolean y){
        if(x)
            velocity.x = -velocity.x;
        if(y)
            velocity.y = -velocity.y;
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }

}
