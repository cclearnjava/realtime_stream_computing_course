package com.cola.course.realtimestreaming.course05.common.services;

import java.util.concurrent.TimeUnit;

public interface Queue<E> {

    /**
     * poll 用于下游的节点向上游的节点拉取数据
     * @param timeout
     * @param unit
     * @return
     * @throws InterruptedException
     */
    E poll(long timeout, TimeUnit unit) throws InterruptedException;

    /**
     * offer 用于上游的节点向下游的节点传递数据
     * @param e
     * @param timeout
     * @param unit
     * @return
     * @throws InterruptedException
     */
    boolean offer(E e,long timeout,TimeUnit unit) throws InterruptedException;
}
