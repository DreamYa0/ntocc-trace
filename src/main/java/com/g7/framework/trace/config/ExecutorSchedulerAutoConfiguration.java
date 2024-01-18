package com.g7.framework.trace.config;

import com.g7.framework.trace.thread.ThreadPoolTaskExecutorMdcWrapper;
import com.g7.framework.trace.thread.ThreadPoolTaskSchedulerMdcWrapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author dreamyao
 * @title
 * @date 2018/11/29 3:48 PM
 * @since 1.0.0
 */
@EnableAsync
@Configuration
@ConditionalOnMissingBean(value = AsyncConfigurer.class)
@EnableConfigurationProperties({ExecutorProperties.class, SchedulerProperties.class})
public class ExecutorSchedulerAutoConfiguration {

    private final ExecutorProperties executorProperties;
    private final SchedulerProperties schedulerProperties;

    public ExecutorSchedulerAutoConfiguration(ExecutorProperties executorProperties, SchedulerProperties schedulerProperties) {
        this.executorProperties = executorProperties;
        this.schedulerProperties = schedulerProperties;
    }

    @Bean
    @ConditionalOnMissingBean(value = ThreadPoolTaskExecutor.class)
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {

        // 对线程池进行包装，使之支持traceId透传
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutorMdcWrapper();
        executor.setCorePoolSize(executorProperties.getCoreSize());
        executor.setMaxPoolSize(executorProperties.getMaxThreads());
        executor.setQueueCapacity(executorProperties.getQueueCapacity());
        executor.setKeepAliveSeconds(executorProperties.getKeepAliveSeconds());
        executor.setThreadNamePrefix("AsyncTasks-");
        // 其他配置
        executor.initialize();
        return executor;
    }

    @Bean
    @ConditionalOnMissingBean(value = ThreadPoolTaskScheduler.class)
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {

        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskSchedulerMdcWrapper();

        scheduler.setPoolSize(schedulerProperties.getThreads());
        scheduler.setThreadNamePrefix("Scheduler-");
        scheduler.initialize();
        return scheduler;
    }
}
