package core;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.lwjgl.util.vector.Vector3f;

public class Chunk {
	private int displayList = -1;

	private Dimension chunkSize = new Dimension(16, 16);
	private HashMap<Point, Tile> groundTiles = new HashMap<Point, Tile>();
	private HashMap<Point, Tile> objectTiles = new HashMap<Point, Tile>();

	private ArrayList<Tile> renderedTiles = new ArrayList<Tile>();

	public Dimension getChunkSize() {
		return chunkSize;
	}

	public int getDisplayList() {
		return displayList;
	}

	public void setDisplayList(int displayList) {
		this.displayList = displayList;
	}

	public void setChunkSize(Dimension chunkSize) {
		this.chunkSize = chunkSize;
	}

	public void setGroundTile(Point vec, Tile newTile) {
		groundTiles.put(vec, newTile);
	}

	public HashMap<Point, Tile> getGroundTiles() {
		return groundTiles;
	}

	public void setGroundTiles(HashMap<Point, Tile> tiles) {
		this.groundTiles = tiles;
	}

	public Tile getGroundTile(int x, int y) {
		return groundTiles.get(new Point(x, y));
	}

	public void setObjectTile(Point vec, Tile newTile) {
		objectTiles.put(vec, newTile);
	}

	public HashMap<Point, Tile> getObjectTiles() {
		return objectTiles;
	}

	public void setObjectTiles(HashMap<Point, Tile> tiles) {
		this.objectTiles = tiles;
	}

	public Tile getObjectTile(int x, int y) {
		return objectTiles.get(new Point(x, y));
	}

	public void addRenderedTiles(Tile tile) {
		this.renderedTiles.add(tile);
	}
	public void clearRenderedTiles() {
		this.renderedTiles.clear();
	}

	public ArrayList<Tile> getRenderedTiles() {
		return this.renderedTiles;
	}
}
