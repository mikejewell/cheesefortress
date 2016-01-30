package cheese.model;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.SlickException;

import deserted.model.Agent;
import deserted.player.PlayerReachedDestinationEvent;
import deserted.player.PlayerUI;
import deserted.tilesystem.TileSystem;

public class PlayerManager {

	private List<PlayerUI> players;
	
	public PlayerManager() {
		setPlayers(new ArrayList<PlayerUI>());
	}
	
	public PlayerUI addPlayer(PlayerReachedDestinationEvent listener) throws SlickException {
		Agent agent = new Agent();
		PlayerUI pui = new PlayerUI(agent);
		pui.addReachedDestinationEvent(listener);
		getPlayers().add(pui);
		return pui;
	}

	public List<PlayerUI> getPlayers() {
		return players;
	}

	public List<Agent> getAgents() {
		ArrayList<Agent> agents = new ArrayList<Agent>();
		for(PlayerUI playerUI: players) {
			agents.add(playerUI.agent);
		}
		return agents;
	}
	
	public void setPlayers(List<PlayerUI> players) {
		this.players = players;
	}
}
