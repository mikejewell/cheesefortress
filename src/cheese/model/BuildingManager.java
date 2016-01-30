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

	public BaseBuilding rootBuilding = null; 
	
	public BuildingManager() throws SlickException {
		this.availableBuildings = new ArrayList<BaseBuilding>();

	/*	addBuilding(new ResourceBuilding("Farm", "A lovely farm", new Cost(5,
				0, 0), 2));
		
		addBuilding(new DecorBuilding("Thor Statue","An amazing statue of Thor", new Cost(0, 0, 5)) {
			@Override
			public void onBuildComplete() {
				// Do something here.
			}
		});*/

		BaseBuilding herbary = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image("images/buildings/herbary/renders/idle_empty/45/000.png"));
			Vector<Image> workingImage = new Vector<Image>();
			for(int i=0; i<4; i++)
				idleImage.add(new Image("images/buildings/herbary/renders/work/45/"+getNumber(i)+".png"));
			herbary = new ResourceBuilding("Herbary", "", idleImage, idleImage, workingImage, null,5000);
			addBuilding(herbary);
		}
		
		BaseBuilding farm = null;
		{
			Vector<Image> fmProgressImage = new Vector<Image>();
			Image fmImage = new Image("images/buildings/farm.png");
			fmProgressImage.add(fmImage.getSubImage(0, 64, 64,64));
			Vector<Image> fmWorkingImage = new Vector<Image>();
			fmWorkingImage.add(fmImage.getSubImage(0, 0,64,64));
			
			Vector<BaseBuilding> subBuildings = new Vector<BaseBuilding>();
			subBuildings.add(herbary);
			
			farm=new ResourceBuilding("Farm", "", fmProgressImage, fmWorkingImage, fmWorkingImage, subBuildings, 100);
			addBuilding(farm);
		}

	
		BaseBuilding barracksWatchTower = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image("images/buildings/watchtower_lvl2-exp_full_size.png"));
			barracksWatchTower = new ResourceBuilding("Watch Tower", "",idleImage, idleImage, idleImage, null, 100);
			addBuilding(barracksWatchTower);
		}
		
		BaseBuilding barracksStable = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image("images/buildings/stable (1).png"));
			barracksStable = new ResourceBuilding("Stable", "",idleImage, idleImage, idleImage, null, 100);
			addBuilding(barracksStable);
		}
		
		BaseBuilding barracksTraining = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image("images/buildings/archery_range.png"));
			barracksTraining = new ResourceBuilding("Training", "",idleImage, idleImage, idleImage, null, 100);
			addBuilding(barracksTraining);
		}
		
		
		BaseBuilding barracksAdvanced = null;
		{
			Vector<Image> bk2IdleImage = new Vector<Image>();
			bk2IdleImage.add(new Image("images/buildings/barracks/renders/idle/45/000.png"));
			
			Vector<BaseBuilding> subBuildings = new Vector<BaseBuilding>();
			subBuildings.add(barracksTraining);
			subBuildings.add(barracksStable);
			subBuildings.add(barracksWatchTower);
			
			barracksAdvanced = new ResourceBuilding("Barracks", "",bk2IdleImage, bk2IdleImage, bk2IdleImage, subBuildings, 100);
			addBuilding(barracksAdvanced);
		}
		
		BaseBuilding signalFire = null;
		{
			Vector<Image> sfIdleImage = new Vector<Image>();
			sfIdleImage.add(new Image("images/buildings/signal_fire/renders/idle/45/000.png"));
			Vector<Image> sfWorkingImage = new Vector<Image>();
			for(int i=0; i<16; i++)
				sfIdleImage.add(new Image("images/buildings/signal_fire/renders/work/45/"+getNumber(i)+".png"));
			signalFire = new ResourceBuilding("Signal Fire", "", sfIdleImage, sfIdleImage, sfWorkingImage, null, 100);
			addBuilding(signalFire);
		}
		
		BaseBuilding weaponSmith = null;
		{
			Vector<Image> wsIdleImage = new Vector<Image>();
			wsIdleImage.add(new Image("images/buildings/weaponsmith/renders/idle/45/000.png"));
			Vector<Image> wsWorkingImage = new Vector<Image>();
			for(int i=0; i<8; i++)
				wsIdleImage.add(new Image("images/buildings/weaponsmith/renders/work/45/"+getNumber(i)+".png"));
			weaponSmith = new ResourceBuilding("Weaponsmith", "", wsIdleImage, wsIdleImage, wsWorkingImage, null, 100);
			addBuilding(weaponSmith);
		}
		
		
		BaseBuilding barracks = null;
		{
			Vector<Image> bkProgressImage = new Vector<Image>();
			Image bkImage = new Image("images/buildings/barracks.png");
			bkProgressImage.add(bkImage.getSubImage(0, 96, 96,96));
			Vector<Image> bkWorkingImage = new Vector<Image>();
			bkWorkingImage.add(bkImage.getSubImage(0, 0,96,96));
			
			Vector<BaseBuilding> subBuildings = new Vector<BaseBuilding>();
			subBuildings.add(barracksAdvanced);
			subBuildings.add(signalFire);
			subBuildings.add(weaponSmith);
			
			barracks = new ResourceBuilding("Barracks", "",bkProgressImage, bkWorkingImage, bkWorkingImage, null, 100);
			addBuilding(barracks);
		}
		
		BaseBuilding fireStation = null;
		{
			Vector<Image> bk2IdleImage = new Vector<Image>();
			bk2IdleImage.add(new Image("images/buildings/firestation/renders/idle/45/000.png"));
			fireStation = new ResourceBuilding("Fire Station", "",bk2IdleImage, bk2IdleImage, bk2IdleImage, null, 100);
			addBuilding(fireStation);
		}
		
		
		BaseBuilding megaMine = null;
		{			
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image("images/buildings/UH/as_mine5x5/idle/45/0.png"));
			Vector<Image> workingImage = new Vector<Image>();
			for(int i=1; i<24; i++)
				idleImage.add(new Image("images/buildings/UH/as_mine5x5/work/45/"+getNumber2(i)+".png"));
			megaMine = new ResourceBuilding("Mega Mine", "", idleImage, idleImage, workingImage, null, 100);
			addBuilding(megaMine);
		}
		
		
		BaseBuilding goldMine = null;
		{
			Vector<Image> gmProgressImage = new Vector<Image>();
			Image gmImage = new Image("images/buildings/gold_mine.png");
			gmProgressImage.add(gmImage.getSubImage(0, 192, 96,96));
			Vector<Image> gmIdleImage = new Vector<Image>();
			gmIdleImage.add(gmImage.getSubImage(0, 0,96,96));
			Vector<Image> gmWorkingImage = new Vector<Image>();
			gmWorkingImage.add(gmImage.getSubImage(0, 96,96,96));
			
			Vector<BaseBuilding> subBuildings = new Vector<BaseBuilding>();
			subBuildings.add(megaMine);
			
			goldMine = new ResourceBuilding("Gold Mine", "",gmProgressImage, gmIdleImage, gmWorkingImage, null, 100);
			addBuilding(goldMine);
		}
		
		BaseBuilding clayPit = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image("images/buildings/UH/as_clay_pit0/idle/45/0.png"));
			
			Vector<BaseBuilding> subBuildings = new Vector<BaseBuilding>();
			subBuildings.add(goldMine);
			
			clayPit = new ResourceBuilding("Clay Pit", "",idleImage, idleImage, idleImage, null, 100);
			addBuilding(clayPit);
		}
		
		BaseBuilding lumberMill = null;
		{
			Vector<Image> lmProgressImage = new Vector<Image>();
			Image lmImage = new Image("images/buildings/human_lumber_mill.png");
			lmProgressImage.add(lmImage.getSubImage(0, 96, 96,96));
			Vector<Image> lmWorkingImage = new Vector<Image>();
			lmWorkingImage.add(lmImage.getSubImage(0, 0,96,96));
			lumberMill =new ResourceBuilding("Lumber Mill", "",lmProgressImage, lmWorkingImage,lmWorkingImage, null, 100);
			addBuilding(lumberMill);
		}
		
		BaseBuilding lumberJack = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image("images/buildings/tent/lumberjack/as_lumberjack0/idle/45/0.png"));
			
			Vector<BaseBuilding> subBuildings = new Vector<BaseBuilding>();
			subBuildings.add(lumberMill);
			
			lumberJack =new ResourceBuilding("Lumber Mill", "",idleImage, idleImage, idleImage, subBuildings, 100);
			addBuilding(lumberJack);
		}
		
		BaseBuilding church = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image("images/buildings/church/renders/idle/45/000.png"));
			church = new ResourceBuilding("Advanced Religion", "",idleImage, idleImage, idleImage, null, 100);
			addBuilding(church);
		}

		
		
		BaseBuilding religionTent = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image("images/buildings/tent/pavilion/as_pavilion0/idle/45/0.png"));
			
			Vector<BaseBuilding> subBuildings = new Vector<BaseBuilding>();
			subBuildings.add(church);
			
			religionTent = new ResourceBuilding("Religion", "",idleImage, idleImage, idleImage, subBuildings, 100);
			addBuilding(religionTent);
		}
	
		BaseBuilding hunterTent = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image("images/buildings/tent/hunter/as_hunter0/idle/45/0.png"));
			
			Vector<BaseBuilding> subBuildings = new Vector<BaseBuilding>();
			subBuildings.add(farm);
			
			hunterTent = new ResourceBuilding("Hunter Abode", "",idleImage, idleImage, idleImage, subBuildings, 100);
			addBuilding(hunterTent);
		}
		
		
		
		BaseBuilding townHall = null;
		{
			Vector<Image> thProgressImage = new Vector<Image>();
			Image thImage = new Image("images/buildings/town_hall.png");
			thProgressImage.add(thImage.getSubImage(0, 128, 128,128));
			Vector<Image> thWorkingImage = new Vector<Image>();
			thWorkingImage.add(thImage.getSubImage(0, 0,128,128));		
		
			Vector<BaseBuilding> subBuildings = new Vector<BaseBuilding>();
			subBuildings.add(hunterTent);
			subBuildings.add(fireStation);
			subBuildings.add(lumberJack);
			subBuildings.add(clayPit);
			subBuildings.add(religionTent);
										
			townHall = new ResourceBuilding("Town Hall", "",thProgressImage, thWorkingImage, thWorkingImage, subBuildings, 100);
			addBuilding(townHall);
			
			rootBuilding = townHall;
		}
		

	}

	public String getNumber2(int val)
	{
		if (val < 10) 
			return "0" + val;
		
		return  ""+ val;
	}
	
	public String getNumber(int val)
	{
		if (val < 10) 
			return "00" + val;
		
		return "0" + val;
	}
	
	public Vector<BaseBuilding>currentBuildingOptions(BaseBuilding currentBuilding)
	{
		if (currentBuilding == null)
			return rootBuilding.subBuildings;
		
		return currentBuilding.subBuildings;
	}
	
}

	
