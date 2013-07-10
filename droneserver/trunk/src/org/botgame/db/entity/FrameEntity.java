package org.botgame.db.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.botgame.game.bot.parts.Frame;

import com.google.appengine.api.datastore.Key;

@Entity
public class FrameEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;

	private String frameName;
	private int health;
	private double moveSpeed;

	public FrameEntity(Frame playerFrame) {
		this.frameName = playerFrame.getFrameName();
		this.health = playerFrame.getHealth();
		this.moveSpeed = playerFrame.getMoveSpeed();
	}

	public Key getKey() {
		return key;
	}

	public Frame getFrame() {
		Frame c = new Frame(frameName, health, moveSpeed);
		return c;
	}
}
