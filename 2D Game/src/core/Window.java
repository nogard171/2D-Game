package core;

import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glViewport;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import game.Data;

public class Window {
	public static void setup() throws LWJGLException {
		Display.setDisplayMode(new DisplayMode(Integer.parseInt(Data.settings.getProperty("window.width")),
				Integer.parseInt(Data.settings.getProperty("window.height"))));
		Display.setResizable(true);
		Display.setFullscreen(Boolean.parseBoolean(Data.settings.getProperty("window.fullscreen")));
		Display.create();

		setupViewport();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		if (Data.settings.contains("window.highdpi")) {
			System.setProperty("org.lwjgl.opengl.Display.enableHighDPI", Data.settings.getProperty("window.highdpi"));
		}
	}
	
	private static void setupViewport()
	{
		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, Display.getWidth(),
				Display.getHeight(), 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	public static void update() {
		if (Display.wasResized()) {
			Data.settings.setProperty("window.width", String.valueOf(Display.getWidth()));
			Data.settings.setProperty("window.height", String.valueOf(Display.getHeight()));
			
			setupViewport();
			
		}
		Display.update();
		if (Data.settings.containsKey("window.vsync")) {
			if (Boolean.parseBoolean(Data.settings.getProperty("window.vsync"))) {
				if (Data.settings.containsKey("window.fps")) {
					int fps = Integer.parseInt(Data.settings.getProperty("window.fps"));
					Display.sync(fps);
				}
			}
		}
	}

	public static void clear() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);
	}

	public static void destroy() {
		Display.destroy();
	}

	public static boolean isCloseRequested() {
		return Display.isCloseRequested();
	}
}
