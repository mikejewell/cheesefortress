package cheese.graphics.tileSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import org.newdawn.slick.geom.Point;

import cheese.graphics.WedgeCamera;
import cheese.graphics.tileSystem.WedgeTile.Type;
import cheese.graphics.tileSystem.WedgeTileOverlay.OverlayType;

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
	
	public void addGrass(float density){
		Random r = new Random();
		int grass = (int)(tiles.length*tiles.length*density);
		int x, y;
		for(int i = 0; i < grass; i++){
			x = r.nextInt(tiles.length);
			y = r.nextInt(tiles.length);
			if(tiles[x][y].getId() == 2 && tiles[x][y].getOverlay() == OverlayType.EMPTY && tiles[x][y].getId() != 3 )
			{
				tiles[x][y].setOverlay(OverlayType.GRASS);
			}
		}
	}
	
	public void addSticks(float density){
		Random r = new Random();
		int grass = (int)(tiles.length*tiles.length*density);
		int x, y;
		for(int i = 0; i < grass; i++){
			x = r.nextInt(tiles.length);
			y = r.nextInt(tiles.length);
			if(tiles[x][y].getId() != 0 && tiles[x][y].getOverlay() == OverlayType.EMPTY && tiles[x][y].getId() != 3)
			{
				if(r.nextInt(2) == 0)
					tiles[x][y].setOverlay(OverlayType.STICK1);
				else
					tiles[x][y].setOverlay(OverlayType.STICK2);
			}
		}
	}
	
	public void addBushes(float density){
		Random r = new Random();
		int grass = (int)(tiles.length*tiles.length*density);
		int x, y;
		for(int i = 0; i < grass; i++){
			x = r.nextInt(tiles.length);
			y = r.nextInt(tiles.length);
			if(tiles[x][y].getId() != 0 && tiles[x][y].getOverlay() == OverlayType.EMPTY && tiles[x][y].getId() != 3)
			{
				tiles[x][y].setOverlay(OverlayType.BUSH);
			}
		}
	}
	
	public void addTrees(float density){
		Random r = new Random();
		int grass = (int)(tiles.length*tiles.length*density);
		int x, y;
		for(int i = 0; i < grass; i++){
			x = r.nextInt(tiles.length);
			y = r.nextInt(tiles.length);
			if(tiles[x][y].getId() != 0 && tiles[x][y].getOverlay() == OverlayType.EMPTY && tiles[x][y].getId() != 3)
			{
				switch(r.nextInt(5)){
					case 0:
						tiles[x][y].setOverlay(OverlayType.TREE1);	
						break;
					case 1:
						tiles[x][y].setOverlay(OverlayType.TREE2);	
						break;
					case 2:
						tiles[x][y].setOverlay(OverlayType.TREE3);	
						break;
					case 3:
						tiles[x][y].setOverlay(OverlayType.TREE4);	
						break;
					default:
						tiles[x][y].setOverlay(OverlayType.TREE1);	
						break;
				}
			}
		}
	}
	
	public void addRocks(float density){
		Random r = new Random();
		int rocks = (int)(tiles.length*tiles.length*density);
		int x, y;
		for(int i = 0; i < rocks; i++){
			x = r.nextInt(tiles.length);
			y = r.nextInt(tiles.length);
			if(tiles[x][y].getId() != 0 && tiles[x][y].getOverlay() == OverlayType.EMPTY)
			{
				switch(r.nextInt(3)){
					case 0:
						tiles[x][y].setOverlay(OverlayType.ROCK1);	
						break;
					case 1:
						tiles[x][y].setOverlay(OverlayType.ROCK2);	
						break;
					case 2:
						tiles[x][y].setOverlay(OverlayType.ROCK3);	
						break;
				}
			}
		}
	}
	
	public WedgeTile[][] getTiles() {
		return tiles;
	}
	
	public WedgeTile getTile(int x, int y){
		if(x > tiles.length || x < 0 || y > tiles.length || y < 0)
			return null;
		return tiles[x][y];
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
			case ' ':
				return new WedgeTile(4, Type.CLIFF);
			default:
				return new WedgeTile(1, Type.DIRT);
		}
	}
	
}
