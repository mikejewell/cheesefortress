package cheese.model.building;

import java.util.Vector;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import cheese.model.Cost;
import deserted.tilesystem.Tile;
import deserted.tilesystem.TileSystem;

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
	
	public double scale = 1;
	
	public int yOffset = 0;
	
	public FootPrint footprint;
	
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
	
	public BaseBuilding(String nameIn, String desc, Vector<Image> buildingInProgressImagesIn, Vector<Image> buildingIdleImagesIn, Vector<Image> buildingWorkingImagesIn, Vector<BaseBuilding> subBuildingsIn, int animationSpeedIn, double imageScale, FootPrint footprintIn)throws SlickException
	{
		name = nameIn;
		buildingInProgressImages = new Vector<Image>(buildingInProgressImagesIn); //resizeImages(buildingInProgressImagesIn,imageScale);
		buildingWorkingImages = new Vector<Image>(buildingWorkingImagesIn);//resizeImages(buildingWorkingImagesIn,imageScale);
		buildingIdleImages = new Vector<Image>(buildingIdleImagesIn);//resizeImages(buildingIdleImagesIn,imageScale);
		scale = imageScale;
		subBuildings = subBuildingsIn;
		animationSpeed = animationSpeedIn;		
	
		Image constructionStarted = new Image("images/buildings/tent_ruin/as_tent_ruin0/idle/45/0.png");
		buildingInProgressImages.insertElementAt(constructionStarted, 0);
		
		width = buildingIdleImagesIn.get(0).getWidth();
		height = buildingIdleImagesIn.get(0).getHeight();
	
		footprint = footprintIn;
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
				Image image = buildingIdleImages.get(0);
				image.draw(x,y,x+width,y+height,0,0, image.getWidth(), image.getHeight());
			}
		}
	}
	
	public Image getCurrentImage() {
		return buildingIdleImages.get(0);	
	}
	

	public Cost getCost() {
		return cost;
	}
	
	
	public Vector<Tile> getOverlappingTiles(TileSystem ts, Tile root) {
		Vector<Tile> tiles = new Vector<Tile>();
		
		
		if (footprint != null) {
			for(int x = root.cornerX-footprint.minX; x<= root.cornerX+footprint.maxX; x++) {
				for(int y = root.cornerY-footprint.minY; y<= root.cornerY+footprint.maxY; y++) {
					tiles.add(ts.getTile(x, y));
				}
			}
		}
		else
		{
			tiles.add(root);
		}
		
		return tiles;
	}
	

	public void setCost(Cost cost) {
		this.cost = cost;
	}
	
	
	
}

