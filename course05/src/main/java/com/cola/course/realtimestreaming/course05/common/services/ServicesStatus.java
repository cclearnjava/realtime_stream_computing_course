package com.cola.course.realtimestreaming.course05.common.services;

public class ServicesStatus {

    private final boolean isStopped;

    private final boolean isShutDown;

    public ServicesStatus(boolean isStopped, boolean isShutDown) {
        this.isStopped = isStopped;
        this.isShutDown = isShutDown;
    }

    public boolean isStopped() {
        return isStopped;
    }

    public boolean isShutDown() {
        return isShutDown;
    }
}
