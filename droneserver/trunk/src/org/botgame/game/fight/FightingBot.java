package org.botgame.game.fight;

import org.botgame.game.arena.Point;
import org.botgame.game.bot.Bot;
import org.botgame.game.util.Damage;

public class FightingBot extends Bot {

	private int currentHP;
	private Point location;
	private double totalDamage = 0;
	private double fitnessScore =0;
	
	private WeightStore weightStore;

	public FightingBot(Bot b) 
	{
		super(b);
		currentHP = getMaxHealth();
	}
	
	public void botWon()
	{
	  fitnessScore = fitnessScore + 1000; 
	}
	
	public void killedBot()
	{
	  fitnessScore = fitnessScore + 100;
	}
	
	public double getBotFitnessScore()
	{
	  return fitnessScore + totalDamage; 
	}
	
	public void addTotalDamage(double damage)
	{
	  totalDamage = totalDamage + damage;
	}
	
	public double getTotalDamage()
	{
	  return totalDamage; 
	}
	
	public void setWeights(WeightStore weightStore)
	{
	  this.weightStore = weightStore;
	}

	public void setLocation(Point p)
	{
		location = p;
	}

	public Point getLocation() {
		return location;
	}

	public boolean isDead() {
		return (currentHP <= 0);
	}
	
	public int applyDamage(Damage d) 
	{
		int mitigatedDamage;
		switch (d.getDamageType()) {
		case BASIC:
			mitigatedDamage = (int) (d.getDamageAmount() - (d.getDamageAmount() * getArmor()
					.getMitigation()));
			break;
		default:
			mitigatedDamage = d.getDamageAmount();
			break;

		}
		
		int shieldLeft = this.getShield().getShieldRemaining();
		if(shieldLeft >= mitigatedDamage)
		{
		  shieldLeft = shieldLeft - mitigatedDamage;
		  mitigatedDamage = 0;
		  this.getShield().setShieldRemaining(shieldLeft);
		}
		else
		{
		  this.getShield().setShieldRemaining(0);
		  mitigatedDamage = mitigatedDamage - shieldLeft;
		}
		
		currentHP = Math.max(0, currentHP - mitigatedDamage); // Not below 0

		// Return actual damage dealt to account for damage reduction
		return mitigatedDamage;
	}

	public int getCurrentHealth() {
		return currentHP;
	}
	
	public WeightStore getWeightStore()
	{
	  return weightStore;
	}
}