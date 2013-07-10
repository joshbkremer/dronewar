package org.botgame.db.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.botgame.game.bot.parts.Armor;

import com.google.appengine.api.datastore.Key;

@Entity
public class ArmorEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	private String armorName;
	private int armorValue;

	public ArmorEntity(Armor playerArmor) {
		this.armorName = playerArmor.getArmorName();
		this.armorValue = playerArmor.getArmorValue();
	}

	public Key getKey() {
		return key;
	}

	public Armor getArmor() {
		Armor a = new Armor(armorName, armorValue);
		return a;
	}

}
