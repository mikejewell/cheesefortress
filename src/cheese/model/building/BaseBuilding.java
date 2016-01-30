package cheese.model.building;

import java.util.Vector;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import cheese.model.Cost;

public abstract class BaseBuilding implements IBuilding {

	private Cost cost;
	private String name;
	private String description;
	public int animationSpeed;
	Vector<Image> buildingInProgressImages = null;
	Vector<Image> buildingIdleImages = null;
	Vector<Image> buildingWorkingImages = null;
	Vector<BaseBuilding> subBuildings = null;
	
	public int width = 0;
	public int height = 0;
	public double buildSpeed = 0.5;
	public int fowArea = 1;
	
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
	
	public double getDuration() {
		return 24*60;
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
	
	public BaseBuilding(String nameIn, String desc, Vector<Image> buildingInProgressImagesIn, Vector<Image> buildingIdleImagesIn, Vector<Image> buildingWorkingImagesIn, Vector<BaseBuilding> subBuildingsIn, int animationSpeedIn, double imageScale)throws SlickException
	{
		name = nameIn;
		buildingInProgressImages = resizeImages(buildingInProgressImagesIn,imageScale);
		buildingWorkingImages = resizeImages(buildingWorkingImagesIn,imageScale);
		buildingIdleImages = resizeImages(buildingIdleImagesIn,imageScale);
		subBuildings = subBuildingsIn;
		animationSpeed = animationSpeedIn;		
	
		Image constructionStarted = new Image("images/buildings/tent_ruin/as_tent_ruin0/idle/45/0.png");
		buildingInProgressImages.insertElementAt(constructionStarted, 0);
		
		width = buildingIdleImagesIn.get(0).getWidth();
		height = buildingIdleImagesIn.get(0).getHeight();
		
	}
	
	private Vector<Image> resizeImages(Vector<Image> original, double scale) {
		
		Vector<Image> images = new Vector<Image>();
		for(int i=0; i< original.size(); i++) {
			Image img = original.get(i);
			images.add(img.getScaledCopy((int)(img.getWidth()*scale), (int)(img.getHeight()*scale)));
		}
		return images;
	}
	

	public void renderBuilding(int x, int y, int width, int height)
	{
		if (buildingIdleImages != null)
		{
			if (buildingIdleImages.size() > 0)
			{
				int imageIndex =(int)(( System.currentTimeMillis()/ animationSpeed) % buildingIdleImages.size());
				
				Image image = buildingIdleImages.get(imageIndex);
				image.draw(x,y,x+width,y+height,0,0, image.getWidth(), image.getHeight());
			}
		}
	}
	
	public Image getCurrentImage() {
		return buildingIdleImages.get(0);	
	}
	
	public int getMinusXFootPrint()	{ 
		return -1; }
	
	public int getPlusXFootPrint()	{ 
		return 1; }
	
	public int getMinusYFootPrint()	{ 
		return -0; }
	
	public int getPlusYFootPrint()	{ 
		return 1; }

	public Cost getCost() {
		return cost;
	}

	public void setCost(Cost cost) {
		this.cost = cost;
	}
	
	
	
}