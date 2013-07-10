package org.botgame.fight;

import java.awt.Point;

import org.botgame.bot.Bot;
import org.botgame.util.Damage;

public class FightingBot extends Bot {

	private int currentHP;
	private Point location;
	
	public FightingBot(Bot b)
	{
		super(b);
		
		currentHP = getMaxHealth();
	}
	
	public void setLocation(Point p)
	{
		location = p;
	}
	
	public Point getLocation()
	{
		return location;
	}
	
	public boolean isDead()
	{
		return (currentHP <= 0);
	}
	
	public int applyDamage(Damage d)
	{
		currentHP = currentHP - d.getDamageAmount();
		
		//Return actual damage dealt to account for damage reduction
		return d.getDamageAmount();
	}
	
	public int getCurrentHealth()
	{
		return currentHP;
	}
}
