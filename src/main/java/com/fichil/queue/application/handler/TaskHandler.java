package com.fichil.queue.application.handler;

public interface TaskHandler {

    String supportType();

    void handle(String message);


}