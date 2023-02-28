package com.puppyhome.backend.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis配置类
 * 实现redis相关配置
 * 以及对bean的注入
 */
@Configuration
public class RedisConfig {

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
		// 2.0后的写法
		configuration.setHostName("10.102.132.150");
		configuration.setPort(6379);
		configuration.setDatabase(0);

		return new JedisConnectionFactory(configuration);
	}

	@Bean(name = "redisTemplate")
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);

		// String的序列化
		StringRedisSerializer stringSerializer = new StringRedisSerializer();
		// key采用String的序列化方式
		template.setKeySerializer(stringSerializer);
		// hash的key采用String的序列化方式
		template.setHashKeySerializer(stringSerializer);

		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = jackson2JsonRedisSerializerConfig();
		//value采用jackson序列化方式
		template.setValueSerializer(jackson2JsonRedisSerializer);
		//hash的value也采用jackson序列化方式
		template.setHashValueSerializer(jackson2JsonRedisSerializer);

		template.afterPropertiesSet();


		return template;
	}

	private Jackson2JsonRedisSerializer jackson2JsonRedisSerializerConfig() {
		// jackson序列化所有的类
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		// jackson序列化的一些配置
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		return jackson2JsonRedisSerializer;
	}


}