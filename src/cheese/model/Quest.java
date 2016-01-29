package cheese.model;

import java.util.List;

abstract public class Quest {

	private String questName;
	private String questDescription;
	private boolean isComplete;
	private God god;
	
	public Quest(String questName, String questDescription, God god) {
		this.questName = questName;
		this.questDescription = questDescription;
		this.god = god;
	}
	
	public boolean isComplete() {
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
}
