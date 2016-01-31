package cheese.graphics;


import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Polygon;

import cheese.graphics.tileSystem.WedgeTile;
import cheese.graphics.tileSystem.WedgeTileMap;
import cheese.graphics.tileSystem.WedgeTileOverlay;
import cheese.graphics.tileSystem.WedgeTileOverlay.OverlayType;
import cheese.graphics.tileSystem.WedgeTileSystem;
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
        	
    	tex.startUse();
    	for(int x = 0; x < tiles.length; x++){
    		for(int y = tiles.length-1; y >= 0; y--){   			
    			int[] uv = tileMap.getUV(tiles[x][y].getId());
    			
    			Tile tile = tiles[x][y].tile;
    			   			   		    		
    			finalX = (((tile.x-camera.x)*32)+((tile.y-camera.y)*32))*camera.zoom-offsets.x-(32*camera.zoom); 
	    		finalY = (((tile.x-camera.x)*16)-((tile.y-camera.y)*16))*camera.zoom-offsets.y-(16*camera.zoom);
    			
	    		float xa = finalX-32*camera.zoom;
	    		float ya = finalY-32*camera.zoom;
	    		float xb = finalX+32*camera.zoom;
	    		float yb = finalY+32*camera.zoom;
	    		
        		tex.drawEmbedded(xa,ya,xb,yb, uv[0], uv[1], uv[0]+64, uv[1]+64);
        		if(tiles[x][y].getOverlay() != OverlayType.EMPTY){
	        		if(tiles[x][y].getOverlay().ordinal() < OverlayType.OVERLAYS3D.ordinal()){
	        			int[] uvOver = WedgeTileOverlay.getOverlay(tiles[x][y].getOverlay()).getUVs();
	        			tex.drawEmbedded(xa,ya,xb,yb, uvOver[0], uvOver[1], uvOver[0]+64, uvOver[1]+64);
	        		}
        		}
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
        
        WedgeTileMap tileMap = ts.getTileMap();
        Image tex = tileMap.getTexture();
        tex.startUse();
    	for(int x = 0; x < tiles.length; x++){				
			Tile tile = tiles[x][row].tile;
    		
    		if(tiles[x][row].getOverlay() != OverlayType.EMPTY){
        		if(tiles[x][row].getOverlay().ordinal() > OverlayType.OVERLAYS3D.ordinal()){
        			finalX = (((tile.x-camera.x)*32)+((tile.y-camera.y)*32))*camera.zoom-offsets.x-(32*camera.zoom); 
            		finalY = (((tile.x-camera.x)*16)-((tile.y-camera.y)*16))*camera.zoom-offsets.y-(16*camera.zoom);
        			
            		int[] uvOver = WedgeTileOverlay.getOverlay(tiles[x][row].getOverlay()).getUVs();
            		int width = uvOver[2] - uvOver[0];
            		int height = uvOver[3] - uvOver[1];
            		
            		float xa = finalX-(width/2)*camera.zoom;
            		float ya = finalY-height*camera.zoom;
            		float xb = finalX+(width/2)*camera.zoom;
            		float yb = finalY;
            		
        			tex.drawEmbedded(xa,ya,xb,yb, uvOver[0], uvOver[1], uvOver[2], uvOver[3]);
        		}
    		}
    	}
    	tex.endUse();
	}
	
	public void render3DBuildings(WedgeTileSystem ts,Graphics g, int row){
		WedgeTile[][] tiles = ts.getTiles();
		float finalX, finalY, scaleX, scaleXOffset, scaleY, scaleYOffset;
		Building type;
		
		row = tiles[0].length-(row+1);
		float resTimesScale = camera.tileRes * camera.zoom;
		
		Vector2f offsets = camera.getOffsets();
        for(int x = 0; x < tiles[0].length; x++){  
        	Tile tile = tiles[x][row].tile;
        	type = tile.getBuildingToDraw();
        	//sprite = SpriteManager.getSprite(type);
        	if(type != null){
        		Image buildingImage = type.getCurrentImage();
	    		scaleX = buildingImage.getWidth()/35;
	        	scaleXOffset = (scaleX - 1)*resTimesScale*0.5f;
	        	scaleY = buildingImage.getHeight()/35;
	        	scaleYOffset = (scaleY - 1)*resTimesScale*0.5f;
	        	finalX = (((tile.x-camera.x)*32)+((tile.y-camera.y)*32))*camera.zoom-offsets.x-(32*camera.zoom); 
	    		finalY = (((tile.x-camera.x)*16)-((tile.y-camera.y)*16))*camera.zoom-offsets.y-(16*camera.zoom);
	    		if(camera.isOnScreen(x, row)){
            			g.drawImage(buildingImage, finalX-scaleXOffset, finalY-scaleXOffset, finalX+scaleXOffset, finalY+scaleYOffset, 0,0,buildingImage.getWidth(), buildingImage.getHeight());
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
        	if (tile.cornerY == row)
        	{
        		System.out.println("Hi");
        		Image buildingImage = building.getCurrentImage();
	    		scaleX = buildingImage.getWidth()/35;
	        	scaleXOffset = (scaleX - 1)*resTimesScale*0.5f;
	        	scaleY = buildingImage.getHeight()/35;
	        	scaleYOffset = (scaleY - 1)*resTimesScale*0.5f;
	        	finalX = (((tile.x-camera.x)*32)+((tile.y-camera.y)*32))*camera.zoom-offsets.x-(32*camera.zoom); 
	    		finalY = (((tile.x-camera.x)*16)-((tile.y-camera.y)*16))*camera.zoom-offsets.y-(16*camera.zoom);
	    		if(camera.isOnScreen(tile.cornerX, row)){
            			g.drawImage(buildingImage, finalX-scaleXOffset, finalY-scaleXOffset, finalX+scaleXOffset, finalY+scaleYOffset, 0,0,buildingImage.getWidth(), buildingImage.getHeight());
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
    		
            	Tile tile = tiles[x][y].tile;
  		    		
    			finalX = (((tile.x-camera.x)*32)+((tile.y-camera.y)*32))*camera.zoom-offsets.x-(32*camera.zoom); 
	    		finalY = (((tile.x-camera.x)*16)-((tile.y-camera.y)*16))*camera.zoom-offsets.y;
	    			    		
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
	
	public void renderTile(WedgeTileSystem ts,Graphics g, Tile tile, Color c){
		float finalX, finalY;
	
		Vector2f offsets = camera.getOffsets();
         	   		
		finalX = (((tile.x-camera.x)*32)+((tile.y-camera.y)*32))*camera.zoom-offsets.x-(32*camera.zoom); 
		finalY = (((tile.x-camera.x)*16)-((tile.y-camera.y)*16))*camera.zoom-offsets.y;
			    		
		Polygon p = new Polygon();
		p.addPoint((int)(finalX-32*camera.zoom), (int)finalY);
		p.addPoint((int)finalX, (int)(finalY+16*camera.zoom));
		p.addPoint((int)(finalX+32*camera.zoom), (int)finalY);
		p.addPoint((int)finalX, (int)(finalY-16*camera.zoom));
		        		
     
    	g.setColor(new Color(c.r, c.g, c.b, 0.5f));   
    	g.fill(p);
	}
	
}
