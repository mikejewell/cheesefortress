package cheese.model;

public abstract class BaseBuilding implements IBuilding {
	private String name;
	private String description;
	
	public BaseBuilding(String name, String desc) {
		this.setName(name);
		this.setDescription(desc);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}