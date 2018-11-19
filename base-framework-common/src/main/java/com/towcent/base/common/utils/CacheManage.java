package com.towcent.base.common.utils;

import com.towcent.base.common.enums.CacheExpireEnum;
import com.towcent.base.common.enums.CacheStateEnum;
import com.towcent.base.common.enums.CacheTypeEnum;

import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 简单的缓存类<br>
 * 
 * 由于多节点问题，会造成数据不一致，仅用于前端缓存非重要数据<br>
 * 
 * @author
 *
 */
public class CacheManage implements CacheManageMBean {

	private static final String CACHE_JMX_ENABLE = "cache.jmx.enable";

	private static final Logger log = Logger.getLogger(CacheManage.class.getName());

	//	private static ConcurrentHashMap<String,CacheHolder> cacheMap = new ConcurrentHashMap<String,CacheHolder>();
	/** JMX constant. */
	public static final String MBEAN_CACHE_MANAGER = "com.towcent.base.common.utils:type=CacheManage";
	/** JMX support. */
	private static MBeanServer mbs;
	/** JMX support. */
	@SuppressWarnings("unused")
	private MBeanInfo mBeanInfo;

	private static Map<CacheTypeEnum, LocalCache> caches;

	private static final int DEFAULE_LRU_MAX_SIZE = 10240;

	static {
		caches = new HashMap<CacheTypeEnum, LocalCache>();
		for (CacheTypeEnum o : CacheTypeEnum.values()) {
			if (CacheStateEnum.STATIC == o.getState()) {
				caches.put(o, new StaticCache(1024));
			} else if (CacheStateEnum.LRU == o.getState()) {
				caches.put(o, new LRUCache(CacheManage.getCacheMaxSizeProperties(o), 1024));
			}
		}
		//注册JMX
		String jmxEnable = System.getProperty(CACHE_JMX_ENABLE);
		if(new Boolean(jmxEnable)){
			registerUnregisterJMX(true);
		}
	}

	/**
	 * 是否包含key <br>
	 * 此方法仍然会查询map一次，所以为了提高性能，请通过get获取empty object判断是否存在
	 * @param key
	 * @param type
	 * @return
	 */
	@Deprecated
	public static boolean containsKey(String key, CacheTypeEnum type) {
		return getLocalCache(type).valideKey(key);
	}

	/**
	 * 存
	 * @param key
	 * @param value
	 * @param type
	 */
	public static void put(String key, Object value, CacheTypeEnum type) {
		put(key, value, type, type.getExpire());
	}

	/**
	 * 存，自定义过期时间
	 * @param key
	 * @param value
	 * @param type
	 * @param expire
	 */
	public static void put(String key, Object value, CacheTypeEnum type, CacheExpireEnum expire) {
		getLocalCache(type).put(key, value, expire.getExpire());
	}

	//	private static void put(String key,Object value,CacheExpireEnum expire){
	////		if(null == value)
	////			throw new NullPointerException("cache value is null");
	//		CacheHolder _c,ch=new CacheHolder(value,expire.getExpire()+System.currentTimeMillis());
	//		
	//		int i = 1000;// 临时记录，看是否会超过
	//		do{
	//			_c = cacheMap.get(key);
	//			if(_c == null){
	//				_c = cacheMap.putIfAbsent(key, ch);
	//				if(_c == null)
	//					return;
	//			}
	//			if(_c.equals(ch) && !_c.isExpired()){
	//				return;
	//			}
	//			if(cacheMap.replace(key, _c, ch))
	//				return;
	//			if(i--==0){// 临时记录，看是否会超过，后面去掉
	//				log.log(Level.SEVERE, "CacheManage put value loop over 1000 times");
	//				break;
	//			}
	//		}while(true);
	//	}

	/**
	 * 取
	 * @param key
	 * @param type
	 * @return
	 */
	public static Object get(String key, CacheTypeEnum type) {
		//		if(null == key)
		//			throw new NullPointerException("cache key is null");
		return getLocalCache(type).get(key);
	}

	public static Object getAndProLong(String key, CacheTypeEnum type) {
		return getLocalCache(type).getAndProlong(key, type.getExpire().getExpire());
	}

	//	private static Object get(String key){
	//		if(null == key)
	//			throw new NullPointerException("cache key is null");
	//		CacheHolder ch = cacheMap.get(key);
	//		if(ch != null && !ch.isExpired()){
	//			return ch.getValue();
	//		}
	//		return null;
	//	}

	/**
	 * 批量取
	 * @param keys
	 * @param type
	 * @return
	 */
	public static Map<String, Object> getAll(List<String> keys, CacheTypeEnum type) {
		if (null == keys) {
			return null;
		}
		Map<String, Object> ret = new HashMap<String, Object>();
		for (String key : keys) {
			ret.put(key, get(key, type));
		}
		return ret;
	}

	/**
	 * 批量存<br>
	 * 大数据量强制避免假死
	 * @param map
	 * @param type
	 */
	public static void putAll(Map<String, ?> map, CacheTypeEnum type) {
		if (null == map) {
			return;
		}
		int i = 0;
		for (Entry<String, ?> o : map.entrySet()) {
			put(o.getKey(), o.getValue(), type);
			if (++i % 1000 == 0) {
				try {
					Thread.sleep(0);
				} catch (InterruptedException e) {
					e.printStackTrace();
					log.log(Level.SEVERE, "CacheManage put all data field fail！", e);
					return;
				}
			}
		}
	}

	/**
	 * 清空某一类型值
	 * @param type
	 */
	public static void clear(CacheTypeEnum type) {
		getLocalCache(type).clear();
	}

	/**
	 * 清除所有缓存
	 */
	public void clearAll() {
		for (CacheTypeEnum type : CacheTypeEnum.values()) {
			clear(type);
		}
	}

	/**
	 * 清空某一给定值
	 * @param key
	 * @param type
	 */
	public static void remove(String key, CacheTypeEnum type) {
		getLocalCache(type).remove(key);
	}

	/**
	 * 某类型缓存数量
	 * @param type
	 * @return
	 */
	public static int size(CacheTypeEnum type) {
		if (null == type) {
			throw new NullPointerException("type is null");
		}
		return getLocalCache(type).size();
	}

	//	/**
	//	 * 某类型有效缓存数量
	//	 * @param type
	//	 * @return
	//	 */
	//	public static int validSize(CacheTypeEnum type){
	//		if(null == type)
	//			throw new NullPointerException("type is null");
	//		Iterator<String> ite = new HashSet<String>(cacheMap.keySet()).iterator();
	//		String _key,typePrix = type.name()+":";
	//		CacheHolder _c;
	//		int count = 0;
	//		while (ite.hasNext()) {
	//			_key = ite.next();
	//			if(null != _key && _key.indexOf(typePrix) == 0){
	//				_c = cacheMap.get(_key);
	//				if(_c !=null && !_c.isExpired())
	//					count++;
	//			}
	//				
	//		}
	//		return count;
	//	}

	public static CumulativeStats getStats(CacheTypeEnum type) {
		return getLocalCache(type).getStats();
	}

	////////////////////////////////////////////////////////////////

	private static Thread cleanThread;

	public static void startCleanExpiredThread() {
		if (null == cleanThread) {
			cleanThread = new Thread(new ResetValue());
		}
		cleanThread.start();
	}

	public static void shutdownCleanExpiredThread() {
		if (null != cleanThread) {
			cleanThread.interrupt();
		}
		ResetValue.setStopFlagTrue();
	}

	/**
	 * 每天1:00清空一次过期数据<br>
	 * 仅清空 STATIC
	 */
	private static class ResetValue implements Runnable {
		private volatile static boolean RUN_FLAG;
		private volatile static boolean LOOP_FLAG;
		private volatile static boolean STOP_FLAG;

		@Override
		public void run() {
			if (RUN_FLAG) {
				return;
			}
			RUN_FLAG = true;
			long diff = calcNextDayTimeInMillis(1) - System.currentTimeMillis();
			//			diff = 10000;
			do {
				try {
					Thread.sleep(diff);
				} catch (InterruptedException e) {
					log.log(Level.WARNING, "CacheManage clean expired thread stop!");
					break;
				}
				if (LOOP_FLAG) {
					continue;
				}
				LOOP_FLAG = true;
				for (CacheTypeEnum type : CacheTypeEnum.values()) {
					if (CacheStateEnum.STATIC != type.getState()) {
						continue;
					}
					getLocalCache(type).clearIfExpired();
					try {
						Thread.sleep(0);
					} catch (InterruptedException e) {
						log.log(Level.WARNING, "CacheManage clean expired thread stop!");
						break;
					}
				}
				LOOP_FLAG = false;
				diff = calcNextDayTimeInMillis(1) - System.currentTimeMillis();
				//				diff = 10;
			} while (!STOP_FLAG);
			RUN_FLAG = false;
			STOP_FLAG = false;
		}

		public static void setStopFlagTrue() {
			STOP_FLAG = true;
		}
	}

	private static long calcNextDayTimeInMillis(int hourInDay) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MILLISECOND, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.HOUR_OF_DAY, hourInDay);
		c.add(Calendar.DAY_OF_MONTH, 1);
		return c.getTimeInMillis();
	}

	/**
	 * 获取cache
	 * @param type
	 * @return
	 */
	private static LocalCache getLocalCache(CacheTypeEnum type) {
		return caches.get(type);
	}

	@SuppressWarnings("unused")
	private static int getCacheMaxSizeProperties(CacheTypeEnum type) {
		if (null == type) {
			return DEFAULE_LRU_MAX_SIZE;
		}
		//"search.cache.maxsize.mkt_tab_item_count"
		String value = null;//自定义大小
		if (null == value || value.length() == 0) {
			return DEFAULE_LRU_MAX_SIZE;
		}
		for (int i = 0, len = value.length(); i < len; i++) {
			if (!Character.isDigit(value.charAt(i))) {
				return DEFAULE_LRU_MAX_SIZE;
			}
		}
		try {
			return Integer.valueOf(value);
		} catch (Exception e) {// ignore this exception
		}
		return DEFAULE_LRU_MAX_SIZE;
	}

	/**
	 * 注册JMX
	 * @param doRegister
	 */
	public static void registerUnregisterJMX(boolean doRegister) {
		if (mbs == null) { // this way makes it easier for mocking.
			mbs = ManagementFactory.getPlatformMBeanServer();
		}

		try {
			ObjectName name = new ObjectName(MBEAN_CACHE_MANAGER);

			if (doRegister) {
				if (!mbs.isRegistered(name)) {
					mbs.registerMBean(new CacheManage(), name);
				}
			} else {
				if (mbs.isRegistered(name)) {
					mbs.unregisterMBean(name);
				}
			}
		} catch (Exception e) {
			log.log(Level.CONFIG, "Unable to start/stop JMX", e);
		}
	}
}
