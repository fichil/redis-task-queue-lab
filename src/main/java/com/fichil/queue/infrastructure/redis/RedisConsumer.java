package com.fichil.queue.infrastructure.redis;

import com.fichil.queue.application.handler.TaskHandlerDispatcher;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class RedisConsumer {

    private static final String QUEUE_NAME = "task_queue";

    private final StringRedisTemplate stringRedisTemplate;

    private final TaskHandlerDispatcher taskHandlerDispatcher;

    private final AtomicBoolean running = new AtomicBoolean(true);

    private Thread consumerThread;

    public RedisConsumer(StringRedisTemplate stringRedisTemplate,
                         TaskHandlerDispatcher taskHandlerDispatcher) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.taskHandlerDispatcher = taskHandlerDispatcher;
    }

    @PostConstruct
    public void startConsumer() {
        consumerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                consume();
            }
        });
        consumerThread.setName("redis-queue-consumer");
        consumerThread.setDaemon(true);
        consumerThread.start();
    }

    private void consume() {
        while (running.get()) {
            try {
                String message = stringRedisTemplate.opsForList().rightPop(QUEUE_NAME, 2, TimeUnit.SECONDS);
                if (Objects.nonNull(message)) {
                    taskHandlerDispatcher.dispatch(message);
                }
            } catch (IllegalStateException e) {
                if (!running.get()) {
                    break;
                }
                System.err.println("消费者正在关闭，停止消费线程");
                break;
            } catch (Exception e) {
                if (!running.get()) {
                    break;
                }
                System.err.println("消费消息异常: " + e.getMessage());
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    @PreDestroy
    public void shutdown() {
        running.set(false);
        if (consumerThread != null) {
            consumerThread.interrupt();
        }
        System.out.println("RedisConsumer 已停止");
    }
}