package cheese.model.quest;

import java.util.ArrayList;

import cheese.model.building.Building;
import cheese.model.building.BuildingManager;
import cheese.model.god.GodType;

import deserted.model.GameSession;

public class QuestBuilding extends Quest {
	private String buildingType;

	public QuestBuilding(String questName, String questDescription, int value, GodType god, String buildingType) {
		super(questName,questDescription, value, god);
		this.buildingType = buildingType;
	}
	
	@Override
	public boolean canComplete() {
		BuildingManager buildingManager = GameSession.getInstance().getBuildingManager();
		ArrayList<Building> suitableBuildings = buildingManager.getNonOfferedBuildingsOfType(this.buildingType);
		return suitableBuildings.size() > 0;
	}
	
	@Override
	public void onComplete() {
		BuildingManager buildingManager = GameSession.getInstance().getBuildingManager();
		ArrayList<Building> suitableBuildings = buildingManager.getNonOfferedBuildingsOfType(this.buildingType);
		suitableBuildings.get(0).setOffered(true);
		super.onComplete();
	}

}
