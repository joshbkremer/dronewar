package org.botgame.bot;

import java.util.ArrayList;
import java.util.List;

import org.botgame.bot.parts.Chassis;
import org.botgame.bot.parts.Weapon;
import org.botgame.bot.strategy.AttackStrategy;
import org.botgame.util.Damage;

public class Bot {
	
	private Chassis chassis;
	private AttackStrategy attackStrat;
	private List<Weapon> weaponList;
	private String name;
	
	public Bot(Chassis c, String name)
	{
		this.chassis = c;
		this.name = name;
		attackStrat = AttackStrategy.CLOSEST_ENEMY;
		weaponList = new ArrayList<Weapon>();
	}

	public Bot(Bot b)
	{
		chassis = b.getChassis();
		attackStrat = b.getAttackStrategy();
		weaponList = new ArrayList<Weapon>();
		this.name = b.getName();
		
		for(Weapon w : b.getWeapons())
		{
			weaponList.add(w);
		}
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getMaxHealth()
	{
		return chassis.getHealth();
	}
	
	public AttackStrategy getAttackStrategy()
	{
		return attackStrat;
	}
	
	public Chassis getChassis()
	{
		return chassis;
	}
	
	public void setAttackStrategy(AttackStrategy as)
	{
		attackStrat = as;
	}
	
	public void setWeapon(int slot, Weapon w)
	{
		if(weaponList.size() <= slot)
			weaponList.add(w);
		else
			weaponList.set(slot, w);
	}
	
	public List<Weapon> getWeapons()
	{
		return weaponList;
	}
	
	public List<Damage> fire()
	{
		List<Damage> damageList = new ArrayList<Damage>();
		
		for(Weapon w : getWeapons())
		{
			damageList.add(w.getDamage());
		}
		
		return damageList;
	}
	
	public int getRange()
	{
		int range = 0;
		for(Weapon w : getWeapons())
		{
			range = Math.max(w.getRange(), range);
		}
		
		return range;
	}
	
}
