package cheese.model.building;



public interface IBuilding {
	public boolean canBuild();
	
	public void onBuildStart();
	public void onBuildComplete();
	
	public void onBuildingTick();
}
