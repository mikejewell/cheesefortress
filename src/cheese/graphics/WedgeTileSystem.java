package cheese.graphics;

public class WedgeTileSystem {
	
	private int tileRes;
	
	private WedgeTile[][] tiles;
	private WedgeTileMap tileMap;
	
	public WedgeTileSystem(String textureName, int tileRes){
		this.tileRes = tileRes;
		tileMap = new WedgeTileMap(textureName, 64);
		
		tiles = new WedgeTile[20][20];
		for(int i = 0; i < 20; i++){
			for(int j = 0; j < 20; j++){
				tiles[i][j] = new WedgeTile(0, WedgeTile.Type.DIRT);
			}
		}
	}
	
	public WedgeTile[][] getTiles() {
		return tiles;
	}
	
	public WedgeTileMap getTileMap(){
		return tileMap;
	}
}
