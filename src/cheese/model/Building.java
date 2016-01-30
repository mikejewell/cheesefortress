package cheese.model;

import java.util.Random;

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
	
	public long animationOffset = 0;
	
	
	public Building(TileSystem tsIn, BaseBuilding baseIn, Vector2f locationIn) {
		ts = tsIn;
		base = baseIn;
		location = locationIn;
		this.isOffered = false;
		
		animationOffset = System.currentTimeMillis();
	}
	
	
	public Image getCurrentImage() {
		
		Image image = null;
		
		if (buildProcess < 1) {
			int imageIndex = (int)(base.buildingInProgressImages.size() * buildProcess);
			
			image = base.buildingInProgressImages.get(imageIndex);
		}
		else {
			int imageIndex =(int)(( (System.currentTimeMillis()-animationOffset)/ base.animationSpeed) % base.buildingWorkingImages.size());
		
			image = base.buildingWorkingImages.get(imageIndex);
		}
		
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
		if (buildProcess<1) {
			Vector2f screenLocation = ts.worldToScreenPos(location.x, location.y);
		
			float progress = buildProcess;
			g.fillRect(screenLocation.x-20*scale,  screenLocation.y-30*scale, 40*scale * progress, 10*scale);
			g.drawRect(screenLocation.x-20*scale,  screenLocation.y-30*scale, 40*scale, 10*scale);
		}
	}
}