package org.botgame.db.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.botgame.game.util.DamageType;

import com.google.appengine.api.datastore.Key;

@Entity
public class DamageTypeEntity {

	@Id
	private Key key;
	private String name;

	public DamageTypeEntity(DamageType damageType, Key key) {
		this.key = key;
		name = damageType.toString();
	}

	public DamageType getDamageType() {
		return DamageType.valueOf(name);
	}

	public Key getKey() {
		return key;
	}

}
