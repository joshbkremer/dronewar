package org.botgame.db.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.botgame.game.bot.parts.Shield;

import com.google.appengine.api.datastore.Key;

@Entity
public class ShieldEntity
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Key key;
  
  private String shieldName;
  private int damageAbsorb;
  private int rechargeAmount;
  private int rechargeCost;
  
  public ShieldEntity(Shield playerShield)
  {
    this.shieldName = playerShield.getShieldName();
    this.damageAbsorb = playerShield.getDamageAbsorb();
    this.rechargeAmount = playerShield.getRechargeAmount();
    this.rechargeCost = playerShield.getRechargeCost();
  }
  
  public Key getKey()
  {
    return key;
  }
  
  public Shield getShield()
  {
    Shield s = new Shield(shieldName, damageAbsorb, rechargeAmount, rechargeCost);
    return s;
  }

}
