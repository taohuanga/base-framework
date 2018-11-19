package com.towcent.base.common.redis;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>(目前只增加了部分常用操作，特殊需要的可以自己添加)</p>
 * <p><b>redis client,封装RedisTemplate操作到一个类中,并保持与redis commands 命名一致</b></p>
 */
@SuppressWarnings("unchecked")
public class RedisTemplateExt<K, V> {

	public static final long DEFAULT_TIMEOUT = 60;

	public static final TimeUnit DEFAULT_TIMEUNIT = TimeUnit.MINUTES;

	private RedisTemplate<K, V> redisTemplate;

	public RedisTemplateExt(RedisTemplate<K, V> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 获取redis模版类
	 * @return
	 */
	public RedisTemplate<K, V> getTemplate() {
		return this.redisTemplate;
	}

	/**------------------------------Serializer Settings-------------------------*/
	public void setDefaultSerializer(RedisSerializer<?> serializer) {
		getTemplate().setDefaultSerializer(serializer);
	}

	public void setKeySerializer(RedisSerializer<?> serializer) {
		getTemplate().setKeySerializer(serializer);
	}

	public RedisSerializer<?> getKeySerializer() {
		return getTemplate().getKeySerializer();
	}

	public void setValueSerializer(RedisSerializer<?> serializer) {
		getTemplate().setValueSerializer(serializer);
	}

	public RedisSerializer<?> getValueSerializer() {
		return getTemplate().getValueSerializer();
	}

	public RedisSerializer<?> getHashKeySerializer() {
		return getTemplate().getHashKeySerializer();
	}

	public void setHashKeySerializer(RedisSerializer<?> hashKeySerializer) {
		getTemplate().setHashKeySerializer(hashKeySerializer);
	}

	public RedisSerializer<?> getHashValueSerializer() {
		return getTemplate().getHashValueSerializer();
	}

	public void setHashValueSerializer(RedisSerializer<?> hashValueSerializer) {
		getTemplate().setHashValueSerializer(hashValueSerializer);
	}

	public RedisSerializer<String> getStringSerializer() {
		return getTemplate().getStringSerializer();
	}

	public void setStringSerializer(RedisSerializer<String> stringSerializer) {
		getTemplate().setStringSerializer(stringSerializer);
	}

	public <T> T exec(RedisCallback<T> callbak) {
		return getTemplate().execute(callbak);
	}

	public <T> T exec(SessionCallback<T> callbak) {
		return getTemplate().execute(callbak);
	}

	/**------------------------------Operator Key-------------------------*/
	/**
	 * 设置key-value失效时间
	 * @param key
	 * @param timeout
	 * @param unit
	 * @return
	 */
	public boolean expire(K key, long timeout, TimeUnit unit) {
		return getTemplate().expire(key, timeout, unit);
	}

	/**
	 * 设置key-value失效时间(默认时间单位为秒)
	 * @param key
	 * @param timeout
	 * @return
	 */
	public boolean expire(K key, long timeout) {
		return expire(key, timeout, TimeUnit.SECONDS);
	}

	/**
	 * 检查key是否存在缓存	
	 * @param key
	 * @return
	 */
	public boolean exists(K key) {
		return getTemplate().hasKey(key);
	}

	/**
	 * 删除缓存记录
	 * @param key
	 */
	public void del(K key) {
		getTemplate().delete(key);
	}

	/**
	 * 批量删除记录
	 * @param keys
	 * @return
	 */
	public void del(Set<K> keys) {
		getTemplate().delete(keys);
	}

	/**
	 * 重命名key
	 * @param keys
	 * @return
	 */
	public void rename(K oldKey, K newKey) {
		getTemplate().rename(oldKey, newKey);
	}

	/**------------------------------Operator String-------------------------*/

	/**
	 * 设置对象类型缓存项，默认一小时
	 * @param key
	 * @param value
	 */
	public void set(K key, V value) {
		set(key, value, DEFAULT_TIMEOUT);
	}

	/**
	 * 设置对象类型缓存项，单位为分钟
	 * @param key
	 * @param value
	 */
	public void set(K key, V value, long timeout) {
		set(key, value, timeout, DEFAULT_TIMEUNIT);
	}

	/**
	 * 设置对象类型缓存项，加入失效时间
	 * @param key
	 * @param value
	 * @param exp
	 */
	public void set(K key, V value, long timeout, TimeUnit timeUnit) {
		getTemplate().opsForValue().set(key, value, timeout, timeUnit);
	}

	/**
	 * 批量设置对象类型缓存项
	 * @param key
	 * @param value
	 * @param exp
	 */
	public void mset(Map<? extends K, V> m) {
		getTemplate().opsForValue().multiSet(m);
	}

	/**
	 * 在后面附加值
	 * @param key
	 * @param value
	 */
	public void append(K key, String value) {
		getTemplate().opsForValue().append(key, value);
	}

	/**
	 * 获取对象值
	 * @param key
	 * @return
	 */
	public V get(K key) {
		return getTemplate().opsForValue().get(key);
	}

	/**
	 * 批量获取对象值
	 * @param key
	 * @return
	 */
	public List<V> mget(Collection<K> keys) {
		return getTemplate().opsForValue().multiGet(keys);
	}

	/**
	 * 返回旧值设置新值
	 * @param key
	 * @return
	 */
	public V getSet(K key, V newValue) {
		return getTemplate().opsForValue().getAndSet(key, newValue);
	}

	/**
	 * 返回值的长度
	 * @param key
	 * @return
	 */
	public long strLen(K key) {
		return getTemplate().opsForValue().size(key);
	}

	/**
	 * 加1操作
	 * @param key
	 * @return 返回操作后的值
	 */
	public long incr(K key) {
		return incr(key, 1);
	}

	/**
	 * 加操作，指定加的量
	 * @param key
	 * @param num
	 * @return 返回操作后的值
	 */
	public long incr(K key, long num) {
		return getTemplate().opsForValue().increment(key, num);
	}

	/**
	 * 减1操作
	 * @param key
	 * @return 返回操作后的值
	 */
	public long decr(K key) {
		return decr(key, 1);
	}

	/**
	 * 减操作，指定减的值
	 * @param key
	 * @param num
	 * @return 返回操作后的值
	 */
	public long decr(K key, long num) {
		return getTemplate().opsForValue().increment(key, (-1 * num));
	}

	/**------------------------------Operator List-------------------------*/

	/**
	 * 获取列表大小
	 * @param listKey
	 * @return
	 */
	public long lLen(K key) {
		return getTemplate().opsForList().size(key);
	}

	/**
	 * 获取所有列表(默认从左边第一个开始)
	 * @param listKey
	 * @return
	 */
	public List<V> lAll(K key) {
		return getTemplate().opsForList().range(key, 0, lLen(key));
	}

	/**
	 * 从左边添加到list
	 * @param listKey
	 * @param value
	 */
	public void lPush(K key, V value) {
		getTemplate().opsForList().leftPush(key, value);
	}

	/**
	 * 从右边添加到list
	 * @param listKey
	 * @param value
	 */
	public void rPush(K key, V value) {
		getTemplate().opsForList().rightPush(key, value);
	}

	/**
	 * 从左边移除一个对象，并返回该对象
	 * @param listKey
	 * @return
	 */
	public Object lPop(K key) {
		return getTemplate().opsForList().leftPop(key);
	}

	/**
	 * 对一个列表中的对应位置的数据进行移除
	 * @param listKey
	 * @return
	 */
	public void lTrim(K key, long start, long end) {
		getTemplate().opsForList().trim(key, start, end);
	}

	/**
	 * 从右边移除一个对象，并返回该对象
	 * @param listKey
	 * @return
	 */
	public Object rPop(K key) {
		return getTemplate().opsForList().rightPop(key);
	}

	/**
	 * 获取list某一范围的段
	 * @param listKey
	 * @param start
	 * @param end
	 * @return
	 */
	public List<V> lRange(K key, long start, long end) {
		return getTemplate().opsForList().range(key, start, end);
	}

	/**
	 * 返回list中对应index的值
	 * @param listKey
	 * @param index
	 * @return
	 */
	public Object lIndex(K key, long index) {
		return getTemplate().opsForList().index(key, index);
	}

	/**
	 * 根据参数 count 的值，移除列表中与参数 value 相等的元素。
	 * @param listKey
	 * @param index
	 * @param value
	 * @return
	 */
	public long lRem(K key, long index, V value) {
		return getTemplate().opsForList().remove(key, index, value);
	}

	/**
	 * 将列表 key 下标为 index 的元素的值设置为 value 
	 * @param key
	 * @param index
	 * @param value
	 */
	public void lSet(K key, long index, V value) {
		getTemplate().opsForList().set(key, index, value);
	}

	/**
	 * 将列表 key 下标为 index 的元素的值设置为 value 
	 * @param key
	 * @param index
	 * @param value
	 */
	public void lSet(final K key, final List<V> values) {
		getTemplate().execute(new SessionCallback<Boolean>() {
			@Override
			@SuppressWarnings("all")
			public <K, V> Boolean execute(RedisOperations<K, V> operations) throws DataAccessException {
				operations.multi();
				BoundListOperations<K, V> boundListOperations = operations.boundListOps((K) key);
				if (!CollectionUtils.isEmpty(values)) {
					for (int i = 0; i < values.size(); i++) {
						V v = (V) values.get(i);
						boundListOperations.rightPush(v);
					}
				}
				operations.exec();
				return true;
			}
		});
	}

	/**------------------------------Operator Hash-------------------------*/

	/**
	 * 返回哈希表 key 是否存在
	 * @param key
	 * @return
	 */
	public long hLen(K key) {
		return getTemplate().opsForHash().size(key);
	}

	/**
	 * 返回哈希表 key 中，所有的域和值
	 * @param key
	 * @return
	 */
	public Map<Object, Object> hGetAll(K key) {
		return getTemplate().opsForHash().entries(key);
	}

	/**
	 * 返回哈希表 key 中给定域 hashKey 的值
	 * @param key
	 * @param hashKey
	 * @return
	 */
	public Object hGet(K key, Object hashKey) {
		return getTemplate().opsForHash().get(key, hashKey);
	}

	/**
	 * 查看哈希表 key 中，给定域 hashKey 是否存在
	 * @param key
	 * @param hashKey
	 * @return
	 */
	public boolean hExists(K key, Object hashKey) {
		return getTemplate().opsForHash().hasKey(key, hashKey);
	}

	/**
	 * 将哈希表 key 中的域 hashKey 的值设为 value
	 * @param mapKey
	 * @param field
	 * @param value
	 */
	public void hSet(K key, Object hashKey, V value) {
		getTemplate().opsForHash().put(key, hashKey, value);
	}

	/**
	 * 将哈希表 key 中的域 hashKey 的值设为 value
	 * @param mapKey
	 * @param field
	 * @param value
	 */
	public void hMSet(K key, Map<K, V> value) {
		getTemplate().opsForHash().putAll(key, value);
	}

	/**
	 * 删除哈希表 key 中的一个域，不存在的域将被忽略
	 * @param mapKey
	 * @param field
	 */
	public void hDel(K key, Object hashKey) {
		getTemplate().opsForHash().delete(key, hashKey);
	}

	/**
	 * 删除哈希表 key 中的多个指定域，不存在的域将被忽略
	 * @param mapKey
	 * @param field
	 */
	public void hDel(K key, Object... hashKeys) {
		getTemplate().opsForHash().delete(key, hashKeys);
	}

	/**
	 * 返回哈希表 key 中的所有域
	 * @param key
	 * @return
	 */
	public Set<Object> hKeys(K key) {
		return getTemplate().opsForHash().keys(key);
	}

	/**
	 * 返回哈希表 key 中所有域的值。
	 * @param key
	 * @return
	 */
	public List<Object> hVals(K key) {
		return getTemplate().opsForHash().values(key);
	}

	/**------------------------------Operator Set-------------------------*/

	/**
	 * 返回集合 key 中的所有成员,不存在的 key 被视为空集合
	 * @param key
	 * @return
	 */
	public Set<V> sMembers(K key) {
		return getTemplate().opsForSet().members(key);
	}

	/**
	 * 判断 member 元素是否集合 key 的成员
	 * @param key
	 * @param member
	 * @return
	 */
	public boolean sIsMember(K key, Object member) {
		return getTemplate().opsForSet().isMember(key, member);
	}

	/**
	 * 移除并返回集合中的一个随机元素
	 * @param key
	 * @return
	 */
	public Object sPop(K key) {
		return getTemplate().opsForSet().pop(key);
	}

	/**
	 * 移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略
	 * @param key
	 * @return
	 */
	public long sRem(K key, Object member) {
		return getTemplate().opsForSet().remove(key, member);
	}

	/**
	 * 将一个或多个 value 元素加入到集合 key 当中，已经存在于集合的 value 元素将被忽略
	 * @param key
	 * @param value
	 * @return
	 */
	public long sAdd(K key, V value) {
		return getTemplate().opsForSet().add(key, value);
	}

	/**
	 * 将一个或多个 value 元素加入到集合 key 当中，已经存在于集合的 value 元素将被忽略
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean sAdd(final K key, final Set<V> values) {
		return getTemplate().execute(new SessionCallback<Boolean>() {
			@Override
			@SuppressWarnings("all")
			public <K, V> Boolean execute(RedisOperations<K, V> operations) throws DataAccessException {
				operations.multi();
				BoundSetOperations<K, V> boundSetOperations = operations.boundSetOps((K) key);
				if (!CollectionUtils.isEmpty(values)) {
					for (Iterator iterator = values.iterator(); iterator.hasNext();) {
						V v = (V) iterator.next();
						boundSetOperations.add(v);
					}
				}
				operations.exec();
				return true;
			}
		});
	}

	/**
	 * 返回集合中元素的数量
	 * @param key
	 * @param value
	 * @return
	 */
	public long sCard(K key) {
		return getTemplate().opsForSet().size(key);
	}

	/**------------------------------Operator SortedSet-------------------------*/

	/**
	 * 返回有序集所有成员,其中成员的位置按 score 值递增(从小到大)来排序
	 * @param key
	 * @return
	 */
	public Set<V> zMembers(K key) {
		return getTemplate().opsForZSet().range(key, 0, zCard(key));
	}

	/**
	 * 返回有序集 key 中，指定区间内的成员,其中成员的位置按 score 值递增(从小到大)来排序
	 * @param key
	 * @return
	 */
	public Set<V> zRange(K key, long start, long end) {
		return getTemplate().opsForZSet().range(key, start, end);
	}

	/**
	 * 返回有序集中元素的数量
	 * @param key
	 * @param value
	 * @return
	 */
	public long zCard(K key) {
		return getTemplate().opsForZSet().size(key);
	}

	/**
	 * 将一个value 元素及其 score 值加入到有序集 key 当中
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean zAdd(K key, V value, double score) {
		return getTemplate().opsForZSet().add(key, value, score);
	}

	/**
	 * 移除有序集 key 中的一个成员，不存在的成员将被忽略
	 * @param key
	 * @param value
	 * @return 返回被移除的个数
	 */
	public long zRem(K key, V value) {
		return getTemplate().opsForZSet().remove(key, value);
	}
}
