package cheese.model.quest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import cheese.model.Cost;
import cheese.model.PlayerManager;
import cheese.model.god.GodType;
import deserted.model.Agent;
import deserted.model.AgentState;
import deserted.model.GameSession;

public class QuestManager {

	private List<Quest> questList;
	private HashMap<GodType, Quest> questSlots;
	private GodType[] order = {GodType.THOR, GodType.FREYA, GodType.HEL, GodType.LOKI, GodType.TRIBE};
	private ArrayList<Quest> completedQuests;
	
	public QuestManager() {
		this.questSlots = new HashMap<GodType, Quest>();
		// TODO make all the quests
		this.questList = new ArrayList<Quest>();
		this.completedQuests = new ArrayList<Quest>();

		// Tribe Quests
//		QuestBuilding farm = new QuestBuilding("Big Farma", "We need food to keep our villagers\nalive - please build a farm!", 50, GodType.TRIBE, "Farm") {
//			@Override
//			public double getHoursToFinish() {
//				return 48;
//			}
//			
//			@Override
//			public void onComplete() {
//				GameSession.getInstance().getInventory().addItem(ItemType.FOOD, 5);
//			}
//			
//			@Override
//			public void onFailure() {
//				System.out.println("Failure");
//			}
//		};
		
		QuestBuilding farm = new QuestBuilding("Big Farma", "We need food to keep our villagers\nalive - please build a farm!", 50, GodType.TRIBE, "Farm");
		QuestBuilding hall = new QuestBuilding("Hall Effect", "If we want to build anything, we'll\nneed a Town Hall.", 50, GodType.TRIBE, "Town Hall");
		QuestBuilding hunter = new QuestBuilding("Hunter Gatherer", "There's a load of wildlife out there\nthat we could be eating!", 50, GodType.TRIBE, "Hunter Abode");
		QuestBuilding stable = new QuestBuilding("Unstable Situation", "If we had some horses, we could get\nresources from other villages.", 50, GodType.TRIBE, "Stable");		
		QuestBuilding lumberjack = new QuestBuilding("Got Wood?", "A lumberjack would be fantastic for\nour wood production.", 50, GodType.TRIBE, "Lumber Jack");
		QuestBuilding blacksmith = new QuestBuilding("Heavy Metal", "With a blacksmith, we could turn\nproduction up to 11!", 50, GodType.TRIBE, "Blacksmith");
		QuestBuilding settlement = new QuestBuilding("Settlers Too", "We could always do with more volunteers\nfor sacrifices...", 50, GodType.TRIBE, "Settlement");
		QuestBuilding barracks = new QuestBuilding("Barracks", "Build a barracks", 50, GodType.TRIBE, "Barracks");
		
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
		
//		questList.add(new QuestTribute("Dragons Hoard", "My precious... Give me 20 metal!", 50, GodType.NEUTRAL, new Cost(0,0,0,20)));
//		questList.add(new QuestTribute("Feed Me", "I'm very hungry - give me 20 food!", 50, GodType.NEUTRAL, new Cost(20,0,0,0)));
		questList.add(new QuestTribute("Sacrifice", "I demand a sacrifice of 20 villagers!", 50, GodType.NEUTRAL) {
			@Override
			public void onComplete() {
				int count = 0;
				int i = 0;
				PlayerManager pm = GameSession.getInstance().getPlayerManager();
				while(count < 3) {
					Agent agent = pm.getAgents().get(i);
					if(agent.getState() != AgentState.DEAD) {
						agent.setState(AgentState.DEAD);
						count++;
					}
					i++;
				}
			}
			
			@Override
			public boolean canComplete() {
				int population = GameSession.getInstance().getPlayerManager().getAgents().size();
				if(population >= 3) {
					return true;
				}
				return false;
			}
		});
	}

	public List<Quest> getQuestList() {
		return questList;
	}

	public Collection<Quest> getCurrentQuests() {
		return questSlots.values();
	}
	
	public void flush() {
		ArrayList<GodType> toRemove = new ArrayList<GodType>();
		
		for(GodType god: questSlots.keySet()) {
			if(questSlots.get(god).isCompleted()) {
				toRemove.add(god);
				questList.remove(questSlots.get(god));
			}
		}
		
		for(GodType remove: toRemove) {
			questSlots.remove(remove);
		}
		// TODO: Add a 'repeatable' flag.
	}
	
	public void assignQuest() {

		GodType god = pickGod();
		Quest quest = pickQuest(god);
		if(quest != null) {
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
		
		if(options.size() == 0) {
			System.out.println("No options for quest");
			return null;
		}
		
		// Filter ones we can actually do.
		ArrayList<Quest> filtered = new ArrayList<Quest>();
		for(Quest quest: options) {
			boolean canAdd = true;
			System.out.println("Check requirements for "+quest.getQuestName());
			for(Quest required: quest.getRequirements()) {
				System.out.println("Do we have "+required.getQuestName());
				if(!this.completedQuests.contains(required)) {
					System.out.println("No");
					canAdd = false;
				}
			}
			if(canAdd) {
				filtered.add(quest);
			}
		}
		
		if(filtered.size() == 0) {
			System.out.println("No options after filtering");
			return null;
		}
		int index = r.nextInt(filtered.size());
		return filtered.get(index);
	}
	
	public ArrayList<Quest> questsForGod(GodType god) {

		ArrayList<Quest> options = new ArrayList<Quest>();
		for(Quest quest: questList) {
			
			// A NEUTRAL quest can apply to any god, but not the tribe.
			if(god != GodType.TRIBE && (quest.getGod() == god || quest.getGod() == GodType.NEUTRAL)) {
				options.add(quest);
			}
			else {
				if(quest.getGod() == god) {
					options.add(quest);
				}
			}
		}
		return options;
	}
	
	public GodType pickGod() {
		Random r = new Random();
		
		ArrayList<GodType> options = new ArrayList<GodType>();
		for(GodType god: order) {
			if(!hasQuest(god)) {
				System.out.println("God "+god+" has no quest");
				if(questsForGod(god).size() > 0) {
					System.out.println("Have some quests we can use");
					options.add(god);
				}
			}
		}
		
		
		if(options.size() == 0) {
			System.out.println("No options for god");
			return null;
		}
		
		int index = r.nextInt(options.size());
		return options.get(index);
	}
	
	public int takenSlots() {
		int taken = 0;
		for(GodType god: order) {
			if(hasQuest(god)) {
				taken++;
			}
		}
		return taken;
	}
	
	public boolean hasQuest(GodType god) {
		// If the key exists, return true if it has a quest.
		if(questSlots.containsKey(god)) {
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
