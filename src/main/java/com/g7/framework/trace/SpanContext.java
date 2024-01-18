package com.g7.framework.trace;

/**
 * @author dreamyao
 * @title
 * @date 2018/9/5 下午3:00
 * @since 1.0.0
 */
public class SpanContext {

    private static final ThreadLocal<SpanContext> LOCAL = ThreadLocal.withInitial(SpanContext::new);
    private String spanId;
    private ChainRelation chainRelation;

    private SpanContext() {
    }

    public String getSpanId() {
        return this.spanId;
    }

    public void setSpanId(String traceId) {
        this.spanId = traceId;
    }

    public String genSpanIdAndSet() {
        String spanId = TraceIdGen.gen();
        this.spanId = spanId;
        return spanId;
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

    public static SpanContext getContext() {
        return LOCAL.get();
    }

    public static void removeContext() {
        LOCAL.remove();
    }
}
