package com.g7.framework.trace.thread;

import com.g7.framework.trace.util.ThreadMdcUtils;
import org.slf4j.MDC;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.concurrent.ListenableFuture;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;

/**
 * @author dreamyao
 * @title
 * @date 2019/11/4 2:56 PM
 * @since 1.0.0
 */
public class ThreadPoolTaskSchedulerMdcWrapper extends ThreadPoolTaskScheduler {

    private static final long serialVersionUID = 8559237296593594200L;

    @Override
    public void execute(Runnable task, long startTimeout) {
        super.execute(ThreadMdcUtils.wrap(task, MDC.getCopyOfContextMap()), startTimeout);
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

    @Override
    public ScheduledFuture<?> schedule(Runnable task, Instant startTime) {
        return super.schedule(ThreadMdcUtils.wrap(task, MDC.getCopyOfContextMap()), startTime);
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, Instant startTime, Duration period) {
        return super.scheduleAtFixedRate(ThreadMdcUtils.wrap(task, MDC.getCopyOfContextMap()), startTime, period);
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, Duration period) {
        return super.scheduleAtFixedRate(ThreadMdcUtils.wrap(task, MDC.getCopyOfContextMap()), period);
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, Instant startTime, Duration delay) {
        return super.scheduleWithFixedDelay(ThreadMdcUtils.wrap(task, MDC.getCopyOfContextMap()), startTime, delay);
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, Duration delay) {
        return super.scheduleWithFixedDelay(ThreadMdcUtils.wrap(task, MDC.getCopyOfContextMap()), delay);
    }

    @Override
    @Nullable
    public ScheduledFuture<?> schedule(Runnable task, Trigger trigger) {
        return super.schedule(ThreadMdcUtils.wrap(task, MDC.getCopyOfContextMap()), trigger);
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable task, Date startTime) {
        return super.schedule(ThreadMdcUtils.wrap(task, MDC.getCopyOfContextMap()), startTime);
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, Date startTime, long period) {
        return super.scheduleAtFixedRate(ThreadMdcUtils.wrap(task, MDC.getCopyOfContextMap()), period);
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, long period) {
        return super.scheduleAtFixedRate(ThreadMdcUtils.wrap(task, MDC.getCopyOfContextMap()), period);
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, Date startTime, long delay) {
        return super.scheduleWithFixedDelay(ThreadMdcUtils.wrap(task, MDC.getCopyOfContextMap()), delay);
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, long delay) {
        return super.scheduleWithFixedDelay(ThreadMdcUtils.wrap(task, MDC.getCopyOfContextMap()), delay);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        // 传入线程池之前先复制当前线程的MDC
        return super.submit(ThreadMdcUtils.wrap(task, MDC.getCopyOfContextMap()));
    }

    @Override
    public void execute(Runnable task) {
        super.execute(ThreadMdcUtils.wrap(task, MDC.getCopyOfContextMap()));
    }
}
