package com.safety4kids.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class SplashScreenLogo {
    private Rectangle bottom;
    private Sprite sprite;
    private Texture texture;
    private float alpha;

    public SplashScreenLogo(){
        bottom = new Rectangle(0.0f,0.0f,400.0f,400.0f);
        texture = new Texture(Gdx.files.internal("core/assets/SAFEGAMES_Logo.png"));
        sprite = new Sprite(texture);
        sprite.setSize(400,400);
        this.setPosition(0,0);
        alpha = 1;
    }

    public void setPosition(float x, float y){
        bottom.x = x;
        bottom.y = y;
        sprite.setPosition(x,y);
    }

    public void fade(){
        alpha -= (1f / 60f) / 3;
        sprite.setAlpha(alpha);
    }

    public float getAlpha(){
        return alpha;
    }

    public float getX(){
        return sprite.getX();
    }

    public void moveLeft(float delta){
        bottom.x -= 10*delta;
        sprite.setPosition(bottom.x,bottom.y);
    }

    public void moveRight(float delta){
        bottom.x += 175*delta;
        sprite.setPosition(bottom.x,bottom.y);
    }

    public void draw(SpriteBatch spriteBatch){
        sprite.draw(spriteBatch);
    }
}
