package core;

import org.lwjgl.util.vector.Vector2f;

public class Entity {
	private Vector2f position;
	private String spriteSheet = "character";
	private Boolean isWalking = false;

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public String getSpriteSheet() {
		return spriteSheet;
	}

	public void setSpriteSheet(String spriteSheet) {
		this.spriteSheet = spriteSheet;
	}

	public Boolean isWalking() {
		return isWalking;
	}

	public void setWalking(Boolean isWalking) {
		this.isWalking = isWalking;
	}
}
