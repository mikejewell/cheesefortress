package cheese.graphics.tileSystem;

import cheese.graphics.tileSystem.WedgeTileOverlay.OverlayType;
import deserted.tilesystem.Tile;

public class WedgeTile {
	
	public enum Type{
		DIRT,
		WATER,
		STONE,
		CLIFF,
		GRASS
	}
	
	private Type type;	//Tile Type
	private int id;		//Tile id in texture
	private OverlayType overlay = OverlayType.EMPTY;	//E.g. Grass, water edge, bush etc.
	
	public Tile tile = null;
	
	public WedgeTile(int textureId, Type type){
		this.type = type;
		this.id = textureId;
	}
	
	public void setOverlay(OverlayType type){
		this.overlay = type;
	}
	
	public Type getType(){
		return type;
	}
	
	public int getId(){
		return id;
	}
	
	public OverlayType getOverlay(){
		return overlay;
	}

}
