package com.g7.framework.trace.thread;

import com.g7.framework.trace.util.ThreadMdcUtils;
import org.slf4j.MDC;

import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * @author dreamyao
 * @title
 * @date 2019/11/4 2:01 PM
 * @since 1.0.0
 */
public class ForkJoinPoolMdcWrapper extends ForkJoinPool {

    public ForkJoinPoolMdcWrapper() {
        super();
    }

    public ForkJoinPoolMdcWrapper(int parallelism) {
        super(parallelism);
    }

    public ForkJoinPoolMdcWrapper(int parallelism, ForkJoinWorkerThreadFactory factory,
                                  Thread.UncaughtExceptionHandler handler, boolean asyncMode) {
        super(parallelism, factory, handler, asyncMode);
    }

    @Override
    public void execute(Runnable task) {
        super.execute(ThreadMdcUtils.wrap(task, MDC.getCopyOfContextMap()));
    }

    @Override
    public <T> ForkJoinTask<T> submit(Runnable task, T result) {
        return super.submit(ThreadMdcUtils.wrap(task, MDC.getCopyOfContextMap()), result);
    }

    @Override
    public <T> ForkJoinTask<T> submit(Callable<T> task) {
        return super.submit(ThreadMdcUtils.wrap(task, MDC.getCopyOfContextMap()));
    }
}
