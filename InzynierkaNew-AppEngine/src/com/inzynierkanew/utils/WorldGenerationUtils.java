package com.inzynierkanew.utils;

import java.util.HashMap;
import java.util.Map;

import com.inzynierkanew.endpoints.map.FieldTypeEndpoint;
import com.inzynierkanew.entities.map.Land;
import com.inzynierkanew.world.WorldGenerator;

public abstract class WorldGenerationUtils {

	private static Map<Integer, String> printableSymbols;

	public static void init(Map<Integer, String> newPrintableSymbols) {
		printableSymbols = newPrintableSymbols;
	}

	private WorldGenerationUtils() {

	}

	public static String mapToString(int[][] map) {
		return mapToString(map, true);
	}

	public static String mapToString(int[][] map, boolean axis) {
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
