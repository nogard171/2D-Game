package game;

import java.awt.Font;
import java.awt.Rectangle;
import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

import core.Chunk;
import core.CharacterController;
import core.RawSprite;
import core.SpriteFrame;
import core.SpriteSheet;
import core.Window;
import utils.Debugger;
import utils.FPSCounter;
import utils.Generator;
import utils.Loader;
import utils.Renderer;

public class Game {

	boolean isRunning = true;
	Chunk chunk;
	CharacterController characterController;

	public void setup() throws Exception {
		Loader.loadSettings("config.properties");
		Window.setup();
		if (Data.settings.containsKey("assets.sprites")) {

			Loader.loadSprites(Data.settings.getProperty("assets.sprites"));
			Loader.loadSpriteSheets(Data.settings.getProperty("assets.spritesheets"));

			chunk = new Chunk();
			Generator.generateChunk(chunk);

			characterController = new CharacterController();
			characterController.setup();

			FPSCounter.setup();

			while (isRunning) {
				isRunning = !Window.isCloseRequested();
				Window.clear();
				update();
				render();
				Window.update();
			}
			destroy();
		}
		Window.destroy();
	}

	public void update() throws Exception {
		FPSCounter.update();
		characterController.update();
	}

	public void render() throws Exception {
		if (Data.texture != null) {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, Data.texture.getTextureID());
		}
		Data.renderedTiles.clear();

		Renderer.renderChunk(chunk);

		characterController.render();

		Debugger.renderDebugger();
	}

	public void destroy() throws Exception {
		Data.renderedTiles.clear();
	}
}
