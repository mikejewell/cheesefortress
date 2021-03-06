package deserted.model.item;

import java.util.HashMap;
import java.util.Map;

public class ItemFactory {
	
	private static Map<ItemType,Item> itemMap;
	
	public static void init() {
		itemMap = new HashMap<ItemType,Item>();

		itemMap.put(ItemType.METAL, new Item("Metal", "metal"));
		itemMap.put(ItemType.FOOD, new Item("Food", "food"));
		itemMap.put(ItemType.STONE, new Item("Stone", "stone"));
		itemMap.put(ItemType.WOOD, new Item("Wood", "wood"));
		itemMap.put(ItemType.ARMY, new Item("Army", "fish"));
		itemMap.put(ItemType.EQUIPMENT, new Item("Equipment", "spear"));
	}
	
	public static Item createItem(ItemType itemType) {
		return itemMap.get(itemType);
	}
}
