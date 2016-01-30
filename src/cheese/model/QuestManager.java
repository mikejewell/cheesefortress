package cheese.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class QuestManager {

	private List<Quest> questList;
	private HashMap<God, Quest> questSlots;
	private God[] order = {God.THOR, God.FREYA, God.HEL, God.LOKI, God.TRIBE};
	
	public QuestManager() {
		this.questSlots = new HashMap<God, Quest>();
		// TODO make all the quests
		this.questList = new ArrayList<Quest>();
		questList.add(new QuestTribute("Iron for Hel", "The armies of Hel requires iron", 10, God.HEL, new Cost(0, 20, 0)));
		questList.add(new QuestTribute("Cheese for Loki", "Loki is hosting a feast but lacks Cheese", 5, God.LOKI, new Cost(0, 20, 0)));
		questList.add(new QuestTribute("Cheese for Freya", "Freya is hosting a feast but lacks Cheese", 5, God.FREYA, new Cost(0, 20, 0)));
		questList.add(new QuestTribute("Cheese for Thor", "Thor is hosting a feast but lacks Cheese", 5, God.THOR, new Cost(0, 20, 0)));
		questList.add(new QuestTribute("Cheese for All", "Tribe is hosting a feast but lacks Cheese", 5, God.TRIBE, new Cost(0, 20, 0)));
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
		
		God god = pickGod();
		Quest quest = pickQuest(god);
		questSlots.put(god, quest);
	}
	
	public Quest pickQuest(God god) {
		Random r = new Random();
		ArrayList<Quest> options = new ArrayList<Quest>();
		for(Quest quest: questList) {
			if(quest.getGod() == god) {
				options.add(quest);
			}
		}
		
		int index = r.nextInt(options.size());
		return options.get(index);
	}
	
	public God pickGod() {
		Random r = new Random();
		ArrayList<God> options = new ArrayList<God>();
		for(God god: order) {
			if(!hasQuest(god)) {
				options.add(god);
			}
		}
		int index = r.nextInt(options.size());
		return options.get(index);
	}
	
	public int takenSlots() {
		int taken = 0;
		for(God god: order) {
			if(hasQuest(god)) {
				taken++;
			}
		}
		return taken;
	}
	
	public boolean hasQuest(God god) {
		if(questSlots.containsKey(god)) {
			return questSlots.get(god) != null;
		}
		return false;
	}
	
	public Quest getQuest(God god) {
		return questSlots.get(god);
	}
	
}
