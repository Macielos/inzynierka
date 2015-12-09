package com.inzynierkanew.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.junit.Test;

import com.inzynierkanew.utils.CollectionUtils;

public class CollectionUtilsTest {

	private static final Log log = LogFactory.getLog(CollectionUtilsTest.class);
	
	@Test
	public void testNRandom(){
		List<Integer> ints = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
		nRandom(ints, 3);
	}
	
	private void nRandom(List<Integer> input, int limit){
		List<Integer> output = CollectionUtils.getNRandomElements(input, limit);
		for(Integer i: output){
			log.info(i+" ");
		}
	}
}
