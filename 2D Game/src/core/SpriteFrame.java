package core;

import java.awt.Point;

public class SpriteFrame {
	private int ID = -1;

	public SpriteFrame(int displayList) {
		this.setID(displayList);
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
}
