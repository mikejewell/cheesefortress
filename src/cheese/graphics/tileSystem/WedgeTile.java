package cheese.graphics.tileSystem;

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
	private int overlayId;	//E.g. Grass, water edge, bush etc.
	
	public Tile tile = null;
	
	public WedgeTile(int textureId, Type type){
		this.type = type;
		this.id = textureId;
	}
	
	public void setOverlay(int id){
		this.overlayId = id;
	}
	
	public Type getType(){
		return type;
	}
	
	public int getId(){
		return id;
	}
	
	public int getOverlay(){
		return overlayId;
	}

}
