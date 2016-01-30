package deserted.player;

import java.util.Enumeration;
import java.util.Random;
import java.util.Vector;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import deserted.model.Agent;
import deserted.model.AgentState;
import deserted.model.GameSession;
import deserted.tilesystem.Tile;
import deserted.tilesystem.TileSystem;
import deserted.tilesystem.TileSystem.TileId;


public class PlayerUI {

	TileSystem ts;
	public Agent agent;
	
	public Vector2f location = new Vector2f(34,29);
	Vector2f destination = new Vector2f(34,29);
	Vector<Tile> destinations = new Vector<Tile>();
	public boolean atDestination = true;
	
	
	float tileSizeM = 50.0f;			//Tile is 100m across
	float gameSpeed = 3600/30;			//Game is 30s is one hour 3600s is 30s => 120s per 1s
	Vector<Vector<Image>> playerImages = null;
	Vector<Image> playerImages2 = null;
	
	float lastValue = 100.0f;
	double lastDecrementTime=0.0;
	boolean showHealth = false;
	
	public PlayerUI(Agent agentIn, TileSystem tsIn, Vector2f nearbyLocation) throws SlickException
	{
		agent = agentIn;
		ts = tsIn;
		
		imageWidth = 64;
		imageHeight = 64;
		Image playerImage = null;
		Random r = new Random();
		double d = r.nextDouble();
		if (d < 0.3) playerImage = new Image("images/player/before.png");
		else if (d < 0.6) playerImage = new Image("images/player/FBI_walk_cycle.png");
		else playerImage = new Image("images/player/professor_walk_cycle_no_hat.png");
		
		playerImages = new Vector<Vector<Image>>();
		for(int y=0;y<4; y++)
		{
			playerImages.add(new Vector<Image>());
			for(int x=0; x<9; x++)
			{
				int imageX = x*64;
				int imageY = y*64;
				playerImages.get(y).add(playerImage.getSubImage(imageX, imageY, imageX+64, imageY+64));
			}
		}
		
		//Random Start location
		location = randomLocation(nearbyLocation);
		agentIn.setTile(ts.getTileFromWorld(location.x, location.y));
	}
	
	private Vector2f randomLocation(Vector2f nearbyLocation)
	{
		Random randomGenerator = new Random();
		while(true)
		{
			int x = (int)nearbyLocation.x+randomGenerator.nextInt(40)-20;
			int y = (int)nearbyLocation.y+randomGenerator.nextInt(40)-20;
			if (x>=0 && y>=0 && x< ts.getSize() && y < ts.getSize())
			{
				Tile tile = ts.getTile(x, y);
				if (tile.id == TileId.GRASS) return new Vector2f(x,y);
			}
		}
	}
	
	public void moveto(float destinationX, float destinationY){
		if(destinationX < 0) destinationX = 0;
		if(destinationX >= ts.size) destinationX = ts.size-1;
		if(destinationY < 0) destinationY = 0;
		if(destinationY >= ts.size) destinationY = ts.size-1;
		atDestination = false;
		PathFinder p = new PathFinder(ts, location);
		destination = new Vector2f(destinationX, destinationY);
		destinations = p.findPath( destination);
	}
	
	int imageWidth = 80;
	int imageHeight = 100;
	float animationFrame = 0;
	float angle = 0;
	
	int direction = 0;
	
	public Image getPlayerImage(int direction)
	{
		if (animationFrame >= 9) animationFrame = 0;
		if (atDestination) animationFrame = 0;
		 return playerImages.get(direction).get((int)animationFrame);
	}
	
	public void render(Graphics g, float scale){
		if(agent.getState() ==  AgentState.DEAD) return;
		
		Vector2f screenLocation = ts.worldToScreenPos(location.x, location.y);
		
		
		g.setColor(new Color(255,0,0));

	if (destinations.size()>1) 
		{
			Vector2f lookPointA = new Vector2f(destinations.get(destinations.size()-2).x,destinations.get(destinations.size()-2).y);
			Vector2f lookPointB = new Vector2f(destinations.get(destinations.size()-1).x,destinations.get(destinations.size()-1).y);
			float xdif = lookPointB.x - lookPointA.x;
			float ydif = lookPointB.y - lookPointA.y;
			if (xdif >0 && ydif==0) direction = 1;
			if (xdif <0 && ydif==0) direction = 3;
			if (xdif ==0 && ydif>0) direction = 0;
			if (xdif ==0 && ydif<0) direction = 2;
	
			if (xdif >0 && ydif>0) direction = 1;
			if (xdif <0 && ydif>0) direction = 3;
			if (xdif >0 && ydif<0) direction = 1;
			if (xdif <0 && ydif<0) direction = 3;
			
			

			
			
		}
		Image realPlayer = getPlayerImage(direction);

		realPlayer.draw(screenLocation.x-15*scale,screenLocation.y-15*scale,
				screenLocation.x+15*scale,screenLocation.y+15*scale,0,0,imageWidth, imageHeight);

		

	
		
		g.setColor(new Color(0,0,255));
		Vector2f lastPoint = ts.worldToScreenPos(location.x, location.y);
		for(int i =destinations.size()-1; i>=0 ; i--)
		{
			Tile dest = destinations.get(i);
			Vector2f pos = new Vector2f(dest.x+0.5f, dest.y+0.5f);
            Vector2f destPos = ts.worldToScreenPos(pos.x, pos.y);
            g.drawLine(destPos.x, destPos.y, lastPoint.x, lastPoint.y);
            lastPoint = destPos;
		}
	}
	
	public void renderOverlay(Graphics g, float scale)
	{
		//Dont bother rendering overlays when the player is dead
		if (agent.getState()==AgentState.DEAD) return;
		
		Vector2f screenLocation = ts.worldToScreenPos(location.x, location.y);
		
		if (agent.hasAction())
		{
			g.setColor(new Color(0,0,255,100));
			float value =agent.getRatioOfActionRemaining() * 360;
			g.fillArc(screenLocation.x-30*scale,screenLocation.y-30*scale,60.0f*scale,60.0f*scale,270.0f, value+270.0f);
		}
		
		if (showHealth)
		{
			float health = agent.getHealth();
			if (health < 20)
				g.setColor(new Color(255,0,0.0f,0.5f));
			else
				g.setColor(new Color(0,255,0.0f,0.5f));
			
			float progress = health/100.0f;
			g.fillRect(screenLocation.x-20*scale,  screenLocation.y-30*scale, 40*scale * progress, 10*scale);
			g.drawRect(screenLocation.x-20*scale,  screenLocation.y-30*scale, 40*scale, 10*scale);
		}
	}
	
	private void updateHealthDisplay()
	{
		float health = agent.getHealth();
		agent.healthDelta = health - lastValue;
		if (health < lastValue)
		{
			lastValue = health;
			showHealth = true;
			GameSession gs = GameSession.getInstance();
			lastDecrementTime = gs.getTimeSurvived();
		}
		if (showHealth)
		{
			GameSession gs = GameSession.getInstance();
			if ((gs.getTimeSurvived()- lastDecrementTime) > 5) 
				showHealth = false;
		}
	}
	
	public void update(float deltaTime) {
		updateHealthDisplay();
		
		if (agent.getState() ==  AgentState.DEAD) return;
			
		if (atDestination) return;
		
		
		
		animationFrame += deltaTime*10;
		//Some basic movement code - a bit elaborate tbh
		
		
		Vector2f currentDestination = destination;
		Tile destTile = null;
		if (destinations.size() > 1)
		{
		    destTile = destinations.get(destinations.size()-1);
			currentDestination = new Vector2f(destTile.x+0.5f, destTile.y+0.5f);
		}
		
		//average walk speed 1.4m per second
		float playerWalkSpeedMS = 1.4f;
		if (ts.getTileFromWorld(location.x, location.y).id == TileId.WATER)
		{
			playerWalkSpeedMS = 0.3f;
		}
		if (ts.getTileFromWorld(location.x, location.y).id == TileId.ROCK)
		{
			playerWalkSpeedMS = 1f;
		}
		if (ts.getTileFromWorld(location.x, location.y).id == TileId.SNOW)
		{
			playerWalkSpeedMS = 0.6f;
		}
		
		float deltaTimeS = (float)deltaTime;
		float distanceTravelled = (deltaTimeS * gameSpeed * playerWalkSpeedMS)/ tileSizeM ;
		
		//Move the player
		Vector2f directionVec = new Vector2f(currentDestination.x - location.x, currentDestination.y-location.y);
		float vecLength = (float)Math.sqrt((directionVec.x * directionVec.x) + (directionVec.y * directionVec.y));
		if (vecLength > distanceTravelled)
		{
			Vector2f directionVecUnit = new Vector2f(directionVec.x / vecLength, directionVec.y / vecLength);
			Vector2f directionInStep = new Vector2f(directionVecUnit.x * distanceTravelled, directionVecUnit.y * distanceTravelled);
		
			Vector2f nextLocation = new Vector2f(location.x + directionInStep.x, location.y+directionInStep.y);
			location = nextLocation;
		}
		else
		{
			location = currentDestination;
			if (destTile != null)
			{
				destinations.removeElement(destTile);
			}
			else
			{
				destinations.clear();
				//If the last step then just set the location and alert listeners
				location = destination;
				atDestination = true;
				firePlayerReachedDestinationEvent();
			}
		
		}
		

		Tile tile = ts.getTileFromWorld(location.x, location.y);
		agent.setTile(tile);
		
		
	}
	
    protected Vector<PlayerReachedDestinationEvent> _listeners;
     
    public void addReachedDestinationEvent(PlayerReachedDestinationEvent listener)
    {
        if (_listeners == null)
            _listeners = new Vector<PlayerReachedDestinationEvent>();
              
        _listeners.addElement(listener);
    }
    
    protected void firePlayerReachedDestinationEvent()
    {
        if (_listeners != null && !_listeners.isEmpty())
        {
            Enumeration<PlayerReachedDestinationEvent> e = _listeners.elements();
            while (e.hasMoreElements())
            {
            	PlayerReachedDestinationEvent ev = e.nextElement();
                ev.reachedDestination(this, destination.x, destination.y);;
            }
        }
    }
     



  
}
