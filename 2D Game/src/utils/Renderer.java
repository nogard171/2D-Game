package utils;

import java.awt.Font;
import java.awt.Rectangle;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureImpl;

import core.Chunk;
import core.RawSprite;
import core.Tile;
import game.Data;

public class Renderer {

	public static void renderQuad(Rectangle bound, Color color) {
		GL11.glDisable(GL11.GL_TEXTURE_2D);

		GL11.glColor4f(color.r, color.g, color.b, color.a);

		GL11.glBegin(GL11.GL_QUADS);

		GL11.glVertex2f(bound.x, bound.y);
		GL11.glVertex2f(bound.x + bound.width, bound.y);
		GL11.glVertex2f(bound.x + bound.width, bound.y + bound.height);
		GL11.glVertex2f(bound.x, bound.y + bound.height);

		GL11.glEnd();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	public static void renderText(Vector2f position, String text, int fontSize, Color color) {

		TrueTypeFont font = Data.fonts.get(fontSize);

		if (font == null) {
			Font awtFont = new Font("Courier", Font.PLAIN, fontSize);
			Data.fonts.put(fontSize, new TrueTypeFont(awtFont, false));
		}
		if (font != null) {
			TextureImpl.bindNone();
			font.drawString(position.x, position.y, text, color);
		}
	}

	public static void renderSprite(Vector2f position, RawSprite raw) {
		Vector4f vec = raw.textureData;

		float stepX = vec.x / Data.texture.getImageWidth();
		float stepY = vec.y / Data.texture.getImageHeight();
		float stepW = vec.w / Data.texture.getImageWidth();
		float stepH = vec.z / Data.texture.getImageHeight();

		GL11.glTexCoord2f(stepX, stepY);
		GL11.glVertex2f(position.x, position.y);
		GL11.glTexCoord2f(stepX + stepW, stepY);
		GL11.glVertex2f(position.x + vec.w, position.y);
		GL11.glTexCoord2f(stepX + stepW, stepY + stepH);
		GL11.glVertex2f(position.x + vec.w, position.y + vec.z);
		GL11.glTexCoord2f(stepX, stepY + stepH);
		GL11.glVertex2f(position.x, position.y + vec.z);
	}

	public static void renderSpriteFrame(Texture texture, RawSprite raw) {
		Vector4f vec = raw.textureData;

		float stepX = vec.x / texture.getImageWidth();
		float stepY = vec.y / texture.getImageHeight();
		float stepW = vec.w / texture.getImageWidth();
		float stepH = vec.z / texture.getImageHeight();

		GL11.glTexCoord2f(stepX, stepY);
		GL11.glVertex2f(0, 0);
		GL11.glTexCoord2f(stepX + stepW, stepY);
		GL11.glVertex2f(vec.w, 0);
		GL11.glTexCoord2f(stepX + stepW, stepY + stepH);
		GL11.glVertex2f(vec.w, vec.z);
		GL11.glTexCoord2f(stepX, stepY + stepH);
		GL11.glVertex2f(0, vec.z);
	}

	public static void renderChunk(Chunk chunk) {
		GL11.glColor3f(1, 1, 1);

		int displayList = chunk.getDisplayList();
		if (displayList == -1) {

			chunk.clearRenderedTiles();

			displayList = GL11.glGenLists(1);

			GL11.glNewList(displayList, GL11.GL_COMPILE);
			GL11.glBegin(GL11.GL_QUADS);

			for (int x = 0; x < chunk.getChunkSize().width; x++) {
				for (int y = 0; y < chunk.getChunkSize().height; y++) {
					Tile groundTile = chunk.getGroundTile(x, y);
					if (groundTile != null) {
						RawSprite raw = Data.sprites.get(groundTile.getSprite());
						if (raw != null) {
							Renderer.renderSprite(new Vector2f(x * 32, y * 32), raw);
							chunk.addRenderedTiles(groundTile);
						}
					}
					Tile objectTile = chunk.getObjectTile(x, y);
					if (objectTile != null) {
						RawSprite raw = Data.sprites.get(objectTile.getSprite());
						if (raw != null) {
							Renderer.renderSprite(new Vector2f(x * 32, y * 32), raw);
							chunk.addRenderedTiles(objectTile);
						}
					}
				}
			}

			GL11.glEnd();
			GL11.glEndList();

			chunk.setDisplayList(displayList);
		} else {
			GL11.glCallList(displayList);
		}
		Data.renderedTiles.addAll(chunk.getRenderedTiles());
	}

}
