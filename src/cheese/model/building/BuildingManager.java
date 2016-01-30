package cheese.model.building;

import java.util.ArrayList;
import java.util.Vector;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import cheese.model.Cost;

import deserted.model.GameSession;
import deserted.model.Inventory;
import deserted.model.item.ItemType;

public class BuildingManager {
	ArrayList<Building> buildingsInPlay;
	ArrayList<BaseBuilding> availableBuildings;

	private void addBuilding(BaseBuilding building) {
		availableBuildings.add(building);
	}

	public void addBuildingInPlay(Building building) {
		buildingsInPlay.add(building);
	}

	public BaseBuilding rootBuilding = null;

	public BuildingManager() throws SlickException {
		this.availableBuildings = new ArrayList<BaseBuilding>();
		this.buildingsInPlay = new ArrayList<Building>();

		BaseBuilding herbary = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image(
					"images/buildings/herbary/renders/idle_empty/45/000.png"));
			Vector<Image> workingImage = new Vector<Image>();
			for (int i = 0; i < 4; i++)
				workingImage.add(new Image(
						"images/buildings/herbary/renders/work/45/"
								+ getNumber(i) + ".png"));
			herbary = new ResourceBuilding("Herbary", "", idleImage, idleImage,
					workingImage, null, 5000, 0.6) { 
				@Override
				public void onBuildingTick() {
					GameSession.getInstance().getInventory().addItem(ItemType.FOOD);
				}
				
				@Override
				public double getDuration() {
					return 24*60;
				}
			};
			herbary.setCost(new Cost(0, 1, 3, 3));
			addBuilding(herbary);
		}

		BaseBuilding farm = null;
		{
			Vector<Image> fmProgressImage = new Vector<Image>();
			Image fmImage = new Image("images/buildings/farm.png");
			fmProgressImage.add(fmImage.getSubImage(0, 64, 64, 64));
			Vector<Image> fmWorkingImage = new Vector<Image>();
			fmWorkingImage.add(fmImage.getSubImage(0, 0, 64, 64));

			Vector<BaseBuilding> subBuildings = new Vector<BaseBuilding>();
			subBuildings.add(herbary);

			farm = new ResourceBuilding("Farm", "", fmProgressImage,
					fmWorkingImage, fmWorkingImage, subBuildings, 100, 1.5);
			farm.setCost(new Cost(0, 1, 3, 3));
			addBuilding(farm);
		}

		BaseBuilding barracksTower = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image("images/buildings/tower.png"));
			barracksTower = new ResourceBuilding("Watch Tower", "", idleImage,
					idleImage, idleImage, null, 100);
			barracksTower.setCost(new Cost(0, 1, 3, 3));
			addBuilding(barracksTower);
		}

		BaseBuilding barracksWatchTowerWood = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image(
					"images/buildings/watchtower_wooden_full_size.png"));
			barracksWatchTowerWood = new ResourceBuilding("Watch Tower", "",
					idleImage, idleImage, idleImage, null, 100, 0.1);
			barracksWatchTowerWood.setCost(new Cost(0, 1, 3, 3));
			addBuilding(barracksWatchTowerWood);
		}

		BaseBuilding barracksWatchTowerStone = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image(
					"images/buildings/watchtower_lvl2-exp_full_size.png"));
			barracksWatchTowerStone = new ResourceBuilding("Watch Tower", "",
					idleImage, idleImage, idleImage, null, 100, 0.1);
			barracksWatchTowerStone.setCost(new Cost(0, 1, 3, 3));
			addBuilding(barracksWatchTowerStone);
		}

		BaseBuilding barracksStable = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image("images/buildings/stable (1).png"));
			barracksStable = new ResourceBuilding("Stable", "", idleImage,
					idleImage, idleImage, null, 100, 0.4);
			barracksStable.setCost(new Cost(0, 1, 3, 3));
			addBuilding(barracksStable);
		}

		BaseBuilding barracksTraining = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image("images/buildings/archery_range.png"));
			barracksTraining = new ResourceBuilding("Training", "", idleImage,
					idleImage, idleImage, null, 100, 0.4);
			barracksTraining.setCost(new Cost(0, 1, 3, 3));
			addBuilding(barracksTraining);
		}

		BaseBuilding castle = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image("images/buildings/castle.png"));

			Vector<BaseBuilding> subBuildings = new Vector<BaseBuilding>();
			subBuildings.add(barracksWatchTowerWood);
			subBuildings.add(barracksWatchTowerStone);

			castle = new ResourceBuilding("Barracks", "", idleImage, idleImage,
					idleImage, subBuildings, 100, 0.3);
			castle.setCost(new Cost(0, 1, 3, 3));
			addBuilding(castle);
		}

		BaseBuilding barracksAdvanced = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image(
					"images/buildings/barracks/renders/idle/45/000.png"));

			Vector<BaseBuilding> subBuildings = new Vector<BaseBuilding>();
			subBuildings.add(barracksTraining);
			subBuildings.add(barracksStable);
			subBuildings.add(barracksTower);
			subBuildings.add(castle);

			barracksAdvanced = new ResourceBuilding("Barracks", "", idleImage,
					idleImage, idleImage, subBuildings, 100, 0.7);
			barracksAdvanced.setCost(new Cost(0, 1, 3, 3));
			addBuilding(barracksAdvanced);
		}

		BaseBuilding signalFireWooden = null;
		{
			Vector<Image> sfIdleImage = new Vector<Image>();
			sfIdleImage
					.add(new Image(
							"images/buildings/signalfire_wooden/as_signalfire_wooden0/idle/45/0060.png"));
			Vector<Image> sfWorkingImage = new Vector<Image>();
			for (int i = 60; i < 135; i += 5)
				sfWorkingImage.add(new Image(
						"images/buildings/signalfire_wooden/as_signalfire_wooden0/idle/45/0"
								+ getNumber(i) + ".png"));
			signalFireWooden = new ResourceBuilding("Signal Fire", "",
					sfIdleImage, sfIdleImage, sfWorkingImage, null, 100, 1.5);
			signalFireWooden.setCost(new Cost(0, 1, 3, 3));
			addBuilding(signalFireWooden);
		}

		BaseBuilding signalFire = null;
		{
			Vector<Image> sfIdleImage = new Vector<Image>();
			sfIdleImage.add(new Image(
					"images/buildings/signal_fire/renders/idle/45/000.png"));
			Vector<Image> sfWorkingImage = new Vector<Image>();
			for (int i = 0; i < 16; i++)
				sfWorkingImage.add(new Image(
						"images/buildings/signal_fire/renders/work/45/"
								+ getNumber(i) + ".png"));
			signalFire = new ResourceBuilding("Signal Fire", "", sfIdleImage,
					sfIdleImage, sfWorkingImage, null, 100, 1.5);
			signalFire.setCost(new Cost(0, 1, 3, 3));
			addBuilding(signalFire);
		}

		BaseBuilding weaponSmith = null;
		{
			Vector<Image> wsIdleImage = new Vector<Image>();
			wsIdleImage.add(new Image(
					"images/buildings/weaponsmith/renders/idle/45/000.png"));
			Vector<Image> wsWorkingImage = new Vector<Image>();
			for (int i = 0; i < 8; i++)
				wsWorkingImage.add(new Image(
						"images/buildings/weaponsmith/renders/work/45/"
								+ getNumber(i) + ".png"));
			weaponSmith = new ResourceBuilding("Weaponsmith", "", wsIdleImage,
					wsIdleImage, wsWorkingImage, null, 100);
			weaponSmith.setCost(new Cost(0, 1, 3, 3));
			addBuilding(weaponSmith);
		}

		BaseBuilding barracks = null;
		{
			Vector<Image> bkProgressImage = new Vector<Image>();
			Image bkImage = new Image("images/buildings/barracks.png");
			bkProgressImage.add(bkImage.getSubImage(0, 96, 96, 96));
			Vector<Image> bkWorkingImage = new Vector<Image>();
			bkWorkingImage.add(bkImage.getSubImage(0, 0, 96, 96));

			Vector<BaseBuilding> subBuildings = new Vector<BaseBuilding>();
			subBuildings.add(barracksAdvanced);
			subBuildings.add(signalFireWooden);
			subBuildings.add(signalFire);

			barracks = new ResourceBuilding("Barracks", "", bkProgressImage,
					bkWorkingImage, bkWorkingImage, subBuildings, 100);
			barracks.setCost(new Cost(0, 1, 3, 3));
			addBuilding(barracks);
		}

		BaseBuilding fireStation = null;
		{
			Vector<Image> bk2IdleImage = new Vector<Image>();
			bk2IdleImage.add(new Image(
					"images/buildings/firestation/renders/idle/45/000.png"));
			fireStation = new ResourceBuilding("Fire Station", "",
					bk2IdleImage, bk2IdleImage, bk2IdleImage, null, 100, 0.6);
			fireStation.setCost(new Cost(0, 1, 3, 3));
			addBuilding(fireStation);
		}

		BaseBuilding megaMine = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image(
					"images/buildings/UH/as_mine5x5/idle/45/0.png"));
			Vector<Image> workingImage = new Vector<Image>();
			for (int i = 1; i < 24; i++)
				workingImage.add(new Image(
						"images/buildings/UH/as_mine5x5/work/45/"
								+ getNumber2(i) + ".png"));
			megaMine = new ResourceBuilding("Mega Mine", "", idleImage,
					idleImage, workingImage, null, 100);
			megaMine.setCost(new Cost(0, 1, 3, 3));
			addBuilding(megaMine);
		}

		BaseBuilding goldMine = null;
		{
			Vector<Image> gmProgressImage = new Vector<Image>();
			Image gmImage = new Image("images/buildings/gold_mine.png");
			gmProgressImage.add(gmImage.getSubImage(0, 192, 96, 96));
			Vector<Image> gmIdleImage = new Vector<Image>();
			gmIdleImage.add(gmImage.getSubImage(0, 0, 96, 96));
			Vector<Image> gmWorkingImage = new Vector<Image>();
			gmWorkingImage.add(gmImage.getSubImage(0, 96, 96, 96));

			Vector<BaseBuilding> subBuildings = new Vector<BaseBuilding>();
			subBuildings.add(megaMine);

			goldMine = new ResourceBuilding("Gold Mine", "", gmProgressImage,
					gmIdleImage, gmWorkingImage, subBuildings, 100);
			goldMine.setCost(new Cost(0, 1, 3, 3));
			addBuilding(goldMine);
		}

		BaseBuilding clayPit = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image(
					"images/buildings/UH/as_clay_pit0/idle/45/0.png"));

			Vector<BaseBuilding> subBuildings = new Vector<BaseBuilding>();
			subBuildings.add(goldMine);

			clayPit = new ResourceBuilding("Clay Pit", "", idleImage,
					idleImage, idleImage, subBuildings, 100, 0.6)  { 
				@Override
				public void onBuildingTick() {
					GameSession.getInstance().getInventory().addItem(ItemType.STONE);
				}
				
				@Override
				public double getDuration() {
					return 24*60;
				}
			};
			clayPit.setCost(new Cost(0, 1, 3, 3));
			addBuilding(clayPit);
		}

		BaseBuilding lumberMill = null;
		{
			Vector<Image> lmProgressImage = new Vector<Image>();
			Image lmImage = new Image("images/buildings/human_lumber_mill.png");
			lmProgressImage.add(lmImage.getSubImage(0, 96, 96, 96));
			Vector<Image> lmWorkingImage = new Vector<Image>();
			lmWorkingImage.add(lmImage.getSubImage(0, 0, 96, 96));
			lumberMill = new ResourceBuilding("Lumber Mill", "",
					lmProgressImage, lmWorkingImage, lmWorkingImage, null, 100)  { 
				@Override
				public void onBuildingTick() {
					GameSession.getInstance().getInventory().addItem(ItemType.WOOD);
				}
				
				@Override
				public double getDuration() {
					return 6*60;
				}
			};
			lumberMill.setCost(new Cost(0, 1, 3, 3));
			addBuilding(lumberMill);
		}

		BaseBuilding lumberJack = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage
					.add(new Image(
							"images/buildings/tent/lumberjack/as_lumberjack0/idle/45/0.png"));

			Vector<BaseBuilding> subBuildings = new Vector<BaseBuilding>();
			subBuildings.add(lumberMill);

			lumberJack = new ResourceBuilding("Lumber Jack", "", idleImage,
					idleImage, idleImage, subBuildings, 100, 0.6)  { 
				@Override
				public void onBuildingTick() {
					GameSession.getInstance().getInventory().addItem(ItemType.WOOD);
				}
				
				@Override
				public double getDuration() {
					return 12*60;
				}
			};
			lumberJack.setCost(new Cost(0, 1, 3, 3));
			addBuilding(lumberJack);
		}

		BaseBuilding gargoyl = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image("images/buildings/gargyle.png"));
			gargoyl = new ResourceBuilding("Gargoyl", "", idleImage, idleImage,
					idleImage, null, 100);
			gargoyl.setCost(new Cost(0, 0, 5, 0));
			addBuilding(gargoyl);
		}

		BaseBuilding demon = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image("images/buildings/demon01.png"));
			demon = new ResourceBuilding("Demon", "", idleImage, idleImage,
					idleImage, null, 100);
			demon.setCost(new Cost(0, 0, 5, 0));
			addBuilding(demon);
		}

		BaseBuilding church = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image(
					"images/buildings/church/renders/idle/45/000.png"));

			Vector<BaseBuilding> subBuildings = new Vector<BaseBuilding>();
			subBuildings.add(gargoyl);
			subBuildings.add(demon);

			church = new ResourceBuilding("Advanced Religion", "", idleImage,
					idleImage, idleImage, subBuildings, 100, 0.6);
			church.setCost(new Cost(0, 2, 2, 2));
			addBuilding(church);
		}

		BaseBuilding blacksmith = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image("images/buildings/blacksmith.png"));

			Vector<BaseBuilding> subBuildings = new Vector<BaseBuilding>();
			subBuildings.add(weaponSmith);

			blacksmith = new ResourceBuilding("Blacksmith", "", idleImage,
					idleImage, idleImage, subBuildings, 100, 0.43);
			blacksmith.setCost(new Cost(0, 2, 2, 2));
			addBuilding(blacksmith);
		}

		BaseBuilding religionTent = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage
					.add(new Image(
							"images/buildings/tent/pavilion/as_pavilion0/idle/45/0.png"));

			Vector<BaseBuilding> subBuildings = new Vector<BaseBuilding>();
			subBuildings.add(church);

			religionTent = new ResourceBuilding("Religion", "", idleImage,
					idleImage, idleImage, subBuildings, 100, 0.6);
			religionTent.setCost(new Cost(0, 2, 0, 0));
			addBuilding(religionTent);
		}

		BaseBuilding hunterTent = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image(
					"images/buildings/tent/hunter/as_hunter0/idle/45/0.png"));

			Vector<BaseBuilding> subBuildings = new Vector<BaseBuilding>();
			subBuildings.add(farm);

			hunterTent = new ResourceBuilding("Hunter Abode", "", idleImage,
					idleImage, idleImage, subBuildings, 100, 0.6)  { 
				@Override
				public void onBuildingTick() {
					GameSession.getInstance().getInventory().addItem(ItemType.FOOD);
				}
				
				@Override
				public double getDuration() {
					return 12*60;
				}
			};
			hunterTent.setCost(new Cost(0, 2, 0, 0));
			addBuilding(hunterTent);
		}

		BaseBuilding well = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image("images/buildings/Well/Well_00000.png"));
			well = new ResourceBuilding("Well", "", idleImage, idleImage,
					idleImage, null, 100, 0.1);
			well.setCost(new Cost(0, 2, 2, 0));
			addBuilding(well);
		}

		BaseBuilding townHall = null;
		{
			Vector<Image> thProgressImage = new Vector<Image>();
			Image thImage = new Image("images/buildings/town_hall.png");
			thProgressImage.add(thImage.getSubImage(0, 128, 128, 128));
			Vector<Image> thWorkingImage = new Vector<Image>();
			thWorkingImage.add(thImage.getSubImage(0, 0, 128, 128));

			Vector<BaseBuilding> subBuildings = new Vector<BaseBuilding>();
			subBuildings.add(hunterTent);
			subBuildings.add(fireStation);
			subBuildings.add(barracks);
			subBuildings.add(lumberJack);
			subBuildings.add(clayPit);
			subBuildings.add(religionTent);
			subBuildings.add(well);
			subBuildings.add(blacksmith);

			townHall = new ResourceBuilding("Town Hall", "", thProgressImage,
					thWorkingImage, thWorkingImage, subBuildings, 100)   { 
				@Override
				public void onBuildingTick() {
					Inventory inv = GameSession.getInstance().getInventory();
					if(inv.getItemCount(ItemType.FOOD) > 2) {
						// TODO: Check for at least 2 villages
						inv.removeItem(ItemType.FOOD, 2);
						// TODO: Add a villager
					}
				}
				
				@Override
				public double getDuration() {
					return 24*60;
				}
			};
			townHall.setCost(new Cost(0, 2, 2, 2));
			addBuilding(townHall);

			rootBuilding = townHall;
		}

	}

	public String getNumber2(int val) {
		if (val < 10)
			return "0" + val;

		return "" + val;
	}

	public String getNumber(int val) {
		if (val < 10)
			return "00" + val;

		if (val < 100)
			return "0" + val;

		return "" + val;
	}

	public Vector<BaseBuilding> currentBuildingOptions(Building currentBuilding) {
		if (currentBuilding == null) {
			Vector<BaseBuilding> rootBuildings = new Vector<BaseBuilding>();
			rootBuildings.add(rootBuilding);
			return rootBuildings;
		}

		return currentBuilding.base.subBuildings;
	}

	public ArrayList<Building> getNonOfferedBuildingsOfType(String type) {
		ArrayList<Building> validBuildings = new ArrayList<Building>();
		for (Building building : buildingsInPlay) {
			if (building.base.getName().equals(type) && !building.isOffered()) {
				validBuildings.add(building);
			}
		}
		return validBuildings;
	}

	public ArrayList<Building> getBuildingsInPlay() {
		return buildingsInPlay;
	}

	public boolean canBuyBuilding(BaseBuilding b) {
		Cost c = b.getCost();
		Inventory i = GameSession.getInstance().getInventory();

		if (i.getItemCount(ItemType.FOOD) < c.getFood()) {
			return false;
		}

		if (i.getItemCount(ItemType.METAL) < c.getMetal()) {
			return false;
		}

		if (i.getItemCount(ItemType.STONE) < c.getStone()) {
			return false;
		}

		if (i.getItemCount(ItemType.WOOD) < c.getWood()) {
			return false;
		}

		return true;
	}

	public void buyBuilding(BaseBuilding b) {
		Cost c = b.getCost();
		Inventory i = GameSession.getInstance().getInventory();
		i.removeItem(ItemType.FOOD, c.getFood());
		i.removeItem(ItemType.METAL, c.getMetal());
		i.removeItem(ItemType.STONE, c.getStone());
		i.removeItem(ItemType.WOOD, c.getWood());
	}
}
