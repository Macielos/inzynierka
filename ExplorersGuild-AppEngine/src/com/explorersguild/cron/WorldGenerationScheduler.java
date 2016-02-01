package com.explorersguild.cron;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import com.explorersguild.world.WorldGenerator.GenerationOutcome;
import com.explorersguild.world.WorldGeneratorFactory;

public class WorldGenerationScheduler extends HttpServlet {

	private static final long serialVersionUID = -8720183933525794276L;
	
	private Log log = LogFactory.getLog(getClass());
	
	public void fireWorldGeneration(){
		log.info("Firing world generation...");
		GenerationOutcome outcome = WorldGeneratorFactory.fireWorldGeneration();
		log.info("World generation completed with result: "+outcome.name());
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		fireWorldGeneration();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
