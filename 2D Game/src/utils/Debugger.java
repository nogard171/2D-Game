package utils;

import java.awt.Rectangle;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

import game.Data;

public class Debugger {

	public static void renderDebugger() {
		Renderer.renderQuad(new Rectangle(0, 0, 200, 32), new Color(0, 0, 0, 0.5f));
		Renderer.renderText(new Vector2f(0, 0), "FPS: " + FPSCounter.getFPS(), 12, Color.white);
		Renderer.renderText(new Vector2f(0, 12), "Rendered Count: " + Data.renderedTiles.size(), 12, Color.white);
	}
}
