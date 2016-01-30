package cheese.model;

import java.util.Collections;
import java.util.List;

public class QuestManager {

	private List<Quest> questList;

	public QuestManager() {
		// TODO make all the quests
		questList.add(new QuestTribute("Iron for Hel", "The armies of Hel requires iron", 10, God.HEL, new Cost(0, 20, 0)));
		questList.add(new QuestTribute("Cheese for Loki", "Loki is hosting a feast but lacks Cheese", 5, God.LOKI, new Cost(0, 20, 0)));
//		questList.add(new QuestBuilding("Farm Please!", "Your villagers demand a farm", 5, God.TRIBE, BuildingType.FARM));
	
		// Shuffle
		Collections.shuffle(questList);
	}

	public List<Quest> getQuestList() {
		return questList;
	}

	public Quest pickQuest() {
		
		return null;
	}
	
}
