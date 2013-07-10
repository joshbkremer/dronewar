package org.botgame.servlet;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.botgame.db.EMF;
import org.botgame.db.entity.ArenaResultEntity;

public class ArenaResults extends HttpServlet
{
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException
  {
    
  }
    
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException
  {
    EntityManager em = EMF.get().createEntityManager();
    Query q = em.createQuery( "SELECT e FROM ArenaResultEntity e");
    List<ArenaResultEntity> list = q.getResultList();
    System.out.println("List size: "+list.size());
    em.close();
    
    req.setAttribute("ArenaResultEntitys", list);
    getServletContext().getRequestDispatcher("/arenaResults.jsp").forward(req, resp);
  }
}
