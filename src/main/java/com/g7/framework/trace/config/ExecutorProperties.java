package com.g7.framework.trace.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author dreamyao
 * @title
 * @date 2018/11/29 3:40 PM
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "spring.executor")
public class ExecutorProperties {

    private final int cpuCount = Runtime.getRuntime().availableProcessors();
    private Integer maxThreads = cpuCount * 5;
    private Integer coreSize = cpuCount * 2 + 1;
    private Integer queueCapacity = 500;
    private Integer keepAliveSeconds = 60;

    public Integer getMaxThreads() {
        return maxThreads;
    }

    public void setMaxThreads(Integer maxThreads) {
        this.maxThreads = maxThreads;
    }

    public Integer getCoreSize() {
        return coreSize;
    }

    public void setCoreSize(Integer coreSize) {
        this.coreSize = coreSize;
    }

    public Integer getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(Integer queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public Integer getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    public void setKeepAliveSeconds(Integer keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }
}
