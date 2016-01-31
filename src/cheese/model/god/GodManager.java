package cheese.model.god;

import java.util.HashMap;
import java.util.Map;

import deserted.model.GameSession;

public class GodManager {
	
	private HashMap<GodType, God> gods;
	private Map<God, Integer> relationshipMap = new HashMap<God, Integer>();
	
	public GodManager() {
		this.gods = new HashMap<GodType, God>();
		Catastrophe famineCatastrophe = new Catastrophe() {
			@Override
			public void activate() {
				super.activate();
				GameSession.getInstance().setProbabilityFood(0.1);
			}
			
			@Override
			public void deactivate() {
				super.deactivate();
				GameSession.getInstance().setProbabilityFood(1.0);
			}
		};
		
		Catastrophe lightningCatastrophe = new Catastrophe() {
			
			@Override
			public void activate() {
				super.activate();
				this.startTiming();
			}
			
			@Override
			public void deactivate() {
				super.deactivate();
				this.stopTiming();
			}
			
			@Override
			public double getDuration() {
				return 4*60;
			}
			
			@Override
			public void onTick() {
				System.out.println("Lightning!");
			}
		};
		
		Catastrophe fertilityCatastrophe = new Catastrophe() {
			@Override
			public void activate() {
				super.activate();
				GameSession.getInstance().setProbabilitySettler(0.1);
			}
			
			@Override
			public void deactivate() {
				super.deactivate();
				GameSession.getInstance().setProbabilitySettler(1.0);
			}
		};
		
		Catastrophe floodCatastrophe = new Catastrophe() {
		};
		
		
		this.gods.put(GodType.THOR, new God("Thor", lightningCatastrophe));
		this.gods.put(GodType.FREYA, new God("Freya", fertilityCatastrophe));
		this.gods.put(GodType.LOKI, new God("Loki", famineCatastrophe));
		this.gods.put(GodType.TRIBE, new God("Tribe", famineCatastrophe)); // Need a catastrophe here.
		this.gods.put(GodType.HEL, new God("Hel", floodCatastrophe));
	}
	
	public God getGod(GodType godType) {
		return gods.get(godType);
	}
	
	

	
}
