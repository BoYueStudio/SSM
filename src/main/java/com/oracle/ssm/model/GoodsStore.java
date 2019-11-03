package com.oracle.ssm.model;

import java.io.Serializable;

public class GoodsStore implements Serializable {
    private Integer goodsId;

    private Integer totalCount;

    private Integer avaliableCount;

    private Integer frozenCount;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getAvaliableCount() {
        return avaliableCount;
    }

    public void setAvaliableCount(Integer avaliableCount) {
        this.avaliableCount = avaliableCount;
    }

    public Integer getFrozenCount() {
        return frozenCount;
    }

    public void setFrozenCount(Integer frozenCount) {
        this.frozenCount = frozenCount;
    }
}