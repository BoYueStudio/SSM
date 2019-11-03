package com.oracle.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

public class Test1 {

	public static void main(String[] args) {
		
		ApplicationContext context=new ClassPathXmlApplicationContext("spring/spring-redis.xml");
		RedisTemplate jedis=(RedisTemplate) context.getBean("redisTemplate");
		//´æsession
		String key="session:wei";
		
		byte[] bytes=jedis.getHashValueSerializer().serialize("dd");
		jedis.boundValueOps(key).set(bytes);
		System.out.println(jedis.opsForValue().get("weiwei"));
	}

}
