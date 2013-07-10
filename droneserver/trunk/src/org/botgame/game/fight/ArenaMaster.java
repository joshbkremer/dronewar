package org.botgame.game.fight;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.botgame.db.entity.ArenaResultEntity;
import org.botgame.db.jaxb.fightlog.AtkDamage;
import org.botgame.db.jaxb.fightlog.AttackEntry;
import org.botgame.db.jaxb.fightlog.DfdDamage;
import org.botgame.db.jaxb.fightlog.KillEntry;
import org.botgame.db.jaxb.fightlog.Log;
import org.botgame.db.jaxb.fightlog.MoveEntry;
import org.botgame.db.jaxb.fightlog.UserID;
import org.botgame.db.jaxb.fightlog.UserType;
import org.botgame.db.jaxb.fightlog.XSDPoint;
import org.botgame.game.arena.Arena;
import org.botgame.game.arena.Point;
import org.botgame.game.bot.Bot;
import org.botgame.game.bot.strategy.AttackStrategy;
import org.botgame.game.util.Damage;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.files.FileWriteChannel;

public class ArenaMaster {

	private ArrayList<FightingBot> fightingBots;
	private Arena currentArena;

	private final Log xmlLog;
	private final ArenaResultEntity arenaResult;

	public ArenaMaster(String title) {
		xmlLog = new Log();
		arenaResult = new ArenaResultEntity();
		arenaResult.setFightDate(Calendar.getInstance().getTime());
		arenaResult.setFightTitle(title);
	}

	public Log getLog() {
		return xmlLog;
	}

	public ArenaResultEntity getArenaResult() {
		return arenaResult;
	}

	public void setBots(List<Bot> bots) {
		currentArena = new Arena(bots.size());
		fightingBots = new ArrayList<FightingBot>();
		List<Point> startingLocs = currentArena.getStartingLocations();

		System.out.println(startingLocs);
		int i = 0;
		for (Bot b : bots) {
			fightingBots.add(new FightingBot(b));
			fightingBots.get(i).setLocation(startingLocs.get(i++));
		}
	}

	public void startFight() {
		while (fightingBots.size() > 1) {
			moveBots();
			attackBots();
		}

		finalizeFight();
	}

	private void finalizeFight() {
		arenaResult.pushParticipant(fightingBots.get(0).getID());

		// push winner to result
		try {
			BlobKey fileLoc = writeLogToFile();
			arenaResult.setLogLocation(fileLoc.getKeyString());
			arenaResult.setWinner(fightingBots.get(0).getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private BlobKey writeLogToFile() throws IOException, JAXBException {
		FileService fileService = FileServiceFactory.getFileService();

		AppEngineFile file = fileService.createNewBlobFile("text/xml");

		FileWriteChannel writeChannel = fileService
				.openWriteChannel(file, true);

		JAXBContext jc = JAXBContext
				.newInstance("org.botgame.db.jaxb.fightlog");
		Marshaller m = jc.createMarshaller();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		m.marshal(xmlLog, baos);

		System.out.println(new String(baos.toByteArray()));
		writeChannel.write(ByteBuffer.wrap(baos.toByteArray()));
		writeChannel.closeFinally();

		return fileService.getBlobKey(file);
	}

	private void attackBots() {

		Iterator<FightingBot> botIter = fightingBots.iterator();

		while (botIter.hasNext()) {
			FightingBot b = botIter.next();

			if (b.isDead()) {
				botIter.remove();
				continue;
			}

			AttackStrategy as = b.getAttackStrategy();

			FightingBot target;
			switch (as) {
			case CLOSEST_ENEMY:
				target = getClosestBot(b);
				break;
			default:
				target = getClosestBot(b);
				break;
			}

			// No targets? We're done.
			if (target == null)
				break;

			double distance = b.getLocation().distance(target.getLocation());
			// System.out.println(b.getName() + " range (" + distance + ") "
			// + target.getName());
			if (distance <= b.getRange()) {
				List<Damage> damages = b.fire();

				UserID attackerID = new UserID();
				attackerID.setType(UserType.ATTACK);
				attackerID.setValue(b.getID().toString());

				UserID defenderID = new UserID();
				defenderID.setType(UserType.DEFEND);
				defenderID.setValue(target.getID().toString());

				for (Damage d : damages) {
					int actualDamage = target.applyDamage(d);

					System.out.println(b.getName() + " -> " + actualDamage
							+ " -> " + target.getName());

					AttackEntry ae = new AttackEntry();

					ae.getBotID().add(attackerID);
					ae.getBotID().add(defenderID);

					AtkDamage atkDamage = new AtkDamage();
					atkDamage.setDamageType(d.getDamageType().toString());
					atkDamage.setValue(d.getDamageAmount());
					ae.setAttackerDamage(atkDamage);

					DfdDamage dfdDamage = new DfdDamage();
					dfdDamage.setHpRemaining(target.getCurrentHealth());
					dfdDamage.setShieldAbs(0);
					dfdDamage.setShieldRemaining(0);
					dfdDamage.setValue(actualDamage);
					ae.setDefenderDamage(dfdDamage);

					ae.setRange(b.getLocation().distance(target.getLocation()));

					xmlLog.getEntry().add(ae);
				}

				if (target.isDead()) {
					KillEntry ke = new KillEntry();
					UserID killerID = new UserID();
					killerID.setType(UserType.KILL);
					killerID.setValue(b.getID().toString());

					UserID deadID = new UserID();
					deadID.setType(UserType.DEAD);
					deadID.setValue(target.getID().toString());

					ke.getBotID().add(killerID);
					ke.getBotID().add(deadID);

					arenaResult.pushParticipant(b.getID());
				}
			}
		}
	}

	private void moveBots() {

		Iterator<FightingBot> botIter = fightingBots.iterator();

		while (botIter.hasNext()) {
			FightingBot b = botIter.next();

			if (b.isDead()) {
				botIter.remove();
				continue;
			}
			AttackStrategy as = b.getAttackStrategy();

			FightingBot target;
			switch (as) {
			case CLOSEST_ENEMY:
				target = getClosestBot(b);
				break;
			default:
				target = getClosestBot(b);
				break;
			}

			if (target == null) // No targets, we're done!
				break;

			double distance = b.getLocation().distance(target.getLocation());
			if (distance > b.getRange()) {
				Point targetLoc = target.getLocation();
				Point curLoc = b.getLocation();

				double xMove = targetLoc.getX() - curLoc.getX();
				double yMove = targetLoc.getY() - curLoc.getY();
				double moveSpeed = b.getMoveSpeed();

				double xRatio = (moveSpeed / distance) * xMove;
				double yRatio = (moveSpeed / distance) * yMove;

				curLoc.setX(curLoc.getX() + xRatio);
				curLoc.setY(curLoc.getY() + yRatio);

				UserID uid = new UserID();
				uid.setValue(b.getID().toString());
				uid.setType(UserType.MOVE);

				MoveEntry me = new MoveEntry();
				me.getBotID().add(uid);

				XSDPoint p = new XSDPoint();
				p.setX(curLoc.getX());
				p.setY(curLoc.getY());
				me.setLocation(p);

				xmlLog.getEntry().add(me);
			}
		}
	}

	private FightingBot getClosestBot(FightingBot b) {
		FightingBot currentClosest = null;
		double closestDist = Double.MAX_VALUE;
		for (FightingBot curBot : fightingBots) {
			if (curBot.equals(b))
				continue;

			double curDist = curBot.getLocation().distance(b.getLocation());
			if (curDist < closestDist) {
				currentClosest = curBot;
				closestDist = curDist;
			}
		}

		return currentClosest;
	}
}
