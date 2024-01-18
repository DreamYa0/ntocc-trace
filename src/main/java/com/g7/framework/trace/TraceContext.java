package com.g7.framework.trace;

/**
 * @author dreamyao
 * @title
 * @date 2018/9/1 下午11:32
 * @since 1.0.0
 */
public class TraceContext {

    private static final ThreadLocal<TraceContext> LOCAL = ThreadLocal.withInitial(TraceContext::new);
    private String traceId;
    private ChainRelation chainRelation;

    private TraceContext() {
    }

    public String getTraceId() {
        return this.traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String genTraceIdAndSet() {
        String traceId = TraceIdGen.gen();
        this.traceId = traceId;
        return traceId;
    }

    public ChainRelation genChainRelationAndSet() {
        ChainRelation chainRelation = new ChainRelation();
        this.chainRelation = chainRelation;
        return chainRelation;
    }

    public ChainRelation getChainRelation() {
        return this.chainRelation;
    }

    public void setChainRelation(ChainRelation chainRelation) {
        this.chainRelation = chainRelation;
    }

    public static TraceContext getContext() {
        return (TraceContext)LOCAL.get();
    }

    public static void removeContext() {
        LOCAL.remove();
    }
}
