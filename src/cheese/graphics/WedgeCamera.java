package cheese.graphics;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.geom.Point;

import deserted.tilesystem.Camera;

public class WedgeCamera {
	
	//private float sx, sy;
	private float x, y;
	public float zoom = 1;
	
	public int tileRes;
	public boolean isFollowing = false;
	public Point windowSize;
	
	public Camera camera;
	
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
	
	public void update()
	{
		x = camera.x;
		y = camera.y;
		zoom = camera.zoom;
		windowSize = camera.windowSize;
	}
	
	
	public Vector2f screenToWorldPos(int scX, int scY){
		update();
		float resTimesZoom = tileRes * zoom;
		
		float wx2 = windowSize.getX()/2;
		float wy2 = (windowSize.getY()/2);
		
		float rx = (resTimesZoom*x);
		float ry = (resTimesZoom*y);
		
		float vx = (scX+(rx-wx2))/camera.zoom;
		float vy = (scY+(ry-wy2))/camera.zoom;
		
		float wy = (vx-2*vy)/64;
		float wx = ((2*vy)+(32*wy))/32;
		
		//float vx = (worldX*32)+(worldY*32); 
		//float vy = (worldX*16)-(worldY*16);
		
		return new Vector2f(wx,wy);
	}
	
	/*public Vector2f worldToScreenPos(float worldX, float worldY){
		update();
		//float resTimesScale = tileRes * zoom;
		//float xd = x - worldX;
		//float yd = y - worldY;
		//return new Vector2f(windowSize.getX()/2 - xd*resTimesScale, windowSize.getY()/2 - yd*resTimesScale);
		float resTimesZoom = tileRes * zoom;
		float finalX = ((worldX*32)+(worldY*32))*camera.zoom-((resTimesZoom*x)-windowSize.getX()/2); 
		float finalY = ((worldX*16)-(worldY*16))*camera.zoom-((resTimesZoom*y)-windowSize.getY()/2); 
		return new Vector2f(finalX, finalY);
	}
	}*/
	
	public Vector2f worldToScreenPos(float worldX, float worldY){
		update();
		//float resTimesScale = tileRes * zoom;
		//float xd = x - worldX;
		//float yd = y - worldY;
		//return new Vector2f(windowSize.getX()/2 - xd*resTimesScale, windowSize.getY()/2 - yd*resTimesScale);
		Vector2f offsets = getOffsets();
		float finalX = ((worldX*32)+(worldY*32))*camera.zoom-offsets.x; 
		float finalY = ((worldX*16)-(worldY*16))*camera.zoom-offsets.y; 
		return new Vector2f(finalX, finalY);
	}
	
	public Vector2f getOffsets(){
		update();
		float resTimesZoom = tileRes * zoom;
		return new Vector2f((resTimesZoom*x)-windowSize.getX()/2, (resTimesZoom*y)-windowSize.getY()/2);
	}
}
