package com.towcent.base.common.utils;

import com.towcent.base.common.enums.CacheStateEnum;

import java.util.*;

/**
 * LRUCache 你懂得<br>
 * 
 * @author 
 * 
 */
class LRUCache extends LocalCache {

	private Map<String,CacheHolder> map;
	
	@SuppressWarnings("serial")
	LRUCache(final int maxSize , int initialSize){
		initialSize = Math.min(maxSize, initialSize);
		map = Collections.synchronizedMap(new LinkedHashMap<String, CacheHolder>(initialSize, 0.75f, true){
			protected boolean removeEldestEntry(Map.Entry<String,CacheHolder> eldest){
				if(size()>maxSize){
					stats.evictions.incrementAndGet();
					return true;
				}
				return false;
			}
		});
		super.state = CacheStateEnum.LRU;
	}
	@Override
	public Object get(String key){
		CacheHolder c = map.get(key);
		stats.lookups.incrementAndGet();
		if(c != null
				&& !c.isExpired()){
			stats.hits.incrementAndGet();
			return c.getValue();
		}
		return null;
	}
	
	public Object getAndProlong(String key,long expire){
		CacheHolder c = map.get(key);
		stats.lookups.incrementAndGet();
		if(c != null
				&& !c.isExpired()){
			c.setTimeout(System.currentTimeMillis()+expire);
			stats.hits.incrementAndGet();
			return c.getValue();
		}
		return null;
	}
	
	@Override
	public void put(String key, Object value, long expire){
		stats.inserts.incrementAndGet();
		map.put(key, new CacheHolder(value, expire + System.currentTimeMillis()));
	}
	@Override
	public void clear(){
		synchronized (map) {
			stats.inserts.addAndGet(size());
			map.clear();
		}
	}
	@Override
	public int size() {
		return map.size();
	}
	@Override
	Object remove(String key) {
		CacheHolder c = map.remove(key);
		if(null == c) {
			return null;
		}
		stats.evictions.incrementAndGet();
		return c.getValue();
	}
	@Override
	Set<String> keySet() {
		return new HashSet<String>(map.keySet());
	}
	@Override
	CacheHolder removeIfExpired(String key) {
		synchronized (map) {
			CacheHolder c = map.get(key);
			if(c !=null 
					&& c.isExpired()){
				stats.evictions.incrementAndGet();
				return map.remove(key);
			}
		}
		return null;
	}
	@Override
	void clearIfExpired() {
		Iterator<String> keys = this.keySet().iterator();
//		String key;
//		CacheHolder c;
//		synchronized (map) {
			while (keys.hasNext()) {
				removeIfExpired(keys.next());
//				key = keys.next();
//				c = map.get(key);
//				if(c !=null 
//						&& c.isExpired()){
//					map.remove(key);
//				}
			}
//		}
	}
	
	@Override
	boolean valideKey(String key) {
		CacheHolder c = map.get(key);
		stats.lookups.incrementAndGet();
		if(null != c && !c.isExpired()){
			stats.hits.incrementAndGet();
			return true;
		}
		return false;
	}
}
