package cheese.graphics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.newdawn.slick.geom.Point;

import cheese.graphics.WedgeTile.Type;

public class WedgeTileSystem {
	
	private WedgeCamera camera;
	private int tileRes;
	
	private WedgeTile[][] tiles;
	private WedgeTileMap tileMap;
	
	public WedgeTileSystem(String textureName, int tileRes, String mapFileName, Point windowSize){
		this.tileRes = tileRes;
		
		camera = new WedgeCamera(20, 20, tileRes, windowSize);
		tileMap = new WedgeTileMap(textureName, 64);
		
		try {
			tiles = load(mapFileName);
		} catch (IOException e) {
			System.err.println("ERROR: Cannot load map " + mapFileName);
			e.printStackTrace();
		}
	}
	
	public WedgeTile[][] getTiles() {
		return tiles;
	}
	
	public WedgeTileMap getTileMap(){
		return tileMap;
	}
	
	public int getTileRes(){
		return tileRes;
	}
	
	public WedgeTile[][] load(String mapName) throws IOException {
		WedgeTile[][] tiles  = null;
		BufferedReader reader = new BufferedReader(new FileReader(mapName));

		// First line is the size (Assuming it's a square)
		int size = Integer.parseInt(reader.readLine());
		if (size < 1)
			throw new IOException("Invalid Map!");

		tiles = new WedgeTile[size][size];

		// Iterate through the file to set the map up.
		for (int x = 0; x < size; x++) {
			String line = reader.readLine();
			for (int y = 0; y < line.length(); y++) {
				tiles[x][y] = createTile(line.charAt(y), x, y);
			}
		}
		
        System.out.format("LocalMapLoader\tMap of size %d loaded.\n", size);
		
		return tiles;
	}

	private WedgeTile createTile(char mapChar, int x, int y) {
		switch(mapChar) {
			case '@':
				return new WedgeTile(0, Type.WATER);
			case '+':
				return new WedgeTile(1, Type.DIRT);
			case '\'':
				return new WedgeTile(2, Type.GRASS);
			case ':':
				return new WedgeTile(3, Type.STONE);
			default:
				return new WedgeTile(0, Type.DIRT);
		}
	}
	
}
