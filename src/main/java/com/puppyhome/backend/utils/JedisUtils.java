package com.puppyhome.backend.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ResourceBundle;

/**
 * jedis工具类
 * 用于配置jedis
 */
public class JedisUtils {
	private static final JedisPool jp;
	private static final String host;
	private static final int port;
	private static final int maxTotal;
	private static final int maxIdle;

	// 静态代码块初始化资源
	static {
		//读取配置文件 获得参数值
		ResourceBundle rb = ResourceBundle.getBundle("redis");
		host = rb.getString("redis.host");
		port = Integer.parseInt(rb.getString("redis.port"));
		maxTotal = Integer.parseInt(rb.getString("redis.maxTotal"));
		maxIdle = Integer.parseInt(rb.getString("redis.maxIdle"));
		JedisPoolConfig jpc = new JedisPoolConfig();
		jpc.setMaxTotal(maxTotal);
		jpc.setMaxIdle(maxIdle);
		jp = new JedisPool(jpc, host, port);
	}

	/**
	 * 对外访问接口，提供jedis连接对象，连接从连接池获取
	 */
	public static Jedis getJedis() {
		return jp.getResource();
	}
}