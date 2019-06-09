package com.safety4kids.game.Utils;

/**
 * This class serves as a utility class where collision bit filters are held, so that multiple classes can access them.
 */
public class B2DConstants {
	//Collision collision bit filters
	/**
	 * The collision bit filter for the TiledMap object.
	 */
	public static final short PLATFORM_BIT =  1;
	/**
	 * The collision bit filter for the MainPlayer object.
	 */
	public static final short BIT_PLAYER = 2;
	/**
	 * The collision bit filter for the BreakableTile object.
	 */
	public static final short BIT_BREAKABLE_BLOCK = 4;
	/**
	 * The collision bit filter for the CoinObj object.
	 */
	public static final short BIT_COIN = 8;
	/**
	 * The collision bit filter for destroyed objects.
	 */
	public static final short BIT_DESTROYED = 16;
	/**
	 * The collision bit filter for  objects.
	 */
	public static final short BIT_OBJECT = 32;
	/**
	 * The collision bit filter for Hazard objects.
	 */
	public static final short BIT_HAZARD = 64;
	/**
	 * The collision bit filter for the MainPlayer object's head.
	 */
	public static final short BIT_PLAYER_HAT = 128;
}