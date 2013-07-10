package org.botgame.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.botgame.db.EMF;
import org.botgame.db.LoadDatabase;
import org.botgame.db.entity.ArenaResultEntity;
import org.botgame.db.entity.BotEntity;
import org.botgame.db.entity.PlayerEntity;
import org.botgame.game.bot.Bot;
import org.botgame.game.fight.BotsGA;
import org.botgame.game.fight.FightingBot;
import org.botgame.game.fight.WeightStore;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class BotsgameServlet extends HttpServlet {

	ArenaResultEntity arenaResultEntity;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		resp.setContentType("text/plain");
		StringBuilder winner = new StringBuilder();
		//winner.append(arenaResultEntity.getWinner() + " won the fight!");

		resp.getWriter().println(winner);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		EntityManager em = EMF.get().createEntityManager();
		PlayerEntity p = em.find(PlayerEntity.class, user.getNickname());

		arenaResultEntity = arenaFight(p.getPlayerBot().getBot());
//		em.persist(arenaResultEntity);
//		em.close();
		doGet(req, resp);
	}

	private ArenaResultEntity arenaFight(Bot playerBot) 
	{
	  List<Bot> botList = getBots();
	  botList.add(playerBot);
		UUID uid = UUID.randomUUID();
		
		List<FightingBot> winnerList = new ArrayList<FightingBot>();
		for(int ii =0; ii < 10; ii++)
		{
		  BotsGA ga = new BotsGA(uid.toString());
		  ga.setTrainingPlayer(playerBot);
		  ga.setBots(botList);
		  ga.initialBreed();
		  ga.startFight();
		  
		  winnerList.add(ga.getTrainingBot());
		  
		}
		
		for(FightingBot fb : winnerList)
		  System.out.println("Bot " + playerBot.getName() + "fitness:" + fb.getBotFitnessScore());


		return null;//ga.getArenaResult();
	}
	
	private List<Bot> getBots()
	{
	   Bot slamBot = LoadDatabase.constructBot("SLAM").getBot();
	    slamBot.setID(KeyFactory.createKey(BotEntity.class.getSimpleName(),
	        "AI-Slam-Bot"));
	    Bot glamBot = LoadDatabase.constructBot("GLAM").getBot();
	    glamBot.setID(KeyFactory.createKey(BotEntity.class.getSimpleName(),
	        "AI-Glam-Bot"));
	    Bot bamBot = LoadDatabase.constructBot("BAM").getBot();
	    bamBot.setID(KeyFactory.createKey(BotEntity.class.getSimpleName(),
	        "AI-Bam-Bot"));
	    Bot hamBot = LoadDatabase.constructBot("HAM").getBot();
      slamBot.setID(KeyFactory.createKey(BotEntity.class.getSimpleName(),
          "AI-Ham-Bot"));
      Bot manBot = LoadDatabase.constructBot("MAN").getBot();
      glamBot.setID(KeyFactory.createKey(BotEntity.class.getSimpleName(),
          "AI-Man-Bot"));
      Bot damBot = LoadDatabase.constructBot("DAM").getBot();
      bamBot.setID(KeyFactory.createKey(BotEntity.class.getSimpleName(),
          "AI-Dam-Bot"));
      Bot tanBot = LoadDatabase.constructBot("TAN").getBot();
      slamBot.setID(KeyFactory.createKey(BotEntity.class.getSimpleName(),
          "AI-Tan-Bot"));
      Bot jamBot = LoadDatabase.constructBot("JAM").getBot();
      glamBot.setID(KeyFactory.createKey(BotEntity.class.getSimpleName(),
          "AI-Jam-Bot"));
      Bot wamBot = LoadDatabase.constructBot("WAM").getBot();
      bamBot.setID(KeyFactory.createKey(BotEntity.class.getSimpleName(),
          "AI-Wam-Bot"));
      Bot yamBot = LoadDatabase.constructBot("YAM").getBot();
      slamBot.setID(KeyFactory.createKey(BotEntity.class.getSimpleName(),
          "AI-Yam-Bot"));
      Bot fanBot = LoadDatabase.constructBot("FAN").getBot();
      glamBot.setID(KeyFactory.createKey(BotEntity.class.getSimpleName(),
          "AI-Fan-Bot"));

	    List<Bot> botList = new ArrayList<Bot>();
	    botList.add(slamBot);
	    botList.add(glamBot);
	    botList.add(bamBot);
	    botList.add(hamBot);
	    botList.add(manBot);
	    botList.add(damBot);
	    botList.add(tanBot);
	    botList.add(jamBot);
	    botList.add(wamBot);
	    botList.add(yamBot);
	    botList.add(fanBot);
	    
	    return botList;
	}
}
