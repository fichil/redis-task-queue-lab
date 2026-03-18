package com.fichil.queue.infrastructure.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisProducer {

    private static final String QUEUE_NAME = "task_queue";

    private final StringRedisTemplate stringRedisTemplate;

    public RedisProducer(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void send(String message) {
        stringRedisTemplate.opsForList().leftPush(QUEUE_NAME, message);
        System.out.println("生产消息成功: " + message);
    }
}