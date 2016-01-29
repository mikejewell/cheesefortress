package cheese.model;


public class ResourceBuilding extends BaseBuilding {

	private Cost cost;
	private int villagers;

	public ResourceBuilding(String name, String desc, Cost cost, int villagers) {
		super(name, desc);
		this.cost = cost;
		this.villagers = villagers;
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
	public void onBuildingTick() {
		
	}
}