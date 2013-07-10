package org.botgame.fight;

import java.util.ArrayList;
import java.util.List;

import org.botgame.Arena;
import org.botgame.bot.Bot;
import org.botgame.bot.parts.Chassis;
import org.botgame.bot.parts.Weapon;
import org.botgame.util.Damage;
import org.botgame.util.DamageType;
import org.junit.Test;

import junit.framework.TestCase;

public class ArenaMasterTest extends TestCase {

	@Test
	public void testFight()
	{
		Chassis simpleChassis = new Chassis(){

			@Override
			public int getHealth() {
				return 200;
			}};
			
		Weapon simpleWeapon = new Weapon(){

			@Override
			public Damage getDamage() {
				
				return new Damage(5 + (int)(Math.random()*5.0), DamageType.BASIC);
			}

			@Override
			public int getRange() {
				return 10;
			}};
			
		Weapon rangedWeapon = new Weapon(){

				@Override
				public Damage getDamage() {
					
					return new Damage(5 + (int)(Math.random()*5.0), DamageType.BASIC);
				}

				@Override
				public int getRange() {
					return 20;
				}};
			
		Bot jdg = new Bot(simpleChassis, "JDG");
		jdg.setWeapon(0, simpleWeapon);
		
		Bot rjb = new Bot(simpleChassis, "RJB");
		rjb.setWeapon(0, rangedWeapon);
		
		List<Bot> botList = new ArrayList<Bot>();
		botList.add(jdg);
		botList.add(rjb);
		
		ArenaMaster tehMaster = new ArenaMaster(new Arena());
		tehMaster.setBots(botList);
		
		tehMaster.startFight();
	}
}
