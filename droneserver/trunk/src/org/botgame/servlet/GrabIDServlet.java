package org.botgame.servlet;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.botgame.db.EMF;
import org.botgame.db.LoadDatabase;
import org.botgame.db.entity.PlayerEntity;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class GrabIDServlet extends HttpServlet {
	private static final long serialVersionUID = 515179047911969247L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		LoadDatabase.go();

		EntityManager em = EMF.get().createEntityManager();
		PlayerEntity p = em.find(PlayerEntity.class, user.getNickname());

		if (p != null) {
			System.out.println("Entity exists!");
			System.out.println("Player name: " + p.getPlayerID());
			// System.out.println("Player name: " + p.getPlayerBot().getName());
		} else {
			System.out.println("Entity doesn't exist create one!");
			em = EMF.get().createEntityManager();

			PlayerEntity pe = new PlayerEntity(user.getNickname());
			pe.setBotEntity(LoadDatabase.constructBot(user.getNickname()
					+ "_bot"));
			// BotEntity b = constructBot(user);
			// pe.setBotEntity(b);

			em.persist(pe);
		}

		em.close();
		resp.sendRedirect("/bots.jsp");
	}

}
