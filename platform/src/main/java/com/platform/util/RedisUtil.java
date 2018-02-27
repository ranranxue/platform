package com.platform.util;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
	private static Logger logger = Logger.getLogger(RedisUtil.class);

	private static JedisPool pool = null;

	/**
	 * 构建redis连接池
	 * 
	 * @param ip
	 * @param port
	 * @return JedisPool
	 */
	public static JedisPool getPool() {
		if (pool == null) {
			// jedispool为null则初始化，
			JedisPoolConfig config = new JedisPoolConfig();
			// 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；

			// 如果赋值为-1，则表示不限制；如果pool已经分配了maxTotal个jedis实例，则此时pool的状态为exhausted(耗尽）.
			config.setMaxTotal(500);

			// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例
			config.setMaxIdle(5);

			// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
			config.setMaxWaitMillis(1000 * 10);

			// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
			config.setTestOnBorrow(true);

			pool = new JedisPool(config, "47.95.218.93", 6379, 10000, "123456");

			// pool = new JedisPool(config, "localhost", 6379,100000,"123456");
			/*
			 * pool = new JedisPool(config, host, port, time out, password);
			 * attention:注意第四个参数time out，设置成我们能容忍的超时时间，单位是毫秒
			 */
		}

		return pool;

	}

	/**
	 * 将jedis实例返回到连接池pool(用完jedis或者jedis使用过程中出错，均需要返回到连接池)
	 * @param pool
	 * @param redis
	 */
	/*public static void returnResource(JedisPool pool, Jedis redis) {
		if (redis != null) {
			pool.returnResource(redis);
		}
	}*/

	/**
	 * 获取数据
	 * 
	 * @param key
	 * @return
	 */

	public static String get(String key) {
		String value = null;
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			value = jedis.get(key);
		} catch (Exception e) {
			// TODO: handle exception
			// 释放redis对象
			pool.returnBrokenResource(jedis);
			logger.error("jedis error is" + "e.printStackTrace()");
			logger.error("fail to get data from jedis ", e);
		} finally {
			// 返还到连接池
			jedis.close();
			// pool.returnResource(jedis);
		}
		return value;

	}

	/**
	 * 给key赋值，并生命周期设置为seconds
	 * 
	 * @param key
	 * @param seconds
	 *            生命周期 秒为单位
	 * @param value
	 */
	public static void setx(String key, int seconds, String value) {
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			jedis.setex(key, seconds, value);
		} catch (Exception e) {
			// 释放redis对象
			pool.returnBrokenResource(jedis);
			logger.error("fail to set key and seconds", e);
		} finally {
			// 返还到连接池
			jedis.close();
			//pool.returnResource(jedis);
		}
	}

	/**
	 * key-value 键值对添加到列表头部
	 * 
	 * @param key
	 * @param value
	 */
	public static void setListx(String key, String value) {
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			jedis.lpush(key, value);
		} catch (Exception e) {
			// TODO: handle exception
			pool.returnBrokenResource(jedis);
			logger.error("fail to add the key-value to jedis", e);

		} finally {
			jedis.close();
			//pool.returnResource(jedis);
		}

	}

	/**
	 * 根据key值来删除已经存在的key-value;
	 * 
	 * @param key
	 * @return
	 */

	public static int removex(String key) {
		int temp = 0;
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			temp = jedis.del(key).intValue();
		} catch (Exception e) {
			// TODO: handle exception
			pool.returnBrokenResource(jedis);
			logger.error("fail to delete the key-value according to the key", e);
		} finally {
			//返回redis实例
			//pool.returnResource(jedis);
			jedis.close();
		}
		return temp;
	}

}
