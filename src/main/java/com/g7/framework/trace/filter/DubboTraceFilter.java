package com.g7.framework.trace.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.RpcInvocation;
import com.g7.framework.trace.SpanContext;
import com.g7.framework.trace.TraceContext;
import org.slf4j.MDC;

import static com.g7.framework.trace.Constants.*;

/**
 * @author dreamyao
 * @title 全站唯一请求ID(traceId)埋点，如果有多个filer时如果指定了order顺序，则在
 * dubbo的spi文件里应该把order越小的放在越前面
 * @date 2018/9/2 下午1:41
 * @since 1.0.0
 */
@Activate(group = { Constants.PROVIDER, Constants.CONSUMER },order = -9)
public class DubboTraceFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext context = RpcContext.getContext();
        boolean isConsumerSide = context.isConsumerSide();
        RpcInvocation rpcInvocation = (RpcInvocation) invocation;
        //是否是跟踪入口
        boolean isTraceEntrance = false;
        boolean isSpanEntrance = false;
        try {
            if (isConsumerSide) {
                //如果是消费端，则将traceId放入Dubbo调用上下文中
                String traceId = TraceContext.getContext().getTraceId();
                if (StringUtils.isBlank(traceId)) {
                    // 兼容现有ntocc tradeId机制
                    String springTraceId = MDC.get(TRACE_ID);
                    if (StringUtils.isBlank(springTraceId)) {
                        springTraceId = MDC.get(MDC_TRACE_NAME);
                    }
                    if (StringUtils.isBlank(springTraceId)) {
                        //生成traceId
                        traceId = TraceContext.getContext().genTraceIdAndSet();
                        // 之前的ZTraceContext没有traceId，则从现在开始进行跟踪
                        isTraceEntrance = true;
                    } else {
                        traceId = springTraceId;
                    }
                }
                rpcInvocation.setAttachment(TRACE_ID, traceId);
                rpcInvocation.setAttachment(MDC_TRACE_NAME, traceId);
            } else {
                //如果是服务端，则将traceId从Dubbo上下文中取出，并放入TraceContext中
                String traceId = rpcInvocation.getAttachment(TRACE_ID);
                if (StringUtils.isBlank(traceId)) {
                    traceId = rpcInvocation.getAttachment(MDC_TRACE_NAME);
                }
                if (StringUtils.isBlank(traceId)) {
                    //生成traceId
                    traceId = TraceContext.getContext().genTraceIdAndSet();
                    // 之前的TraceContext没有traceId，则从现在开始进行跟踪
                    isTraceEntrance = true;
                }
                //加入slf4j.MDC
                MDC.put(MDC_TRACE_NAME, traceId);
                MDC.put(TRACE_ID, traceId);
                TraceContext.getContext().setTraceId(traceId);

                String spanId = SpanContext.getContext().genSpanIdAndSet();
                MDC.put(MDC_SPAN_NAME, spanId);
                MDC.put(SPAN_ID, spanId);
                isSpanEntrance = true;
            }

            return invoker.invoke(invocation);
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
}