package com.explorersguild.cron;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import com.explorersguild.utils.EMF;

public class ClearVisitScheduler extends HttpServlet {

	private static final long serialVersionUID = -8720183933525794276L;
	
	private Log log = LogFactory.getLog(getClass());
	
	public void clearVisits(){
		log.info("Removing dungeon and town visits...");
		clearEntities("DungeonVisit", "TownVisit");
		log.info("Visits removed. Units and monsters respawned.");
	}
	
	private void clearEntities(String... entityNames) {
		EntityManager manager = null;
		try {
			manager = EMF.get().createEntityManager();
			for (String entityName : entityNames) {
				if(!manager.createQuery("select from " + entityName + " as " + entityName).setMaxResults(1).getResultList().isEmpty()){
					log.info("Deleting from table " + entityName + "...");
					manager.createQuery("delete from " + entityName + " as " + entityName).executeUpdate();
				}
			}
		} finally {
			manager.close();
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		clearVisits();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
