package com.g7.framework.trace.thread;

import com.g7.framework.trace.util.ThreadMdcUtils;
import org.slf4j.MDC;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author dreamyao
 * @title
 * @date 2019/11/4 2:00 PM
 * @since 1.0.0
 */
public class ThreadPoolTaskExecutorMdcWrapper extends ThreadPoolTaskExecutor {

    private static final long serialVersionUID = -3480877636420890897L;

    @Override
    public void execute(Runnable task) {
        super.execute(ThreadMdcUtils.wrap(task, MDC.getCopyOfContextMap()));
    }

    @Override
    public void execute(Runnable task, long startTimeout) {
        super.execute(ThreadMdcUtils.wrap(task, MDC.getCopyOfContextMap()), startTimeout);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return super.submit(ThreadMdcUtils.wrap(task, MDC.getCopyOfContextMap()));
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(ThreadMdcUtils.wrap(task, MDC.getCopyOfContextMap()));
    }

    @Override
    public ListenableFuture<?> submitListenable(Runnable task) {
        return super.submitListenable(ThreadMdcUtils.wrap(task, MDC.getCopyOfContextMap()));
    }

    @Override
    public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
        return super.submitListenable(ThreadMdcUtils.wrap(task, MDC.getCopyOfContextMap()));
    }
}
