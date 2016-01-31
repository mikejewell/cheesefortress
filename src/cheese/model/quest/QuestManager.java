package cheese.model.quest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import cheese.model.Cost;
import cheese.model.PlayerManager;
import cheese.model.building.Building;
import cheese.model.building.BuildingManager;
import cheese.model.god.GodType;
import deserted.model.Agent;
import deserted.model.AgentState;
import deserted.model.GameSession;
import deserted.model.item.ItemType;

public class QuestManager {

	private List<Quest> questList;
	private HashMap<GodType, Quest> questSlots;
	private GodType[] order = { GodType.THOR, GodType.FREYA, GodType.HEL,
			GodType.LOKI, GodType.TRIBE };
	private ArrayList<Quest> completedQuests;

	public QuestManager() {
		this.questSlots = new HashMap<GodType, Quest>();
		// TODO make all the quests
		this.questList = new ArrayList<Quest>();
		this.completedQuests = new ArrayList<Quest>();

		QuestBuilding farm = new QuestBuilding(
				"Big Farma",
				"We need food to keep our villagers\nalive - please build a farm!",
				5, GodType.TRIBE, "Farm");
		QuestBuilding hall = new QuestBuilding("Hall Effect",
				"If we want to build anything, we'll\nneed a Town Hall.", 5,
				GodType.TRIBE, "Town Hall");
		QuestBuilding hunter = new QuestBuilding(
				"Hunter Gatherer",
				"There's a load of wildlife out there\nthat we could be eating!",
				5, GodType.TRIBE, "Hunter Abode");
		QuestBuilding stable = new QuestBuilding(
				"Unstable Situation",
				"If we had some horses, we could get\nresources from other villages.",
				505, GodType.TRIBE, "Stable");
		QuestBuilding lumberjack = new QuestBuilding("Got Wood?",
				"A lumberjack would be fantastic for\nour wood production.",
				5, GodType.TRIBE, "Lumber Jack");
		QuestBuilding blacksmith = new QuestBuilding("Heavy Metal",
				"With a blacksmith, we could turn\nproduction up to 11!", 5,
				GodType.TRIBE, "Blacksmith");
		QuestBuilding settlement = new QuestBuilding("Settlers Too",
				"We could always do with more volunteers\nfor sacrifices...",
				5, GodType.TRIBE, "Settlement");
		QuestBuilding barracks = new QuestBuilding("Barracks",
				"Build a barracks", 5, GodType.TRIBE, "Barracks");
		
		questList.add(hall);

		questList.add(hunter);
		hunter.addRequirement(hall);

		questList.add(barracks);
		barracks.addRequirement(hall);

		questList.add(stable);
		stable.addRequirement(barracks);

		questList.add(farm);
		farm.addRequirement(hunter);

		questList.add(lumberjack);
		lumberjack.addRequirement(hall);

		questList.add(blacksmith);
		blacksmith.addRequirement(hall);

		questList.add(settlement);
		settlement.addRequirement(hall);


		QuestBuilding statue = new QuestBuilding("Make Me A Statue",
				"Make me a statue to show your devotion.", 25, GodType.NEUTRAL, "")  {
			@Override
			public boolean canComplete() {
				BuildingManager buildingManager = GameSession.getInstance().getBuildingManager();
				ArrayList<Building> s1 = buildingManager.getNonOfferedBuildingsOfType("Gargoyl");
				ArrayList<Building> s2 = buildingManager.getNonOfferedBuildingsOfType("Demon");
				return s1.size() > 0 || s2.size() > 0;
			}
			
			@Override
			public void onComplete() {

				BuildingManager buildingManager = GameSession.getInstance().getBuildingManager();
				ArrayList<Building> s1 = buildingManager.getNonOfferedBuildingsOfType("Gargoyl");
				ArrayList<Building> s2 = buildingManager.getNonOfferedBuildingsOfType("Demon");
				
				if(s1.size() > 0) {
					s1.get(0).setOffered(true);
				}
				else if(s2.size() > 0) {
					s2.get(0).setOffered(true);
				}
			}
			
			@Override
			public double getHoursToFinish() {
				return 48*5;
			}
		};
		
		Quest armyQuest = new QuestNumbered("Building Forces", 5, 2, 40,
				GodType.NEUTRAL) {
			@Override
			public void onComplete() {
				GameSession.getInstance().getInventory()
						.removeItem(ItemType.ARMY, this.getNumber());
				super.onComplete();
			}

			@Override
			public boolean canComplete() {
				return GameSession.getInstance().getInventory()
						.getItemCount(ItemType.ARMY) >= this.getNumber();
			}

			@Override
			public String getQuestDescription() {
				return "I must attack my foes - bring me " + this.getNumber()
						+ " men!";
			}
			
			@Override
			public double getHoursToFinish() {
				return 48*(getNumber()/5);
			}
		};

		Quest hoardeQuest = new QuestNumbered("Dragon's Hoarde", 5, 2, 40,
				GodType.NEUTRAL) {
			@Override
			public void onComplete() {
				GameSession.getInstance().getInventory()
						.removeItem(ItemType.FOOD, this.getNumber());
				super.onComplete();
			}

			@Override
			public boolean canComplete() {
				return GameSession.getInstance().getInventory()
						.getItemCount(ItemType.FOOD) >= this.getNumber();
			}

			@Override
			public String getQuestDescription() {
				return "My precious... Give me " + getNumber() + " metal!";
			}
			
			@Override
			public double getHoursToFinish() {
				return 48*(getNumber()/5);
			}
		};

		Quest feedQuest = new QuestNumbered("Feast In My Honour", 5, 2, 40,
				GodType.NEUTRAL) {
			@Override
			public void onComplete() {
				GameSession.getInstance().getInventory()
						.removeItem(ItemType.FOOD, this.getNumber());
				super.onComplete();
			}

			@Override
			public boolean canComplete() {
				return GameSession.getInstance().getInventory()
						.getItemCount(ItemType.FOOD) >= this.getNumber();
			}

			@Override
			public String getQuestDescription() {
				return "I'm very hungry - give me " + getNumber() + " food!";
			}
			
			@Override
			public double getHoursToFinish() {
				return 48*(getNumber()/5);
			}
		};

		Quest sacQuest = new QuestNumbered("Sacrifice!", 5, 2, 40,
				GodType.NEUTRAL) {
			@Override
			public void onComplete() {
				int count = 0;
				int i = 0;
				PlayerManager pm = GameSession.getInstance().getPlayerManager();
				while (count < getNumber()) {
					Agent agent = pm.getAgents().get(i);
					if (agent.getState() != AgentState.DEAD) {
						agent.setState(AgentState.DEAD);
						count++;
					}
					i++;
				}
				super.onComplete();
			}

			@Override
			public boolean canComplete() {
				int population = GameSession.getInstance().getPlayerManager()
						.getAgents().size();
				if (population >= getNumber()) {
					return true;
				}
				return false;
			}

			@Override
			public String getQuestDescription() {
				return "I demand a sacrifice of " + getNumber() + " villagers!";
			}
			
			@Override
			public double getHoursToFinish() {
				return 48*(getNumber()/5);
			}
		};

		armyQuest.addRequirement(barracks);

		sacQuest.addRequirement(hall);
		feedQuest.addRequirement(hunter);
		hoardeQuest.addRequirement(hall);
		armyQuest.addRequirement(hall);
		statue.addRequirement(hall);
		
		questList.add(sacQuest);
		questList.add(feedQuest);
		questList.add(hoardeQuest);
		questList.add(armyQuest);
		questList.add(statue);
		
	};

	public List<Quest> getQuestList() {
		return questList;
	}

	public Collection<Quest> getCurrentQuests() {
		return questSlots.values();
	}

	public void flush() {
		ArrayList<GodType> toRemove = new ArrayList<GodType>();

		for (GodType god : questSlots.keySet()) {
			Quest quest = questSlots.get(god);
			if (quest.isCompleted()) {
				toRemove.add(god);
				if (!quest.isRepeatable()) {
					questList.remove(quest);
				} else {
					quest.setHours(0);
					quest.setCompleted(false);
					questList.add(quest);
				}
			}
		}

		for (GodType remove : toRemove) {
			questSlots.remove(remove);
		}
	}

	public void assignQuest() {

		GodType god = pickGod();
		if (god == null) {
			return;
		}
		Quest quest = pickQuest(god);
		if (quest != null) {
			quest.setValidFrom(GameSession.getInstance().getTimeSurvived());
			quest.setActualGod(god);
			quest.setCardX(Math.random());
			quest.setCardY(Math.random());
			questSlots.put(god, quest);
			questList.remove(quest);
			quest.startTiming();
		}
	}

	public Quest pickQuest(GodType god) {
		Random r = new Random();
		ArrayList<Quest> options = questsForGod(god);

		if (options.size() == 0) {
			return null;
		}

		// Filter ones we can actually do.
		ArrayList<Quest> filtered = new ArrayList<Quest>();
		for (Quest quest : options) {
			boolean canAdd = true;
			for (Quest required : quest.getRequirements()) {
				if (!this.completedQuests.contains(required)) {
					canAdd = false;
				}
			}
			if (canAdd) {
				filtered.add(quest);
			}
		}

		if (filtered.size() == 0) {
			return null;
		}
		int index = r.nextInt(filtered.size());
		return filtered.get(index);
	}

	public ArrayList<Quest> questsForGod(GodType god) {
		ArrayList<Quest> options = new ArrayList<Quest>();
		for (Quest quest : questList) {

			if (god == GodType.TRIBE) {
				if (quest.getGod() == GodType.TRIBE) {
					options.add(quest);
				}
			} else {
				if (quest.getGod() == GodType.NEUTRAL) {
					options.add(quest);
				} else if (quest.getGod() == god) {
					options.add(quest);
				}
			}
		}
		return options;
	}

	public GodType pickGod() {
		Random r = new Random();

		ArrayList<GodType> options = new ArrayList<GodType>();
		for (GodType god : order) {

			if (!hasQuest(god)) {
				if (questsForGod(god).size() > 0) {
					options.add(god);
				}
			}
		}

		if (options.size() == 0) {
			return null;
		}

		int index = r.nextInt(options.size());
		return options.get(index);
	}

	public int takenSlots() {
		int taken = 0;
		for (GodType god : order) {
			if (hasQuest(god)) {
				taken++;
			}
		}
		return taken;
	}

	public boolean hasQuest(GodType god) {
		// If the key exists, return true if it has a quest.
		if (questSlots.containsKey(god)) {
			return questSlots.get(god) != null;
		}
		return false;
	}

	public Quest getQuest(GodType god) {
		return questSlots.get(god);
	}

	public void addCompletedQuest(Quest quest) {
		this.completedQuests.add(quest);

	}

}
