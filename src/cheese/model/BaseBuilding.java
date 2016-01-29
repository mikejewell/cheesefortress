package cheese.model;

import java.util.Vector;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class BaseBuilding implements IBuilding {
	private String name;
	private String description;
	Vector<Image> images = null;

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
		images = new Vector<Image>();
		images.add(new Image(imageNameIn));
	}
	
	public BaseBuilding(String nameIn, String desc, Vector<String> imageNamesIn)throws SlickException
	{
		name = nameIn;
		images = new Vector<Image>();
		for(int i=0; i< imageNamesIn.size()-1; i++)
		{
			images.add(new Image(imageNamesIn.get(i)));
		}
	}
	
	int imageIndex = 0;
	public void renderBuilding(int x, int y, int width, int height)
	{
		if (images != null)
		{
			if (images.size() > 0)
			{
				imageIndex++;
				if (imageIndex >= images.size()) imageIndex = 0;
				Image image = images.get(imageIndex);
				image.draw(x,y,x+width,y+height,0,0, image.getWidth(), image.getHeight());
			}
		}
	}
}