package org.botgame.servlet;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.botgame.db.EMF;
import org.botgame.db.entity.ArmorEntity;
import org.botgame.db.entity.BotEntity;
import org.botgame.db.entity.FrameEntity;
import org.botgame.db.entity.PlayerEntity;
import org.botgame.db.entity.WeaponEntity;

import com.google.appengine.api.users.User;

public class GeneralInfo {

	@SuppressWarnings("unchecked")
	public static List<WeaponEntity> getAvailableWeapons(User u) {
		EntityManager em = EMF.get().createEntityManager();
		Query q = em.createQuery("SELECT e FROM WeaponEntity e");
		List<WeaponEntity> list = q.getResultList();
		em.close();

		return list;
	}

	@SuppressWarnings("unchecked")
	public static List<ArmorEntity> getAvailableArmor(User u) {
		EntityManager em = EMF.get().createEntityManager();
		Query q = em.createQuery("SELECT e FROM ArmorEntity e");
		List<ArmorEntity> list = q.getResultList();
		em.close();

		return list;
	}

	@SuppressWarnings("unchecked")
	public static List<FrameEntity> getAvailableFrame(User u) {
		EntityManager em = EMF.get().createEntityManager();
		Query q = em.createQuery("SELECT e FROM FrameEntity e");
		List<FrameEntity> list = q.getResultList();
		em.close();

		return list;
	}

	public static BotEntity getBotEntity(User u) {
		// ADD get bot info

		EntityManager em = EMF.get().createEntityManager();
		PlayerEntity pe = em.find(PlayerEntity.class, u.getNickname());
		BotEntity be = pe.getPlayerBot();
		em.close();
		return be;
	}
}
