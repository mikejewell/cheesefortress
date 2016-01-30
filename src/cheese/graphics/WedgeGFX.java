package cheese.graphics;

import org.newdawn.slick.Image;

public class WedgeGFX {
	
	private WedgeCamera camera;
     
    public WedgeGFX() {
    	
    }
     
    public void drawTileSystem(WedgeTileSystem ts){
    	WedgeTile[][] tiles = ts.getTiles();
    	WedgeTileMap tileMap = ts.getTileMap();
    	Image tex = tileMap.getTexture();
    	
    	tex.startUse();
    	for(int i = 0; i < 20; i++){
    		for(int j = 20-1; j >= 0; j--){
    			int[] uv = tileMap.getUV(tiles[i][j].getId());
        		tex.drawEmbedded((j*32)+(i*32), 400+(i*16)-(j*16), (j*32)+(i*32)+32, 400+(i*16)-(j*16)+32, uv[0], uv[1], uv[0]+64, uv[1]+64);
        	}
    	}
    	tex.endUse();
    }
    
}
