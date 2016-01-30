package cheese.model;

public class QuestTribute extends Quest {
	
	//Amount of tribute
	private Cost cost;
	
	
	public QuestTribute(String questName, String questDescription, int value, GodType god, Cost cost) {
		super(questName,questDescription, value, god);
		this.cost = cost;
	}
	
	public boolean checkIfComplete() {
		//TODO check if tribute has been paid
		return false;
		
	}
	
}
