package cheese.model;

import org.newdawn.slick.Image;

public class Building
{
	public BaseBuilding base = null;
	private boolean isOffered;

	public Building() {
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
	
}