package com.fichil.queue.application.handler;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TaskHandlerDispatcher {

    private final List<TaskHandler> taskHandlers;

    private final Map<String, TaskHandler> handlerMap = new HashMap<String, TaskHandler>();

    public TaskHandlerDispatcher(List<TaskHandler> taskHandlers) {
        this.taskHandlers = taskHandlers;
    }

    @PostConstruct
    public void init() {
        for (TaskHandler taskHandler : taskHandlers) {
            handlerMap.put(taskHandler.supportType(), taskHandler);
        }
    }

    public void dispatch(String rawMessage) {
        if (rawMessage == null || !rawMessage.contains(":")) {
            throw new IllegalArgumentException("消息格式非法，必须是 TYPE:message");
        }

        String[] parts = rawMessage.split(":", 2);
        String type = parts[0];
        String message = parts[1];

        TaskHandler taskHandler = handlerMap.get(type);
        if (taskHandler == null) {
            throw new IllegalArgumentException("未找到对应的TaskHandler, type=" + type);
        }

        taskHandler.handle(message);
    }
}