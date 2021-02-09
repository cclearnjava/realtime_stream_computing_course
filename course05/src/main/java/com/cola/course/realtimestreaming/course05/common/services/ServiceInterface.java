package com.cola.course.realtimestreaming.course05.common.services;

public interface ServiceInterface {

    void start();

    void stop();

    void waitToShutdown();

    String getName();

    ServicesStatus getStatus();
}
