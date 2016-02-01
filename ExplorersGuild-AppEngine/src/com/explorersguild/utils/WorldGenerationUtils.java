package com.explorersguild.utils;

import java.util.HashMap;
import java.util.Map;

import com.explorersguild.entities.map.Land;
import com.explorersguild.init.ApplicationInitializer;
import com.explorersguild.shared.SharedConstants;
import com.explorersguild.world.WorldGenerator;

public abstract class WorldGenerationUtils {

	private static Map<Integer, String> printableSymbols = createPrintableSymbols();

	private WorldGenerationUtils() {

	}
	
	public static void init(Map<Integer, String> newPrintableSymbols) {
		printableSymbols = newPrintableSymbols;
	}

	public static Map<Integer, String> createPrintableSymbols() {
		Map<Integer, String> printableSymbols = new HashMap<>();

		printableSymbols.put(WorldGenerator.EMPTY, " ");
		printableSymbols.put(WorldGenerator.EXISTING_LAND, "E");
		printableSymbols.put(WorldGenerator.CROSSROAD, "X");
		printableSymbols.put(WorldGenerator.OVERLAPPING, "V");

		for(int i: WorldGenerator.roadFieldTypes){
			printableSymbols.put(i, "+");
		}
		printableSymbols.put(SharedConstants.PASSAGE_HORIZONTAL_ID.intValue(), "I");
		printableSymbols.put(SharedConstants.PASSAGE_VERTICAL_ID.intValue(), "I");
		printableSymbols.put(SharedConstants.PORTAL_ID.intValue(), "I");
		printableSymbols.put(SharedConstants.TOWN_ID.intValue(), "T");
		printableSymbols.put(SharedConstants.DUNGEON_ID.intValue(), "D");
		printableSymbols.put((int)ApplicationInitializer.BASIC_PASSABLE_ID, ".");
		printableSymbols.put((int)ApplicationInitializer.BASIC_NONPASSABLE_ID, "^");
		printableSymbols.put((int)ApplicationInitializer.BASIC_NONPASSABLE_ID2, "^");
		printableSymbols.put((int)ApplicationInitializer.EXTRA_NONPASSABLE_ID, "^");
		printableSymbols.put((int)ApplicationInitializer.EXTRA_NONPASSABLE_ID2, "^");

		return printableSymbols;
	}
	
	public static String mapToString(int[][] map) {
		return mapToString(map, true);
	}

	public static String mapToString(int[][] map, boolean axis) {
		return mapToString(map, axis, printableSymbols);
	}
	
	public static String mapToString(int[][] map, Map<Integer, String> printableSymbols) {
		return mapToString(map, true, printableSymbols);
	}
	
	public static String mapToString(int[][] map, boolean axis, Map<Integer, String> printableSymbols) {
		StringBuilder sb = new StringBuilder("\n");
		if (axis) {
			sb.append("   ");
			for (int j = 0; j < map[0].length; ++j) {
				sb.append(j < 10 ? " " : "").append(j);
			}
			sb.append("\n");
		}

		for (int i = 0; i < map.length; ++i) {
			if (axis) {
				sb.append(i).append(i < 10 ? "  " : " ");
			}
			for (int j = 0; j < map[0].length; ++j) {
				sb.append(" ").append(printableSymbols.get(map[i][j]) == null ? map[i][j] : printableSymbols.get(map[i][j]));
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public static long calcMapSegment(Land land) {
		return calcMapSegment(land.getMinX(), land.getMinY(), land.getMaxX(), land.getMaxY());
	}

	public static long calcMapSegment(int minX, int minY, int maxX, int maxY) {
		return (minX + maxX) / (2 * 2 * WorldGenerator.LAND_MAX_WIDTH) * WorldGenerator.MAP_SEGMENT_FACTOR + (minY + maxY)
				/ (2 * 2 * WorldGenerator.LAND_MAX_HEIGHT);
	}
}
