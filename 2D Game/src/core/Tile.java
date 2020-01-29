package core;

public class Tile {
	private String sprite = "sand";

	public Tile(String newSprite) {
		this.sprite = newSprite;
	}

	public Tile() {
	}

	public String getSprite() {
		return sprite;
	}

	public void setSprite(String sprite) {
		this.sprite = sprite;
	}

}
