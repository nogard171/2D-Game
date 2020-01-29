package utils;

import java.awt.Point;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import core.Chunk;
import core.Tile;

public class Generator {
	static Random r = new Random();

	public static void generateChunk(Chunk chunk) {
		for (int x = 0; x < chunk.getChunkSize().width; x++) {
			for (int y = 0; y < chunk.getChunkSize().height; y++) {
				Tile tile = new Tile();
				int result = r.nextInt(4 - 1) + 1;
				
				if(result==1)
				{
					tile.setSprite("grass");
				}
				else if(result==2)
				{
					tile.setSprite("dirt");
				}
				else if(result==3)
				{
					tile.setSprite("sand");
				}
				chunk.setGroundTile(new Point(x, y), tile);
			}
		}
	}
}
