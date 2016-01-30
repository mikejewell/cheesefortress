package cheese.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class BuildingManager {
	ArrayList<BaseBuilding> availableBuildings;

	private void addBuilding(BaseBuilding building) {
		availableBuildings.add(building);
	}

	public BuildingManager() throws SlickException {
		this.availableBuildings = new ArrayList<BaseBuilding>();

		addBuilding(new ResourceBuilding("Farm", "A lovely farm", new Cost(5,
				0, 0), 2));
		
		addBuilding(new DecorBuilding("Thor Statue",
				"An amazing statue of Thor", new Cost(0, 0, 5)) {
			@Override
			public void onBuildComplete() {
				// Do something here.
			}
		});

		{
			Vector<Image> fmProgressImage = new Vector<Image>();
			Image fmImage = new Image("images/buildings/farm.png");
			fmProgressImage.add(fmImage.getSubImage(0, 64, 64,64));
			Vector<Image> fmWorkingImage = new Vector<Image>();
			fmWorkingImage.add(fmImage.getSubImage(0, 0,64,64));		
			addBuilding(new ResourceBuilding("Farm", "", fmProgressImage, fmWorkingImage, fmWorkingImage));
		}
		{
			Vector<Image> thProgressImage = new Vector<Image>();
			Image thImage = new Image("images/buildings/town_hall.png");
			thProgressImage.add(thImage.getSubImage(0, 128, 128,128));
			Vector<Image> thWorkingImage = new Vector<Image>();
			thWorkingImage.add(thImage.getSubImage(0, 0,128,128));		
			addBuilding(new ResourceBuilding("Town Hall", "",thProgressImage, thWorkingImage, thWorkingImage));
		}
		
		{
			Vector<Image> bkProgressImage = new Vector<Image>();
			Image bkImage = new Image("images/buildings/barracks.png");
			bkProgressImage.add(bkImage.getSubImage(0, 96, 96,96));
			Vector<Image> bkWorkingImage = new Vector<Image>();
			bkWorkingImage.add(bkImage.getSubImage(0, 0,96,96));
			addBuilding(new ResourceBuilding("Barracks", "",bkProgressImage, bkWorkingImage, bkWorkingImage));
		}
		
		{
			Vector<Image> bk2IdleImage = new Vector<Image>();
			bk2IdleImage.add(new Image("images/buildings/barracks/renders/idle/45/000.png"));
			addBuilding(new ResourceBuilding("Barracks", "",bk2IdleImage, bk2IdleImage, bk2IdleImage));			
		}
		
		{
			Vector<Image> bk2IdleImage = new Vector<Image>();
			bk2IdleImage.add(new Image("images/buildings/firestation/renders/idle/45/000.png"));
			addBuilding(new ResourceBuilding("Fire Station", "",bk2IdleImage, bk2IdleImage, bk2IdleImage));
		}
		
		{
		Vector<Image> gmProgressImage = new Vector<Image>();
		Image gmImage = new Image("images/buildings/gold_mine.png");
		gmProgressImage.add(gmImage.getSubImage(0, 192, 96,96));
		Vector<Image> gmIdleImage = new Vector<Image>();
		gmIdleImage.add(gmImage.getSubImage(0, 0,96,96));
		Vector<Image> gmWorkingImage = new Vector<Image>();
		gmWorkingImage.add(gmImage.getSubImage(0, 96,96,96));
		addBuilding(new ResourceBuilding("Gold Mine", "",gmProgressImage, gmIdleImage, gmWorkingImage));
		}
		
		{
		Vector<Image> lmProgressImage = new Vector<Image>();
		Image lmImage = new Image("images/buildings/human_lumber_mill.png");
		lmProgressImage.add(lmImage.getSubImage(0, 96, 96,96));
		Vector<Image> lmWorkingImage = new Vector<Image>();
		lmWorkingImage.add(lmImage.getSubImage(0, 0,96,96));
		addBuilding(new ResourceBuilding("Lumber Mill", "",lmProgressImage, lmWorkingImage,lmWorkingImage));
		}
		
		{
		Vector<Image> wsIdleImage = new Vector<Image>();
		wsIdleImage.add(new Image("images/buildings/weaponsmith/renders/idle/45/000.png"));
		Vector<Image> wsWorkingImage = new Vector<Image>();
		for(int i=0; i<8; i++)
			wsIdleImage.add(new Image("images/buildings/weaponsmith/renders/work/45/"+getNumber(i)+".png"));
		addBuilding(new ResourceBuilding("Weaponsmith", "", wsIdleImage, wsIdleImage, wsWorkingImage));	
		}
		
		{
		Vector<Image> sfIdleImage = new Vector<Image>();
		sfIdleImage.add(new Image("images/buildings/signal_fire/renders/idle/45/000.png"));
		Vector<Image> sfWorkingImage = new Vector<Image>();
		for(int i=0; i<16; i++)
			sfIdleImage.add(new Image("images/buildings/signal_fire/renders/work/45/"+getNumber(i)+".png"));
		addBuilding(new ResourceBuilding("Signal Fire", "", sfIdleImage, sfIdleImage, sfWorkingImage));	
		}
	}
	
	public String getNumber(int val)
	{
		if (val < 10) 
			return "00" + val;
		
		return "0" + val;
	}
	
	public ArrayList<BaseBuilding>currentBuildingOptions()
	{
		return this.availableBuildings;
	}
	
}

	
