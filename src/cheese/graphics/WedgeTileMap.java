package cheese.graphics;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class WedgeTileMap {
	
	private int tileRes;
	private Image tileMap;
	
	public WedgeTileMap(String texFileName, int tileRes)
	{
		tileMap = loadImage(texFileName);
		this.tileRes = tileRes;
	}
	
	public Image getTexture(){
		return tileMap;
	}
	
	public int getTileRes(){
		return tileRes;
	}
	
	public int[] getUV(int tileId){
		int[] r;
		
		switch(tileId){
		case 0:
			r = new int[]{0,0};
		case 1:
			r = new int[]{64,448};
		default:
			r = new int[]{0,0};
		}
		
		return r;
	}
	
	private Image loadImage(String fileName){
		Image r = null;
		try {
			r = new Image(fileName);
			r.setFilter(Image.FILTER_NEAREST);
		} catch (SlickException e) {
			System.out.println("Error: Cannot load image " + fileName);
			e.printStackTrace();
		}
		return r;
	}

}
