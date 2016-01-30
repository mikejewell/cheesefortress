package cheese.model;

import java.util.Vector;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class BaseBuilding implements IBuilding {
	private String name;
	private String description;
	Vector<Image> buildingInProgressImages = null;
	Vector<Image> buildingIdleImages = null;
	Vector<Image> buildingWorkingImages = null;
	Vector<BaseBuilding> subBuildings = null;
	
	public BaseBuilding(String name, String desc) {
		this.setName(name);
		this.setDescription(desc);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
		
	
	public BaseBuilding(String nameIn, String desc, String imageNameIn)throws SlickException
	{
		name = nameIn;
		buildingInProgressImages = new Vector<Image>();
		buildingInProgressImages.add(new Image(imageNameIn));
		

		buildingWorkingImages = new Vector<Image>();
		buildingWorkingImages.add(new Image(imageNameIn));
		

		buildingIdleImages = new Vector<Image>();
		buildingIdleImages.add(new Image(imageNameIn));
	}
	
	public BaseBuilding(String nameIn, String desc, Vector<Image> buildingInProgressImagesIn, Vector<Image> buildingIdleImagesIn, Vector<Image> buildingWorkingImagesIn, Vector<BaseBuilding> subBuildingsIn)throws SlickException
	{
		name = nameIn;
		buildingInProgressImages = buildingInProgressImagesIn;
		buildingWorkingImages = buildingWorkingImagesIn;
		buildingIdleImages = buildingIdleImagesIn;
		subBuildings = subBuildingsIn;
	}
	

	public void renderBuilding(int x, int y, int width, int height)
	{
		if (buildingIdleImages != null)
		{
			if (buildingIdleImages.size() > 0)
			{
				int imageIndex =(int)(( System.currentTimeMillis()/ 100) % buildingIdleImages.size());
				
				Image image = buildingIdleImages.get(imageIndex);
				image.draw(x,y,x+width,y+height,0,0, image.getWidth(), image.getHeight());
			}
		}
	}
}