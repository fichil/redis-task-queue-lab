package com.fichil.queue.api;

import com.fichil.queue.infrastructure.redis.RedisProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job")
public class JobController {

    private final RedisProducer redisProducer;

    public JobController(RedisProducer redisProducer) {
        this.redisProducer = redisProducer;
    }

    @GetMapping("/send")
    public String send(@RequestParam("msg") String msg) {
        redisProducer.send(msg);
        return "消息已发送: " + msg;
    }
}