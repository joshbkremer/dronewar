package org.botgame.game.fight;

import java.util.HashMap;
import java.util.Map;

public enum ExecutePriorityEnum
{
  SHIELD_PRIORITY(0),
  HEALTH_PRIORITY(1),
  FIRE_PRIORITY(2),
  MULTI_PLAYER_MOVE_PRIORITY(3),
  TOWARDS_WALL_MOVE_PRIORITY(4),
  TOWARDS_PLAYER_MOVE_PRIORITY(5),
  AWAY_FROM_PLAYERS_MOVE_PRIORITY(6),
  WALL_AND_ONE_PLAYER_MOVE_PRIORITY(7),
  LAST_MOVE_PRIORITY(8);
  
  private final int id;
  private static final Map<Integer, ExecutePriorityEnum> lookup 
      = new HashMap<Integer, ExecutePriorityEnum>();
  
  static
  {
    for(ExecutePriorityEnum epe : ExecutePriorityEnum.values())
      lookup.put(epe.id, epe);
  }
  
  ExecutePriorityEnum(int id)
  {
    this.id = id;
  }
  
  public static ExecutePriorityEnum getId(int id)
  {
    return lookup.get(id);
  }
  
  public static int getnumWeights()
  {
    return ExecutePriorityEnum.values().length;
  }
}

