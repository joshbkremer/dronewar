package org.botgame.game.bot;

import java.util.ArrayList;
import java.util.List;

import org.botgame.game.bot.parts.Armor;
import org.botgame.game.bot.parts.Core;
import org.botgame.game.bot.parts.Frame;
import org.botgame.game.bot.parts.Shield;
import org.botgame.game.bot.parts.Weapon;
import org.botgame.game.bot.strategy.AttackStrategy;
import org.botgame.game.util.Damage;

import com.google.appengine.api.datastore.Key;

public class Bot
{

	private String name;
	private Key id;
	private AttackStrategy attackStrat;
	private Frame frame;
	private Armor armor;
	private Core core;
	private Shield shield;
	private final List<Weapon> weaponList;

	public Bot(Frame c, Key id) 
	{

		this.frame = c;
		this.name = new String("Temp Bot Name");
		attackStrat = AttackStrategy.CLOSEST_ENEMY;
		weaponList = new ArrayList<Weapon>();
		armor = new Armor("SimpleArmor", 0);
		this.id = id;
	}

	public Bot(Bot b) {
		this.frame = b.getFrame();
		this.attackStrat = b.getAttackStrategy();
		this.weaponList = new ArrayList<Weapon>();
		this.shield = b.getShield();
		this.core = b.getCore();
		this.name = b.getName();
		this.armor = b.getArmor();
		this.id = b.getID();

		weaponList.addAll(b.getWeapons());
	}

	public void setID(Key id) {
		this.id = id;
	}

	public void setFrame(Frame f) {
		frame = f;
	}

	public void setCore(Core c) {
		core = c;
	}

	public Core getCore() {
		return core;
	}

	public void setShield(Shield s) {
		shield = s;
	}

	public Shield getShield() {
		return shield;
	}

	public double getMoveSpeed() {
		return frame.getMoveSpeed();
	}

	public Key getID() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getMaxHealth() {
		return frame.getHealth();
	}

	public AttackStrategy getAttackStrategy() {
		return attackStrat;
	}

	public Frame getFrame() {
		return frame;
	}

	public void setAttackStrategy(AttackStrategy as) {
		attackStrat = as;
	}

	public void setWeapon(int slot, Weapon w) {
		if (weaponList.size() <= slot)
			weaponList.add(w);
		else
			weaponList.set(slot, w);
	}

	public List<Weapon> getWeapons() {
		return weaponList;
	}

	public List<Damage> fire() {
		List<Damage> damageList = new ArrayList<Damage>();

		for (Weapon w : getWeapons()) {
			damageList.add(w.getDamage());
		}

		return damageList;
	}

	public void setArmor(Armor armor) {
		this.armor = armor;
	}

	public Armor getArmor() {
		return armor;
	}

	public double getRange() {
		double range = 0;
		for (Weapon w : getWeapons()) {
			range = Math.max(w.getRange(), range);
		}

		return range;
	}

}
