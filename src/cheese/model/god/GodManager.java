package cheese.model.god;

import java.util.*;

public class GodManager {
	
	private HashMap<GodType, God> gods;
	private Map<God, Integer> relationshipMap = new HashMap<God, Integer>();
	
	public GodManager() {
		this.gods = new HashMap<GodType, God>();
		this.gods.put(GodType.THOR, new God("Thor"));
		this.gods.put(GodType.FREYA, new God("Freya"));
		this.gods.put(GodType.LOKI, new God("Loki"));
		this.gods.put(GodType.TRIBE, new God("Tribe"));
		this.gods.put(GodType.HEL, new God("Hel"));
	}
	
	public God getGod(GodType godType) {
		return gods.get(godType);
	}
	
	

	
}
