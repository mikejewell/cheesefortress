package cheese.model.building;

import cheese.model.Cost;


public class DecorBuilding extends BaseBuilding {

	private Cost cost;

	public DecorBuilding(String name, String desc, Cost cost) {
		super(name, desc);
		this.cost = cost;
	}

	@Override
	public boolean canBuild() {
		return false;
	}

	@Override
	public void onBuildStart() {
		
	}

	@Override
	public void onBuildComplete() {
		
	}

	@Override
	public void onBuildingTick(Building building) {
		
	}
}