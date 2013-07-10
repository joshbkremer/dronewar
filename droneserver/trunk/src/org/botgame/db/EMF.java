package org.botgame.db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.botgame.db.entity.ArmorEntity;
import org.botgame.db.entity.CoreEntity;
import org.botgame.db.entity.DamageTypeEntity;
import org.botgame.db.entity.FrameEntity;
import org.botgame.db.entity.ShieldEntity;
import org.botgame.db.entity.WeaponEntity;

public class EMF {

	/**
	 * Code from:
	 * https://developers.google.com/appengine/docs/java/datastore/jpa
	 * /overview-dn2
	 */

	private static final EntityManagerFactory emfInstance = Persistence
			.createEntityManagerFactory("transactions-optional");

	private EMF() {
	}

	public static EntityManagerFactory get() {
		return emfInstance;
	}

	public static List<WeaponEntity> findWeaponEntity(String name) {
		EntityManager em = emfInstance.createEntityManager();
		Query weaponQuery = em
				.createQuery("select e from WeaponEntity e where e.weaponName='"
						+ name + "'");
		List<WeaponEntity> wl = weaponQuery.getResultList();
		em.close();
		return wl;
	}

	public static List<ArmorEntity> findArmorEntity(String name) {
		EntityManager em = emfInstance.createEntityManager();
		Query armorQuery = em
				.createQuery("select e from ArmorEntity e where e.armorName='"
						+ name + "'");
		List<ArmorEntity> al = armorQuery.getResultList();
		em.close();
		return al;
	}

	public static List<FrameEntity> findFrameEntity(String name) {
		EntityManager em = emfInstance.createEntityManager();
		Query frameQuery = em
				.createQuery("select e from FrameEntity e where e.frameName='"
						+ name + "'");
		List<FrameEntity> fl = frameQuery.getResultList();
		em.close();
		return fl;
	}

	public static List<CoreEntity> findCoreEntity(String name) {
		EntityManager em = emfInstance.createEntityManager();
		Query coreQuery = em
				.createQuery("select e from CoreEntity e where e.coreName='"
						+ name + "'");
		List<CoreEntity> fl = coreQuery.getResultList();
		em.close();
		return fl;
	}

	public static List<ShieldEntity> findShieldEntity(String name) {
		EntityManager em = emfInstance.createEntityManager();
		Query shieldQuery = em
				.createQuery("select e from ShieldEntity e where e.shieldName='"
						+ name + "'");
		List<ShieldEntity> fl = shieldQuery.getResultList();
		em.close();
		return fl;
	}

	public static DamageTypeEntity findDamageTypeEntity(String name) {
		EntityManager em = emfInstance.createEntityManager();
		Query frameQuery = em
				.createQuery("select e from DamageTypeEntity e where e.damageType='"
						+ name + "'");
		List<DamageTypeEntity> fl = frameQuery.getResultList();
		em.close();
		return fl.get(0);
	}
}
