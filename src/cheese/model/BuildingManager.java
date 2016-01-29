package cheese.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

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

		addBuilding(new ResourceBuilding("Farm", "", "images/buildings/farm.png"));
		addBuilding(new ResourceBuilding("Town Hall", "","images/buildings/town_hall.png"));
		addBuilding(new ResourceBuilding("Barracks", "","images/buildings/barracks.png"));
		addBuilding(new ResourceBuilding("Gold Mine", "","images/buildings/gold_mine.png"));
		addBuilding(new ResourceBuilding("Lumber Mill", "","images/buildings/human_lumber_mill.png"));
		
		Vector<String> wsImages = new Vector<String>();
		for(int i=0; i<7; i++)
			wsImages.add("images/buildings/weaponsmith/renders/work/45/00"+i+".png");
		addBuilding(new ResourceBuilding("Weaponsmith", "", wsImages));	

	}
	public ArrayList<BaseBuilding>currentBuildingOptions()
	{
		return this.availableBuildings;
	}
	
}

	
