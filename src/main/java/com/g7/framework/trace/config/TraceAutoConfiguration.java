package com.g7.framework.trace.config;

import com.g7.framework.trace.filter.SpringTraceFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dreamyao
 * @title
 * @date 2018/9/11 下午8:11
 * @since 1.0.0
 */
@Configuration
public class TraceAutoConfiguration {

    @Bean
    public SpringTraceFilter springTraceFilter() {
        return new SpringTraceFilter();
    }
}
