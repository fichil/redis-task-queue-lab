package com.fichil.queue.application.handler;

import org.springframework.stereotype.Component;

@Component
public class SaveTaskHandler implements TaskHandler {

    @Override
    public String supportType() {
        return "SAVE";
    }

    @Override
    public void handle(String message) {
        System.out.println("保存任务处理成功: " + message);
    }
}