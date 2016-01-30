package cheese.model.quest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import cheese.model.Cost;
import cheese.model.god.GodType;
import deserted.model.GameSession;
import deserted.model.item.ItemType;

public class QuestManager {

	private List<Quest> questList;
	private HashMap<GodType, Quest> questSlots;
	private GodType[] order = {GodType.THOR, GodType.FREYA, GodType.HEL, GodType.LOKI, GodType.TRIBE};
	
	public QuestManager() {
		this.questSlots = new HashMap<GodType, Quest>();
		// TODO make all the quests
		this.questList = new ArrayList<Quest>();

		// Tribe Quests
		questList.add(new QuestBuilding("Big Farma", "We need food to keep our villagers alive - please build a farm!", 5, GodType.TRIBE, "Farm") {
			@Override
			public double getHoursToFinish() {
				return 48;
			}
			
			@Override
			public void onComplete() {
				GameSession.getInstance().getInventory().addItem(ItemType.FOOD, 5);
			}
			
			@Override
			public void onFailure() {
				System.out.println("Failure");
			}
		});
		questList.add(new QuestBuilding("Hall Effect", "If we want to build anything, we'll need a Town Hall.", 5, GodType.TRIBE, "Town Hall"));
		questList.add(new QuestBuilding("Hunter Gatherer", "There's a load of wildlife out there that we could be eating!", 5, GodType.TRIBE, "Hunter Abode"));
		questList.add(new QuestBuilding("Unstable Situation", "If we had some horses, we could get resources from other villages.", 5, GodType.TRIBE, "Stable"));
		
		questList.add(new QuestTribute("Feed Me", "I'm very hungry - give me 10 food!", 5, GodType.LOKI, new Cost(10,0,0,0)));
		
		// Shuffle
		Collections.shuffle(questList);
	}

	public List<Quest> getQuestList() {
		return questList;
	}

	public Collection<Quest> getCurrentQuests() {
		return questSlots.values();
	}
	
	public void removeQuest(Quest quest) {
		ArrayList<GodType> toRemove = new ArrayList<GodType>();
		for(GodType god: questSlots.keySet()) {
			if(questSlots.get(god) == quest) {
				toRemove.add(god);
			}
		}
		for(GodType remove: toRemove) {
			questSlots.remove(remove);
		}
		// TODO: Add a 'repeatable' flag.
		questList.remove(quest);
	}
	
	public void assignQuest() {
		// How many slots are currently taken?
		// TODO: Only allow 1 quest for now
		if(takenSlots() > 1) {
			return;
		}
		
		GodType god = pickGod();
		Quest quest = pickQuest(god);
		if(quest != null) {
			quest.setValidFrom(GameSession.getInstance().getTimeSurvived());
			questSlots.put(god, quest);
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
		
		int index = r.nextInt(options.size());
		return options.get(index);
	}
	
	public ArrayList<Quest> questsForGod(GodType god) {

		ArrayList<Quest> options = new ArrayList<Quest>();
		for(Quest quest: questList) {
			if(quest.getGod() == god) {
				options.add(quest);
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
	
}
