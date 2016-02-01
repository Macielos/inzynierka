package com.explorersguild.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Statistics<T> {
	
	private Map<T, Integer> map;
	
	public Statistics() {
		this.map = new HashMap<>();
	}
	
	public Statistics(int initialCapacity) {
		this.map = new HashMap<>(initialCapacity);
	}

	public void increment(T key){
		add(key, 1);
	}
	
	public void add(T key, int value){
		Integer currentValue = map.get(key);
		map.put(key, currentValue == null ? value : new Integer(currentValue.intValue()+value));
	}
	
	public int get(T key){
		Integer value = map.get(key);
		return value == null ? 0 : value.intValue();
	}
	
	public T getMax(){
		if(map.isEmpty()){
			return null;
		}
		T key = null;
		int value = 0;
		for(Entry<T, Integer> entry: map.entrySet()){
			if(entry.getKey()!=null && entry.getValue()>=value){
				key = entry.getKey();
				value = entry.getValue();
			}
		}
		return key;
	}

	@Override
	public String toString() {
		return "Statistics [map=" + map + "]";
	}
}
