package com.safety4kids.game.Entities;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.safety4kids.game.Screens.GameScreen;

public abstract class Hazard extends Sprite {
    protected World world;
    protected Screen screen;
    public Body b2body;

    public Hazard (GameScreen screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x,y);
        createHazard();
    }

    protected abstract void createHazard();
}
