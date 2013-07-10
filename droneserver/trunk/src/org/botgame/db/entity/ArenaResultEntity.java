package org.botgame.db.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

@Entity
public class ArenaResultEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;

	private String fightTitle;
	private String logLocation;
	private String winner;
	private Date fightDate;
	private final List<Key> participants;

	public ArenaResultEntity() {
		participants = new ArrayList<Key>();
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

	public String getFightTitle() {
		return fightTitle;
	}

	public void setFightTitle(String fightTitle) {
		this.fightTitle = fightTitle;
	}

	public String getLogLocation() {
		return logLocation;
	}

	public void setLogLocation(String logLocation) {
		this.logLocation = logLocation;
	}

	public Date getFightDate() {
		return fightDate;
	}

	public void setFightDate(Date fightDate) {
		this.fightDate = fightDate;
	}

	public void pushParticipant(Key user) {
		if (participants.isEmpty())
			participants.add(user);
		else
			participants.add(0, user);
	}

	public List<Key> getParticipants() {
		return participants;
	}

}
