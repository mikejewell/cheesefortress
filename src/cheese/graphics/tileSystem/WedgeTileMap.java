package cheese.graphics.tileSystem;

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
			r = new int[]{256,512};	//Water 
			break;
		case 1:
			r = new int[]{128,0};	//Dirt
			break;
		case 2:
			r = new int[]{0,0};		//Grass
			break;
		case 3:
			r = new int[]{256,320};	//Rock
			break;
		case 4:
			r = new int[]{320,448};	//Cliff
			break;
		default:
			r = new int[]{0,0};
			break;
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
