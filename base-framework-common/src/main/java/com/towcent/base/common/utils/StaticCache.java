package com.towcent.base.common.utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

class StaticCache extends LocalCache {

	private static final Logger log = Logger.getLogger(StaticCache.class
			.getName());

	private final ConcurrentHashMap<String, CacheHolder> map;

	StaticCache(int initialSize) {
		map = new ConcurrentHashMap<String, CacheHolder>(initialSize, 0.75f);
	}

	@Override
	void put(String key, Object value , long expire) {
		int i = 1000;// 临时记录，看是否会超过
		CacheHolder _c,c=new CacheHolder(value, expire+System.currentTimeMillis());
		do {
			_c = map.get(key);
			if (_c == null) {
				_c = map.putIfAbsent(key, c);
				if (_c == null) {
					break;
				}
			}
			if (_c.equals(value) && !_c.isExpired()) {
				return;
			}
			if (map.replace(key, _c, c)) {
				break;
			}
			if (i-- == 0) {// 临时记录，看是否会超过，后面去掉
				log.log(Level.SEVERE,
						"CacheManage put value loop over 1000 times");
				return;
			}
		} while (true);
		stats.inserts.incrementAndGet();
	}

	@Override
	Object get(String key) {
		if (null == key) {
			throw new NullPointerException("cache key is null");
		}
		CacheHolder c = map.get(key);
		stats.lookups.incrementAndGet();
		if(c != null && !c.isExpired()){
			stats.hits.incrementAndGet();
			return c.getValue();
		}
		return null;
	}
	
	@Override
	Object getAndProlong(String key, long expire) {
		if (null == key) {
			throw new NullPointerException("cache key is null");
		}
		CacheHolder c = map.get(key);
		stats.lookups.incrementAndGet();
		if(c != null && !c.isExpired()){
			c.setTimeout(System.currentTimeMillis()+expire);
			stats.hits.incrementAndGet();
			return c.getValue();
		}
		return null;
	}

	@Override
	void clear() {
		synchronized (map) {
			stats.evictions.addAndGet(size());
			map.clear();
		}
	}

	@Override
	int size() {
		return map.size();
	}

	@Override
	Object remove(String key) {
		CacheHolder c = map.get(key);
		if(null == c) {
			return null;
		}
		if(map.remove(key, c)){
			stats.evictions.incrementAndGet();
			return c.getValue();
		}
		return null;
	}

	@Override
	Set<String> keySet() {
		return new HashSet<String>(map.keySet());
	}

	@Override
	Object removeIfExpired(String key) {
		CacheHolder c = map.get(key);
		if (null == c) {
			return null;
		}
		if (c.isExpired() 
				&& map.remove(key, c)){
			stats.evictions.incrementAndGet();
			return c.getValue();
		}
		return null;
	}

	@Override
	void clearIfExpired() {
		Iterator<String> keys = this.keySet().iterator();
//		String key;
//		CacheHolder c;
		while (keys.hasNext()) {
			removeIfExpired(keys.next());
//			key = keys.next();
//			c = cacheMap.get(key);
//			if (null != c && c.isExpired())
//				cacheMap.remove(key, c);
		}
		
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
