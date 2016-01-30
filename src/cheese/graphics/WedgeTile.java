package cheese.graphics;

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
	
	public WedgeTile(int textureId, Type type){
		this.type = type;
		this.id = textureId;
	}
	
	public int getId(){
		return id;
	}

}
