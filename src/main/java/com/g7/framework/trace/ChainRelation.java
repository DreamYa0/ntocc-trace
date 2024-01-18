package com.g7.framework.trace;

import java.io.Serializable;

/**
 * @author dreamyao
 * @title
 * @date 2018/9/1 下午11:31
 * @since 1.0.0
 */
public class ChainRelation implements Serializable {

    private static final String START_CHAIN_ID = "1";
    private static final int START_INDEX = 1;
    private String currentChainId;
    private Integer index;

    public String getCurrentChainId() {
        return this.currentChainId;
    }

    public void setCurrentChainId(String currentChainId) {
        this.currentChainId = currentChainId;
    }

    public Integer getIndex() {
        return this.index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public ChainRelation() {
        this.currentChainId = "1";
        this.index = 1;
    }

    public ChainRelation(String currentChainId) {
        this.currentChainId = currentChainId;
        this.index = 1;
    }

    public String getChildChainId() {
        return this.currentChainId + "." + this.index;
    }

    public void increaseChainId() {
        Integer var1 = this.index;
        Integer var2 = this.index = this.index + 1;
    }
}