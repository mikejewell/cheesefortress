package cheese.model.god;

public class God {
	private String name;
	private int playerRelationshipValue;
	
	public God(String name) {
		setName(name);
	}
	
	public void changeRelationship(int change) {
		if (playerRelationshipValue + change < 0) playerRelationshipValue = 0;
		else if (playerRelationshipValue + change > 100) playerRelationshipValue = 100;
		else playerRelationshipValue = playerRelationshipValue + change;
	} 

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
