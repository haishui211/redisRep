package com.navy.redis;

import java.io.Serializable;

public class Person implements Serializable{
	
	private static final long serialVersionUID = -2369291896679131628L;
	
	private String id;
	private String name;
	private int age;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	
}
