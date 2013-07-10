package org.botgame.fight;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.botgame.Arena;
import org.botgame.bot.Bot;
import org.botgame.bot.strategy.AttackStrategy;
import org.botgame.util.Damage;

public class ArenaMaster {

	private ArrayList<FightingBot> fightingBots;
	private Arena currentArena;

	public ArenaMaster(Arena a)
	{
		currentArena = a;
	}
	
	public void setBots(List<Bot> bots)
	{
		fightingBots = new ArrayList<FightingBot>();
		List<Point> startingLocs = currentArena.getStartingLocations();
		
		int i = 0;
		for(Bot b : bots)
		{
			fightingBots.add(new FightingBot(b));
			fightingBots.get(i).setLocation(startingLocs.get(i++));
		}
	}
	
	public void startFight()
	{
		while(fightingBots.size() > 1)
		{
			moveBots();
			
			attackBots();
			
			clearDead();
		}
		
		if(fightingBots.size() == 1)
		{
			System.out.println(fightingBots.get(0).getName() + " is the winner!");
		}
		else
		{
			System.out.println("DRAW!");
		}
	}
	
	private void clearDead()
	{
		Iterator<FightingBot> iterBots = fightingBots.iterator();
		
		while(iterBots.hasNext())
		{
			FightingBot curBot = iterBots.next();
			if(curBot.isDead())
				iterBots.remove();
		}
	}
	
	private void attackBots()
	{
		for(FightingBot b : fightingBots)
		{
			AttackStrategy as = b.getAttackStrategy();
			
			FightingBot target;
			switch(as)
			{
			case CLOSEST_ENEMY:
				target = getClosestBot(b);
				break;
			default:
				target = getClosestBot(b);
				break;
			}
			
			if(b.getLocation().distance(target.getLocation()) < b.getRange())
			{
				List<Damage> damages = b.fire();
				for(Damage d : damages)
				{
					int actualDamage = target.applyDamage(d);
					System.out.println(String.format("Bot %s fired at Bot %s for %d damage (%d remaining)", b.getName(), target.getName(), actualDamage, target.getCurrentHealth()));
				}
			}
		}
	}
	
	private void moveBots()
	{
		for(FightingBot b : fightingBots)
		{
			AttackStrategy as = b.getAttackStrategy();
			
			FightingBot target;
			switch(as)
			{
			case CLOSEST_ENEMY:
				target = getClosestBot(b);
				break;
			default:
				target = getClosestBot(b);
				break;
			}
			
			if(b.getLocation().distance(target.getLocation()) > b.getRange())
			{
				Point targetLoc = target.getLocation();
				Point curLoc = b.getLocation();
				
				double xDist = curLoc.getX() - targetLoc.getX();
				double yDist = curLoc.getY() - targetLoc.getY();
				
				if(xDist > yDist)
				{
					curLoc.x = (int) (curLoc.x - (xDist / Math.abs(xDist)));
				}
				else
				{
					curLoc.y = (int) (curLoc.y - (yDist / Math.abs(yDist)));
				}
				
				System.out.println("Bot \"" + b.getName() + "\" moved to " + curLoc.toString());
			}			
		}
	}
	
	private FightingBot getClosestBot(FightingBot b)
	{
		FightingBot currentClosest = null;
		double closestDist = Double.MAX_VALUE;
		for(FightingBot curBot : fightingBots)
		{
			if(curBot.equals(b))
				continue;
			
			double curDist = curBot.getLocation().distance(b.getLocation());
			if(curDist < closestDist)
			{
				currentClosest = curBot;
				closestDist = curDist; 
			}
		}
		
		return currentClosest;
	}
}
