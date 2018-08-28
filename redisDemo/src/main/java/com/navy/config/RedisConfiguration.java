package com.navy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RedisConfiguration {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
		RedisTemplate redisTemplate = new RedisTemplate();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
//		redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
//		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(Object.class));
//		redisTemplate.setValueSerializer(new StringRedisSerializer());
		
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		
		
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}
}
