package com.navy.redis;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.navy.util.JsonUtil;

@Component("redisService")
public class RedisService {
	
	@SuppressWarnings("unused")
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Resource(name="stringRedisTemplate")
	private ValueOperations<String, String> vorStr;
	
	@SuppressWarnings("unused")
	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;
	
	@Resource(name="redisTemplate")
	private ValueOperations<String, Object> vorObj;
	
	public void saveObj(String key, Object obj) {
		vorObj.set(key, obj);
	}
	
	public Object getObj(String key) {
		return vorObj.get(key);
	}
	
	public void saveObj2(String key, Object obj) throws Exception {
		vorStr.set(key, toJson(obj));
	}
	
	private String toJson(Object obj) throws Exception {
		return JsonUtil.jsonBean(obj);
	}
	
	public Object getObj2(String key) throws Exception {
		
		String str = vorStr.get(key);
		
		if(str == null) {
			return null;
		}
		return JsonUtil.asBean(Object.class, str);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object getObj2(String key, Class classType) throws Exception {
		
		String str = vorStr.get(key);
		
		if(str == null) {
			return null;
		}
		return JsonUtil.asBean(classType, str);
	}
}
