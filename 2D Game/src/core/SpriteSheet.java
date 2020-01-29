package core;

import java.awt.Dimension;

import org.newdawn.slick.opengl.Texture;

public class SpriteSheet {
	private Texture texture;
	private Dimension spriteSize;
	private SpriteFrame[][] frames;

	public SpriteFrame[][] getFrames() {
		return frames;
	}

	public void setFrames(SpriteFrame[][] frames) {
		this.frames = frames;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public Dimension getSpriteSize() {
		return spriteSize;
	}

	public void setSpriteSize(Dimension spriteSize) {
		this.spriteSize = spriteSize;
	}

	public void setFrame(int x, int y, int displayList) {

	}

	public SpriteFrame getFrame(int i, int j) {
		return frames[i][j];
	}
}
