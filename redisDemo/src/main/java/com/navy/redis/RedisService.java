package com.navy.redis;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Component;

import com.navy.util.JsonUtil;

@Component("redisService")
public class RedisService {
	
	@SuppressWarnings("unused")
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Resource(name="stringRedisTemplate")
	private ValueOperations<String, String> valueOperations;
	
	@SuppressWarnings("unused")
	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;
	
	@Resource(name="redisTemplate")
	private ValueOperations<String, Object> vorObj;
	
	@Resource(name="redisTemplate")
	private ZSetOperations<String, Object> zsetOperations;
	
	public boolean zadd(String key, Object value, long score) {
		try {
			return this.zsetOperations.add(key, toJson(value), score);
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Set<TypedTuple<Object>> zrange(String key, long start, long end) {
		return this.zsetOperations.rangeWithScores(key, start, end);
	}
	
	public TypedTuple<Object> zrangeFirst(String key) {
		TypedTuple<Object> result = null;
		try {
			Set<TypedTuple<Object>> set = this.zrange(key, 0, 0);
			for(TypedTuple<Object> obj : set) {
				result = obj;
				break;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public long zremFirst(String key) {
		return this.zsetOperations.removeRange(key, 0, 0);
	}
	
	public boolean setTenx(String key, String value) {
		return this.valueOperations.setIfAbsent(key, value);
	}
	
	public boolean del(String key) {
		return this.redisTemplate.delete(key);
	}
	
	public String get(String key) {
		return this.valueOperations.get(key);
	}
	
	public boolean expire(String key, long seconds) {
		return this.redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
	}
	
	public void saveObj(String key, Object obj) {
		vorObj.set(key, obj);
	}
	
	public Object getObj(String key) {
		return vorObj.get(key);
	}
	
	public void saveObj2(String key, Object obj) throws Exception {
		valueOperations.set(key, toJson(obj));
	}
	
	private String toJson(Object obj) throws Exception {
		return JsonUtil.jsonBean(obj);
	}
	
	public Object getObj2(String key) throws Exception {
		
		String str = valueOperations.get(key);
		
		if(str == null) {
			return null;
		}
		return JsonUtil.asBean(Object.class, str);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object getObj2(String key, Class classType) throws Exception {
		
		String str = valueOperations.get(key);
		
		if(str == null) {
			return null;
		}
		return JsonUtil.asBean(classType, str);
	}
}
