package com.fichil.queue.application.handler;

import org.springframework.stereotype.Component;

@Component
public class PrintTaskHandler implements TaskHandler {

    @Override
    public String supportType() {
        return "PRINT";
    }

    @Override
    public void handle(String message) {
        System.out.println("处理任务成功: " + message);
    }
}