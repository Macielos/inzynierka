package com.inzynierkanew.dumps;

import java.util.List;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import com.inzynierkanew.endpoints.map.LandEndpoint;
import com.inzynierkanew.entities.map.Land;
import com.inzynierkanew.utils.WorldGenerationUtils;
import com.inzynierkanew.world.WorldGenerator;

public class WorldDump {
	
	private final LandEndpoint landEndpoint;
	
	private Log log = LogFactory.getLog(getClass());
	
	public WorldDump(LandEndpoint landEndpoint){
		this.landEndpoint = landEndpoint;
	}
		
	public void dump(){
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int maxY = Integer.MIN_VALUE;
		
		List<Land> lands = (List<Land>) landEndpoint.listLand(null, null).getItems();
		for(Land land: lands){
			if(land.getMinX()<minX){
				minX = land.getMinX();
			}
			if(land.getMinY()<minY){
				minY = land.getMinY();
			}
			if(land.getMaxX()>maxX){
				maxX = land.getMaxX();
			}
			if(land.getMaxY()>maxY){
				maxY = land.getMaxY();
			}
			log.info("Land "+land.getId()+": "+land.getMinX()+"-"+land.getMaxX()+", "+land.getMinY()+"-"+land.getMaxY());
		}
		
		int height = maxY-minY+1;
		int width = maxX-minX+1;
		
		int[][] map = WorldGenerator.newMapSegment(height, width);
		
		for(int j=0; j<height; ++j){
			for(int i=0; i<width; ++i){
				map[j][i] = WorldGenerator.EMPTY;
			}
		}
		
		int i, x, y;
		int landWidth;
		for(Land land: lands){
			landWidth = land.getWidth();
			i = 0;
			for(int f: land.getFields()){
				x = i % landWidth;
				y = i / landWidth;
				if(f!=WorldGenerator.EMPTY){
					if(map[land.getMinY()-minY+y][land.getMinX()-minX+x]==WorldGenerator.EMPTY){
						map[land.getMinY()-minY+y][land.getMinX()-minX+x] = f;
					} else {
						map[land.getMinY()-minY+y][land.getMinX()-minX+x] = WorldGenerator.OVERLAPPING;
					}
				}
				++i;
			}
		}

		log.info("WORLD DUMP: "+minX+"-"+maxX+", "+minY+"-"+maxY);
		log.info(WorldGenerationUtils.mapToString(map));
	}
	
}
