package com.g7.framework.trace.util;

import com.g7.framework.trace.Constants;
import com.g7.framework.trace.SpanContext;
import com.g7.framework.trace.TraceContext;
import org.slf4j.MDC;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author dreamyao
 * @title
 * @date 2018/11/29 2:53 PM
 * @since 1.0.0
 */
public class ThreadMdcUtils {

    public static <T> Callable<T> wrap(final Callable<T> callable, final Map<String, String> context) {

        return () -> {

            if (CollectionUtils.isEmpty(context)) {

                MDC.clear();

            } else {

                MDC.setContextMap(context);
            }

            setTraceIdAndSpanIdIfAbsent();

            try {

                return callable.call();

            } finally {

                MDC.clear();
            }
        };
    }

    public static Runnable wrap(final Runnable runnable, final Map<String, String> context) {

        return () -> {

            if (CollectionUtils.isEmpty(context)) {

                MDC.clear();

            } else {

                MDC.setContextMap(context);
            }

            setTraceIdAndSpanIdIfAbsent();

            try {

                runnable.run();

            } finally {

                MDC.clear();
            }
        };
    }

    private static void setTraceIdAndSpanIdIfAbsent() {

        if (CollectionUtils.isEmpty(MDC.getCopyOfContextMap())) {
            final String traceId = TraceContext.getContext().genTraceIdAndSet();
            MDC.put(Constants.MDC_TRACE_NAME, traceId);
            MDC.put(Constants.TRACE_ID, traceId);
            final String spanId = SpanContext.getContext().genSpanIdAndSet();
            MDC.put(Constants.MDC_SPAN_NAME, spanId);
            MDC.put(Constants.SPAN_ID, spanId);
        }
    }
}
