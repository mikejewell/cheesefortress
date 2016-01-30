package cheese.model;

import java.util.List;

import deserted.model.GameSession;

abstract public class Quest {

	private String questName;
	private String questDescription;
	private GodType god;
	private double validFrom;
	
	//Relationship value to god
	private int value;
	
	public Quest(String questName, String questDescription, int value, GodType god) {
		this.setQuestName(questName);
		this.setQuestDescription(questDescription);
		this.setGod(god);
	}
	
	public abstract boolean canComplete();
	
	public void complete() {
		QuestManager questManager = GameSession.getInstance().getQuestManager();
		questManager.removeQuest(this);
		questManager.assignQuest();
		onComplete();
	}
	
	public void onComplete() {
	}
	
	public void onFailure() {
	}

	public GodType getGod() {
		return god;
	}

	public void setGod(GodType god) {
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

	public double getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(double validFrom) {
		this.validFrom = validFrom;
	}
}
