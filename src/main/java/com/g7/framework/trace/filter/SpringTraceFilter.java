package com.g7.framework.trace.filter;

import com.g7.framework.trace.SpanContext;
import com.g7.framework.trace.TraceContext;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

import static com.g7.framework.trace.Constants.*;

/**
 * @author dreamyao
 * @title SpringMVC 请求时获取前端传入的traceId，需要时配置bean
 * @date 2018/9/4 下午10:55
 * @since 1.0.0
 */
public class SpringTraceFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        Object traceIdValue = servletRequest.getAttribute(TRACE_ID);
        Object spanIdObjValue = servletRequest.getAttribute(SPAN_ID);

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        if (Objects.isNull(traceIdValue)) {
            traceIdValue = httpRequest.getHeader("EagleEye-TraceId");
        }

        if (Objects.isNull(traceIdValue)) {
            traceIdValue = httpRequest.getHeader(TRACE_ID);
        }

        if (Objects.isNull(spanIdObjValue)) {
            spanIdObjValue = httpRequest.getHeader(SPAN_ID);
        }

        //是否是跟踪入口
        boolean isTraceEntrance = false;
        boolean isSpanEntrance = false;
        try {
            String traceId;
            String spanId;
            if (Objects.isNull(traceIdValue) || StringUtils.isEmpty(String.valueOf(traceIdValue))) {
                // 当从header没有获取到traceId时，自动生成一个
                traceId = TraceContext.getContext().genTraceIdAndSet();
                // 之前的TraceContext没有traceId，则从现在开始进行跟踪
                isTraceEntrance = true;

            } else {
                traceId = String.valueOf(traceIdValue);
            }

            if (Objects.isNull(spanIdObjValue) || StringUtils.isEmpty(String.valueOf(spanIdObjValue))) {
                // 当从header没有获取到traceId时，自动生成一个
                spanId = TraceContext.getContext().genTraceIdAndSet();
                // 之前的TraceContext没有traceId，则从现在开始进行跟踪
                isSpanEntrance = true;

            } else {
                spanId = String.valueOf(traceIdValue);
            }

            //加入slf4j.MDC
            MDC.put(MDC_TRACE_NAME, traceId);
            MDC.put(TRACE_ID, traceId);
            TraceContext.getContext().setTraceId(traceId);

            MDC.put(MDC_SPAN_NAME, spanId);
            MDC.put(SPAN_ID, spanId);
            SpanContext.getContext().setSpanId(spanId);

            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            if (isTraceEntrance) {
                //自己拉的屎，怎么也得自己清理
                TraceContext.removeContext();
            }
            if (isSpanEntrance) {
                SpanContext.removeContext();
            }
        }
    }

    @Override
    public void destroy() {

    }
}
