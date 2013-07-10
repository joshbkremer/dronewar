package org.botgame.db;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.botgame.db.entity.ArmorEntity;
import org.botgame.db.entity.BotEntity;
import org.botgame.db.entity.CoreEntity;
import org.botgame.db.entity.DamageTypeEntity;
import org.botgame.db.entity.FrameEntity;
import org.botgame.db.entity.ShieldEntity;
import org.botgame.db.entity.WeaponEntity;
import org.botgame.game.bot.parts.Armor;
import org.botgame.game.bot.parts.Core;
import org.botgame.game.bot.parts.Frame;
import org.botgame.game.bot.parts.Shield;
import org.botgame.game.bot.parts.Weapon;
import org.botgame.game.util.DamageType;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class LoadDatabase {

	public static String SIMPLE_ARMOR = "Simple Armor";
	public static String LIGHT_WEAPON = "Light Weapon";
	public static String LIGHT_FRAME = "Light Frame";
	public static String SIMPLE_CORE = "Simple Power Core";
	public static String SIMPLE_SHIELD = "Simple Shield";

	public static void go() {
		EntityManager em = EMF.get().createEntityManager();

		Query q = em
				.createQuery("select e from ArmorEntity e where e.armorName='Heavy Armor'");
		if (q.getResultList().size() > 0)
			return;

		// em.getTransaction().begin();
		//
		// DamageTypeEntity dte = new DamageTypeEntity(DamageType.BASIC);
		// em.persist(dte);
		// em.getTransaction().commit();

		em.getTransaction().begin();

		Armor noArmor = new Armor("No Armor", 0);
		Armor simpleArmor = new Armor("Simple Armor", 1);
		Armor mediumArmor = new Armor("Medium Armor", 2);
		Armor heavyArmor = new Armor("Heavy Armor", 3);

		ArmorEntity noAE = new ArmorEntity(noArmor);
		em.persist(noAE);
		em.getTransaction().commit();

		em.getTransaction().begin();
		ArmorEntity simpleAE = new ArmorEntity(simpleArmor);
		em.persist(simpleAE);
		em.getTransaction().commit();

		em.getTransaction().begin();
		ArmorEntity mediumAE = new ArmorEntity(mediumArmor);
		em.persist(mediumAE);
		em.getTransaction().commit();

		em.getTransaction().begin();
		ArmorEntity heavyAE = new ArmorEntity(heavyArmor);
		em.persist(heavyAE);
		em.getTransaction().commit();

		Frame lightFrame = new Frame("Light Frame", 100, 2);
		Frame mediumFrame = new Frame("Medium Frame", 200, 1);
		Frame heavyFrame = new Frame("Heavy Frame", 300, .5);

		em.getTransaction().begin();
		FrameEntity lightFE = new FrameEntity(lightFrame);
		em.persist(lightFE);
		em.getTransaction().commit();

		em.getTransaction().begin();
		FrameEntity mediumFE = new FrameEntity(mediumFrame);
		em.persist(mediumFE);
		em.getTransaction().commit();

		em.getTransaction().begin();
		FrameEntity heavyFE = new FrameEntity(heavyFrame);
		em.persist(heavyFE);
		em.getTransaction().commit();

		em.getTransaction().begin();
		Key dteKey = KeyFactory.createKey(
				DamageTypeEntity.class.getSimpleName(), 1);
		DamageTypeEntity dte = new DamageTypeEntity(DamageType.BASIC, dteKey);
		em.persist(dte);
		em.getTransaction().commit();

		em.getTransaction().begin();
		Shield simpleShield = new Shield("Simple Shield", 100, 1, 5);
		ShieldEntity simpleShieldEntity = new ShieldEntity(simpleShield);
		em.persist(simpleShieldEntity);
		em.getTransaction().commit();

		em.getTransaction().begin();
		Core simpleCore = new Core("Simple Power Core", 20, 1);
		CoreEntity simpleCoreEntity = new CoreEntity(simpleCore);
		em.persist(simpleCoreEntity);
		em.getTransaction().commit();

		Weapon lightWeapon = new Weapon("Light Weapon", 5, 10,
				DamageType.BASIC, 20);
		Weapon heavyWeapon = new Weapon("Heavy Weapon", 10, 20,
				DamageType.BASIC, 10);
		Weapon rangedWeapon = new Weapon("Ranged Weapon", 3, 5,
				DamageType.BASIC, 30);

		em.getTransaction().begin();
		WeaponEntity lightWE = new WeaponEntity(lightWeapon);
		lightWE.setDamageTypeKey(dteKey);
		em.persist(lightWE);
		em.getTransaction().commit();

		em.getTransaction().begin();
		WeaponEntity heavyWE = new WeaponEntity(heavyWeapon);
		heavyWE.setDamageTypeKey(dteKey);
		em.persist(heavyWE);
		em.getTransaction().commit();

		em.getTransaction().begin();
		WeaponEntity rangedWE = new WeaponEntity(rangedWeapon);
		rangedWE.setDamageTypeKey(dteKey);
		em.persist(rangedWE);
		em.getTransaction().commit();

		em.close();

		for (int i = 0; i < 5; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
	}

	public static BotEntity constructBot(String botName) {
		ArmorEntity ae = EMF.findArmorEntity(LoadDatabase.SIMPLE_ARMOR).get(0);
		WeaponEntity we = EMF.findWeaponEntity(LoadDatabase.LIGHT_WEAPON)
				.get(0);
		FrameEntity fe = EMF.findFrameEntity(LoadDatabase.LIGHT_FRAME).get(0);
		CoreEntity ce = EMF.findCoreEntity(LoadDatabase.SIMPLE_CORE).get(0);
		ShieldEntity se = EMF.findShieldEntity(LoadDatabase.SIMPLE_SHIELD).get(
				0);

		BotEntity be = new BotEntity(botName);
		be.setArmorKey(ae.getKey());
		be.setWeaponKey(0, we.getKey());
		be.setFrameKey(fe.getKey());
		be.setCoreKey(ce.getKey());
		be.setShieldKey(se.getKey());

		return be;
	}
}
