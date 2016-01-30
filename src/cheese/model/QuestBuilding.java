package cheese.model;

public class QuestBuilding extends Quest {
	private String buildingType;

	public QuestBuilding(String questName, String questDescription, int value, GodType god, String buildingType) {
		super(questName,questDescription, value, god);
		this.buildingType = buildingType;
	}
	
	public boolean checkIfComplete() {
		
		// Go through all buildings that were built as of the quest being given
		
		return false;
		
	}

}
