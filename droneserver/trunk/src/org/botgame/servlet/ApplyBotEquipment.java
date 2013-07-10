package org.botgame.servlet;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.botgame.db.EMF;
import org.botgame.db.entity.ArmorEntity;
import org.botgame.db.entity.BotEntity;
import org.botgame.db.entity.FrameEntity;
import org.botgame.db.entity.PlayerEntity;
import org.botgame.db.entity.WeaponEntity;
import org.botgame.web.ParameterEnum;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class ApplyBotEquipment extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String baseURL = req
				.getRequestURL()
				.toString()
				.replace(req.getRequestURI().substring(1), req.getContextPath());

		resp.sendRedirect(baseURL
				+ "/botcontrol/botcontrols.jsp?msg=Bot equipment has not be saved, please try again");
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		EntityManager em = EMF.get().createEntityManager();
		PlayerEntity p = em.find(PlayerEntity.class, user.getNickname());

		BotEntity be = p.getPlayerBot();

		for (Object key : req.getParameterMap().keySet()) {
			ParameterEnum pe;
			try {
				pe = ParameterEnum.valueOf(key.toString().toUpperCase());
			} catch (IllegalArgumentException iae) {
				continue;
			}

			switch (pe) {
			case ARMOR:
				be.setArmorKey(KeyFactory.createKey(
						ArmorEntity.class.getSimpleName(),
						Long.parseLong(req.getParameter(key.toString()))));
				break;
			case FRAME:
				be.setFrameKey(KeyFactory.createKey(
						FrameEntity.class.getSimpleName(),
						Long.parseLong(req.getParameter(key.toString()))));
				break;
			case NAME:
				be.setName(req.getParameter(key.toString()));
				break;
			case WEAPON:
				be.setWeaponKey(0, KeyFactory.createKey(
						WeaponEntity.class.getSimpleName(),
						Long.parseLong(req.getParameter(key.toString()))));
				break;
			default:
				continue;
			}
		}

		em.persist(p);
		em.close();

		String baseURL = req
				.getRequestURL()
				.toString()
				.replace(req.getRequestURI().substring(1), req.getContextPath());

		resp.sendRedirect(baseURL
				+ "/botcontrol/botcontrols.jsp?msg=Bot equipment saved!");
	}
}
