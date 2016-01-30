package cheese.model;

import java.util.List;

abstract public class Quest {

	private String questName;
	private String questDescription;
	private boolean isComplete;
	private God god;
	
	//Relationship value to god
	private int value;
	
	public Quest(String questName, String questDescription, int value, God god) {
		this.setQuestName(questName);
		this.setQuestDescription(questDescription);
		this.setGod(god);
	}
	
	public boolean getIsComplete() {
		return isComplete;
	}
	
	public void onComplete() {
		//TODO God is happy
		isComplete = true;
	}
	
	public void onFailure() {
		//TODO God is unhappy
		isComplete = true;
	}
	
	abstract boolean checkIfComplete();

	public God getGod() {
		return god;
	}

	public void setGod(God god) {
		this.god = god;
	}

	public String getQuestName() {
		return questName;
	}

	public void setQuestName(String questName) {
		this.questName = questName;
	}

	public String getQuestDescription() {
		return questDescription;
	}

	public void setQuestDescription(String questDescription) {
		this.questDescription = questDescription;
	}
}
