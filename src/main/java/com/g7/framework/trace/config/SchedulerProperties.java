package com.g7.framework.trace.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author dreamyao
 * @title
 * @date 2018/11/29 4:07 PM
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "spring.scheduler")
public class SchedulerProperties {

    private final int cpuCount = Runtime.getRuntime().availableProcessors();
    private Integer threads = cpuCount * 2 + 1;

    public Integer getThreads() {
        return threads;
    }

    public void setThreads(Integer threads) {
        this.threads = threads;
    }
}
