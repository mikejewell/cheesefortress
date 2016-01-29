package cheese.model;

import java.util.Vector;

import org.newdawn.slick.Image;

public class Building {

	private String name = "?"; 
	Vector<Image> images = null;
	
	public Building(String nameIn)
	{
		name = nameIn;
	}
}
