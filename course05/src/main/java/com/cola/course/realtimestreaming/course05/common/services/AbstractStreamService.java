package com.cola.course.realtimestreaming.course05.common.services;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class AbstractStreamService<I, O> implements ServiceInterface {

    private static final Logger logger = LoggerFactory.getLogger(AbstractStreamService.class);


    abstract protected void beforeStart() throws Exception;

    abstract protected List<I> poll(List<Queue<I>> inputQueues) throws Exception;

    abstract protected List<O> process(List<I> inputs) throws Exception;

    abstract protected boolean offer(List<Queue<O>> outputQueues, List<O> outputs) throws Exception;

    abstract protected void beforeShutdown() throws Exception;


    private List<ServiceInterface> upstreams;

    private List<Queue<I>> inputQueues;
    private List<Queue<O>> outputQueues;
    protected volatile boolean stopped = false;
    protected volatile boolean isShutdown = false;

    /**
     * 核心功能，从输入队列拉取消息，然后process之后，发给下游的队列
     *
     * @param checkStop
     * @param exitWhenNoInput
     * @return
     */
    private boolean pipeline(boolean checkStop, boolean exitWhenNoInput) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("service[%s] checkStop[%s] exitWhenNoInput[%s]",
                    getName(), checkStop, exitWhenNoInput));
        }
        boolean exit = false;
        try {
            List<I> inputs;
            while (true) {
                if (checkStop && stopped) {
                    throw new ExitException();
                }
                inputs = poll(inputQueues);   //核心代码
                if (CollectionUtils.isEmpty(inputs)) {
                    break;
                } else {
                    if (exitWhenNoInput) {
                        throw new ExitException();
                    }
                    if (logger.isDebugEnabled()) {
                        logger.debug(String.format("service[%s] get no input", getName()));
                    }
                }
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format("service[%s] get %d inputs", getName(), inputs.size()));
                }
            }

            List<O> outputs = process(inputs);  //核心代码

            if (outputs != null && outputs.size() > 0) {
                while (true) {
                    if (offer(outputQueues, outputs)) {
                        if (checkStop && stopped) throw new ExitException();
                        break;
                    }
                    if (logger.isDebugEnabled()) {
                        logger.debug(String.format("service[%s] not all outputs offered", getName()));
                    }
                }
            }

        } catch (InterruptedException e) {
            logger.warn(String.format("InterruptedException caught, service[%s] will exit.", getName()));
            exit = true;
        } catch (ExitException e) {
            logger.info(String.format("service[%s] will exit", getName()));
            exit = true;
        }
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("service[%s] pipeline result[%s]", getName(), exit));
        }
        return exit;

    }



    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void waitToShutdown() {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public ServicesStatus getStatus() {
        return null;
    }
}
