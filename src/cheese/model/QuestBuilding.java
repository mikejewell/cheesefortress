package cheese.model;

public class QuestBuilding extends Quest {
	
	private Building building;
	
	public QuestBuilding(String questName, String questDescription, int value, God god, Building building) {
		super(questName,questDescription, value, god);
		this.building = building;
	}
	
	public boolean checkIfComplete() {
		//TODO Check if building has been built
		return false;
		
	}

}
