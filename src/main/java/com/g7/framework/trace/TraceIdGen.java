package com.g7.framework.trace;

import java.util.UUID;

/**
 * @author dreamyao
 * @title
 * @date 2018/9/1 下午11:32
 * @since 1.0.0
 */
public class TraceIdGen {

    private TraceIdGen() {}

    public static String gen() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
