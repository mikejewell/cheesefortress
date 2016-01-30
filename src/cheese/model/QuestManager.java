package cheese.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import deserted.model.GameSession;

public class QuestManager {

	private List<Quest> questList;
	private HashMap<GodType, Quest> questSlots;
	private GodType[] order = {GodType.THOR, GodType.FREYA, GodType.HEL, GodType.LOKI, GodType.TRIBE};
	
	public QuestManager() {
		this.questSlots = new HashMap<GodType, Quest>();
		// TODO make all the quests
		this.questList = new ArrayList<Quest>();
		
		// Tribe Quests
		questList.add(new QuestBuilding("Big Farma", "We need food to keep our villagers alive - please build a farm!", 5, GodType.TRIBE, "Farm"));
		questList.add(new QuestBuilding("Hall Effect", "If we want to build anything, we'll need a Town Hall.", 5, GodType.TRIBE, "Town Hall"));
		questList.add(new QuestBuilding("Hunter Gatherer", "There's a load of wildlife out there that we could be eating!", 5, GodType.TRIBE, "Hunter Abode"));
		questList.add(new QuestBuilding("Unstable Situation", "If we had some horses, we could get resources from other villages.", 5, GodType.TRIBE, "Stable"));
		
		
		// God Quests
//		questList.add(new QuestTribute("Iron for Hel", "The armies of Hel requires iron", 10, GodType.HEL, new Cost(0, 20, 0)));
//		questList.add(new QuestTribute("Cheese for Loki", "Loki is hosting a feast but lacks Cheese", 5, GodType.LOKI, new Cost(0, 20, 0)));
//		questList.add(new QuestTribute("Cheese for Freya", "Freya is hosting a feast but lacks Cheese", 5, GodType.FREYA, new Cost(0, 20, 0)));
//		questList.add(new QuestTribute("Cheese for Thor", "Thor is hosting a feast but lacks Cheese", 5, GodType.THOR, new Cost(0, 20, 0)));
//		questList.add(new QuestTribute("Cheese for All", "Tribe is hosting a feast but lacks Cheese", 5, GodType.TRIBE, new Cost(0, 20, 0)));
//		questList.add(new QuestBuilding("Farm Please!", "Your villagers demand a farm", 5, God.TRIBE, BuildingType.FARM));
	
		// Shuffle
		Collections.shuffle(questList);
		
		assignQuest();
	}

	public List<Quest> getQuestList() {
		return questList;
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
		}
	}
	
	public Quest pickQuest(GodType god) {
		Random r = new Random();
		ArrayList<Quest> options = new ArrayList<Quest>();
		for(Quest quest: questList) {
			if(quest.getGod() == god) {
				options.add(quest);
			}
		}
		if(options.size() == 0) {
			return null;
		}
		
		int index = r.nextInt(options.size());
		return options.get(index);
	}
	
	public GodType pickGod() {
		Random r = new Random();
		ArrayList<GodType> options = new ArrayList<GodType>();
		for(GodType god: order) {
			if(!hasQuest(god)) {
				options.add(god);
			}
		}
		if(options.size() == 0) {
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
		if(questSlots.containsKey(god)) {
			return questSlots.get(god) != null;
		}
		return false;
	}
	
	public Quest getQuest(GodType god) {
		return questSlots.get(god);
	}
	
}
