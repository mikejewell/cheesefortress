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
		this.questName = questName;
		this.questDescription = questDescription;
		this.god = god;
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
}
