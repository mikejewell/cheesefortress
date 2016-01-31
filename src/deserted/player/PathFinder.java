package deserted.player;
import java.util.Vector;

import org.lwjgl.util.vector.Vector2f;

import deserted.tilesystem.Tile;
import deserted.tilesystem.TileSystem;
import deserted.tilesystem.TileSystem.TileId;

public class PathFinder
	{
  	public int distances[][];
  	private int size;
  	private TileSystem ts;
  	private Tile startTile;
  	
  	public PathFinder(TileSystem tsIn, Vector2f start)
  	{
  		ts = tsIn;
  		size = ts.size;
  		distances = new int[size][size];
  		
  		for(int x = 0; x < size; x++){
              for(int y = 0; y < size; y++){
              	distances[x][y] = 9999;
              }
  		}
  		
  	    startTile = ts.getTileFromWorld(start.x,start.y);
  		
  		if (startTile != null)
  			setDistances(startTile, 0);
  	}
  
  	public Vector<Tile> findPath(Vector2f end)
  	{
  		Tile endTile = ts.getTileFromWorld(end.x, end.y);
 
  		if (startTile == null || endTile == null)
  			return new Vector<Tile>();
  		
  		if (startTile.x == endTile.x && startTile.y == endTile.y) return new Vector<Tile>();
  		
  		Vector<Tile> tiles = getPath(startTile, endTile);
  		
  		return tiles;
  	}
  	
  	public Vector<Tile> findPath(Vector2f start, Vector2f end)
  	{
  		Tile startTile = ts.getTileFromWorld(start.x,start.y);
  		Tile endTile = ts.getTileFromWorld(end.x, end.y);
 
  		if (startTile == null)
  			throw new IllegalArgumentException("Start tile is nothing !!!");
  		
  		if (endTile == null)
  			throw new IllegalArgumentException("End tile is nothing !!!");
  		
  		if (startTile.x == endTile.x && startTile.y == endTile.y) return new Vector<Tile>();
  		
  		setDistances(startTile, 0);
  		
  		Vector<Tile> tiles = getPath(startTile, endTile);
  		
  		return tiles;
  	}
  	
  	private Vector<Tile> getPath(Tile startTile, Tile endTile)
  	{
  		Vector<Tile> tiles = new Vector<Tile>();
  		
  		Tile currentTile = endTile;
  		do
  		{
  			tiles.add(currentTile);
  		    currentTile = getNextTile(currentTile);
  		    if (currentTile == null) return tiles;
  		    
  		   // System.out.println(currentTile.x + ", " + currentTile.y + " - " + distances[currentTile.x][currentTile.y]);
  		    if (currentTile == startTile) return tiles;
  		}while (true);
  	}

  	private Tile getNextTile(Tile currentTile)
  	{
  		int min = 9999;
  		Tile minTile = null;
  		if (currentTile.cornerX >0)
  		{
  			Tile tile = ts.getTile(currentTile.cornerX-1, currentTile.cornerY);
  			int dist = distances[tile.cornerX][tile.cornerY];
  			if (dist < min)
  			{
  				 min=dist;
  				minTile = tile;
  			}
  		}
  		if (currentTile.cornerY >0)
  		{
  			Tile tile = ts.getTile(currentTile.cornerX, currentTile.cornerY-1);
  			int dist = distances[tile.cornerX][tile.cornerY];
  			if (dist < min)
  			{
  				 min=dist;
  				minTile = tile;
  			}
  		}
  		if (currentTile.cornerX >0 && currentTile.cornerY >0)
  		{
  			Tile tile = ts.getTile(currentTile.cornerX-1, currentTile.cornerY-1);
  			int dist = distances[tile.cornerX][tile.cornerY];
  			if (dist < min)
  			{
  				 min=dist;
  				minTile = tile;
  			}
  		}
  		if (currentTile.cornerX < size-2)
  		{
  			Tile tile = ts.getTile(currentTile.cornerX+1, currentTile.cornerY);
  			int dist = distances[tile.cornerX][tile.cornerY];
  			if (dist < min)
  			{
  				 min=dist;
  				minTile = tile;
  			}
  		}
  		if (currentTile.cornerY < size-2)
  		{
  			Tile tile = ts.getTile(currentTile.cornerX, currentTile.cornerY+1);
  			int dist = distances[tile.cornerX][tile.cornerY];
  			if (dist < min)
  			{
  				 min=dist;
  				minTile = tile;
  			}
  		}
  		if (currentTile.cornerX < size-2 && currentTile.y < size-2)
  		{
  			Tile tile = ts.getTile(currentTile.cornerX+1, currentTile.cornerY+1);
  			int dist = distances[tile.cornerX][tile.cornerY];
  			if (dist < min)
  			{
  				 min=dist;
  				minTile = tile;
  			}
  		}
  		if (currentTile.cornerX >0 && currentTile.cornerY < size-2)
  		{
  			Tile tile = ts.getTile(currentTile.cornerX-1, currentTile.cornerY+1);
  			int dist = distances[tile.cornerX][tile.cornerY];
  			if (dist < min)
  			{
  				 min=dist;
  				minTile = tile;
  			}
  		}
  		if (currentTile.cornerX < size-2 && currentTile.cornerY >0)
  		{
  			Tile tile = ts.getTile(currentTile.cornerX+1, currentTile.cornerY-1);
  			int dist = distances[tile.cornerX][tile.cornerY];
  			if (dist < min)
  			{
  				 min=dist;
  				minTile = tile;
  			}
  		}
  		return minTile;
  	}
  	
  	private void setDistances(Tile startTile, int distance)
  	{
  		distances[startTile.cornerX][startTile.cornerY] = distance;
  		if (startTile.cornerX >0)
  		{
  			Tile currentDest = ts.getTile(startTile.cornerX-1, startTile.cornerY);
  			int moveValue = getTileMoveAbility(currentDest);
  			int currentValue = distance + moveValue;
  			if (currentValue < distances[currentDest.cornerX][currentDest.cornerY])
  				setDistances(currentDest, currentValue);
  		}
  		if (startTile.y >0)
  		{
  			Tile currentDest = ts.getTile(startTile.cornerX, startTile.cornerY-1);
  			int moveValue = getTileMoveAbility(currentDest);
  			int currentValue = distance + moveValue;
  			if (currentValue < distances[currentDest.cornerX][currentDest.cornerY])
  				setDistances(currentDest, currentValue);
  		}
  		if (startTile.cornerX >0 && startTile.cornerY >0)
  		{
  			Tile currentDest = ts.getTile(startTile.cornerX-1, startTile.cornerY-1);
  			int moveValue = getTileMoveAbility(currentDest);
  			int currentValue = distance + moveValue;
  			if (currentValue < distances[currentDest.cornerX][currentDest.cornerY])
  				setDistances(currentDest, currentValue);
  		}
  		if (startTile.cornerX < size-2)
  		{
  			Tile currentDest = ts.getTile(startTile.cornerX+1, startTile.cornerY);
  			int moveValue = getTileMoveAbility(currentDest);
  			int currentValue = distance + moveValue;
  			if (currentValue < distances[currentDest.cornerX][currentDest.cornerY])
  				setDistances(currentDest, currentValue);
  		}
  		if (startTile.cornerY < size-2)
  		{
  			Tile currentDest = ts.getTile(startTile.cornerX, startTile.cornerY+1);
  			int moveValue = getTileMoveAbility(currentDest);
  			int currentValue = distance + moveValue;
  			if (currentValue < distances[currentDest.cornerX][currentDest.cornerY])
  				setDistances(currentDest, currentValue);
  		}
  		if (startTile.cornerX < size-2 && startTile.cornerY < size-2)
  		{
  			Tile currentDest = ts.getTile(startTile.cornerX+1, startTile.cornerY+1);
  			int moveValue = getTileMoveAbility(currentDest);
  			int currentValue = distance + moveValue;
  			if (currentValue < distances[currentDest.cornerX][currentDest.cornerY])
  				setDistances(currentDest, currentValue);
  		}
  		
  		if (startTile.cornerX >0  && startTile.cornerY < size-2)
  		{
  			Tile currentDest = ts.getTile(startTile.cornerX-1, startTile.cornerY+1);
  			int moveValue = getTileMoveAbility(currentDest);
  			int currentValue = distance + moveValue;
  			if (currentValue < distances[currentDest.cornerX][currentDest.cornerY])
  				setDistances(currentDest, currentValue);
  		}
  		
  		if (startTile.cornerX < size-2 && startTile.cornerY >0)
  		{
  			Tile currentDest = ts.getTile(startTile.cornerX+1, startTile.cornerY-1);
  			int moveValue = getTileMoveAbility(currentDest);
  			int currentValue = distance + moveValue;
  			if (currentValue < distances[currentDest.cornerX][currentDest.cornerY])
  				setDistances(currentDest, currentValue);
  		}
  	}
  	
  	private int getTileMoveAbility(Tile tileIn)
  	{
  		//This is how much is added to the distance variable per tile move bigger = slower
  		//negative = er no cannot do it
  		if (tileIn.id == TileId.WATER) return 9999;
  		if (tileIn.id == TileId.GRASS) return 99;
  		if (tileIn.id == TileId.DIRT) return 99;
			return 9999;
  	}
  	
	}

