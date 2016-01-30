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
		//Overlays
		case 100:
			r = new int[]{256,704};	//Grass square
			break;
		case 101:
			r = new int[]{256,768};	//Stick 1
			break;
		case 102:
			r = new int[]{320,768};	//Stick 2
			break;
		case 103:
			r = new int[]{64,768};	//Bush 1
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
