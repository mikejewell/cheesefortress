package cheese.model.god;


public class God {
	private String name;
	private int playerRelationshipValue;
	private Catastrophe catastrophe;
	
	public God(String name, Catastrophe catastrophe) {
		setName(name);
		this.catastrophe = catastrophe;
		this.playerRelationshipValue = 50;
	}
	
	public void changeRelationship(int change) {
		if (playerRelationshipValue + change < 0) playerRelationshipValue = 0;
		else if (playerRelationshipValue + change > 100) playerRelationshipValue = 100;
		else playerRelationshipValue = playerRelationshipValue + change;
		System.out.println("Relationship to "+name+"="+playerRelationshipValue);
		
		if(this.playerRelationshipValue == 0) {
			this.catastrophe.activate();
		}
		else {
			if(this.catastrophe.isActive()) {
				this.catastrophe.deactivate();
			}
		}
	} 

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Catastrophe getCatastrophe() {
		return catastrophe;
	}

	public void setCatastrophe(Catastrophe catastrophe) {
		this.catastrophe = catastrophe;
	}

}
