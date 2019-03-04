package com.navy.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisServiceTest {
	
	private final static Integer totals = 1000;
	
	@Autowired
	private RedisService redisService;
	
	@Test
	public void testSaveObj() {
		
		long startTime=System.currentTimeMillis();
		
		for(int i = 0; i < totals; i++) {
			Person person = new Person();
			person.setId(i+"");
			person.setName("zhangsan" + i);
			person.setAge(i);
			redisService.saveObj(person.getId(), person);
		}
		
		long endTime=System.currentTimeMillis();
		System.out.println(endTime-startTime);
	}
	
	@Test
	public void testSaveObj2() throws Exception {
		
		long startTime=System.currentTimeMillis();
		
		for(int i = 0; i < 1; i++) {
			Person person = new Person();
			person.setId(i+"");
			person.setName("zhangsan" + i);
			person.setAge(i);
			redisService.saveObj2(person.getId(), person);
		}
		
		long endTime=System.currentTimeMillis();
		System.out.println(endTime-startTime);
	}
	
	@Test
	public void testGetObj() {
		
		long startTime=System.currentTimeMillis();
		
		for(int i = 0; i < totals; i++) {
			Person person = (Person) redisService.getObj(i+"");
			System.out.println(person.getName());
			System.out.println(person.getAge());
		}
		long endTime=System.currentTimeMillis();
		System.out.println(endTime-startTime);
	}
	
	@Test
	public void testGetObj2() throws Exception {
		
		long startTime=System.currentTimeMillis();
		
		for(int i = 0; i < 1; i++) {
			Person person = (Person) redisService.getObj2(i+"", Person.class);
			System.out.println(person.getName());
			System.out.println(person.getAge());
		}
		long endTime=System.currentTimeMillis();
		System.out.println(endTime-startTime);
	}
	
	@Test
	public void testZset() {
		for(int i = 0; i < 10; i++) {
			
		}
	}
}
