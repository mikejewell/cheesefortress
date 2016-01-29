package cheese.model;

import java.util.ArrayList;

public class BuildingManager {
	ArrayList<BaseBuilding> availableBuildings;

	private void addBuilding(BaseBuilding building) {
		availableBuildings.add(building);
	}

	public BuildingManager() {
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

	}

}
