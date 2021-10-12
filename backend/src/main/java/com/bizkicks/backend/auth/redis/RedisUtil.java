package com.bizkicks.backend.auth.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtil {
    @Autowired 
	private StringRedisTemplate redisTemplate;

    public void set(String key, String value, Long milisecond){
        redisTemplate.opsForValue().set(key, value, milisecond, TimeUnit.MILLISECONDS);
    }

    public String get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public Boolean delete(String key){
        return redisTemplate.delete(key);
    }

    public Boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    
}
