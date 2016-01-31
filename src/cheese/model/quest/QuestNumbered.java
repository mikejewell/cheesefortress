package cheese.model.quest;

import cheese.model.god.GodType;

public class QuestNumbered extends Quest {
	private int number;
	private int multiplier;
	private int cap;

	public QuestNumbered(String title, int number, int multiplier, int cap, GodType godType) {
		super("", "", number * 5, GodType.NEUTRAL);
		this.setNumber(number);
		this.multiplier = multiplier;
		this.cap = cap;
	}

	@Override
	public int getValue() {
		return this.number * 5;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public void onComplete() {
		super.onComplete();
		this.number = this.number * this.multiplier;
		if(this.number > this.cap) {
			this.setRepeatable(false);
		}
		else {
			this.setRepeatable(true);
		}
	}
	
	@Override
	public boolean canComplete() {
		return false;
	}


	public int getMultiplier() {
		return multiplier;
	}



	public void setMultiplier(int multiplier) {
		this.multiplier = multiplier;
	}



	public int getCap() {
		return cap;
	}



	public void setCap(int cap) {
		this.cap = cap;
	}
}
