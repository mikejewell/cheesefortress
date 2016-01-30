package cheese.model.quest;

import cheese.model.Cost;
import cheese.model.god.GodType;

public class QuestTribute extends Quest {
	
	//Amount of tribute
	private Cost cost;
	
	
	public QuestTribute(String questName, String questDescription, int value, GodType god, Cost cost) {
		super(questName,questDescription, value, god);
		this.cost = cost;
	}
	
	public boolean canComplete() {
		//TODO check if tribute has been paid
		return false;
		
	}
	
}
