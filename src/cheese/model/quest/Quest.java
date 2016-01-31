package cheese.model.quest;

import cheese.model.TimedItem;
import cheese.model.god.GodType;
import deserted.model.GameSession;

abstract public class Quest extends TimedItem {

	private String questName;
	private String questDescription;
	private GodType god;
	private GodType actualGod;
	private double validFrom;
	
	private double cardX;
	private double cardY;
	
	//Relationship value to god
	private int value;
	private int hours;
	private boolean completed;
	
	public Quest(String questName, String questDescription, int value, GodType god) {
		this.setQuestName(questName);
		this.setQuestDescription(questDescription);
		this.setGod(god);
		this.hours = 0;
		this.value = value;
		this.setCompleted(false);
	}
	
	public abstract boolean canComplete();
	
	public void complete() {
		QuestManager questManager = GameSession.getInstance().getQuestManager();
		questManager.assignQuest();
		GameSession.getInstance().getGodManager().getGod(actualGod).changeRelationship(this.value);
		onComplete();
		this.setCompleted(true);
	}
	
	public double getHoursToFinish() {
		return 24;
	}
	
	public int getHoursElapsed() {
		return this.hours;
	}
	
	@Override
	public void onTick() {
		hours++;
		if(hours >= getHoursToFinish()) {
			stopTiming();
			GameSession.getInstance().getGodManager().getGod(actualGod).changeRelationship(-this.value);
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

	public GodType getActualGod() {
		return actualGod;
	}

	public void setActualGod(GodType actualGod) {
		this.actualGod = actualGod;
	}

	public double getCardX() {
		return cardX;
	}

	public void setCardX(double cardX) {
		this.cardX = cardX;
	}

	public double getCardY() {
		return cardY;
	}

	public void setCardY(double cardY) {
		this.cardY = cardY;
	}
}
