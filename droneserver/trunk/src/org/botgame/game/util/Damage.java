package org.botgame.game.util;

public class Damage 
{
	private int damageVal;
	private DamageType type;

	public Damage(int value, DamageType dt)
	{
		damageVal = value;
		type = dt;
	}
	
	public int getDamageAmount()
	{
		return damageVal;
	}
	
	public DamageType getDamageType()
	{
		return type;
	}
}
