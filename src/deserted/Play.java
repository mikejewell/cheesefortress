package deserted;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import cheese.model.BaseBuilding;
import cheese.model.Building;
import cheese.model.BuildingManager;
import cheese.model.God;
import cheese.model.GodManager;
import cheese.model.GodType;
import cheese.model.Quest;
import cheese.model.QuestManager;
import deserted.model.Agent;
import deserted.model.AgentState;
import deserted.model.GameSession;
import deserted.model.RecipeBook;
import deserted.model.action.BaseAction;
import deserted.model.item.Item;
import deserted.model.item.ItemFactory;
import deserted.model.item.ItemType;
import deserted.player.MonsterManager;
import deserted.player.MonsterUI;
import deserted.player.PlayerReachedDestinationEvent;
import deserted.player.PlayerUI;
import deserted.sprite.SpriteType;
import deserted.tilesystem.MiniMap;
import deserted.tilesystem.Tile;
import deserted.tilesystem.TileSystem;
import deserted.tilesystem.TileSystem.TileId;

public class Play extends BasicGameState implements GameState,
		PlayerReachedDestinationEvent {

	public static final int STATE_PLAY = 1;

	RecipeBook recipeBook;
	TileSystem ts;
	GameSession gs;
	MonsterManager monsterManager;
	MiniMap miniMap;
	Messenger messenger;

	List<PlayerUI> players;
	List<Item> selectedItems;
	Map<ItemType, Image> itemImages;
	int actionKeyPressed = -1;
	String name;

	Agent selectedAgent;
	Image stickFigure;

	// UI Variables
	int header_height;
	int header_pad;
	int h_y;
	int h_h;

	// Footer vars
	int footer_height;
	int footer_y;
	int footer_pad;
	int f_y;
	int f_h;

	// Action Bar vars
	int action_bar_height;
	int action_bar_y;
	int action_bar_pad;
	int a_y;
	int a_h;

	// Agent vars
	int agent_bar_y;
	int agent_bar_height;
	int agent_bar_pad;
	int ag_y;
	int ag_h;
	int agent_bar_width;
	int ag_x;

	Rectangle headerRect;
	Rectangle footerRect;
	Rectangle actionRect;
	Rectangle agentRect;

	private int quest_bar_y;
	private int quest_bar_height;
	private int quest_width;

	private GodManager godManager;

	BaseBuilding currentDragging = null;
	Building currentBuilding = null;

	private Rectangle questRect;

	private int quest_bar_pad;

	private BuildingManager buildingManager;

	private QuestManager questManager;
	

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {

		name = game.getTitle();
		// TODO: Tile system needs modifying when the screen resolution / window
		// size changes.
		ts = TileSystem.getInstance();
		ts.init(new Point(container.getWidth(), container.getHeight()));

		ItemFactory.init();
		gs = GameSession.getInstance();
		buildingManager = gs.getBuildingManager();
		questManager = gs.getQuestManager();

		questManager.assignQuest();
		Vector2f wreckageCenter = WreckageLocationDecider();

		players = new ArrayList<PlayerUI>();
		for (int i = 0; i < gs.getAgents().size(); i++) {
			Agent agent = gs.getAgents().get(i);
			PlayerUI pui = new PlayerUI(agent, ts,
					wreckageCenter);
			pui.addReachedDestinationEvent(this);
			players.add(pui);
		}
		selectedAgent = gs.getAgents().get(0);
		selectedItems = new ArrayList<Item>();
		messenger = new Messenger();

		stickFigure = new Image("images/icons/stickperson.png");

		itemImages = new HashMap<ItemType, Image>();
		for (ItemType type : ItemType.values()) {
			Item item = ItemFactory.createItem(type);
			if (item.hasImage()) {
				Image image = new Image("images/icons/" + item.getImageName()
						+ ".png");
				itemImages.put(type, image);
			}
		}
		recipeBook = new RecipeBook();

		monsterManager = new MonsterManager(ts, players);
		godManager = new GodManager();

		ts.getCamera().x = players.get(0).location.x;
		ts.getCamera().y = players.get(0).location.y;

		miniMap = new MiniMap(ts, players);

		RandomTileObject(TileId.GRASS, SpriteType.TREE, 700, true);
		RandomTileObject(TileId.DIRT, SpriteType.PALM_TREE, 200, true);
		RandomTileObject(TileId.ROCK, SpriteType.PINE_TREE, 100, true);
		RandomTileObject(TileId.SNOW, SpriteType.ALIEN_ARTIFACT, 1, false);
		RandomTileObject(TileId.DIRT, SpriteType.ALTAR, 3, false);
		RandomTileObject(TileId.WATER, SpriteType.BOAT, 2, false);
		RandomTileObject(TileId.ROCK, SpriteType.CAVE, 2, false);
		RandomTileObject(TileId.DIRT, TileId.POND, 100, false);
		RandomTileObject(TileId.DIRT, TileId.TARPIT, 30, false);
		RandomTileObject(TileId.GRASS, SpriteType.SHRUB, 30, false);
		RandomTileObject(TileId.ROCK, SpriteType.CAVE, 10, false);
		RandomTileObject(TileId.DIRT, SpriteType.WRECKAGE, 20, false);

		WreckageSpreader(wreckageCenter, 40, false);

		container.setShowFPS(false);

		messenger.addMessage("Collect items using action buttons below.",
				Color.green, 20);
		messenger.addMessage("Use WASD or ARROW keys to move the camera.",
				Color.green, 20);
		messenger.addMessage("Click to move the selected player.", Color.green,
				20);
		messenger.addMessage("WELCOME TO THE ISLAND OF THE LOST", Color.green,
				20);

		// Initialise UI Variables---------------------------------------

		// Header vars
		header_height = 25;
		header_pad = 3;
		h_y = header_pad;
		h_h = header_height - (2 * header_pad);
		
		quest_bar_y = header_height;
		quest_bar_height = 80;
		quest_bar_pad = 0;
		quest_width = ((container.getWidth() - 250) / 5) - (quest_bar_pad * 5);

		// Footer vars
		footer_height = 60;
		footer_y = container.getHeight() - footer_height;
		footer_pad = 9;
		// Use f_h, f_y for the actual footer height / y pos
		f_y = footer_y + footer_pad;
		f_h = footer_height - (2 * footer_pad);

		// Action Bar vars
		action_bar_height = 35;
		action_bar_y = footer_y - action_bar_height;
		action_bar_pad = 3;
		a_y = action_bar_y + action_bar_pad;
		a_h = action_bar_height - (2 * action_bar_pad);

		// Agent vars
		agent_bar_y = header_height;
		agent_bar_height = action_bar_y - header_height;
		agent_bar_pad = 3;
		ag_y = agent_bar_y + agent_bar_pad;
		ag_h = agent_bar_height + (2 * agent_bar_pad);
		agent_bar_width = 250;
		ag_x = container.getWidth() - agent_bar_width - 2;

		headerRect = new Rectangle(0, 0, container.getWidth(), header_height);
		footerRect = new Rectangle(0, footer_y, container.getWidth(),
				footer_height);
		actionRect = new Rectangle(0, action_bar_y, container.getWidth(),
				action_bar_height);
		agentRect = new Rectangle(ag_x, ag_y, agent_bar_width, agent_bar_height);
		questRect = new Rectangle(0, quest_bar_y, container.getWidth(), quest_bar_height);
	}

	private void RandomTileObject(TileId tileType, SpriteType spriteType,
			int treeCount, boolean preferGroupings) {
		Random randomGenerator = new Random();
		while (true) {
			int x = randomGenerator.nextInt(ts.getSize() - 2) + 1;
			int y = randomGenerator.nextInt(ts.getSize() - 2) + 1;
			Tile tile = ts.getTile(x, y);
			if (tile.id == tileType && tile.numSprites() == 0
					&& tile.variant == 0) {
				float surroundTree = 1;
				if (ts.getTile(x + 1, y).hasSprite(spriteType))
					surroundTree++;
				if (ts.getTile(x - 1, y).hasSprite(spriteType))
					surroundTree++;
				if (ts.getTile(x, y + 1).hasSprite(spriteType))
					surroundTree++;
				if (ts.getTile(x, y - 1).hasSprite(spriteType))
					surroundTree++;
				float num = (float) randomGenerator.nextInt(100);
				if (preferGroupings)
					num /= surroundTree;
				else
					num /= 1.25;
				num *= surroundTree;

				if (num > 50) {
					treeCount -= 1;
					tile.addSprite(spriteType);
				}
			}

			if (treeCount == 0)
				return;
		}

	}

	private Vector2f WreckageLocationDecider() {
		int centerX = 0;
		int centerY = 0;
		while (true) {
			Random randomGenerator = new Random();
			centerX = randomGenerator.nextInt(ts.getSize() - 40) + 1;
			centerY = randomGenerator.nextInt(ts.getSize() - 40) + 1;
			Tile tile = ts.getTileFromWorld(centerX, centerY);
			if (tile.id == TileId.GRASS) {
				return new Vector2f(centerX, centerY);
			}
		}
	}

	private void WreckageSpreader(Vector2f wreckageCenter, int treeCount,
			boolean preferGroupings) {
		Random randomGenerator = new Random();
		while (true) {
			float rad = (randomGenerator.nextInt(20));// *randomGenerator.nextInt(20))/10;
			float angle = (float) (randomGenerator.nextDouble() * (Math.PI * 2));
			float x = wreckageCenter.x + (float) Math.asin(angle - Math.PI)
					* rad;
			float y = wreckageCenter.y + (float) Math.acos(angle - Math.PI)
					* rad;

			Tile tile = ts.getTileFromWorld(x, y);
			if (tile != null) {
				if (tile.numSprites() == 0) {
					treeCount -= 1;
					tile.addSprite(SpriteType.WRECKAGE);
				}
			}
			if (treeCount == 0)
				return;
		}

	}

	private void RandomTileObject(TileId tileType, TileId tileDestType,
			int treeCount, boolean preferGroupings) {
		Random randomGenerator = new Random();
		while (true) {
			int x = randomGenerator.nextInt(ts.getSize() - 2) + 1;
			int y = randomGenerator.nextInt(ts.getSize() - 2) + 1;
			Tile tile = ts.getTile(x, y);
			if (tile.id == tileType && tile.numSprites() == 0
					&& tile.variant == 0) {
				float surroundTree = 1;
				if (ts.getTile(x + 1, y).id == tileDestType)
					surroundTree++;
				if (ts.getTile(x - 1, y).id == tileDestType)
					surroundTree++;
				if (ts.getTile(x, y + 1).id == tileDestType)
					surroundTree++;
				if (ts.getTile(x, y - 1).id == tileDestType)
					surroundTree++;
				float num = (float) randomGenerator.nextInt(100);
				if (preferGroupings)
					num /= surroundTree;
				else
					num /= 1.25;
				num *= surroundTree;

				if (num > 50) {
					treeCount -= 1;
					ts.setTileID(x, y, tileDestType);
				}
			}

			if (treeCount == 0)
				return;
		}

	}

	public void renderWorld(Graphics g) {
		ts.renderTiles(g);
		for (int y = 0; y < ts.size; y++) {
			ts.renderGroundSprites(g, y);
			for (PlayerUI player : players) {
				if (player.location.y >= y - 0.4f
						&& player.location.y < y + 0.6f)
					player.render(g, ts.camera.zoom);
			}

			ts.render3DBuildings(g, y);
			
			ts.render3DSprites(g, y);

			monsterManager.render(g, ts.camera.zoom, y);
		}

		for (PlayerUI player : players) {
			player.renderOverlay(g, ts.camera.zoom);
		}

		for (Building building : buildingManager.getBuildingsInPlay()) {
			building.renderOverlay(g, ts.camera.zoom);
		}
		
		monsterManager.renderOverlay(g, ts.camera.zoom);

		ts.renderFog(g);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {

		Input input = container.getInput();

		renderWorld(g);

		miniMap.render(g);

		messenger.render(g, container.getHeight());

		// Header
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, container.getWidth(), header_height);
		g.setColor(Color.gray);
		g.drawRect(0, 0, container.getWidth(), header_height);
		g.setColor(Color.black);
		g.drawString(name + " - " + gs.getDate().toString("dd/MM/yyyy HH:mm"),
				5, h_y + header_pad);
		g.drawString("" + Math.round(gs.getTimeSurvived() / 60)
				+ " hour(s) since incident", 5, h_y + header_pad + 18);

		// Footer
		g.setColor(Color.gray);
		g.drawRect(0, footer_y, container.getWidth(), footer_height);
		g.setColor(Color.lightGray);
		g.fillRect(0, footer_y, container.getWidth(), footer_height);

		// Quest bar
		g.setColor(Color.gray);
		g.drawRect(0, quest_bar_y, container.getWidth(), quest_bar_height);
		g.setColor(Color.lightGray);
		g.fillRect(0, quest_bar_y, container.getWidth(), quest_bar_height);
		
		// Action bar
		g.setColor(Color.gray);
		g.drawRect(0, action_bar_y, container.getWidth(), action_bar_height);
		g.setColor(Color.lightGray);
		g.fillRect(0, action_bar_y, container.getWidth(), action_bar_height);

		// Draw agents
		g.setColor(Color.lightGray);
		g.fillRect(ag_x, ag_y, agent_bar_width, agent_bar_height);
		// int agent_zone_x = 500;
		
		Vector<BaseBuilding> currentBuildingOptions = buildingManager.currentBuildingOptions(currentBuilding);
		
		ArrayList<Rectangle> buildingZones = new ArrayList<Rectangle>();
		ArrayList<BaseBuilding> validBuildings = new ArrayList<BaseBuilding>();
		
		if (currentBuildingOptions != null)
		{
			for (int i =0; i< currentBuildingOptions.size(); i++)
			{
				BaseBuilding building = currentBuildingOptions.get(i);
				int y = ag_y + i * 60;
				int pad = 7;
				g.setColor(Color.black);
				g.drawString(building.getName(), ag_x + pad+70, y + pad);
			
				building.renderBuilding( ag_x, y,50,50);
				
				Rectangle rect = new Rectangle(ag_x, y,50,50);
				buildingZones.add(rect);
				validBuildings.add(building);
			}
		}
		

		
		
		
		List<Agent> agents = gs.getAgents();
		List<Rectangle> agentZones = new ArrayList<Rectangle>();

		int quest_zone_x = 0;
		int quest_label_y = quest_bar_y;
		int quest_y_pad = 20;
		
		
		
		// Draw quests

		ArrayList<Rectangle> questZones = new ArrayList<Rectangle>();
		ArrayList<Quest> validQuests = new ArrayList<Quest>();
		GodType[] order = {GodType.THOR, GodType.FREYA, GodType.HEL, GodType.LOKI, GodType.TRIBE};
		for(int i=0; i<order.length; i++) {
			God god = godManager.getGod(order[i]);
			int x = quest_zone_x + (i * quest_width) + (i*quest_bar_pad);
			g.setColor(Color.white);
			g.drawString(god.getName(), x, quest_label_y);
		}
		
		int quest_button_height = 20;
		int quest_button_y = quest_bar_y+quest_bar_height-quest_button_height - 5;
		for(int i=0; i<order.length; i++) {
			int x = quest_zone_x + (i * quest_width) + (i*quest_bar_pad);
			g.setColor(Color.black);
			g.fillRect(x, quest_bar_y+quest_y_pad, quest_width, quest_bar_height-quest_y_pad);
			
			if(questManager.hasQuest(order[i])) {
				Quest quest = questManager.getQuest(order[i]);
				g.setColor(Color.white);
				g.drawString(quest.getQuestName(), x+5, quest_bar_y+quest_y_pad+5);

				if(quest.canComplete()) {
					// Draw Complete button
					String name = "Complete";
					int t_w = g.getFont().getWidth(name);
					int t_h = g.getFont().getHeight(name);
					int b_w = t_w + 6;
					int b_h = quest_button_height;
					int t_y = (quest_button_height - t_h) / 2;
					int t_x = (b_w - t_w) / 2;
	
					int b_x = x + (quest_width-b_w)/2;
					
					g.setColor(Color.darkGray);
					g.drawRect(b_x, quest_button_y, b_w, b_h);
					g.setColor(Color.lightGray);
					g.fillRect(b_x, quest_button_y, b_w, b_h);
					g.setColor(Color.black);
					g.drawString(name, b_x + t_x, quest_button_y + t_y);
					
					Rectangle zone = new Rectangle(b_x, quest_button_y, b_w, b_h);
					questZones.add(zone);
					validQuests.add(quest);
				}
			}
			
		}
		
		// Draw inventory
		int inventory_zone_x = 10;
		List<ItemType> items = gs.getInventory().getItems();
		List<Rectangle> inventoryZones = new ArrayList<Rectangle>();
		g.setColor(Color.black);
		for (int i = 0; i < ItemType.values().length; i++) {

			int x = inventory_zone_x + (i * f_h) + (i * 6);
			if (i < items.size()) {
				itemImages.get(items.get(i)).draw(x, f_y, f_h, f_h);

				Rectangle rect = new Rectangle(x, f_y, f_h, f_h);
				inventoryZones.add(rect);
				int count = gs.getInventory().getItemCount(items.get(i));
				if (count > 1) {
					int w = g.getFont().getWidth("" + count);
					int h = g.getFont().getHeight("" + count);
					g.setColor(Color.cyan);
					g.fillRect(x + f_h - w, f_y + f_h - h, w, h);
					g.setColor(Color.black);
					g.drawString("" + count, x + f_h - w, f_y + f_h - h);
				}
			} else {
				g.setColor(Color.black);
				g.fillRect(x, f_y, f_h, f_h);
			}
			g.setColor(Color.darkGray);
			g.drawRect(x - 1, f_y - 1, f_h + 2, f_h + 2);
		}
		// Draw toolTips
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		for (Rectangle r : inventoryZones) {
			if (r.contains(mouseX, mouseY)) {
				g.setColor(Color.black);
				g.drawString(
						items.get(inventoryZones.indexOf(r)).name()
								+ " : "
								+ getDescription(items.get(inventoryZones
										.indexOf(r))),
						container.getWidth() - 400, container.getHeight() - 40);
			}
		}

		ArrayList<Rectangle> actionZones = new ArrayList<Rectangle>();
		List<BaseAction> validActions = new ArrayList<BaseAction>();
		if (selectedAgent != null) {

			// Draw Action Bar (TM)
			int x = 5;
			validActions = recipeBook.getValidActions(selectedAgent);

			// Detect F-Key presses
			BaseAction actionHotKeyPressed = null;
			for (int i = 0; i < validActions.size(); i++) {
				BaseAction action = validActions.get(i);
				String name = action.getName();

				if ((i == 0 && (input.isKeyDown(Input.KEY_F1) || input
						.isKeyDown(Input.KEY_Z)))
						|| (i == 1 && (input.isKeyDown(Input.KEY_F2) || input
								.isKeyDown(Input.KEY_X)))
						|| (i == 2 && (input.isKeyDown(Input.KEY_F3) || input
								.isKeyDown(Input.KEY_C)))
						|| (i == 3 && (input.isKeyDown(Input.KEY_F4) || input
								.isKeyDown(Input.KEY_V)))
						|| (i == 4 && (input.isKeyDown(Input.KEY_F5) || input
								.isKeyDown(Input.KEY_B)))
						|| (i == 5 && (input.isKeyDown(Input.KEY_F6) || input
								.isKeyDown(Input.KEY_N)))
						|| (i == 6 && (input.isKeyDown(Input.KEY_F7) || input
								.isKeyDown(Input.KEY_M)))
						|| (i == 7 && (input.isKeyDown(Input.KEY_F8) || input
								.isKeyDown(Input.KEY_COMMA)))
						|| (i == 8 && (input.isKeyDown(Input.KEY_F9) || input
								.isKeyDown(Input.KEY_STOP)))
						|| (i == 9 && (input.isKeyDown(Input.KEY_F10) || input
								.isKeyDown(Input.KEY_SLASH)))
						|| (i == 10 && input.isKeyDown(Input.KEY_F11))
						|| (i == 11 && input.isKeyDown(Input.KEY_F12))) {
					if (actionKeyPressed != i) {
						actionKeyPressed = i;
						actionHotKeyPressed = action;
					}
				} else {
					actionKeyPressed = -1;
					actionHotKeyPressed = null;
				}

				int t_w = g.getFont().getWidth(name);
				int t_h = g.getFont().getHeight(name);
				int b_w = t_w + 6;
				int b_h = a_h;
				int t_y = (a_h - t_h) / 2;
				int t_x = (b_w - t_w) / 2;

				g.setColor(Color.darkGray);
				g.drawRect(x, a_y, b_w, b_h);
				g.setColor(Color.lightGray);
				g.fillRect(x, a_y, b_w, b_h);
				g.setColor(Color.black);
				g.drawString(name, x + t_x, a_y + t_y);

				Rectangle zone = new Rectangle(x, a_y, b_w, b_h);
				actionZones.add(zone);
				x += (b_w + 2);
			}

			// If a key is pressed, run its action here
			if (actionHotKeyPressed != null) {
				performAction(actionHotKeyPressed);
			}
		}
		
		if (headerRect.contains(mouseX, mouseY)
				|| footerRect.contains(mouseX, mouseY)
				|| actionRect.contains(mouseX, mouseY)
				|| agentRect.contains(mouseX, mouseY)
				|| questRect.contains(mouseX, mouseY)) {
		} else if (miniMap.isWithin(mouseX, mouseY)) {
		}else{
			if (currentDragging != null) {
				currentDragging.renderBuilding(mouseX-currentDragging.width/2, mouseY -currentDragging.height/2, currentDragging.width, currentDragging.height);
			}			
		}
		
		if (input.isMousePressed(1) || input.isMousePressed(2)) {
			currentDragging = null;
		}

		if (input.isMousePressed(0)) {
			mouseX = input.getMouseX();
			mouseY = input.getMouseY();

			if (headerRect.contains(mouseX, mouseY)
					|| footerRect.contains(mouseX, mouseY)
					|| actionRect.contains(mouseX, mouseY)
					|| agentRect.contains(mouseX, mouseY)
					|| questRect.contains(mouseX, mouseY)) {
			
				// Check the UI elements
				
				
				//Building selection
				mouseX = input.getMouseX();
				mouseY = input.getMouseY();
				for (Rectangle r : buildingZones) {
					if (r.contains(mouseX, mouseY)) {
						currentDragging = validBuildings.get(buildingZones.indexOf(r));
					}
				}
				
				// Player selection
				for (int i = 0; i < agentZones.size(); i++) {
					Rectangle agentZone = agentZones.get(i);
					if (agentZone.contains(mouseX, mouseY)) {
						selectedAgent = agents.get(i);
					}
				}

				// Dead agents can't interact with inventory etc.
				if (selectedAgent.getState() != AgentState.DEAD) {
					// for (int i = 0; i < inventoryZones.size(); i++) {
					// Rectangle inventoryZone = inventoryZones.get(i);
					// if (inventoryZone.contains(mouseX, mouseY)) {
					// if (selectedItems.contains(items.get(i))) {
					// selectedItems.remove(items.get(i));
					// } else {
					// selectedItems.add(items.get(i));
					// }
					// }
					// }

					for (int i = 0; i < actionZones.size(); i++) {
						Rectangle actionZone = actionZones.get(i);
						if (actionZone.contains(mouseX, mouseY)) {
							BaseAction action = validActions.get(i);
							performAction(action);
						}
					}
					
					for (int i = 0; i < questZones.size(); i++) {
						Rectangle questZone = questZones.get(i);
						if (questZone.contains(mouseX, mouseY)) {
							Quest quest = validQuests.get(i);
							System.out.println("Try to complete quest: "+quest.getQuestName());
							quest.complete();
						}
					}
				}

			} else if (miniMap.isWithin(mouseX, mouseY)) {
				miniMap.goTo(mouseX, mouseY);
			} else {

				if (currentDragging != null) {
					currentDragging.renderBuilding(mouseX-currentDragging.width/2, mouseY -currentDragging.height/2, currentDragging.width, currentDragging.height);
					
					Tile t = ts.getTileFromScreen(mouseX, mouseY);
					Building b = new Building(ts, currentDragging, new Vector2f(t.x,t.y));
					
					for(int x=b.base.getMinusXFootPrint(); x< b.base.getPlusXFootPrint()+1; x++) {
						for(int y=b.base.getMinusYFootPrint(); y< b.base.getPlusYFootPrint()+1; y++) {
							Tile baseTile = ts.getTile(t.x +x, t.y+y);
							baseTile.addBuilding(b, false);
						}
					}
					
					t.addBuilding(b, true);
					
					buildingManager.addBuildingInPlay(b);
					
					currentDragging = null;
				}
				else {
					Tile t = ts.getTileFromScreen(mouseX, mouseY);
					if (t.building != null)
					{
						currentBuilding = t.building;	
					}
					else {
						// See if we are attacking a monster
						boolean monsterSelectionHappens = false;
						boolean playerSelectionHappens = false;
						Vector2f pos = ts.screenToWorldPos(mouseX, mouseY);
		
						// Rearranged these functions to ensure fighting monsters
						// overrides selecting and moving players
						if (selectedAgent != null) {
							for (int i = 0; i < monsterManager.monsters.size(); i++) {
								MonsterUI monster = monsterManager.monsters.get(i);
								float difX = monster.location.x - pos.x;
								float difY = monster.location.y - pos.y;
								float len = (float) Math.sqrt((difX * difX)
										+ (difY * difY));
								if (len < 0.5) {
									if (selectedAgent != null
											&& selectedAgent.getState() != AgentState.DEAD) {
										monster.agent.decHealth(20);
										if (monster.agent.getHealth() <= 0) {
											monster.agent.setState(AgentState.DEAD);
		//									gs.getInventory().addItem(ItemType.MEAT);
										}
		
										monsterSelectionHappens = true;
									}
								}
							}
						}
		
						if (!monsterSelectionHappens) {
							// This code handles mouse selection of other players
		
							for (int i = 0; i < players.size(); i++) {
								PlayerUI player = players.get(i);
								if (player.agent.getState() != AgentState.DEAD) {
									float difX = player.location.x - pos.x;
									float difY = player.location.y - pos.y;
									float len = (float) Math.sqrt((difX * difX)
											+ (difY * difY));
									if (len < 0.5) {
										selectedAgent = player.agent;
										playerSelectionHappens = true;
									}
								}
							}
						}
		
						if ((!playerSelectionHappens) && (!monsterSelectionHappens)) {
							if (selectedAgent != null
									&& selectedAgent.getState() != AgentState.DEAD) {
								if (selectedAgent.hasAction()) {
									selectedAgent.stopAction();
								}
								players.get(agents.indexOf(selectedAgent)).moveto(
										pos.x, pos.y);
								ts.getCamera().x = players.get(agents
										.indexOf(selectedAgent)).location.x;
								ts.getCamera().y = players.get(agents
										.indexOf(selectedAgent)).location.y;
								ts.getCamera().isFollowing = true;
							}
						}
					}
				}
			}
		}
	}

	private void performAction(BaseAction action) {
		int player_index = gs.getAgents().indexOf(selectedAgent);
		PlayerUI player = players.get(player_index);
		selectedAgent.startAction(action);
		messenger.addMessage(
				selectedAgent.getName() + " is " + action.getDescription(),
				Color.green, 6);

		ts.getTileFromWorld(player.location.x, player.location.y);
		action.onStart(selectedAgent);
	}

	private String getDeathMessage() {
		Random r = new Random();
		switch (r.nextInt(6)) {
		case 0:
			return " has died a slow and painful death...";
		case 1:
			return " has been impaled by a spider.";
		case 2:
			return "'s organs were strewn across the ground.";
		case 3:
			return "'s did something wrong. Then died. ";
		case 4:
			return " went for a very, very long nap.";
		case 5:
			return " lies still. Oh, so very tasty...";
		default:
			return " has died a slow and painful death...";
		}
	}

	private String getDescription(ItemType item) {
		switch (item) {
		case FOOD:
			return "Cheese";
		case STONE:
			return "Stone.";
		case GOLD:
			return "Gold";
		case METAL:
			return "Metal";
		case WOOD:
			return "Wood";
		default:
			return "";
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		boolean alive = false;

		List<Agent> agents = gs.getAgents();
		for (int i = 0; i < agents.size(); i++) {
			if (agents.get(i).getState() != AgentState.DEAD) {
				alive = true;
			}
		}

		if (!alive) {
			game.enterState(GameOver.STATE_OVER);
			game.getState(GameOver.STATE_OVER).init(container, game);
		}

		if (gs.isCompleted()) {
			game.enterState(GameWin.STATE_WIN);
			game.getState(GameWin.STATE_WIN).init(container, game);
		}

		float seconds = (float) (delta / 1000.0);
		updateCamera(container, seconds);
		for (PlayerUI player : players) {
			player.update(seconds);
		}
		
		for (Building building : buildingManager.getBuildingsInPlay()) {
			building.update(seconds);
		}
		
		
		monsterManager.update(seconds);
		ts.update(players, gs, seconds);
		gs.update(seconds);
		messenger.update(seconds);

		Input input = container.getInput();
		Agent newAgent = null;
		if (input.isKeyDown(Input.KEY_0)) {
			newAgent = agents.get(9);
		}
		if (input.isKeyDown(Input.KEY_1)) {
			newAgent = agents.get(0);
		}
		if (input.isKeyDown(Input.KEY_2)) {
			newAgent = agents.get(1);
		}
		if (input.isKeyDown(Input.KEY_3)) {
			newAgent = agents.get(2);
		}
		if (input.isKeyDown(Input.KEY_4)) {
			newAgent = agents.get(3);
		}
		if (input.isKeyDown(Input.KEY_5)) {
			newAgent = agents.get(4);
		}
		if (input.isKeyDown(Input.KEY_6)) {
			newAgent = agents.get(5);
		}
		if (input.isKeyDown(Input.KEY_7)) {
			newAgent = agents.get(6);
		}
		if (input.isKeyDown(Input.KEY_8)) {
			newAgent = agents.get(7);
		}
		if (input.isKeyDown(Input.KEY_9)) {
			newAgent = agents.get(8);
		}

		if (newAgent != null) {
			selectedAgent = newAgent;
			ts.getCamera().isFollowing = true;
		}

		for (int i = 0; i < players.size(); i++) {
			PlayerUI player = players.get(i);
			Agent agent = agents.get(i);
			AgentState state = agent.getState();
			
			if (agent.hasAction() && agent.haveFinishedAction()) {
				BaseAction action = agent.getAction();
				if (state != AgentState.DEAD) {
					action.onComplete(agent);
					if (action.canPerform(agent)) {
						agent.startAction(action);
						action.onStart(agent);
					} else {
						agent.stopAction();
					}
				}
			}

			boolean atDestination = players.get(i).atDestination;

			if (state == AgentState.WALKING && atDestination) {
				agents.get(i).setState(AgentState.STANDING);
			}
			if (state != AgentState.WALKING && !atDestination) {
				agents.get(i).setState(AgentState.WALKING);
			}
			if (state == AgentState.DEAD) {
				Tile tile = ts.getTileFromWorld(player.location.x,
						player.location.y);
				if (!agent.hasPlacedCorpse()) {
					tile.addSprite(SpriteType.CORPSE);
					messenger.addMessage(agent.getName() + getDeathMessage(),
							Color.red, 8);
					agent.setPlacedCorpse(true);
				}
			}
		}

		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			container.exit();
		}
	}

	private void updateCamera(GameContainer container, float delta) {
		Input input = container.getInput();

		int dWheel = Mouse.getDWheel();
		if (dWheel < 0)
			ts.zoom(dWheel * delta * 0.06f);
		else if (dWheel > 0) {
			ts.zoom(dWheel * delta * 0.06f);
		}

		if (input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A)) {
			ts.getCamera().move(-20 / ts.camera.zoom * delta, 0);
			ts.getCamera().isFollowing = false;
		}

		if (input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W)) {
			ts.getCamera().move(0, -20 / ts.camera.zoom * delta);
			ts.getCamera().isFollowing = false;
		}

		if (input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D)) {
			ts.getCamera().move(20 / ts.camera.zoom * delta, 0);
			ts.getCamera().isFollowing = false;
		}

		if (input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S)) {
			ts.getCamera().move(0, 20 / ts.camera.zoom * delta);
			ts.getCamera().isFollowing = false;
		}

		if (ts.getCamera().x < 0)
			ts.getCamera().x = 0;

		if (ts.getCamera().y < 0)
			ts.getCamera().y = 0;

		if (ts.getCamera().x > ts.size)
			ts.getCamera().x = ts.size;

		if (ts.getCamera().y > ts.size)
			ts.getCamera().y = ts.size;

		if (ts.getCamera().isFollowing) {
			for (PlayerUI p : players) {
				if (p.agent == selectedAgent) {
					ts.getCamera().x = p.location.x;
					ts.getCamera().y = p.location.y;
					// if(!SoundManager.isPlaying(SoundManager.walk))
					// SoundManager.playSound(SoundManager.walk, 0.1f, true);
					break;
				}
			}
		} else {
			// SoundManager.stopSound(SoundManager.walk);
		}

		for (PlayerUI p : players) {
			if (p.agent == selectedAgent) {
				if (p.atDestination) {
					// SoundManager.stopSound(SoundManager.walk);
					break;
				}
			}
		}
	}

	@Override
	public int getID() {
		return STATE_PLAY;
	}

	@Override
	public void reachedDestination(PlayerUI pui, float x, float y) {

		// Tile reachedTile = ts.getTileFromWorld(x, y);
		// if (reachedTile.id == TileId.GRASS) {
		// gs.addItem(new Grass());
		// }
	}

}
