package org.botgame.game.fight;

import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.botgame.db.entity.ArenaResultEntity;
import org.botgame.db.jaxb.fightlog.Log;
import org.botgame.game.arena.Arena;
import org.botgame.game.arena.Point;
import org.botgame.game.bot.Bot;
import org.botgame.game.util.Damage;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.files.FileWriteChannel;

public class BotsGA 
{
  
	private List<FightingBot> fightingBots;
	private List<FightingBot> deadBots = new ArrayList<FightingBot>();
	private Arena currentArena;
	private final ArenaResultEntity arenaResult;
	private final Log xmlLog;
	private BotGeneration currentGen;
	private FightingBot trainingPlayer = null;

	public BotsGA(String uuid) {
		arenaResult = new ArenaResultEntity();
		arenaResult.setFightDate(Calendar.getInstance().getTime());
		arenaResult.setFightTitle(uuid);

		xmlLog = new Log();
	}
	
	public void setBots(List<Bot> bots) {
		currentArena = new Arena(bots.size());
		fightingBots = new ArrayList<FightingBot>();
		List<Point> startingLocs = currentArena.getStartingLocations();

		//System.out.println(startingLocs);
		int i = 0;
		for (Bot b : bots) {
			fightingBots.add(new FightingBot(b));
			fightingBots.get(i).setLocation(startingLocs.get(i++));
		}
	}
	public void setTrainingPlayer(Bot playerBot)
	{
	  this.trainingPlayer = new FightingBot(playerBot); 
	}
	
	public void initialBreed()
	{
	  currentGen = new BotGeneration(fightingBots, trainingPlayer);
	  fightingBots = currentGen.getBotsWithWeights();
	}
	
	public void newGeneration()
	{
	  currentGen.newGeneration();
	  
	}
	
	public FightingBot getTrainingBot()
	{
	  for(FightingBot bot : deadBots)
	    if(trainingPlayer.getName().equals(bot.getName()))
	      return bot;
	  
	  for(FightingBot bot : fightingBots)
	    if(trainingPlayer.getName().equals(bot.getName()))
	      return bot;
	  
	  return trainingPlayer;
	}

	public void startFight()
	{
	  int count=0;
		while (fightingBots.size() > 1)
		{
		  count++;
		  if(count > 10000)
		    break;
			Iterator<FightingBot> botIter = fightingBots.iterator();
			while (botIter.hasNext())
			{
				FightingBot b = botIter.next();
				if (b.isDead()) 
				{
				  //System.out.println(b.getName() + " is dead.");
				  deadBots.add(b);
					botIter.remove();
					continue;
				}

				if(fightingBots.size() == 1)
				  continue;
				ExecutePriorityEnum ep = getMove(b);
				executeMove(ep, b);
			}
		}
		
		WeightStore ws = null;
		double bestFitness = 0;
		
		for(FightingBot b : fightingBots)
		{
		  //System.out.println(b.getName() + " is left, with fitness: " + b.getBotFitnessScore());
		  if(bestFitness < b.getBotFitnessScore())
		  {
		    bestFitness = b.getBotFitnessScore();
		    ws = b.getWeightStore();
		  }
		}
		
		System.out.println("Best fitness: " + bestFitness);
		//System.out.println("Best weight: " + ws.getShieldWeight() + ", " + ws.getFireWeaponWeight() + ", " + ws.getTowardsPlayerMoveWeight());
		

		finalizeFight();
	}
	
	private void executeMove(ExecutePriorityEnum ep, FightingBot b)
	{
	  switch(ep) 
	  {
	    case SHIELD_PRIORITY:
	    {
	      rechargeShield(b);
	      break;
	    }
	    case FIRE_PRIORITY:
	    {
	      fire(b);
	      break;
	    }
	    case TOWARDS_PLAYER_MOVE_PRIORITY:
	    {
	      moveTowardsPlayer(b);
	      break;
	    }
	    case MULTI_PLAYER_MOVE_PRIORITY:
	    case TOWARDS_WALL_MOVE_PRIORITY:
	    case AWAY_FROM_PLAYERS_MOVE_PRIORITY:
	    case WALL_AND_ONE_PLAYER_MOVE_PRIORITY:
	    default:
	    {
	      break; 
	    }
	  }
	}
	
	private void rechargeShield(FightingBot b)
	{
	  int rechargeAmount = b.getShield().getRechargeAmount();
	  int shieldRemaining = b.getShield().getShieldRemaining();
	  shieldRemaining = rechargeAmount + shieldRemaining;
	  if(shieldRemaining > b.getShield().getDamageAbsorb())
	    shieldRemaining = b.getShield().getDamageAbsorb();
	  
	  b.getShield().setShieldRemaining(shieldRemaining);
	  
    //System.out.println(b.getName() + " recharged shields");
	}
	
	private void moveTowardsPlayer(FightingBot b)
	{
	  FightingBot target = getClosestBot(b);
	  
	  double distance = b.getLocation().distance(target.getLocation());
    if (distance <= b.getRange())
      return;
    
    Point targetLoc = target.getLocation();
    Point curLoc = b.getLocation();

    double xMove = targetLoc.getX() - curLoc.getX();
    double yMove = targetLoc.getY() - curLoc.getY();
    double moveSpeed = b.getMoveSpeed();

    double xRatio = (moveSpeed / distance) * xMove;
    double yRatio = (moveSpeed / distance) * yMove;

    curLoc.setX(curLoc.getX() + xRatio);
    curLoc.setY(curLoc.getY() + yRatio);
    
    //System.out.println(b.getName() + " moved to (" + curLoc.getX() + "," + curLoc.getY() + ")");
	}
	
	private void fire(FightingBot b)
	{
	  FightingBot target = getClosestBot(b);
	  double distance =0;
	  try
	  {
	    distance = b.getLocation().distance(target.getLocation());
	  }
	  catch(Exception e)
	  {
	    getClosestBot(b);
	    System.out.println(e);
	  }
    if (distance > b.getRange()) 
      return;
    
    List<Damage> damages = b.fire();
    for(Damage d : damages)
    {
      int actualDamage = target.applyDamage(d);
      if(target.isDead())
      {
        b.killedBot();
        if(fightingBots.size() == 2)
          b.botWon();
      }
      b.addTotalDamage(actualDamage);
    }
	}

	private ExecutePriorityEnum getMove(FightingBot b)
	{
	  ExecutePriorityEnum ep = ExecutePriorityEnum.FIRE_PRIORITY;
	  
		double shieldPriority = getShieldPriority(b) + getHealthPriority(b);
		double fireWeaponPriority = getFireWeaponPriority(b); 
		double towardsPlayerMovePriority = getTowardsPlayerMovePriority(b);
		
		double multiPlayerMovePriority = getMultiPlayerMovePriority();
		double towardsWallMovePriority = getTowardsWallMovePriority();
		double awayFromPlayersMovePriority = getAwayFromPlayersMovePriority();
		double wallAndOnePlayerMovePriority = getWallAndOnePlayerMovePriority();
		
		maxDouble =0;
		ep = getMaxPriority(shieldPriority, ExecutePriorityEnum.SHIELD_PRIORITY, ep);
		ep = getMaxPriority(fireWeaponPriority, ExecutePriorityEnum.FIRE_PRIORITY, ep);
		ep = getMaxPriority(towardsPlayerMovePriority, ExecutePriorityEnum.TOWARDS_PLAYER_MOVE_PRIORITY, ep);
		ep = getMaxPriority(multiPlayerMovePriority, ExecutePriorityEnum.MULTI_PLAYER_MOVE_PRIORITY, ep);
		ep = getMaxPriority(towardsWallMovePriority, ExecutePriorityEnum.TOWARDS_WALL_MOVE_PRIORITY, ep);
		ep = getMaxPriority(awayFromPlayersMovePriority, ExecutePriorityEnum.AWAY_FROM_PLAYERS_MOVE_PRIORITY, ep);
		ep = getMaxPriority(wallAndOnePlayerMovePriority, ExecutePriorityEnum.WALL_AND_ONE_PLAYER_MOVE_PRIORITY, ep);
		
		return ep;
	}
	
	private double maxDouble =0;
	private ExecutePriorityEnum getMaxPriority(double exPr, ExecutePriorityEnum ep, ExecutePriorityEnum in)
	{
	  if(maxDouble < exPr)
	    return ep;
	  return in;
	}

	private double getShieldPriority(FightingBot b)
	{
		double calcShieldWeight = 1 - (b.getShield().getShieldRemaining() / b
				.getShield().getDamageAbsorb());

		calcShieldWeight = calcShieldWeight * b.getWeightStore().getShieldWeight();
		return calcShieldWeight;
	}
	
	private double getFireWeaponPriority(FightingBot b)
	{
	  FightingBot closeBot = getClosestBot(b);
	  
	  if(closeBot == null)
	    return 0;
	  
	  double distance = b.getLocation().distance(closeBot.getLocation());
    if (distance > b.getRange())
      return 0;
	  
	  return b.getWeightStore().getFireWeaponWeight();
	}
	
	private double getTowardsPlayerMovePriority(FightingBot b) 
	{ 
	  FightingBot closeBot = getClosestBot(b);
	  
	  if(closeBot == null)
	    return 0;
	  
	  double distance = b.getLocation().distance(closeBot.getLocation());
    if (distance > b.getRange())
	    return b.getWeightStore().getTowardsPlayerMoveWeight();
    
	  return 0;
	}
	
	private double getHealthPriority(FightingBot b) { return 0; }
	private double getMultiPlayerMovePriority() { return 0; }
	private double getTowardsWallMovePriority() { return 0; }
	private double getAwayFromPlayersMovePriority() { return 0; }
	private double getWallAndOnePlayerMovePriority() { return 0; }

	private void finalizeFight() 
	{
//	  System.out.println(fightingBots.get(0).getName() + " won!");
//	  System.out.println("With weights: ");
//	  System.out.println("Shield: " + fightingBots.get(0).getWeightStore().getShieldWeight());
//	  System.out.println("Fire  : " + fightingBots.get(0).getWeightStore().getFireWeaponWeight());
//	  System.out.println("Move T: " + fightingBots.get(0).getWeightStore().getTowardsPlayerMoveWeight());
		arenaResult.pushParticipant(fightingBots.get(0).getID());
		try {
			BlobKey fileLoc = writeLogToFile();
			arenaResult.setLogLocation(fileLoc.getKeyString());
			arenaResult.setWinner(fightingBots.get(0).getName());
		} catch (Exception e) {
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

		//System.out.println(new String(baos.toByteArray()));
		writeChannel.write(ByteBuffer.wrap(baos.toByteArray()));
		writeChannel.closeFinally();

		return fileService.getBlobKey(file);
	}
	
	 private FightingBot getClosestBot(FightingBot b)
	 {
	    FightingBot currentClosest = null;
	    double closestDist = Double.MAX_VALUE;
	    for (FightingBot curBot : fightingBots) 
	    {
	      if (curBot.equals(b))
	        continue;

	      double curDist = curBot.getLocation().distance(b.getLocation());
	      if (curDist < closestDist)
	      {
	        currentClosest = curBot;
	        closestDist = curDist;
	      }
	    }

	    return currentClosest;
	  }

	public ArenaResultEntity getArenaResult() {
		return arenaResult;
	}
}
