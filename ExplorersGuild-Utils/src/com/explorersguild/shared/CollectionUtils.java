package com.explorersguild.shared;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public abstract class CollectionUtils {

	private CollectionUtils(){
		
	}
	
	public static <T> boolean isEmpty(Collection<T> collection){
		return collection == null || collection.isEmpty();
	}
	
	public static <T> List<T> getNRandomElements(List<T> input, Integer limit){
		if(isEmpty(input)){
			return new ArrayList<>(0);
		}
		List<T> list = new ArrayList<>(input);
		Random random = new Random();
		if(limit == null){
			limit = list.size();
		}
		Set<T> randomElements = new HashSet<>(limit);
		T randomElement;
		while(!list.isEmpty() && randomElements.size()<limit){
			randomElement = list.get(random.nextInt(list.size()));
			randomElements.add(randomElement);
			list.remove(randomElement);
		}
		return new ArrayList<>(randomElements);
	}
}
