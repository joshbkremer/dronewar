package org.botgame.db.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.botgame.db.EMF;
import org.botgame.game.bot.Bot;

import com.google.appengine.api.datastore.Key;

@Entity
public class BotEntity {
	// we may have to change this later
	// im not sure if different entity classes
	// can have the same name
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;

	private Key frameKey;
	private Key armorKey;
	private Key coreKey;
	private Key shieldKey;

	@ElementCollection
	private List<Key> weaponsKeyCollection;

	private String botName;

	public BotEntity(String name) {
		botName = name;
		weaponsKeyCollection = new ArrayList<Key>();
	}

	public void setName(String name) {
		botName = name;
	}

	public Key getkey() {
		return key;
	}

	public Bot getBot() {
		EntityManager em = EMF.get().createEntityManager();

		// Grab hard objects
		FrameEntity fe = em.find(FrameEntity.class, frameKey);
		ArmorEntity ae = em.find(ArmorEntity.class, armorKey);
		CoreEntity ce = em.find(CoreEntity.class, coreKey);
		ShieldEntity se = em.find(ShieldEntity.class, shieldKey);

		Bot b = new Bot(fe.getFrame(), key);
		b.setName(botName);

		b.setArmor(ae.getArmor());
		b.setCore(ce.getCore());
		b.setShield(se.getShield());

		int keyCount = 0;
		for (Key wk : weaponsKeyCollection) {
			WeaponEntity we = em.find(WeaponEntity.class, wk);
			b.setWeapon(keyCount++, we.getWeapon());
		}

		return b;
	}

	public void setFrameKey(Key key) {
		frameKey = key;
	}

	public void setArmorKey(Key key) {
		armorKey = key;
	}

	public void setCoreKey(Key key) {
		coreKey = key;
	}

	public void setShieldKey(Key key) {
		shieldKey = key;
	}

	public void setWeaponKey(int position, Key key) {
		if (position < weaponsKeyCollection.size())
			weaponsKeyCollection.set(position, key);
		else
			weaponsKeyCollection.add(key);
	}

	public List<Key> getWeaponKeys() {
		return weaponsKeyCollection;
	}

	public Key getArmorKey() {
		return armorKey;
	}

	public Key getFrameKey() {
		return frameKey;
	}

	public Key getCoreKey() {
		return coreKey;
	}

	public Key getShieldKey() {
		return shieldKey;
	}
}
