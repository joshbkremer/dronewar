package org.botgame.game.arena;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.botgame.game.fight.FightingBot;

public class Arena {

	private static int AREA_PER_BOT = 1000;
	private static int MINIMUM_DISTANCE = 30;
	private ArrayList<Point> startingLocs;
	private Point maxDimensions;
	private int dimensions;

	public Arena(int bots) {

		int totalArea = AREA_PER_BOT * bots;

		dimensions = (int) Math.round(Math.sqrt(totalArea));

		maxDimensions = new Point(dimensions, dimensions);
		randomizeStartingLocations(bots);
	}

	public List<Point> getStartingLocations() {
		return startingLocs;
	}

	public Point getMaxSize() {
		return maxDimensions;
	}

	public Point getDistanceToEdge(FightingBot fb) {
		Point loc = fb.getLocation();
		double xDist = (Math.abs(0.0 - loc.getX()) < (dimensions - loc.getX()) ? 0.0 - loc
				.getX() : dimensions - loc.getX());
		double yDist = (Math.abs(0.0 - loc.getY()) < (dimensions - loc.getY()) ? 0.0 - loc
				.getY() : dimensions - loc.getY());
		return new Point(xDist, yDist);
	}

	private void randomizeStartingLocations(int bots) {

		Random rand = new Random();
		startingLocs = new ArrayList<Point>();

		double edgeRoom = dimensions / 2;
		int loops = 0;

		while (startingLocs.size() < bots) {

			double tempOne = rand.nextDouble() * edgeRoom;
			double tempTwo;
			if (tempOne > (edgeRoom / 2)) {
				tempTwo = rand.nextDouble() * (edgeRoom / 2.0);
			} else {
				tempTwo = rand.nextDouble() * edgeRoom;
			}

			double oneLoc = ((rand.nextInt(2) == 0) ? tempOne : dimensions
					- tempOne);
			double twoLoc = ((rand.nextInt(2) == 0) ? tempTwo : dimensions
					- tempTwo);

			Point tempP = ((rand.nextInt(2) == 0) ? new Point(oneLoc, twoLoc)
					: new Point(twoLoc, oneLoc));

			Iterator<Point> pointIter = startingLocs.iterator();
			while (pointIter.hasNext()) {
				Point curP = pointIter.next();
				if (curP.distance(tempP) < MINIMUM_DISTANCE) {
					tempP = null;
					break;
				}
			}

			if (loops++ > bots * 10)
				randomizeStartingLocations(bots);

			if (tempP != null)
				startingLocs.add(tempP);
		}
	}
}
