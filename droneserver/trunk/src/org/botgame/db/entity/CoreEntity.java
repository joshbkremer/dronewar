package org.botgame.db.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.botgame.game.bot.parts.Core;

import com.google.appengine.api.datastore.Key;

@Entity
public class CoreEntity
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Key key;
  
  private String coreName;
  private int energyStorage;
  private int energyRegenRate;
  
  public CoreEntity(Core playerCore)
  {
    this.coreName = playerCore.getCoreName();
    this.energyStorage = playerCore.getEnergyStorage();
    this.energyRegenRate = playerCore.getEnergyRegenRate();
  }
  
  public Key getKey()
  {
    return key;
  }
  
  public Core getCore()
  {
    Core c = new Core(coreName, energyStorage, energyRegenRate);
    return c;
  }

}
