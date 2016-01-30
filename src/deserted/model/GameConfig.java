package deserted.model;

public class GameConfig {
	// 30 seconds = 1 hour
	// 1 second = 2 minutes
	public static final int MINS_PER_SEC = 48;
	public static final int NUMBER_AGENTS = 3;

	public static final float FOOD_PER_SEC_WALK = 1.0f;
	public static final float FOOD_PER_SEC_STAND = 0.75f;
	public static final float FOOD_PER_SEC_SLEEP = 0.7f;

	public static final float HEALTH_PER_SEC = 0.25f;
	
	//Starting resources
	public static final int STARTING_FOOD = 10;
	public static final int STARTING_WOOD = 10;
	public static final int STARTING_STONE = 10;
	public static final int STARTING_METAL = 10;

}
