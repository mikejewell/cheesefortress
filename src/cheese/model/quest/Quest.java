package cheese.model.quest;

import java.util.List;

import cheese.model.TimedItem;
import cheese.model.god.GodType;

import deserted.model.GameSession;

abstract public class Quest extends TimedItem {

	private String questName;
	private String questDescription;
	private GodType god;
	private double validFrom;
	
	//Relationship value to god
	private int value;
	private int hours;
	private boolean completed;
	
	public Quest(String questName, String questDescription, int value, GodType god) {
		this.setQuestName(questName);
		this.setQuestDescription(questDescription);
		this.setGod(god);
		this.hours = 0;
		this.setCompleted(false);
	}
	
	public abstract boolean canComplete();
	
	public void complete() {
		QuestManager questManager = GameSession.getInstance().getQuestManager();
		questManager.assignQuest();
		onComplete();
		this.setCompleted(true);
	}
	
	public double getHoursToFinish() {
		return 24;
	}
	
	@Override
	public void onTick() {
		hours++;
		if(hours >= getHoursToFinish()) {
			stopTiming();
			GameSession.getInstance().getQuestManager().assignQuest();
			this.setCompleted(true);
			this.hours = 0;
			onFailure();
		}
	}
	
	@Override
	public double getDuration() {
		return 60;
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

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
}
