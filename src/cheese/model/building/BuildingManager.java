package cheese.model.building;

import java.util.ArrayList;
import java.util.Vector;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import cheese.model.Cost;
import deserted.model.GameSession;
import deserted.model.Inventory;
import deserted.model.item.ItemType;
import deserted.player.PlayerUI;

public class BuildingManager {
	ArrayList<Building> buildingsInPlay;
	ArrayList<BaseBuilding> availableBuildings;

	//Adds a building
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
					"images/buildings/herbary/renders/idel_empty/45/000.png"));
			Vector<Image> workingImage = new Vector<Image>();
			for (int i = 0; i < 140; i++)
				workingImage.add(new Image(
						"images/buildings/herbary/renders/idel_empty/45/"
								+ getNumber(i) + ".png"));
			
			FootPrint fp = new FootPrint(0,1,1,0);
			
			herbary = new ResourceBuilding("Herbary", "", idleImage, idleImage,
					workingImage, null, 100, 120,fp) {
				@Override
				public void onBuildingTick(Building building) {
					if (Math.random() < GameSession.getInstance()
							.getProbabilityFood()*2) {
						GameSession.getInstance().getInventory()
								.addItem(ItemType.FOOD);
					}
				}

				@Override
				public double getDuration() {
					return 8 * 60;
				}
			};
			herbary.setCost(new Cost(0, 1, 3, 3));
			addBuilding(herbary);
		}

		BaseBuilding farm = null;
		{
			Vector<Image> fmProgressImage = new Vector<Image>();
			Image fmImage = (new Image("images/buildings/rem_000.png")).getScaledCopy(0.6f);
			//fmProgressImage.add(fmImage.getSubImage(0, 64, 64, 64));
			Vector<Image> fmWorkingImage = new Vector<Image>();
			fmWorkingImage.add(fmImage);//fmImage.getSubImage(0, 0, 64, 64));

			Vector<BaseBuilding> subBuildings = new Vector<BaseBuilding>();
			subBuildings.add(herbary);

			FootPrint fp = new FootPrint(1,0,0,1);
			
			farm = new ResourceBuilding("Farm", "", fmWorkingImage,
					fmWorkingImage, fmWorkingImage, subBuildings, 100, 120, fp);
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
					idleImage, idleImage, idleImage, null, 100, 60);
			barracksWatchTowerWood.setCost(new Cost(0, 1, 3, 3));
			addBuilding(barracksWatchTowerWood);
			barracksWatchTowerWood.yOffset =-22;
			barracksWatchTowerWood.fowArea = 5;
		}

		BaseBuilding barracksWatchTowerStone = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image(
					"images/buildings/watchtower_lvl2-exp_full_size.png"));
			barracksWatchTowerStone = new ResourceBuilding("Watch Tower", "",
					idleImage, idleImage, idleImage, null, 100, 60);
			barracksWatchTowerStone.setCost(new Cost(0, 1, 3, 3));
			addBuilding(barracksWatchTowerStone);
			barracksWatchTowerStone.yOffset =-22;
			barracksWatchTowerStone.fowArea = 6;
		}

		BaseBuilding barracksStable = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image("images/buildings/stable (1).png"));
			
			FootPrint fp = new FootPrint(1,1,1,1);
			
			barracksStable = new ResourceBuilding("Stable", "", idleImage,
					idleImage, idleImage, null, 100, 160,fp);
			barracksStable.setCost(new Cost(0, 1, 3, 3));
			addBuilding(barracksStable);
		}

		BaseBuilding barracksTraining = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image("images/buildings/archery_range.png"));
		
			FootPrint fp = new FootPrint(1,1,1,1);
			
			barracksTraining = new ResourceBuilding("Training", "", idleImage,
					idleImage, idleImage, null, 100, 160,fp);
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

			FootPrint fp = new FootPrint(1,2,2,1);
			
			castle = new ResourceBuilding("Mega Barracks", "", idleImage, idleImage,
					idleImage, subBuildings, 100, 200,fp);
			castle.setCost(new Cost(0, 1, 3, 3));
			addBuilding(castle);
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
			signalFireWooden = new ResourceBuilding("Signal Fire Cheap", "",
					sfIdleImage, sfIdleImage, sfWorkingImage, null, 100, 60);
			signalFireWooden.setCost(new Cost(0, 1, 3, 3));
			
			signalFireWooden.yOffset = -30;
			signalFireWooden.fowArea = 3;
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
			signalFire = new ResourceBuilding("Signal Fire Pricey", "", sfIdleImage,
					sfIdleImage, sfWorkingImage, null, 100, 60);
			signalFire.setCost(new Cost(0, 1, 3, 3));
			
			signalFire.yOffset = -30;
			signalFire.fowArea = 4;
			addBuilding(signalFire);
		}
		
		BaseBuilding outpost = null;
		{
			Vector<Image> sfIdleImage = new Vector<Image>();
			sfIdleImage.add(new Image(
					"images/buildings/Outpost/old_outpost_iso_00000.png"));
			
			outpost = new ResourceBuilding("Outpost", "", sfIdleImage,
					sfIdleImage, sfIdleImage, null, 100, 60);
			outpost.setCost(new Cost(0, 1, 3, 3));
			addBuilding(outpost);
			
			outpost.yOffset =-5;
			outpost.fowArea = 2;
		}
		
		
		BaseBuilding barracksAdvanced = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image(
					"images/buildings/barracks/renders/idle/45/000.png"));

			Vector<BaseBuilding> subBuildings = new Vector<BaseBuilding>();
			subBuildings.add(barracksTraining);
			subBuildings.add(barracksStable);
			subBuildings.add(castle);
			subBuildings.add(signalFireWooden);
			subBuildings.add(signalFire);

			FootPrint fp = new FootPrint(1,1,1,1);
			
			barracksAdvanced = new ResourceBuilding("Barracks", "", idleImage,
					idleImage, idleImage, subBuildings, 100, 120,fp);
			barracksAdvanced.setCost(new Cost(0, 1, 3, 3));
			addBuilding(barracksAdvanced);
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
			
			FootPrint fp = new FootPrint(0,1,1,0);
			
			weaponSmith = new ResourceBuilding("Weaponsmith", "", wsIdleImage,
					wsIdleImage, wsWorkingImage, null, 100,120,fp);
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

			FootPrint fp = new FootPrint(1,1,1,1);
			
			barracks = new ResourceBuilding("Barracks", "", bkProgressImage,
					bkWorkingImage, bkWorkingImage, subBuildings, 120,fp);
			barracks.setCost(new Cost(0, 1, 3, 3));
			addBuilding(barracks);
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
			
			FootPrint fp = new FootPrint(1,4,4,0);
			
			megaMine = new ResourceBuilding("Mega Mine", "", idleImage,
					idleImage, workingImage, null, 100,200,fp) {
				@Override
				public void onBuildingTick(Building building) {
					GameSession.getInstance().getInventory()
							.addItem(ItemType.STONE);
					GameSession.getInstance().getInventory()
							.addItem(ItemType.METAL);
				}

				@Override
				public double getDuration() {
					return 8 * 60;
				}
			};
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

			FootPrint fp = new FootPrint(1,1,1,1);
			
			goldMine = new ResourceBuilding("Gold Mine", "", gmProgressImage,
					gmIdleImage, gmWorkingImage, subBuildings, 100,80, fp) {
				@Override
				public void onBuildingTick(Building building) {
					GameSession.getInstance().getInventory()
							.addItem(ItemType.METAL);
				}

				@Override
				public double getDuration() {
					return 8 * 60;
				}
			};
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

			FootPrint fp = new FootPrint(0,1,1,0);
			
			clayPit = new ResourceBuilding("Clay Pit", "", idleImage,
					idleImage, idleImage, subBuildings, 100, 120, fp) {
				@Override
				public void onBuildingTick(Building building) {
					GameSession.getInstance().getInventory()
							.addItem(ItemType.STONE);
				}

				@Override
				public double getDuration() {
					return 8 * 60;
				}
			};
			clayPit.setCost(new Cost(0, 2, 0, 2));
			addBuilding(clayPit);
		}

		BaseBuilding lumberMill = null;
		{
			Vector<Image> lmProgressImage = new Vector<Image>();
			Image lmImage = new Image("images/buildings/human_lumber_mill.png");
			lmProgressImage.add(lmImage.getSubImage(0, 96, 96, 96));
			Vector<Image> lmWorkingImage = new Vector<Image>();
			lmWorkingImage.add(lmImage.getSubImage(0, 0, 96, 96));
			
			FootPrint fp = new FootPrint(1,0,0,1);
			
			lumberMill = new ResourceBuilding("Lumber Mill", "",
					lmProgressImage, lmWorkingImage, lmWorkingImage, null, 100,50, fp) {
				@Override
				public void onBuildingTick(Building building) {
					GameSession.getInstance().getInventory()
							.addItem(ItemType.WOOD);
				}

				@Override
				public double getDuration() {
					return 6 * 60;
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
					idleImage, idleImage, subBuildings, 100, 60) {
				@Override
				public void onBuildingTick(Building building) {
					GameSession.getInstance().getInventory()
							.addItem(ItemType.WOOD);
				}

				@Override
				public double getDuration() {
					return 8 * 60;
				}
			};
			lumberJack.setCost(new Cost(0, 2, 0, 0));
			addBuilding(lumberJack);
		}

		BaseBuilding gargoyl = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image("images/buildings/gargyle.png"));
			gargoyl = new ResourceBuilding("Gargoyl", "", idleImage, idleImage,
					idleImage, null, 100,120);
			gargoyl.setCost(new Cost(0, 0, 5, 0));
			addBuilding(gargoyl);
		}

		BaseBuilding demon = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image("images/buildings/demon01.png"));
			demon = new ResourceBuilding("Demon", "", idleImage, idleImage,
					idleImage, null, 100,120);
			demon.setCost(new Cost(0, 0, 5, 0));
			addBuilding(demon);
		}

		BaseBuilding tombStone = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image(
					"images/buildings/cursed_grave.png").getSubImage(0, 256, 128, 128));

			tombStone = new ResourceBuilding("Retreat for the dead", "", idleImage,
					idleImage, idleImage, null, 100, 60);
			tombStone.setCost(new Cost(0, 2, 2, 2));
			addBuilding(tombStone);
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
					idleImage, idleImage, subBuildings, 100, 60);
			church.setCost(new Cost(0, 2, 2, 2));
			addBuilding(church);
		}

	
		
		BaseBuilding blacksmith = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image("images/buildings/blacksmith.png"));

			Vector<BaseBuilding> subBuildings = new Vector<BaseBuilding>();
			subBuildings.add(weaponSmith);

			FootPrint fp = new FootPrint(1,1,1,1);
			
			blacksmith = new ResourceBuilding("Blacksmith", "", idleImage,
					idleImage, idleImage, subBuildings, 100, 150,fp) {
				@Override
				public void onBuildingTick(Building building) {
					GameSession.getInstance().getInventory()
							.addItem(ItemType.METAL);
				}

				@Override
				public double getDuration() {
					return 8 * 60;
				}
			};
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
			subBuildings.add(tombStone);

			religionTent = new ResourceBuilding("Religion", "", idleImage,
					idleImage, idleImage, subBuildings, 100, 60);
			religionTent.setCost(new Cost(0, 2, 0, 0));
			addBuilding(religionTent);
		}

		//
		
		BaseBuilding fisherTent = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image(
					"images/buildings/fisherman/as_fisherman0/idle/45/0.png"));

			fisherTent = new ResourceBuilding("Fisher Hovel", "", idleImage,
					idleImage, idleImage, null, 100, 60) {
				@Override
				public void onBuildingTick(Building building) {
					if (Math.random() < GameSession.getInstance()
							.getProbabilityFood()*1.5) {
						GameSession.getInstance().getInventory()
								.addItem(ItemType.FOOD);
					}
					else {
						System.out.println("No food for you");
					}
				}

				@Override
				public double getDuration() {
					return 8 * 60;
				}
			};
			fisherTent.setCost(new Cost(0, 2, 0, 0));
			addBuilding(fisherTent);
		}

		BaseBuilding smallFarmTent = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image(
					"images/buildings/smallFarm.png"));

			Vector<BaseBuilding> subBuildings = new Vector<BaseBuilding>();
			subBuildings.add(farm);

			FootPrint fp = new FootPrint(0,1,1,0);
			
			smallFarmTent = new ResourceBuilding("Small Farm", "", idleImage,
					idleImage, idleImage, subBuildings, 100, 120,fp) {
				@Override
				public void onBuildingTick(Building building) {
					if (Math.random() < GameSession.getInstance()
							.getProbabilityFood()) {
						GameSession.getInstance().getInventory()
								.addItem(ItemType.FOOD);
					}
					else {
						System.out.println("No food for you");
					}
				}

				@Override
				public double getDuration() {
					return 8 * 60;
				}
			};
			smallFarmTent.setCost(new Cost(0, 2, 0, 0));
			addBuilding(smallFarmTent);
		}
		
		BaseBuilding hunterTent = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image(
					"images/buildings/tent/hunter/as_hunter0/idle/45/0.png"));

			Vector<BaseBuilding> subBuildings = new Vector<BaseBuilding>();
			subBuildings.add(smallFarmTent);
			subBuildings.add(fisherTent);

			hunterTent = new ResourceBuilding("Hunter Abode", "", idleImage,
					idleImage, idleImage, subBuildings, 100, 60) {
				@Override
				public void onBuildingTick(Building building) {
					if (Math.random() < GameSession.getInstance()
							.getProbabilityFood()) {
						GameSession.getInstance().getInventory()
								.addItem(ItemType.FOOD);
					}
					else {
						System.out.println("No food for you");
					}
				}

				@Override
				public double getDuration() {
					return 8 * 60;
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
					idleImage, null, 100, 60);
			well.setCost(new Cost(0, 2, 2, 0));
			addBuilding(well);
		}


		BaseBuilding residentPosh = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image("images/buildings/house1.png"));
					idleImage.add(new Image("images/buildings/house1b.png"));
							idleImage.add(new Image("images/buildings/house1c.png"));
			
			FootPrint fp = new FootPrint(1,1,1,1);
			
			residentPosh = new ResourceBuilding("Overload Houses", "", idleImage,
					idleImage, idleImage, null, 0, 120,fp) {
				@Override
				public void onBuildingTick(Building building) {
					Inventory inv = GameSession.getInstance().getInventory();
					if (inv.getItemCount(ItemType.FOOD) > 1) {
						GameSession gs = GameSession.getInstance();
						if (gs.getPlayerManager().getAgents().size() >= 2) {
							if (Math.random() < GameSession.getInstance()
									.getProbabilitySettler()) {
								inv.removeItem(ItemType.FOOD, 1);
								try {
									PlayerUI player = gs.getPlayerManager()
											.addPlayer(gs.getPlay());
									player.location = building.location;
								} catch (SlickException se) {
								}
							}
						}
					}
				}

				@Override
				public double getDuration() {
					return 24 * 60;
				}
			};
			residentPosh.setCost(new Cost(0, 2, 2, 2));
			addBuilding(residentPosh);
		}
		
		
		BaseBuilding residentMed = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image("images/buildings/Medieval_Building_06.png"));
			
			FootPrint fp = new FootPrint(1,1,1,1);
			
			Vector<BaseBuilding> subBuildings = new Vector<BaseBuilding>();
			subBuildings.add(residentPosh);
			
			
			residentMed = new ResourceBuilding("Better Settlement", "", idleImage,
					idleImage, idleImage, subBuildings, 100, 120,fp) {
				@Override
				public void onBuildingTick(Building building) {
					Inventory inv = GameSession.getInstance().getInventory();
					if (inv.getItemCount(ItemType.FOOD) > 2) {
						GameSession gs = GameSession.getInstance();
						if (gs.getPlayerManager().getAgents().size() >= 2) {
							if (Math.random() < GameSession.getInstance()
									.getProbabilitySettler()) {
								inv.removeItem(ItemType.FOOD, 2);
								try {
									PlayerUI player = gs.getPlayerManager()
											.addPlayer(gs.getPlay());
									player.location = building.location;
								} catch (SlickException se) {
								}
							}
						}
					}
				}

				@Override
				public double getDuration() {
					return 24 * 60;
				}
			};
			residentMed.setCost(new Cost(0, 2, 2, 2));
			addBuilding(residentMed);
		}
		
		BaseBuilding residentTent = null;
		{
			Vector<Image> idleImage = new Vector<Image>();
			idleImage.add(new Image(
					"images/buildings/residential/as_tent0/idle/135/0.png"));
			
			Vector<BaseBuilding> subBuildings = new Vector<BaseBuilding>();
			subBuildings.add(residentMed);
			
			residentTent = new ResourceBuilding("Settlement", "", idleImage,
					idleImage, idleImage, subBuildings, 100, 60) {
				@Override
				public void onBuildingTick(Building building) {
					Inventory inv = GameSession.getInstance().getInventory();
					if (inv.getItemCount(ItemType.FOOD) > 4) {
						GameSession gs = GameSession.getInstance();
						if (gs.getPlayerManager().getAgents().size() >= 2) {
							if (Math.random() < GameSession.getInstance()
									.getProbabilitySettler()) {
								inv.removeItem(ItemType.FOOD, 4);
								try {
									PlayerUI player = gs.getPlayerManager()
											.addPlayer(gs.getPlay());
									player.location = building.location;
								} catch (SlickException se) {
								}
							}
						}
					}
				}

				@Override
				public double getDuration() {
					return 24 * 60;
				}
			};
			residentTent.setCost(new Cost(0, 2, 2, 2));
			addBuilding(residentTent);
		}

		BaseBuilding townHall = null;
		{
			//Vector<Image> thProgressImage = new Vector<Image>();
			//Image thImage = new Image("images/buildings/town_hall.png");
				//thProgressImage.add(thImage.getSubImage(0, 128, 128, 128));
			//Vector<Image> thWorkingImage = new Vector<Image>();
			//thWorkingImage.add(thImage.getSubImage(0, 0, 128, 128));
			
			Vector<Image> images = new Vector<Image>();
			Image image = new Image("images/buildings/firestation/renders/idle/45/000.png");
			images.add(image);
			
			Vector<BaseBuilding> subBuildings = new Vector<BaseBuilding>();
			subBuildings.add(hunterTent);

			subBuildings.add(barracksAdvanced);
			subBuildings.add(lumberJack);
			subBuildings.add(clayPit);
			subBuildings.add(religionTent);
			subBuildings.add(well);
			subBuildings.add(blacksmith);
			subBuildings.add(residentTent);
			subBuildings.add(outpost);
			

			FootPrint fp = new FootPrint(0,1,1,0);
			
			townHall = new ResourceBuilding("Town Hall", "", images,
					images, images, subBuildings, 100,120,fp);
			townHall.setCost(new Cost(0, 2, 2, 2));
			addBuilding(townHall);

			rootBuilding = townHall;
		}
		
		//Descriptions
		herbary.setDescription("Vikings don't like plants but will eat them when needed.");
		farm.setDescription("Its almost harvesting season!");

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
