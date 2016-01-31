package cheese.graphics.tileSystem;

public class WedgeTileOverlay {
	
	public enum OverlayType{
		EMPTY,
		GRASS,
		BUSH,
		STICK1,
		STICK2,
		OVERLAYS3D,
		TREE1,
		TREE2,
		TREE3,
		TREE4
	}
	
	public int[] uvs;
	
	private static WedgeTileOverlay[] ids = new WedgeTileOverlay[] {
			null,
			new WedgeTileOverlay(256, 704, 320, 768),
			new WedgeTileOverlay(256, 768, 320, 832),
			new WedgeTileOverlay(320, 768, 384, 832),
			new WedgeTileOverlay(64, 768, 128, 832),
			null,
			new WedgeTileOverlay(128, 768, 192, 896),
			new WedgeTileOverlay(448, 832, 640, 1024),
			new WedgeTileOverlay(0, 832, 64, 1024),
			new WedgeTileOverlay(64, 832, 128, 1024),
	};
	
	public WedgeTileOverlay(int u, int v, int u2, int v2){
		uvs = new int[]{
			u, v, u2, v2
		};
	}
	
	public int[] getUVs(){
		return uvs;
	}

	public static WedgeTileOverlay getOverlay(OverlayType type){
		return ids[type.ordinal()];
	}
	
}
