package com.towcent.base.common.httpapi.example.common;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.TimeoutUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 访问Redis工具类
 * @author huangtao
 *
 */
public class TokenRedisUtil {
	
	protected static Logger logger = LoggerFactory.getLogger(TokenRedisUtil.class);
	
	//Redis服务器IP
    private static String ADDR = "120.24.169.143";
    //	private static String ADDR = "10.169.200.51";
    
    //Redis的端口号
    private static int PORT = 6379;
    
    //访问密码
    private static String AUTH = null;
    
    //可用连接实例的最大数目，默认值为8；
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    @SuppressWarnings("unused")
	private static int MAX_ACTIVE = 1024;
    
    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 200;
    
    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT = 10000;
    
    private static int TIMEOUT = 10000;
    
    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;
    
    private static JedisPool jedisPool = null;
    
    /**
     * 初始化Redis连接池
     */
    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 获取Jedis实例
     * @return
     */
    public synchronized static Jedis getJedis() {
    	Jedis jedis = null;
        try {
            if (jedisPool != null) {
                jedis = jedisPool.getResource();
                System.out.println(jedisPool.getNumActive());
            } 
        } catch (Exception e) {
        	logger.error("", e);
        } finally {
			returnResource(jedis);
		}
        return jedis;
    }
    
    /**
     * 释放jedis资源
     * @param jedis
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
    
    public static String getValue(String key) {
    	return getJedis().get(key);
    }
    
    public static void set(String key, String value) {
    	getJedis().set(key, value);
    }
    
    /**
     * 
     * @param key
     * @param timeout 过期时间(分钟)
     * @param value
     */
    public static void set(String key, long timeout, String value) {
    	getJedis().setex(key, (int)TimeoutUtils.toSeconds(timeout, TimeUnit.MINUTES), value);
    }
    
    public static void main(String[] args) {
    	TokenRedisUtil.set("ht:test:1", 1, "yes");
    	for (int i = 0; i < 300; i++) {
    		System.out.println(TokenRedisUtil.getValue("ht:test:1"));			
		}
    }
}
