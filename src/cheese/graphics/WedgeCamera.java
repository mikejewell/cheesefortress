package cheese.graphics;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.geom.Point;


public class WedgeCamera {
	
	//private float sx, sy;
	public float x, y;
	public float zoom = 1;
	
	public int tileRes;
	public boolean isFollowing = false;
	public Point windowSize;
	
	public WedgeCamera(float x, float y, int tileRes, Point windowSize){
		this.x = x;
		this.y = y;
		this.tileRes = 64;
		this.windowSize = windowSize;
	}
	
	
	public void move(float x, float y){
		this.x += x;
		this.y += y;
	}
	
	public void zoom(float zoomDelta){
		zoom = zoom + zoomDelta;
		if (zoom >= 2.6f)
			zoom = 2.6f;
		if (zoom <= 0.5f)
			zoom = 0.5f;
	}

	
	
	public Vector2f screenToWorldPos(int scX, int scY){
		float wx2 = windowSize.getX()/2;
		float wy2 = (windowSize.getY()/2);
		
		float vx = (scX+(0-wx2))/zoom;
		float vy = (scY+(0-wy2))/zoom;
		
		float wy = (vx-2*vy)/64;
		float wx = ((2*vy)+(32*wy))/32;
		
		return new Vector2f(wx+(x+0.5f),wy+(y+0.5f));
	}
	
	
	public Vector2f worldToScreenPos(float worldX, float worldY){
		Vector2f offsets = getOffsets();
		float finalX = (((worldX-(x-0.5f))*32)+((worldY-(y-0.5f))*32))*zoom-offsets.x; 
		float finalY = (((worldX-(x-0.5f))*16)-((worldY-(y-0.5f))*16))*zoom-offsets.y; 
		return new Vector2f(finalX, finalY);
	}
	
	public Vector2f getOffsets(){
		return new Vector2f(-windowSize.getX()/2, -windowSize.getY()/2);
	}
	
	public boolean isOnScreen(float x, float y){
		float resTimesScale = tileRes * zoom;
		
		Vector2f sc = worldToScreenPos(x, y);
		if(sc.x < -resTimesScale)
			return false;
		if(sc.x > (windowSize.getX()+resTimesScale))
			return false;
		if(sc.y < -resTimesScale)
			return false;
		if(sc.y > (windowSize.getY()+resTimesScale))
			return false;
		return true;
	}
}
