package cheese.model.god;

import cheese.model.TimedItem;

public class Catastrophe extends TimedItem {
	
	private boolean active;
	public void activate() {
		this.setActive(true);
		System.out.println("Catastrophe active");
	}
	
	public void deactivate() {
		this.setActive(false);
		System.out.println("Catastrophe inactive");
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
