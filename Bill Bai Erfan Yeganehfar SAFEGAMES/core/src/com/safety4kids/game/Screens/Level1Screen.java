package com.safety4kids.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.safety4kids.game.Entities.MainPlayer;
import com.safety4kids.game.OverLays.Hud;
import com.safety4kids.game.Safety4Kids;
import com.safety4kids.game.Utils.Box2DCollisionCreator;
import com.safety4kids.game.Utils.CustomMapRenderer;
import com.safety4kids.game.Utils.GameContactListener;
import com.safety4kids.game.Utils.InputHandler;

import static com.safety4kids.game.Safety4Kids.PPM;
import static com.safety4kids.game.Safety4Kids.STEP;
import static com.safety4kids.game.Screens.GameScreen.GameState.NEXT_LEVEL;
import static com.safety4kids.game.Screens.GameScreen.GameState.RUN;

//import java.awt.*;

/**
 * This class is the win screen for level 3.
 *
 * <h2>Course info:</h2>
 * ICS4U with V. Krasteva
 *
 * @author Erfan Yeganehfar, Bill Bai
 * @version 1.2 06/09/19
 */
@SuppressWarnings("Duplicates")
public class Level1Screen extends GameScreen {

    //Tile map Instance variables
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private CustomMapRenderer tiledMapRenderer;

    private InputHandler input;

    //Instance of the main character
    public MainPlayer player;

    //Bitmap Font object to draw and format text onscreen.
    private BitmapFont font;
    private BitmapFont font2;
    private GlyphLayout fontGlyph = new GlyphLayout();
    private GlyphLayout fontGlyphMiddle = new GlyphLayout();
    private GlyphLayout fontGlyphMiddle2 = new GlyphLayout();
    private GlyphLayout fontGlyphBottom = new GlyphLayout();
    private GlyphLayout fontGlyphBottom2 = new GlyphLayout();
    private final float TEXT_CEIL = 123f;
    private final float STAGE_WIDTH = 225f;


    /**
     * The constructor creates all the necessary components for this specific platformer. This includes the actual game,
     * the sprite batches, game camera, viewport, box2d world through the gameState.
     * and the collision detection, main player, tileMaps, and Hud in this constructor.
     *
     * @param game The Safety4Kids Game that this level screen is displayed on
     */
    public Level1Screen(Safety4Kids game) {
        super();
        //Sets the hud for this level
        hud = new Hud(batch, false, 1);

        //Loads, fixes (added padding), and creates the renderer for the TileMap for level 1
        map = new TmxMapLoader().load("MapAssets/level1a.tmx");
        tiledMapRenderer = new CustomMapRenderer(map, 1 / PPM);
        renderer = new OrthogonalTiledMapRenderer(map, 1 / PPM);

        //Generates the Box2D world for the objects within the Tile Map
        creator = new Box2DCollisionCreator(this);

        //The player is created inside of the Box2D world
        player = new MainPlayer(this, 350, 200);

        //Processes input for the player
        input = new InputHandler(player);

        world.setContactListener(new GameContactListener(this));

        //Font to draw and format text.
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/eight-bit-dragon.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 10;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = .5f;
        generator.scaleForPixelHeight(10);
        font = generator.generateFont(parameter);
        generator.dispose();

        //Font to draw and format text.
        FreeTypeFontGenerator generator2 = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/eight-bit-dragon.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter2.size = 10;
        parameter2.borderColor = Color.WHITE;
        parameter2.borderWidth = .3f;
        parameter2.color = Color.BLACK;
        font2 = generator2.generateFont(parameter2);
        generator2.dispose();
    }

    public void update(float dt) {
        if (!isPaused) {
            //user input handler
            input.inputProcess();

            world.step(STEP, 6, 2);

            player.update(dt);

            hud.update(dt);
            //Sets the min and max bounds if the camera following the player
            if (player.b2body.getPosition().x > 2.5 && player.b2body.getPosition().x < 35)
                gameCam.position.x = (player.b2body.getPosition().x);

            //update the gameCam with the player whenever they move
            gameCam.update();

            //sets the view of the renderer to the games orthographic camera and renders teh tilemap
            renderer.setView(gameCam);
            tiledMapRenderer.setView(gameCam);
            tiledMapRenderer.render();
        }
    }

    /**
     * The renderer method updates and displays new graphical/technical changes to the game screen based on the game camera
     * This includes the Tiled Map, the box2d debugger, the camera position, and the onscreen Hud.
     *
     * @param delta
     */
    @Override
    public void render(float delta) {
        if (isPaused) {
            if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY))
                state = RUN;
        }
        switch (state) {
            case RUN:
                if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                    if (!isPaused)
                        isPaused = true;
                    else if (isPaused)
                        isPaused = false;
                }
                if (!isPaused)
                    //update is separated from the render logic
                    update(delta);
                //Clears the game screen
                Gdx.gl.glClearColor(0, 0, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

                //Renders the Game map
                renderer.render();

                //Draws the sprites to the game screen based on the cam
                game.batch.setProjectionMatrix(gameCam.combined);

                game.batch.begin();
                player.draw(game.batch);
                game.batch.end();

                //Box2D Debug renderer
                b2dr.render(world, gameCam.combined);
                hud.stage.draw();

                game.batch.begin();
                drawText(game.batch, player.getXPos());
                game.batch.end();

                //shows the screen based on the Camera with the hud
                game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
                if (isPaused)
                    pause.stage.draw();


                if (player.b2body.getPosition().x > 37.5)
                    state = NEXT_LEVEL;

                break;
            case NEXT_LEVEL:
                dispose();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Level2IntroScreen(game));
                break;
            case RETURN:
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(game));
                dispose();
                break;
            default:
                break;
        }
    }


    /**
     * Based on the character's x-position on the map, this method will draw correct text. The text is composed of
     * educational material that will teach the user of safety.
     *
     * @param batch The SpriteBatch to be drawn on.
     * @param xPos  The character's x-position on the map.
     */
    public void drawText(SpriteBatch batch, float xPos) {
        if (xPos < 4) {
            fontGlyph.setText(font, "Welcome to Safety4Kids!");
            font.draw(batch, fontGlyph, STAGE_WIDTH - fontGlyph.width / 2, TEXT_CEIL);

            fontGlyphMiddle.setText(font, "... I can sense intelligence off of you... you're truly different.");
            font.draw(batch, fontGlyphMiddle, STAGE_WIDTH - fontGlyphMiddle.width / 2, TEXT_CEIL - fontGlyph.height - 10);

            fontGlyphMiddle2.setText(font, "I'm Gekyume, and I need your help.");
            font.draw(batch, fontGlyphMiddle2, STAGE_WIDTH - fontGlyphMiddle2.width / 2, TEXT_CEIL - fontGlyph.height - fontGlyphMiddle.height - 20);

            fontGlyphBottom.setText(font, "I'll explain, keep walking.");
            font.draw(batch, fontGlyphBottom, STAGE_WIDTH - fontGlyphBottom.width / 2, TEXT_CEIL - fontGlyph.height - fontGlyphMiddle.height - fontGlyphMiddle2.height - 30);

        } else if (xPos < 6.5) {
            fontGlyph.setText(font, "For the past year, I've been stuck inside of this game.");
            font.draw(batch, fontGlyph, STAGE_WIDTH - fontGlyph.width / 2, TEXT_CEIL);

            fontGlyphMiddle.setText(font, "I need somebody to pass through all the levels, to help me escape!");
            font.draw(batch, fontGlyphMiddle, STAGE_WIDTH - fontGlyphMiddle.width / 2, TEXT_CEIL - fontGlyph.height - 10);

            fontGlyphMiddle2.setText(font, "Anyways, there's no time to waste. Let's get you started learning.");
            font.draw(batch, fontGlyphMiddle2, STAGE_WIDTH - fontGlyphMiddle2.width / 2, TEXT_CEIL - fontGlyph.height - fontGlyphMiddle.height - 20);

            fontGlyphBottom.setText(font, "In this game, there are a few main topics that you'll be tested on:");
            font.draw(batch, fontGlyphBottom, STAGE_WIDTH - fontGlyphBottom.width / 2, TEXT_CEIL - fontGlyph.height - fontGlyphMiddle.height - fontGlyphMiddle2.height - 30);

            fontGlyphBottom2.setText(font, "Fire, Weather, Injuries, and STRANGER DANGER!");
            font.draw(batch, fontGlyphBottom2, STAGE_WIDTH - fontGlyphBottom2.width / 2, TEXT_CEIL - fontGlyph.height - fontGlyphMiddle.height - fontGlyphMiddle2.height - fontGlyphBottom.height - 40);
        } else if (xPos < 9) {
            fontGlyph.setText(font, "And the rest will require you to use your safety intuition.");
            font.draw(batch, fontGlyph, STAGE_WIDTH - fontGlyph.width / 2, TEXT_CEIL / 2 + fontGlyph.height);
            fontGlyphMiddle.setText(font, "That means your ability to do the right thing!");
            font.draw(batch, fontGlyphMiddle, STAGE_WIDTH - fontGlyphMiddle.width / 2, TEXT_CEIL / 2 + fontGlyph.height - fontGlyphMiddle.height - 10);
        } else if (xPos < 11) {
            fontGlyph.setText(font, "So let's get started. First, I'm going to teach you about fire.");
            font.draw(batch, fontGlyph, STAGE_WIDTH - fontGlyph.width / 2, TEXT_CEIL);

            fontGlyphMiddle.setText(font, "Fire is dangerous and deadly, and should never be messed with.");
            font.draw(batch, fontGlyphMiddle, STAGE_WIDTH - fontGlyphMiddle.width / 2, TEXT_CEIL - fontGlyph.height - 10);

            fontGlyphMiddle2.setText(font, "Example of fire hazards are candles, cooking fires, and electrical fires.");
            font.draw(batch, fontGlyphMiddle2, STAGE_WIDTH - fontGlyphMiddle2.width / 2, TEXT_CEIL - fontGlyph.height - fontGlyphMiddle.height - 20);

            fontGlyphBottom.setText(font, "All of these are equally dangerous, and all pose a major threat!");
            font.draw(batch, fontGlyphBottom, STAGE_WIDTH - fontGlyphBottom.width / 2, TEXT_CEIL - fontGlyph.height - fontGlyphMiddle.height - fontGlyphMiddle2.height - 30);
        } else if (xPos < 13.5) {
            fontGlyph.setText(font, "The best way to deal with fires is to prevent fires in the first place.");
            font.draw(batch, fontGlyph, STAGE_WIDTH - fontGlyph.width / 2, TEXT_CEIL);

            fontGlyphMiddle.setText(font, "Remember, never leave a burning candle unattended.");
            font.draw(batch, fontGlyphMiddle, STAGE_WIDTH - fontGlyphMiddle.width / 2, TEXT_CEIL - fontGlyph.height - 10);

            fontGlyphMiddle2.setText(font, "ALWAYS blow out candles before you leave a room.");
            font.draw(batch, fontGlyphMiddle2, STAGE_WIDTH - fontGlyphMiddle2.width / 2, TEXT_CEIL - fontGlyph.height - fontGlyphMiddle.height - 20);

            fontGlyphBottom.setText(font, "As well, always remember to turn off stoves when you're done using them.");
            font.draw(batch, fontGlyphBottom, STAGE_WIDTH - fontGlyphBottom.width / 2, TEXT_CEIL - fontGlyph.height - fontGlyphMiddle.height - fontGlyphMiddle2.height - 30);

            fontGlyphBottom2.setText(font, "Make sure you have an extinguisher accessible in your house.");
            font.draw(batch, fontGlyphBottom2, STAGE_WIDTH - fontGlyphBottom2.width / 2, TEXT_CEIL - fontGlyph.height - fontGlyphMiddle.height - fontGlyphMiddle2.height - fontGlyphBottom.height - 40);
        } else if (xPos < 16) {
            fontGlyph.setText(font, "Now, in the case of a real fire, you should always be prepared.");
            font.draw(batch, fontGlyph, STAGE_WIDTH - fontGlyph.width / 2, TEXT_CEIL);

            fontGlyphMiddle.setText(font, "When using a fire extinguisher, aim at the BASE of the fire.");
            font.draw(batch, fontGlyphMiddle, STAGE_WIDTH - fontGlyphMiddle.width / 2, TEXT_CEIL - fontGlyph.height - 10);

            fontGlyphMiddle2.setText(font, "If you don't have a fire extinguisher, you may use water.");
            font.draw(batch, fontGlyphMiddle2, STAGE_WIDTH - fontGlyphMiddle2.width / 2, TEXT_CEIL - fontGlyph.height - fontGlyphMiddle.height - 20);

            fontGlyphBottom.setText(font, " But only on burning SOLIDS!");
            font.draw(batch, fontGlyphBottom, STAGE_WIDTH - fontGlyphBottom.width / 2, TEXT_CEIL - fontGlyph.height - fontGlyphMiddle.height - fontGlyphMiddle2.height - 30);

            fontGlyphBottom2.setText(font, "Do not pour water on LIQUID fires, such as grease fires.");
            font.draw(batch, fontGlyphBottom2, STAGE_WIDTH - fontGlyphBottom2.width / 2, TEXT_CEIL - fontGlyph.height - fontGlyphMiddle.height - fontGlyphMiddle2.height - fontGlyphBottom.height - 40);
        } else if (xPos < 18) {
            fontGlyph.setText(font, "Pouring water on liquid fires will cause it to explode and worsen.");
            font.draw(batch, fontGlyph, STAGE_WIDTH - fontGlyph.width / 2, TEXT_CEIL + 25);

            fontGlyphMiddle.setText(font, "Instead, use baking soda or a metal pan lid to smother the fire.");
            font.draw(batch, fontGlyphMiddle, STAGE_WIDTH - fontGlyphMiddle.width / 2, TEXT_CEIL + 25 - fontGlyph.height - 10);

            fontGlyphMiddle2.setText(font, "If worse comes to worst, and you need to evacuate a burning building,");
            font.draw(batch, fontGlyphMiddle2, STAGE_WIDTH - fontGlyphMiddle2.width / 2, TEXT_CEIL + 25 - fontGlyph.height - fontGlyphMiddle.height - 20);

            fontGlyphBottom.setText(font, "Do NOT re-enter to save anything or anybody, unless");
            font.draw(batch, fontGlyphBottom, STAGE_WIDTH - fontGlyphBottom.width / 2, TEXT_CEIL + 25 - fontGlyph.height - fontGlyphMiddle.height - fontGlyphMiddle2.height - 30);

            fontGlyphBottom2.setText(font, "you are given permission by firefighters.");
            font.draw(batch, fontGlyphBottom2, STAGE_WIDTH - fontGlyphBottom2.width / 2, TEXT_CEIL + 25 - fontGlyph.height - fontGlyphMiddle.height - fontGlyphMiddle2.height - fontGlyphBottom.height - 40);
        } else if (xPos < 20) {
            fontGlyph.setText(font, "Next up, you must learn about weather safety.");
            font.draw(batch, fontGlyph, STAGE_WIDTH - fontGlyph.width / 2, TEXT_CEIL + 25);

            fontGlyphMiddle.setText(font, "The most common hazard is lightning and thunderstorms.");
            font.draw(batch, fontGlyphMiddle, STAGE_WIDTH - fontGlyphMiddle.width / 2, TEXT_CEIL + 25 - fontGlyph.height - 10);

            fontGlyphMiddle2.setText(font, "When you're outside and there's a thunderstorm, avoid all tall objects,");
            font.draw(batch, fontGlyphMiddle2, STAGE_WIDTH - fontGlyphMiddle2.width / 2, TEXT_CEIL + 25 - fontGlyph.height - fontGlyphMiddle.height - 20);

            fontGlyphBottom.setText(font, "and get to low ground. This will greatly reduce your chances of");
            font.draw(batch, fontGlyphBottom, STAGE_WIDTH - fontGlyphBottom.width / 2, TEXT_CEIL + 25 - fontGlyph.height - fontGlyphMiddle.height - fontGlyphMiddle2.height - 30);

            fontGlyphBottom2.setText(font, "getting struck.");
            font.draw(batch, fontGlyphBottom2, STAGE_WIDTH - fontGlyphBottom2.width / 2, TEXT_CEIL + 25 - fontGlyph.height - fontGlyphMiddle.height - fontGlyphMiddle2.height - fontGlyphBottom.height - 40);
        } else if (xPos < 22) {
            fontGlyph.setText(font, "If you're inside and there's a thunderstorm, ");
            font.draw(batch, fontGlyph, STAGE_WIDTH - fontGlyph.width / 2, TEXT_CEIL + 25);

            fontGlyphMiddle.setText(font, "unplug all appliances. Lightning strikes can surge your appliances");
            font.draw(batch, fontGlyphMiddle, STAGE_WIDTH - fontGlyphMiddle.width / 2, TEXT_CEIL + 25 - fontGlyph.height - 10);

            fontGlyphMiddle2.setText(font, "that are plugged in, and cause them malfunction or even blow up.");
            font.draw(batch, fontGlyphMiddle2, STAGE_WIDTH - fontGlyphMiddle2.width / 2, TEXT_CEIL + 25 - fontGlyph.height - fontGlyphMiddle.height - 20);

            fontGlyphBottom.setText(font, "As well, avoid using landline telephones, since they're connected to");
            font.draw(batch, fontGlyphBottom, STAGE_WIDTH - fontGlyphBottom.width / 2, TEXT_CEIL + 25 - fontGlyph.height - fontGlyphMiddle.height - fontGlyphMiddle2.height - 30);

            fontGlyphBottom2.setText(font, "external wires. They could electrify you, if you're holding it.");
            font.draw(batch, fontGlyphBottom2, STAGE_WIDTH - fontGlyphBottom2.width / 2, TEXT_CEIL + 25 - fontGlyph.height - fontGlyphMiddle.height - fontGlyphMiddle2.height - fontGlyphBottom.height - 40);
        } else if (xPos < 23.5) {

            fontGlyph.setText(font, "You must also learn about injuries.");
            font.draw(batch, fontGlyph, STAGE_WIDTH - fontGlyph.width / 2, TEXT_CEIL + 25);

            fontGlyphMiddle.setText(font, "If you are ever injured, during sports or anytime,");
            font.draw(batch, fontGlyphMiddle, STAGE_WIDTH - fontGlyphMiddle.width / 2, TEXT_CEIL + 25 - fontGlyph.height - 10);

            fontGlyphMiddle2.setText(font, "tell an adult immediately and stop what you're doing.");
            font.draw(batch, fontGlyphMiddle2, STAGE_WIDTH - fontGlyphMiddle2.width / 2, TEXT_CEIL + 25 - fontGlyph.height - fontGlyphMiddle.height - 20);

            fontGlyphBottom.setText(font, "They will help you deal with your injury. If no adults");
            font.draw(batch, fontGlyphBottom, STAGE_WIDTH - fontGlyphBottom.width / 2, TEXT_CEIL + 25 - fontGlyph.height - fontGlyphMiddle.height - fontGlyphMiddle2.height - 30);

            fontGlyphBottom2.setText(font, "are near, you should call 911 if it is serious.");
            font.draw(batch, fontGlyphBottom2, STAGE_WIDTH - fontGlyphBottom2.width / 2, TEXT_CEIL + 25 - fontGlyph.height - fontGlyphMiddle.height - fontGlyphMiddle2.height - fontGlyphBottom.height - 40);
        } else if (xPos < 25.135944) {
            fontGlyph.setText(font, "A serious injury means an injury that affects your vitals functions");
            font.draw(batch, fontGlyph, STAGE_WIDTH - fontGlyph.width / 2, TEXT_CEIL + 25);

            fontGlyphMiddle.setText(font, "such as a tight chest, difficulty breathing, or double-vision,");
            font.draw(batch, fontGlyphMiddle, STAGE_WIDTH - fontGlyphMiddle.width / 2, TEXT_CEIL + 25 - fontGlyph.height - 10);

            fontGlyphMiddle2.setText(font, "or an injury that you know can lead to bad results.");
            font.draw(batch, fontGlyphMiddle2, STAGE_WIDTH - fontGlyphMiddle2.width / 2, TEXT_CEIL + 25 - fontGlyph.height - fontGlyphMiddle.height - 20);

            fontGlyphBottom.setText(font, "This includes overdosing on medicine, accidentally swallowing");
            font.draw(batch, fontGlyphBottom, STAGE_WIDTH - fontGlyphBottom.width / 2, TEXT_CEIL + 25 - fontGlyph.height - fontGlyphMiddle.height - fontGlyphMiddle2.height - 30);

            fontGlyphBottom2.setText(font, "something you shouldn't, or touching something hazardous.");
            font.draw(batch, fontGlyphBottom2, STAGE_WIDTH - fontGlyphBottom2.width / 2, TEXT_CEIL + 25 - fontGlyph.height - fontGlyphMiddle.height - fontGlyphMiddle2.height - fontGlyphBottom.height - 40);
        } else if (xPos < 27) {
            fontGlyph.setText(font2, "Never hesitate to call 911 if you're unsure whether your injury");
            font2.draw(batch, fontGlyph, STAGE_WIDTH - fontGlyph.width / 2, 400);

            fontGlyphMiddle.setText(font, "is serious enough. Seriously, it could save your life.");
            font2.draw(batch, fontGlyphMiddle, STAGE_WIDTH - fontGlyphMiddle.width / 2, 400 - fontGlyph.height - 10);

            fontGlyphMiddle2.setText(font, "Just don't call 911 for non-emergencies.");
            font2.draw(batch, fontGlyphMiddle2, STAGE_WIDTH - fontGlyphMiddle2.width / 2, 400 - fontGlyph.height - fontGlyphMiddle.height - 20);
        }else if (xPos < 30){
            fontGlyph.setText(font2, "Lastly, I need to teach you about stranger danger.");
            font2.draw(batch, fontGlyph, STAGE_WIDTH - fontGlyph.width / 2, 400);

            fontGlyphMiddle.setText(font2, "The world we live in isn't perfect, and there are bad");
            font2.draw(batch, fontGlyphMiddle, STAGE_WIDTH - fontGlyphMiddle.width / 2, 400 - fontGlyph.height - 10);

            fontGlyphMiddle2.setText(font2, "people everywhere. That's why, you should always");
            font2.draw(batch, fontGlyphMiddle2, STAGE_WIDTH - fontGlyphMiddle2.width / 2, 400 - fontGlyph.height - fontGlyphMiddle.height - 20);

            fontGlyphBottom.setText(font2, "avoid talking to, trusting, or accept anything from strangers.");
            font2.draw(batch, fontGlyphBottom, STAGE_WIDTH - fontGlyphBottom.width / 2, 400 - fontGlyph.height - fontGlyphMiddle.height - fontGlyphMiddle2.height - 30);
        } else if (xPos < 32){
            fontGlyph.setText(font2, "If a stranger comes up to you offering candy");
            font2.draw(batch, fontGlyph, STAGE_WIDTH - fontGlyph.width / 2, 400);

            fontGlyphMiddle.setText(font2, "or asks you to follow them somewhere,");
            font2.draw(batch, fontGlyphMiddle, STAGE_WIDTH - fontGlyphMiddle.width / 2, 400 - fontGlyph.height - 10);

            fontGlyphMiddle2.setText(font2, "do not say anything, and run to the nearest trusted adult,");
            font2.draw(batch, fontGlyphMiddle2, STAGE_WIDTH - fontGlyphMiddle2.width / 2, 400 - fontGlyph.height - fontGlyphMiddle.height - 20);

            fontGlyphBottom.setText(font2, "police, or shop, and inform them of what's happened.");
            font2.draw(batch, fontGlyphBottom, STAGE_WIDTH - fontGlyphBottom.width / 2, 400 - fontGlyph.height - fontGlyphMiddle.height - fontGlyphMiddle2.height - 30);
        } else if (xPos < 34){
            fontGlyph.setText(font2, "Stranger danger also applies to online settings.");
            font2.draw(batch, fontGlyph, STAGE_WIDTH - fontGlyph.width / 2, 400);

            fontGlyphMiddle.setText(font2, "NEVER give any information to strangers anywhere.");
            font2.draw(batch, fontGlyphMiddle, STAGE_WIDTH - fontGlyphMiddle.width / 2, 400 - fontGlyph.height - 10);
        } else if (xPos < 36){
            fontGlyph.setText(font2, "");
            font2.draw(batch, fontGlyph, STAGE_WIDTH - fontGlyph.width / 2, 400);

            fontGlyphMiddle.setText(font2, "Anyways, you're set now. Good luck on level 2.");
            font2.draw(batch, fontGlyphMiddle, STAGE_WIDTH - fontGlyphMiddle.width / 2, 400 - fontGlyph.height - 10);

            fontGlyphMiddle2.setText(font2, "I believe in you!");
            font2.draw(batch, fontGlyphMiddle2, STAGE_WIDTH - fontGlyphMiddle2.width / 2, 400 - fontGlyph.height - fontGlyphMiddle.height - 20);
        }

    }

    /**
     * Based on the screen size, the viewport of the game is positioned to correctly display the game screen
     *
     * @param width  the world width to be displayed
     * @param height the world height to be displayed
     */
    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    /**
     * Used for memory efficiency, disposes of game assets
     */
    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        tiledMapRenderer.dispose();
        pause.dispose();
        hud.dispose();
        font.dispose();
        font2.dispose();
        atlas.dispose();
        game.batch.dispose();
        game.dispose();
    }

    public TiledMap getMap() {
        return map;
    }

}
