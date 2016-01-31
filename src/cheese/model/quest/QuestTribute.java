package cheese.model.quest;

import java.util.ArrayList;

import cheese.model.Cost;
import cheese.model.building.BaseBuilding;
import cheese.model.building.Building;
import cheese.model.building.BuildingManager;
import cheese.model.god.GodType;
import deserted.model.GameSession;
import deserted.model.Inventory;
import deserted.model.item.ItemType;

public class QuestTribute extends Quest {
	
	//Amount of tribute
	private Cost cost;
	
	
	public QuestTribute(String questName, String questDescription, int value, GodType god, Cost cost) {
		super(questName,questDescription, value, god);
		this.cost = cost;
	}

	public QuestTribute(String questName, String questDescription, int value, GodType god) {
		super(questName,questDescription, value, god);
	}
	
	@Override
	public void onComplete() {
		payTribute();
	}
	
	@Override
	public boolean canComplete() {
		return canPayTribute();
	}
	
	private boolean canPayTribute() {
		Inventory i = GameSession.getInstance().getInventory();

		if (i.getItemCount(ItemType.FOOD) < cost.getFood()) {
			return false;
		}

		if (i.getItemCount(ItemType.METAL) < cost.getMetal()) {
			return false;
		}

		if (i.getItemCount(ItemType.STONE) < cost.getStone()) {
			return false;
		}

		if (i.getItemCount(ItemType.WOOD) < cost.getWood()) {
			return false;
		}

		return true;
	}

	private void payTribute() {
		Inventory i = GameSession.getInstance().getInventory();
		i.removeItem(ItemType.FOOD, cost.getFood());
		i.removeItem(ItemType.METAL, cost.getMetal());
		i.removeItem(ItemType.STONE, cost.getStone());
		i.removeItem(ItemType.WOOD, cost.getWood());
	}
}
