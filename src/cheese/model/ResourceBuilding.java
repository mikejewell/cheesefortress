package cheese.model;

import java.util.Vector;

import org.newdawn.slick.SlickException;

public class ResourceBuilding extends BaseBuilding {

	private Cost cost;
	private int villagers;

	public ResourceBuilding(String name, String desc, Cost cost, int villagers) {
		super(name, desc);
		this.cost = cost;
		this.villagers = villagers;
	}
	
	public ResourceBuilding(String name, String desc, String image) throws SlickException  {
		super(name, desc, image);
		this.villagers = 3232;
	}
	
	public ResourceBuilding(String name, String desc, Vector<String> images)throws SlickException  {
		super(name, desc, images);
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