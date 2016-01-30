package cheese.graphics;


import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Polygon;

import cheese.model.building.BaseBuilding;
import cheese.model.building.Building;
import deserted.sprite.Sprite;
import deserted.sprite.SpriteManager;
import deserted.sprite.SpriteType;
import deserted.tilesystem.Tile;

public class WedgeGFX {
	
	public WedgeCamera camera;
     
    public WedgeGFX() {
    	
    }
     
    public void drawTileSystem(WedgeTileSystem ts){
    	WedgeTile[][] tiles = ts.getTiles();
    	WedgeTileMap tileMap = ts.getTileMap();
    	Image tex = tileMap.getTexture();
    	
    	Vector2f offsets = camera.getOffsets();
    	
    	
    	float finalX, finalY;
    	float x = camera.x;
    	float y = camera.y;
    	
    	tex.startUse();
    	for(int i = 0; i < tiles.length; i++){
    		for(int j = tiles.length-1; j >= 0; j--){
    			int[] uv = tileMap.getUV(tiles[i][j].getId());
    			
    			finalX = offsets.x;
    			finalY = offsets.y;
    			
        		tex.drawEmbedded((((j-y)*32)+((i-x)*32))*camera.zoom-finalX, (((i-x)*16)-((j-y)*16))*camera.zoom-finalY, (((j-y)*32)+((i-x)*32)+64)*camera.zoom-finalX, (((i-x)*16)-((j-y)*16)+64)*camera.zoom-finalY, uv[0], uv[1], uv[0]+64, uv[1]+64);
        	}
    	}
    	tex.endUse();
    }
    
    
	public void renderGroundSprites(WedgeTileSystem ts, Graphics g, Image spriteMap, int row){
		WedgeTile[][] tiles = ts.getTiles();
		
		float finalX, finalY, scale, scaleOffset;
		Sprite sprite;
		SpriteType type;
		
		row = tiles[0].length-(row+1);
		float resTimesScale = camera.tileRes * camera.zoom;
		
		Vector2f offsets = camera.getOffsets();
        for(int x = 0; x < tiles[0].length; x++){
        	type = tiles[x][row].tile.getSpriteToDraw();
        	sprite = SpriteManager.getSprite(type);
        	if(sprite != null && sprite.isOnGround()){
	        	scale = sprite.getScale();
	        	scaleOffset = (scale - 1)*resTimesScale*0.5f;
	        	finalX = (((x-camera.x)*32)+((row-camera.y)*32))*camera.zoom-offsets.x; 
	    		finalY = (((x-camera.x)*16)-((row-camera.y)*16))*camera.zoom-offsets.y;
	    		if(camera.isOnScreen(x, row)){
            		Point src = sprite.getTexCoord(tiles[x][row].tile.getSpriteData(type).timeOffset);
            		if(src != null)
            			g.drawImage(spriteMap, finalX, finalY, finalX+resTimesScale+scaleOffset*2, finalY+resTimesScale+scaleOffset, src.getX(), src.getY(), src.getX()+32, src.getY()+32);
	        	}
        	}
        }
	}
	
	public void render3DSprites(WedgeTileSystem ts, Graphics g, Image spriteMap, int row){
		WedgeTile[][] tiles = ts.getTiles();
		float finalX, finalY, scale, scaleOffset;
		Sprite sprite;
		SpriteType type;
		
		row = tiles[0].length-(row+1);
		float resTimesScale = camera.tileRes * camera.zoom;
		
		Vector2f offsets = camera.getOffsets();
        for(int x = 0; x < tiles[0].length; x++){
        	type = tiles[x][row].tile.getSpriteToDraw();
        	sprite = SpriteManager.getSprite(type);
        	if(sprite != null && !sprite.isOnGround()){
	        	scale = sprite.getScale();
	        	scaleOffset = (scale - 1)*resTimesScale*0.5f;
	        	finalX = (((x-camera.x)*32)+((row-camera.y)*32))*camera.zoom-offsets.x; 
	    		finalY = (((x-camera.x)*16)-((row-camera.y)*16))*camera.zoom-offsets.y; 
	    		if(camera.isOnScreen(x, row)){
	    			Point src = sprite.getTexCoord(tiles[x][row].tile.getSpriteData(type).timeOffset);
            		if(src != null)
            			g.drawImage(spriteMap, finalX, finalY, finalX+resTimesScale+scaleOffset*2, finalY+resTimesScale+scaleOffset*2, src.getX(), src.getY(), src.getX()+32, src.getY()+32);
	        	}
        	}
        }
	}
	
	public void render3DBuildings(WedgeTileSystem ts,Graphics g, int row){
		WedgeTile[][] tiles = ts.getTiles();
		float finalX, finalY, scaleX, scaleXOffset, scaleY, scaleYOffset;
		Building type;
		
		row = tiles[0].length-(row+1);
		float resTimesScale = camera.tileRes * camera.zoom;
		
		Vector2f offsets = camera.getOffsets();
        for(int x = 0; x < tiles[0].length; x++){
        	type = tiles[x][row].tile.getBuildingToDraw();
        	//sprite = SpriteManager.getSprite(type);
        	if(type != null){
        		Image buildingImage = type.getCurrentImage();
	    		scaleX = buildingImage.getWidth()/35;
	        	scaleXOffset = (scaleX - 1)*resTimesScale*0.5f;
	        	scaleY = buildingImage.getHeight()/35;
	        	scaleYOffset = (scaleY - 1)*resTimesScale*0.5f;
	        	finalX = (((x-camera.x)*32)+((row-camera.y)*32))*camera.zoom-offsets.x; 
	    		finalY = (((x-camera.x)*16)-((row-camera.y)*16))*camera.zoom-offsets.y;     		
	    		if(camera.isOnScreen(x, row)){
            			g.drawImage(buildingImage, finalX, finalY, finalX+resTimesScale+scaleXOffset*2, finalY+resTimesScale+scaleYOffset*2, 0,0,buildingImage.getWidth(), buildingImage.getHeight());
	        	}
        	}
        }
	}
	
	public void render3DBuilding(WedgeTileSystem ts,Graphics g, int row, Tile tile, BaseBuilding building){
		float finalX, finalY, scaleX, scaleXOffset, scaleY, scaleYOffset;
		//REALLY LAZY NEW TILE RENDERER COMING SOON :)
		WedgeTile[][] tiles = ts.getTiles();
		row = tiles[0].length-(row+1);
		float resTimesScale = camera.tileRes * camera.zoom;
		
		Vector2f offsets = camera.getOffsets();
     //   for(int x = 0; x < size; x++){
        	if (tile.y == row)
        	{
        		Image buildingImage = building.getCurrentImage();
	    		scaleX = buildingImage.getWidth()/35;
	        	scaleXOffset = (scaleX - 1)*resTimesScale*0.5f;
	        	scaleY = buildingImage.getHeight()/35;
	        	scaleYOffset = (scaleY - 1)*resTimesScale*0.5f;
	        	finalX = (((tile.x-camera.x)*32)+((row-camera.y)*32))*camera.zoom-offsets.x; 
	    		finalY = (((tile.x-camera.x)*16)-((row-camera.y)*16))*camera.zoom-offsets.y;
	    		
	    		if(camera.isOnScreen(tile.x, row)){
            			g.drawImage(buildingImage, finalX, finalY, finalX+resTimesScale+scaleXOffset*2, finalY+resTimesScale+scaleYOffset*2, 0,0,buildingImage.getWidth(), buildingImage.getHeight());
	        	}
        	}
       // }
	}
	
	
	public void renderFog(WedgeTileSystem ts,Graphics g){
		float finalX, finalY;
		WedgeTile[][] tiles = ts.getTiles();
		
		Vector2f offsets = camera.getOffsets();
		for(int x = 0; x < tiles[0].length; x++){
            for(int y = 0; y < tiles[0].length; y++){           	
    		
	        	finalX = (((x-camera.x)*32)+((y-camera.y)*32))*camera.zoom-offsets.x; 
	    		finalY = (((x-camera.x)*16)-((y-camera.y)*16))*camera.zoom-offsets.y;
	    			    		
	    		Polygon p = new Polygon();
        		p.addPoint((int)(finalX-32*camera.zoom), (int)finalY);
        		p.addPoint((int)finalX, (int)(finalY+16*camera.zoom));
        		p.addPoint((int)(finalX+32*camera.zoom), (int)finalY);
        		p.addPoint((int)finalX, (int)(finalY-16*camera.zoom));
        		        		
            	if (tiles[x][y].tile.vis ==0)
            	{         
            		g.setColor(Color.black);   
            		g.fill(p);
            	}
            	else if (tiles[x][y].tile.vis <100)
            	{
            		g.setColor(new Color(0, 0, 0,1.0f-((float)tiles[x][y].tile.vis)/100));
            		g.fill(p);   
            	}
            }
		}
	}
	
}
