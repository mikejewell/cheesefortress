package cheese.model;

import java.util.HashMap;

public class GodManager {
	
	private HashMap<GodType, God> gods;
	
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
