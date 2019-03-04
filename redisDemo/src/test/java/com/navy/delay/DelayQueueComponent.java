package com.navy.delay;

import java.util.UUID;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Component;

import com.navy.redis.RedisService;

@Component
public class DelayQueueComponent implements DisposableBean{
	
	private final static String delayQueueKey = "delay:queue";
	
	@Autowired
	private RedisService redisService;
	
	/**
	 * 将延迟对象推送至队列中
	 * @param obj
	 * @param seconds
	 */
	public void add(Object obj, long seconds) {
		this.redisService.zadd(delayQueueKey, obj, getDelayTimeMills(seconds));
	}
	
	public void startMonitor() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				monitorQueue();
			}
		};
		System.out.println("start monitor delay queue.");
		new Thread(runnable).start();
		System.out.println("finish start monitor delay queue.");
	}
	
	private void monitorQueue() {
		while(true) {
			if(lock()) {
				
				TypedTuple<Object> tuple = zrangeOne();
				
				if(isCanPush(tuple)) {
					zrem();
					releaseLock();
					resolveDelayMsg(tuple);
				}
				else {
					releaseLock();
				}
			}
			sleep();
		}
	}
	
	/**
	 * 删除掉处理的延迟消息
	 */
	private void zrem() {
		this.redisService.zremFirst(delayQueueKey);
	}
	
	/**
	 * 从延迟队列中拿一个最旧的
	 * @return
	 */
	private TypedTuple<Object> zrangeOne() {
		try {
			TypedTuple<Object> tuple = this.redisService.zrangeFirst(delayQueueKey);
			return tuple;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 是否可推送
	 * @param tuple
	 * @return
	 */
	private boolean isCanPush(TypedTuple<Object> tuple) {
		if(tuple == null) {
			return false;
		}
		long currentTimeMills = System.currentTimeMillis();
		if(currentTimeMills >= tuple.getScore()) {
			return true;
		}
		return false;
	}
	
	/**
	 * 处理延迟的消息
	 * @param tuple
	 */
	private void resolveDelayMsg(TypedTuple<Object> tuple) {
		try {
			System.out.println("resolve delay msg: " + tuple.getValue());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void sleep() {
		try {
			Thread.sleep(1000);
		}
		catch(Exception e) {
			
		}
	}
	
	private static final String lockKey = "lock:delay";
	private static final String lockValue = UUID.randomUUID().toString();
	/**
	 * 锁一下
	 * @return
	 */
	private boolean lock() {
		boolean result = false;
		try {
			result = this.redisService.setTenx(lockKey, lockValue);
		}
		finally {
			if(result) {
				result = this.redisService.expire(lockKey, 60*1);
			}
		}
		return result;
	}
	
	/**
	 * 释放锁
	 */
	private void releaseLock() {
		String value = this.redisService.get(lockKey);
		if(lockValue.equals(value)) {
			this.redisService.del(lockKey);
		}
	}
	
	private long getDelayTimeMills(long seconds) {
		long result = System.currentTimeMillis() + seconds * 1000;
		return result;
	}

	@Override
	public void destroy() throws Exception {
		releaseLock();
	}
}
