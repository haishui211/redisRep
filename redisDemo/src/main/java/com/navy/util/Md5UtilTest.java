package com.navy.util;

import org.apache.commons.codec.digest.DigestUtils;

public class Md5UtilTest {
	
	public static void main(String[] args) {
		long times = System.currentTimeMillis();
		String key = "ddd23W#t@&12De";
		String token = DigestUtils.md5Hex(times + key);
		System.out.println(token);
	}
	
}
