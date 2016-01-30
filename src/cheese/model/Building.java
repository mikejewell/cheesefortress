package cheese.model;

import org.newdawn.slick.Image;

public class Building
{
	public BaseBuilding base = null;

	
	public Image getCurrentImage() {
		//Do animation here then its different for each instance
		return base.buildingWorkingImages.get(0);
		
	}
	
}