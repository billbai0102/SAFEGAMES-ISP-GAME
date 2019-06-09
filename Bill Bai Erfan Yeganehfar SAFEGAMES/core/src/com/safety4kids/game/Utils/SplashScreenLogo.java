package com.safety4kids.game.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

/**
 * This class creates a <i>SplashScreenLogo</i> object used in the <i>IntroAnimation</i> class. It contains methods and
 * variables that alter it's positioning on the screen and it's alpha value, which is it's transparency.
 *
 * @author Bill Bai, Erfan Yeganehfar
 * Ms. Krasteva
 * @version 3.2 06/06/19
 */
public class SplashScreenLogo implements Disposable {
    /**
     * This Rectangle instance control's it's positioning on the screen.
     */
    private Rectangle bottom;

    /**
     * The Sprite of the logo, which will be altered on the screen.
     */
    private Sprite sprite;

    /**
     * The Texture that will be placed on the sprite, so it will be able to be altered.
     */
    private Texture texture;

    /**
     * The alpha value of the sprite.
     */
    private float alpha;

    /**
     * This is the constructor, that creates the instance of SplashScreenLogo. It initializes bottom, texture, sprite,
     * alpha, and places the sprite at location 0,0.
     */
    public SplashScreenLogo() {
        bottom = new Rectangle(0.0f, 0.0f, 400.0f, 400.0f); //Creates new rectangle, 400x400
        texture = new Texture(Gdx.files.internal("SAFEGAMES_Logo.png")); //Texture containing image of logo
        sprite = new Sprite(texture); //Creates sprite, with texture on it
        sprite.setSize(400, 400);//Sets size of sprite to 400x400
        this.setPosition(0, 0); //Sets position of <i>this</i> object to 0,0
        alpha = 1; //Sets starting alpha value of sprite to 1
    }

    /**
     * This method allows you to set the position of the sprite. It alters the Rectangle associated with the sprite, and
     * changes the location.
     *
     * @param x The new x-location of the sprite.
     * @param y The new y-location of the sprite.
     */
    public void setPosition(float x, float y) {
        bottom.x = x; //Sets x of Rectangle to x
        bottom.y = y; //Sets y of Rectangle to y
        sprite.setPosition(x, y); //Sets position of sprite to x,y
    }

    /**
     * This method decreases the alpha value of the sprite, giving it a fading out appearance.
     */
    public void fade() {
        alpha -= (1f / 60f) / 3; //Decreases the alpha value of sprite
        sprite.setAlpha(alpha); //Resets sprite alpha to new alpha value
    }

    /**
     * This method returns the alpha value of the sprite.
     *
     * @return The alpha value of the sprite.
     */
    public float getAlpha() {
        return alpha; //Returns alpha
    }

    /**
     * This method returns the x-location of the sprite.
     *
     * @return The sprite's x-location.
     */
    public float getX() {
        return sprite.getX(); //Returns X loc of sprite
    }

    /**
     * This method shifts the location of the sprite right. This is done by increasing the x-value of the sprite.
     *
     * @param delta
     */
    public void moveRight(float delta) {
        bottom.x += 175 * delta; //Increases Rectangle x value
        sprite.setPosition(bottom.x, bottom.y); //Resets position of the sprite to new location
    }

    /**
     * Draws the sprite onto the screen.
     *
     * @param spriteBatch The SpriteBatch object to be drawn onto.
     */
    public void draw(SpriteBatch spriteBatch) {
        sprite.draw(spriteBatch); //Draws sprite
    }

    /**
     * Overridden method from the Disposable interface. It disposes the texture, then calls the JVM's garbage collection.
     * This is called reduce memory usage.
     */
    @Override
    public void dispose() {
        texture.dispose(); //Disposes texture
        System.gc(); //Calls JVM Garbage collection
    }
}
