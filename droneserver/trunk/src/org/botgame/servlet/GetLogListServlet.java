package org.botgame.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetLogListServlet extends HttpServlet {
	private static final long serialVersionUID = -3761590368038188591L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		//
		// Query q = em.createQuery("select ar from ArenaResult ar");
		//
		// List<ArenaResult> arList = q.getResultList();
		//
		// System.out.println(arList.size());
		//
		// for (ArenaResult ar : arList) {
		// System.out.println(ar.getFightTitle() + ": Winner - "
		// + ar.getParticipants().get(0));
		// }
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doGet(req, resp);
	}
}