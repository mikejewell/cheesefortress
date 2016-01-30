package cheese.model;

public class Cost {
	private int wood;
	private int stone;
	private int food;
	private int metal;

	public Cost(int food) {
		this(food, 0, 0, 0);
	}

	public Cost(int food, int wood) {
		this(food, wood, 0, 0);
	}

	public Cost(int food, int wood, int stone) {
		this(food, wood, stone, 0);
	}

	public Cost(int food, int wood, int stone, int metal) {
		this.setFood(food);
		this.setWood(wood);
		this.setStone(stone);
		this.setMetal(metal);
	}

	public int getWood() {
		return wood;
	}

	public void setWood(int wood) {
		this.wood = wood;
	}

	public int getStone() {
		return stone;
	}

	public void setStone(int stone) {
		this.stone = stone;
	}

	public int getFood() {
		return food;
	}

	public void setFood(int food) {
		this.food = food;
	}

	public int getMetal() {
		return metal;
	}

	public void setMetal(int metal) {
		this.metal = metal;
	}

}
