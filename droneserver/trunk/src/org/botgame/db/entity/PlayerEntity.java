package org.botgame.db.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class PlayerEntity {
	@Id
	private String playerID;

	@OneToOne(cascade = CascadeType.PERSIST)
	private BotEntity botEntity;

	public PlayerEntity(String playerID) {
		this.playerID = playerID;
	}

	public String getPlayerID() {
		return playerID;
	}

	public void setBotEntity(BotEntity be) {
		botEntity = be;
	}

	public BotEntity getPlayerBot() {
		return botEntity;
	}
}
