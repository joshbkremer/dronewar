package org.botgame.db.entity;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.botgame.db.EMF;
import org.botgame.game.bot.parts.Weapon;

import com.google.appengine.api.datastore.Key;

@Entity
public class WeaponEntity {
	// not quite sure how to serialize all the weapons right now so
	// im just going to do one
	// i think i need to make a WeaponsEntity but i don't
	// know how to map all to this class
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	private String weaponName;
	private int damageMin;
	private int damageMax;
	private double range;

	private Key damageTypeKey;

	public WeaponEntity(Weapon w) {
		this.weaponName = w.getWeaponName();
		this.damageMin = w.getDamageMin();
		this.damageMax = w.getDamageMax();
		this.range = w.getRange();
	}

	public void setDamageTypeKey(Key key) {
		this.damageTypeKey = key;
	}

	public Key getKey() {
		return key;
	}

	public Weapon getWeapon() {
		EntityManager em = EMF.get().createEntityManager();
		DamageTypeEntity dte = em.find(DamageTypeEntity.class, damageTypeKey);

		Weapon w = new Weapon(weaponName, damageMin, damageMax,
				dte.getDamageType(), range);
		return w;
	}

}
