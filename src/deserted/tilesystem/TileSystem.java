package deserted.tilesystem;

import java.io.IOException;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

import cheese.graphics.WedgeCamera;
import cheese.graphics.WedgeGFX;
import cheese.graphics.WedgeTileSystem;
import cheese.model.building.BaseBuilding;
import cheese.model.building.Building;
import deserted.map.LocalMapLoader;
import deserted.model.AgentState;
import deserted.model.GameSession;
import deserted.model.item.ItemType;
import deserted.player.PlayerUI;
import deserted.sprite.Sprite;
import deserted.sprite.SpriteManager;
import deserted.sprite.SpriteType;

public class TileSystem {
	
	public Camera camera;
	public int tileRes = 32;
	public int size;
	float resTimesScale = 32;
	
	private Image tileMap;
	private Image spriteMap;
	
	//private Renderer r;
	
	//private final Color semi = new Color(0, 0, 0, 0.3f);
	
	WedgeGFX gfx = new WedgeGFX();
	WedgeTileSystem ts;

	public enum TileId{
		GRASS,
		DIRT,
		WATER,
		SNOW,
		ROCK,
		WALL,
		POND,
		TARPIT,
		WRECKAGE
	}

	private Tile tiles[][];
	
	private static TileSystem instance = null;
	
	public static TileSystem getInstance() {
		if(instance == null) {
			instance = new TileSystem();
		}
		return instance;
	}
	
	public TileSystem() {
		
	}
	
	public void init(Point windowSize) {
		LocalMapLoader loader = new LocalMapLoader();
		//PerlinMapGenerator loader = new PerlinMapGenerator();

		ts = new WedgeTileSystem("assets/images/iso-64x64-outside.png", 64, "assets/maps/2.map", windowSize);
		ts.addGrass(0.07f);
		ts.addSticks(0.01f);
		ts.addBushes(0.05f);
		
		
		setTileMap("dg_edging132.gif");
		setSpriteMap("itemsprites.gif");

		try {
			tiles = loader.load("2");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.size = tiles[0].length;
		VariantChooser variantChooser = new VariantChooser(size,tiles);
		variantChooser.setVariants();

		camera = new Camera((int)(size/2), 0, tileRes, windowSize);
		resTimesScale = tileRes * camera.zoom;
		gfx.camera  = new WedgeCamera(camera.x, camera.y, camera.tileRes, camera.windowSize);
		gfx.camera.camera = camera;
		
		for(int x = 0; x < size; x++){
			for(int y = 0; y < size; y++){
				getTile(x, y).x = x;
				getTile(x, y).y = y;
				ts.getTile(x,y).tile =getTile(x, y); 
			}
		}
	}
	
	public void setTile(int x, int y, Tile tile){
		tiles[x][y] = tile;
	}

	public void setTileID(int x, int y, TileSystem.TileId id) {
		tiles[x][y].id = id;
		VariantChooser variantChooser = new VariantChooser(size, tiles);
		variantChooser.setVariantAround(x,y);
	}
	
	public Tile getTile(int x, int y){
		return tiles[x][y];
	}
	
	public Tile getTileFromScreen(int x, int y){
		Vector2f world = screenToWorldPos(x, y);
		return getTileFromWorld(world.getX(), world.getY());
	}
	
	public int getSize(){
		return size;
	}
	
	public Tile getTileFromWorld(float x, float y){
		if(x > size || x < 0 || y > size || y < 0)
			return null;
		return tiles[(int)x][(int)y];
	}
	
	public Vector2f screenToWorldPos(int scX, int scY){
		return gfx.camera.screenToWorldPos(scX, scY);
	}
	
	public Vector2f worldToScreenPos(float worldX, float worldY){
		return gfx.camera.worldToScreenPos(worldX, worldY);
	}
	
	public void zoom(float zoomDelta){
		camera.zoom(zoomDelta);
		resTimesScale = tileRes * camera.zoom;
	}
	
	public void renderTiles(Graphics g){		
		gfx.drawTileSystem(ts);
	}
	
	public void renderGroundSprites(Graphics g, int row){
		gfx.renderGroundSprites(ts,g,spriteMap,row);
	}
	
	public void render3DSprites(Graphics g, int row){
		gfx.render3DSprites(ts, g, spriteMap, row);
	}
	
	public void render3DBuildings(Graphics g, int row){
		gfx.render3DBuildings(ts, g, row);
	}

	public void render3DBuilding(Graphics g, int row, Tile tile, BaseBuilding building){
		gfx.render3DBuilding(ts, g, row, tile, building);
	}
	
	
	private boolean isOnScreen(float x, float y){
		Vector2f sc = camera.worldToScreenPos(x, y);
		if(sc.x < -resTimesScale)
			return false;
		if(sc.x > (camera.windowSize.getX()+resTimesScale))
			return false;
		if(sc.y < -resTimesScale)
			return false;
		if(sc.y > (camera.windowSize.getY()+resTimesScale))
			return false;
		return true;
	}
	
	public void renderFog(Graphics g){
		float finalX, finalY;
		
		if (true ==true) return;
	
		Vector2f offsets = camera.getOffsets();
		for(int x = 0; x < size; x++){
            for(int y = 0; y < size; y++){
            	finalX = (x*resTimesScale)-offsets.x;
        		finalY = (y*resTimesScale)-offsets.y;
            	if (tiles[x][y].vis ==0)
            	{
            		g.setColor(Color.black);
            		g.fillRect(finalX, finalY, resTimesScale, resTimesScale);
            	}
            	else if (tiles[x][y].vis <100)
            	{
            		g.setColor(new Color(0, 0, 0,1.0f-((float)tiles[x][y].vis)/100));
            		g.fillRect(finalX, finalY, resTimesScale, resTimesScale);       
            	}
            }
		}
	}
	
	public void update(List<PlayerUI> players, GameSession gs, float delta){
		for(int x = 0; x < size; x++){
            for(int y = 0; y < size; y++){
            	if(tiles[x][y].vis > 30)
            		tiles[x][y].vis = 30;
            	tiles[x][y].update();
            }
		}
		
		for(int x = 0; x < size; x++){
            for(int y = 0; y < size; y++){
            	if(tiles[x][y].building != null){
            		clearFowArea((float)x, (float)y,tiles[x][y].building.base.fowArea);
            	}
            }
		}
		
		for(int x = 0; x < size; x++){
            for(int y = 0; y < size; y++){
            	if(tiles[x][y].hasSprite(SpriteType.FIRE)){
            		clearFowArea((float)x, (float)y,8);
            	}
            }
		}
		
		for(PlayerUI p : players){
			if (p.agent.getState() != AgentState.DEAD)
			{
				float xp = p.location.x-0.5f;
				float yp = p.location.y-0.5f;
				clearFowArea(xp,yp,3);
			}
		}
		Sprite.elapsedTime += delta;
		Tile.deltaPassed += delta;
	}
	
	private void clearFowArea(float xp, float yp, int fowClearArea)
	{
		//Hi, hope everyone likes this, if not just disable new Fog of war here
		boolean progressiveFow= true;
		int area = 30;
		int fowScaler = 25;
		for(int x = (int)xp - area; x < xp + area; x++){
			for(int y = (int)yp - area; y < yp + area; y++){
				if(x > 0 && y > 0 && x < size && y < size)
				{
					if (progressiveFow)	{
						float playerDist = dist(xp, yp, x, y);
						int vis = 100-((int)(((playerDist)-fowClearArea)*fowScaler));
						if (vis <0) vis =0;
						if (vis>100) vis = 100;
						if (vis>tiles[x][y].vis) tiles[x][y].vis =vis;
					}
					else{
						float playerDist = dist((int)(xp+0.5f), (int)(yp+0.5f), x, y);
						if (playerDist <= fowClearArea+1) {
							
							if (tiles[x][y].vis < 100) tiles[x][y].vis =100;
						}
					}
				}
			}
		}
	}
	
	private float dist(float x, float y, float x2, float y2){
		return (float) Math.sqrt((x2-x)*(x2-x)+(y2-y)*(y2-y));
	}
	
	public Camera getCamera(){
		return camera;
	}
	
	public void setTileRes(int res){
		tileRes = res;
	}
	
	public void setTileMap(String fileName){
		try {
			tileMap = new Image("images/tiles/"+fileName);
			tileMap.setFilter(Image.FILTER_NEAREST);
		} catch (SlickException e) {
			System.out.println("Error: Cannot load image " + fileName);
			e.printStackTrace();
		}
	}
	
	public void setSpriteMap(String fileName){
		try {
			spriteMap = new Image("images/tiles/"+fileName);
			spriteMap.setFilter(Image.FILTER_NEAREST);
		} catch (SlickException e) {
			System.out.println("Error: Cannot load image " + fileName);
			e.printStackTrace();
		}
	}

}
