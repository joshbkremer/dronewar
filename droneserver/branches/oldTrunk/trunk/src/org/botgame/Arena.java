package org.botgame;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Arena {
	
	public List<Point> getStartingLocations()
	{
		List<Point> startingLocs = new ArrayList<Point>();
		
		startingLocs.add(new Point(0, 0));
		startingLocs.add(new Point(50, 50));
		
		return startingLocs;
	}
}
