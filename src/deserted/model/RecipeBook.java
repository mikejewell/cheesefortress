package deserted.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import deserted.model.action.BaseAction;
import deserted.model.action.CraftAction;
import deserted.model.action.EatAction;
import deserted.model.action.HarvestAction;
import deserted.model.action.OtherAction;
import deserted.model.item.EdibleItem;
import deserted.model.item.ItemFactory;
import deserted.model.item.ItemStack;
import deserted.model.item.ItemType;
import deserted.sprite.SpriteType;
import deserted.tilesystem.Tile;
import deserted.tilesystem.TileSystem;
import deserted.tilesystem.TileSystem.TileId;

/*
 * Recipes are a sort of action that operate on items in the inventory: this is distinct 
 * from TileActions (where the user acts on a tile and get something) or EatActions 
 * (where the user consumes an item).
 */
public class RecipeBook {

	private ArrayList<BaseAction> actions;

	public void addAction(BaseAction action) {
		actions.add(action);
	}

	public RecipeBook() {
		actions = new ArrayList<BaseAction>();
	}

	public List<BaseAction> getValidActions(Agent agent) {
		List<BaseAction> validActions = new ArrayList<BaseAction>();
		for (BaseAction action : actions) {
			if (action.canPerform(agent)) {
				validActions.add(action);
			}
		}
		return validActions;
	}
}
