package com.towcent.base.common.utils;

import com.towcent.base.common.enums.CacheStateEnum;

import java.util.Set;

abstract class LocalCache {
	
	protected final CumulativeStats stats = new CumulativeStats();

	protected CacheStateEnum state;

	/**
	 * 有效的key
	 * 包含并且未过期
	 * @param key
	 * @return
	 */
	abstract boolean valideKey(String key);
	
	/**
	 * put 值
	 * @param key
	 * @param value
	 */
	abstract void put(String key,Object value,long expire);
	
	/**
	 * get 值
	 * @param key
	 * @return
	 */
	abstract Object get(String key);
	
	/**
	 * get 值 并延长超时时间
	 * @param key
	 * @param expire
	 * @return
	 */
	abstract Object getAndProlong(String key,long expire);
	
	/**
	 * 大小
	 * @return
	 */
	abstract int size();
	
	/**
	 * 移除
	 * @param key
	 * @return
	 */
	abstract Object remove(String key);
	
	/**
	 * 如果是过期的，则移除
	 * @param key
	 * @return
	 */
	abstract Object removeIfExpired(String key);
	
	/**
	 * 获得缓存 key Set
	 * @return
	 */
	abstract Set<String> keySet();
	
	/**
	 * 清空缓存
	 * 性能有消耗，慎用！
	 */
	abstract void clear();
	
	/**
	 * 清空过期缓存
	 */
	abstract void clearIfExpired();
	
	/**
	 * 获取缓存状态<br>
	 * STATIC OR LRU
	 * @return
	 */
	CacheStateEnum getState(){
		return state;
	}
	
	/**
	 * 获取缓存信息<br>
	 * 访问次数，命中次数，删除次数，添加次数等
	 * @return
	 */
	CumulativeStats getStats(){
		return stats.clone();
	}
	
}
