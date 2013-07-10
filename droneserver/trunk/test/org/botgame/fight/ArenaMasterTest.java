package org.botgame.fight;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.botgame.game.arena.Arena;
import org.botgame.game.bot.Bot;
import org.botgame.game.bot.parts.Armor;
import org.botgame.game.bot.parts.Chassis;
import org.botgame.game.bot.parts.Weapon;
import org.botgame.game.fight.ArenaMaster;
import org.botgame.game.util.Damage;
import org.botgame.game.util.DamageType;
import org.junit.Test;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;

public class ArenaMasterTest extends TestCase {

	@Test
	public void testFight() 
	{
    Frame simpleFrame = new Frame("SimpleFrame", 200, 1);
    Damage d = new Damage(5 + (int) (Math.random() * 5.0), DamageType.BASIC);
    Weapon simpleWeapon = new Weapon("SimpleWeapon", d, 10);

		Bot jdg = new Bot(simpleFrame, "JDG");
		jdg.setWeapon(0, simpleWeapon);
		jdg.setArmor(heavyArmor);

		Bot rjb = new Bot(simpleFrame, "RJB");
		rjb.setWeapon(0, simpleWeapon);
		rjb.setArmor(simpleArmor);

		List<Bot> botList = new ArrayList<Bot>();
		botList.add(jdg);
		botList.add(rjb);

		ArenaMaster tehMaster = new ArenaMaster(new Arena(),
				"King of the Drones");

		tehMaster.setBots(botList);

		tehMaster.startFight();

		FileService fileService = FileServiceFactory.getFileService();

		BlobKey blobKey = new BlobKey(tehMaster.getArenaResult()
				.getLogLocation());

		BlobstoreService blobService = BlobstoreServiceFactory
				.getBlobstoreService();
		BlobInfo bi = new BlobInfoFactory().loadBlobInfo(blobKey);
		System.out.println(blobService.fetchData(blobKey, 0, bi.getSize()));

	}
}
