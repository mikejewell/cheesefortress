package cheese.model;

import java.util.Vector;

public class BuildingManager {

	public Vector<Building>CurrentBuildingOptions()
	{
		Vector<Building> buildings = new Vector<Building>();
		buildings.add(new Building("Farm"));
		buildings.add(new Building("Temple"));
		buildings.add(new Building("Castle"));
		return buildings;
	}
	
}
