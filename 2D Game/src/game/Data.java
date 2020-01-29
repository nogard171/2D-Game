package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

import core.RawSprite;
import core.SpriteSheet;
import core.Tile;
import core.Entity;

public class Data {
	// project variables
	public static Properties settings;
	
	//game variables
	public static Entity player;
	public static HashMap<String, RawSprite> chunks = new HashMap<String, RawSprite>();

	// assets variables
	public static HashMap<Integer, TrueTypeFont> fonts = new HashMap<Integer, TrueTypeFont>();
	public static Texture texture;
	public static HashMap<String, RawSprite> sprites = new HashMap<String, RawSprite>();
	public static HashMap<String, SpriteSheet> spriteSheets = new HashMap<String, SpriteSheet>();

	// renderer variables
	public static ArrayList<Tile> renderedTiles = new ArrayList<Tile>();

}
