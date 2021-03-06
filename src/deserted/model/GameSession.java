package deserted.model;

import java.util.LinkedList;

import org.joda.time.LocalDateTime;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import cheese.model.PlayerManager;
import cheese.model.building.BuildingManager;
import cheese.model.god.Catastrophe;
import cheese.model.god.GodManager;
import cheese.model.quest.QuestManager;
import deserted.Messenger;
import deserted.model.item.ItemType;
import deserted.player.PlayerReachedDestinationEvent;

public class GameSession {
	private static GameSession instance;

	// private boolean walking = false;
	// Play time in seconds
	private double gameTimer;
	// Game time in minutes
	private double timeSurvived;
	// When we 'crashed'
	private LocalDateTime crashDate;
	private boolean completed;
	private int completionType;
	private Inventory inventory;
	private PlayerReachedDestinationEvent play;


	private BuildingManager buildingManager;
	private QuestManager questManager;
	private PlayerManager playerManager;
	private GodManager godManager;
	
	private Messenger messenger;
	
	private double probabilitySettler = 1.0;
	private double probabilityFood = 1.0;

	private LinkedList<Catastrophe> catastrophes;
	
	private GameSession() {
		this.setCompleted(false);
		this.setCompletionType(0);
		this.inventory = new Inventory();
		this.catastrophes = new LinkedList<Catastrophe>();

		this.setPlayerManager(new PlayerManager());

		inventory.addItem(ItemType.FOOD, GameConfig.STARTING_FOOD);
		inventory.addItem(ItemType.WOOD, GameConfig.STARTING_WOOD);
		inventory.addItem(ItemType.METAL, GameConfig.STARTING_METAL);
		inventory.addItem(ItemType.STONE, GameConfig.STARTING_STONE);

		this.gameTimer = 0;
		this.timeSurvived = 0;
		int year = (int) (1009 + (Math.round(Math.random() * 5) - 10));
		int month = (int) Math.ceil(Math.random() * 12);
		int day = (int) Math.ceil(Math.random() * 27);
		int hour = (int) (Math.random() * 24);
		int minute = (int) (Math.random() * 60);
		this.crashDate = new LocalDateTime(year, month, day, hour, minute);

//		for (int i = 0; i < GameConfig.NUMBER_AGENTS; i++) {
//			getAgents().add(new Agent());
//		}

		try {
			this.setBuildingManager(new BuildingManager());
			this.setQuestManager(new QuestManager());
			this.setGodManager(new GodManager());
		} catch (SlickException se) {
			System.err.println("Slick exception :(");
		}
	}

	public static GameSession getInstance() {
		if (instance == null) {
			instance = new GameSession();
		}
		return instance;
	}

	public void update(float delta) {
		this.gameTimer += delta;
		this.timeSurvived = gameTimer * GameConfig.MINS_PER_SEC;
//
//		// Update agent stats
//		for (Agent agent : agents) {
//			agent.update(delta);
//		}
	}

	public double getTimeSurvived() {
		return this.timeSurvived;
	}

	public LocalDateTime getDate() {
		return this.crashDate.plusMinutes((int) Math.floor(this.timeSurvived));
	}


	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public int getCompletionType() {
		return completionType;
	}

	public void setCompletionType(int completionType) {
		this.completionType = completionType;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public BuildingManager getBuildingManager() {
		return buildingManager;
	}

	public void setBuildingManager(BuildingManager buildingManager) {
		this.buildingManager = buildingManager;
	}

	public QuestManager getQuestManager() {
		return questManager;
	}

	public void setQuestManager(QuestManager questManager) {
		this.questManager = questManager;
	}

	public PlayerManager getPlayerManager() {
		return playerManager;
	}

	public void setPlayerManager(PlayerManager playerManager) {
		this.playerManager = playerManager;
	}

	public void setPlay(PlayerReachedDestinationEvent play) {
		this.play = play;
	}
	
	public PlayerReachedDestinationEvent getPlay() {
		return this.play;
	}

	public double getProbabilitySettler() {
		return probabilitySettler;
	}

	public void setProbabilitySettler(double probabilitySettler) {
		this.probabilitySettler = probabilitySettler;
	}

	public double getProbabilityFood() {
		return probabilityFood;
	}

	public void setProbabilityFood(double probabilityFood) {
		this.probabilityFood = probabilityFood;
	}

	public GodManager getGodManager() {
		return godManager;
	}

	public void setGodManager(GodManager godManager) {
		this.godManager = godManager;
	}
	
	public void queueCatastrophe(Catastrophe c){
		catastrophes.add(c);
	}
	
	public void renderCatastrophes(Graphics g){
		for(Catastrophe c : catastrophes){
			c.render(g);
		}
		catastrophes.clear();
	}
	
	public void setMessenger(Messenger messenger){
		this.messenger = messenger;
	}
	
	public Messenger getMessenger(){
		return messenger;
	}
}
