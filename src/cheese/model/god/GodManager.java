package cheese.model.god;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import deserted.model.GameSession;
import deserted.model.item.ItemType;

public class GodManager {
	
	private HashMap<GodType, God> gods;
	private Map<God, Integer> relationshipMap = new HashMap<God, Integer>();
	
	Random rand = new Random();
	
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
			
			int time = 0;
			int flash = 0;
			
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
			public void render(Graphics g){
				if(flash++ < 5)
					g.setColor(new Color(1, 1, 1, flash/4));
				else
					g.setColor(new Color(1, 1, 1, (1-flash)/4));
				g.fillRect(0, 0, Display.getWidth(), Display.getHeight());
				if(flash < 10){
					GameSession.getInstance().queueCatastrophe(this);
				}else{
					for(int i = 0; i < 10 + (rand.nextInt(5)-2); i++){
						if(GameSession.getInstance().getInventory().getItemCount(ItemType.values()[i]) > 0){
							GameSession.getInstance().getInventory().removeItem(ItemType.values()[i], 1);
						}						
					}
				}
			}
			
			@Override
			public void onTick() {
				time++;
				if(time % 60 < 10){
					if(rand.nextFloat() < 0.7f){
						GameSession.getInstance().queueCatastrophe(this);
						GameSession.getInstance().getMessenger().addMessage("ZAPPPP!!!!!", Color.yellow, 20);
					}else{
						flash = 0;
					}
				}
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
