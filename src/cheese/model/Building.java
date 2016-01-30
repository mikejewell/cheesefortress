package cheese.model;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import deserted.tilesystem.TileSystem;

public class Building
{
	TileSystem ts;
	public BaseBuilding base = null;
	private boolean isOffered;

	public Vector2f location = new Vector2f(0,0);
	
	public float buildProcess = 0; 
	
	
	public Building(TileSystem tsIn, BaseBuilding baseIn, Vector2f locationIn) {
		ts = tsIn;
		base = baseIn;
		location = locationIn;
		this.isOffered = false;
	}
	
	
	public Image getCurrentImage() {
		
		int imageIndex =(int)(( System.currentTimeMillis()/ base.animationSpeed) % base.buildingWorkingImages.size());
		
		Image image = base.buildingWorkingImages.get(imageIndex);
		
		//Do animation here then its different for each instance
		return image;
		
	}

	public boolean isOffered() {
		return isOffered;
	}


	public void setOffered(boolean isOffered) {
		this.isOffered = isOffered;
	}

	public void update(float deltaTime) {
		
		//Build stuff here like wool or sheep
		buildProcess += deltaTime * base.buildSpeed; 
		if (buildProcess>1) buildProcess = 1;
	}
	
	public void renderOverlay(Graphics g, float scale) {
		
		Vector2f screenLocation = ts.worldToScreenPos(location.x, location.y);
		
		float progress = buildProcess/100.0f;
		g.fillRect(screenLocation.x-20*scale,  screenLocation.y-30*scale, 40*scale * progress, 10*scale);
		g.drawRect(screenLocation.x-20*scale,  screenLocation.y-30*scale, 40*scale, 10*scale);
	}
}