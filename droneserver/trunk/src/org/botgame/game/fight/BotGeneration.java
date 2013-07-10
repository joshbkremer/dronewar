package org.botgame.game.fight;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BotGeneration
{
  private List<FightingBot> fightingBots;
  private FightingBot trainingPlayer;
  
  public BotGeneration(List<FightingBot> fightingBots,
      FightingBot trainingPlayer)
  {
    this.trainingPlayer = trainingPlayer;
    this.fightingBots = fightingBots; 
    getFirstGenerationPriority();
  }
  
  public void newGeneration()
  {
    
  }
  
  public List<FightingBot> getBotsWithWeights()
  {
    return fightingBots;
  }
  
  public void getFirstGenerationPriority()
  {
    List<FightingBot> tempList = new ArrayList<FightingBot>();
    for(FightingBot fb : fightingBots)
    {
      if(trainingPlayer.getName().equals(fb.getName()))
        fb.setWeights(generateNewWeightStore());
      else
      {
        WeightStore ws = new WeightStore(5,0,10,0,0,5,0,0,0);
        fb.setWeights(ws);
      }
      tempList.add(fb);
    }
    fightingBots = tempList;
  }
  
  public WeightStore breedParents(WeightStore parent1, 
      WeightStore parent2)
  {
    WeightStore child = parent1.clone();
    
    Random rand = new Random();
    
    int numWeight = child.getNumWeights();
    int numMutations = rand.nextInt(numWeight) + 1; 
    
    for(int ii=0; ii < numMutations; ii++)
    {
      int whichMutation = rand.nextInt(numWeight) + 1; 
      double newPriority = parent2.getPriority(
          ExecutePriorityEnum.getId(whichMutation));
      
      child.changeWeight(ExecutePriorityEnum.getId(whichMutation), 
          newPriority);
    }
    
    return child; 
  }

  public WeightStore generateNewWeightStore()
  {
    WeightStore ws = new WeightStore(
        WeightStore.generateNewPriority(),
        WeightStore.generateNewPriority(),
        WeightStore.generateNewPriority(),
        WeightStore.generateNewPriority(),
        WeightStore.generateNewPriority(),
        WeightStore.generateNewPriority(),
        WeightStore.generateNewPriority(),
        WeightStore.generateNewPriority(),
        WeightStore.generateNewPriority());
    
    return ws;
  }
}
