package cheese.graphics.tileSystem;

public class WedgeTileOverlay {
	
	public enum OverlayType{
		GRASS,
		BUSH,
		STICK1,
		STICK2,
		TREE1,
		TREE2
	}
	
	public float u, v, u2, v2;	//Texture Coords
	
	private static WedgeTileOverlay[] ids = new WedgeTileOverlay[] {
			new WedgeTileOverlay(256, 704, 320, 768),
			new WedgeTileOverlay(256, 768, 320, 832),
			new WedgeTileOverlay(320, 768, 384, 832),
			new WedgeTileOverlay(64, 768, 128, 832)
	};
	
	public WedgeTileOverlay(float u, float v, float u2, float v2){
		this.u = u;
		this.v = v;
		this.u2 = u2;
		this.v2 = v2;
	}
	
	public float getV(){
		return v;
	}
	
	public float getU(){
		return u;
	}
	
	public float getV2(){
		return v2;
	}
	
	public float getU2(){
		return u2;
	}

	
	
}
