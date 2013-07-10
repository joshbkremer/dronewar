package org.botgame.game.fight;

import java.util.Random;

public class WeightStore implements Cloneable
{
  private double shieldWeight = 0;
  private double healthWeight = 0;
  private double fireWeaponWeight = 0;
  private double multiPlayerMoveWeight = 0;
  private double towardsWallMoveWeight = 0;
  private double towardsPlayerMoveWeight = 0;
  private double awayFromPlayersMoveWeight = 0;
  private double wallAndOnePlayerMoveWeight = 0;
  private double lastMoveWeight =0;
  
  @Override
  public WeightStore clone()
  {
    WeightStore ws = new WeightStore(this.shieldWeight, 
        this.healthWeight, this.fireWeaponWeight, this.multiPlayerMoveWeight,
        this.towardsWallMoveWeight, this.towardsPlayerMoveWeight,
        this.awayFromPlayersMoveWeight, this.wallAndOnePlayerMoveWeight,
        this.lastMoveWeight);
    
    return ws;
  }

  public WeightStore(double shieldWeight, double healthWeight,
      double fireWeaponWeight, double multiPlayerMoveWeight,
      double towardsWallMoveWeight, double towardsPlayerMoveWeight,
      double awayFromPlayersMoveWeight, double wallAndOnePlayerMoveWeight,
      double lastMoveWeight)
  {
    this.shieldWeight = shieldWeight;
    this.healthWeight = healthWeight;
    this.fireWeaponWeight = fireWeaponWeight;
    this.multiPlayerMoveWeight = multiPlayerMoveWeight;
    this.towardsWallMoveWeight = towardsPlayerMoveWeight;
    this.towardsPlayerMoveWeight = towardsPlayerMoveWeight;
    this.awayFromPlayersMoveWeight = awayFromPlayersMoveWeight;
    this.wallAndOnePlayerMoveWeight = wallAndOnePlayerMoveWeight;
    this.lastMoveWeight = lastMoveWeight;
  }
  
  public double getPriority(ExecutePriorityEnum epe)
  {
    switch(epe)
    {
      case SHIELD_PRIORITY:
        return this.shieldWeight; 
      case HEALTH_PRIORITY:
        return this.healthWeight;
      case FIRE_PRIORITY:
        return this.fireWeaponWeight;
      case MULTI_PLAYER_MOVE_PRIORITY:
        return this.multiPlayerMoveWeight;
      case TOWARDS_WALL_MOVE_PRIORITY:
        return this.towardsWallMoveWeight;
      case TOWARDS_PLAYER_MOVE_PRIORITY:
        return this.towardsPlayerMoveWeight;
      case AWAY_FROM_PLAYERS_MOVE_PRIORITY:
        return this.awayFromPlayersMoveWeight;
      case WALL_AND_ONE_PLAYER_MOVE_PRIORITY:
        return this.wallAndOnePlayerMoveWeight;
      case LAST_MOVE_PRIORITY:
        return this.lastMoveWeight;
      default:
        return 0;
    }
  }
  
  public int getNumWeights()
  {
    return ExecutePriorityEnum.getnumWeights();
  }
  
  public void changeWeight(ExecutePriorityEnum epe, double newWeight)
  {
    switch(epe)
    {
      case SHIELD_PRIORITY:
        this.shieldWeight = newWeight; 
        break;
      case HEALTH_PRIORITY:
        this.healthWeight = newWeight;
        break;
      case FIRE_PRIORITY:
        this.fireWeaponWeight = newWeight;
        break;
      case MULTI_PLAYER_MOVE_PRIORITY:
        this.multiPlayerMoveWeight = newWeight;
        break;
      case TOWARDS_WALL_MOVE_PRIORITY:
        this.towardsWallMoveWeight = newWeight;
        break;
      case TOWARDS_PLAYER_MOVE_PRIORITY:
        this.towardsPlayerMoveWeight = newWeight;
        break;
      case AWAY_FROM_PLAYERS_MOVE_PRIORITY:
        this.awayFromPlayersMoveWeight = newWeight;
        break;
      case WALL_AND_ONE_PLAYER_MOVE_PRIORITY:
        this.wallAndOnePlayerMoveWeight = newWeight;
        break;
      case LAST_MOVE_PRIORITY:
        this.lastMoveWeight = newWeight;
        break;
      default:
        break;
    }
  }
  
  public WeightStore mutate()
  {
    WeightStore ws = clone();
    
    Random rand = new Random();
    
    int numWeight = ws.getNumWeights();
    int numMutations = rand.nextInt(numWeight) + 1; 
    
    for(int ii=0; ii < numMutations; ii++)
    {
      int whichMutation = rand.nextInt(numWeight) + 1; 
      double newPriority = WeightStore.generateNewPriority(); 
      
      ws.changeWeight(ExecutePriorityEnum.getId(whichMutation), 
          newPriority);
    }
    
    return ws;
  }
  
  public static double generateNewPriority()
  {
    Random r = new Random();
    return r.nextInt(20) - 10;
  }
  
  public double getLastMoveWeight()
  {
    return lastMoveWeight;
  }
  
  public double getShieldWeight()
  {
    return shieldWeight;
  }

  public double getHealthWeight()
  {
    return healthWeight;
  }

  public double getFireWeaponWeight()
  {
    return fireWeaponWeight;
  }

  public double getMultiPlayerMoveWeight()
  {
    return multiPlayerMoveWeight;
  }

  public double getTowardsWallMoveWeight()
  {
    return towardsWallMoveWeight;
  }

  public double getTowardsPlayerMoveWeight()
  {
    return towardsPlayerMoveWeight;
  }

  public double getAwayFromPlayersMoveWeight()
  {
    return awayFromPlayersMoveWeight;
  }

  public double getWallAndOnePlayerMoveWeight()
  {
    return wallAndOnePlayerMoveWeight;
  }
}
