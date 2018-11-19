package com.towcent.base.common.utils;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

@SuppressWarnings("serial")
public class CumulativeStats implements Cloneable,Serializable {
	/**
	 * 查询
	 */
	AtomicLong lookups = new AtomicLong();
	/**
	 * 命中
	 */
	AtomicLong hits = new AtomicLong();
	/**
	 * 插入
	 */
	AtomicLong inserts = new AtomicLong();
	/**
	 * 移除
	 */
	AtomicLong evictions = new AtomicLong();
	
	@Override
	public CumulativeStats clone(){
		CumulativeStats _stats = new CumulativeStats();
		_stats.lookups.set(this.lookups.longValue());
		_stats.hits.set(this.hits.longValue());
		_stats.inserts.set(this.inserts.longValue());
		_stats.evictions.set(this.evictions.longValue());
		return _stats;
	}

	public AtomicLong getLookups() {
		return lookups;
	}

	public AtomicLong getHits() {
		return hits;
	}

	public AtomicLong getInserts() {
		return inserts;
	}

	public AtomicLong getEvictions() {
		return evictions;
	}
	
	
}
