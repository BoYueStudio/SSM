package com.oracle.ssm.model;

import java.io.Serializable;

public class GoodsPrice implements Serializable {
    private Integer goodsId;

    private Long originalPrice;

    private Long salePrice;

    private Long discountPrice;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Long getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Long originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Long getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Long salePrice) {
        this.salePrice = salePrice;
    }

    public Long getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Long discountPrice) {
        this.discountPrice = discountPrice;
    }

	@Override
	public String toString() {
		return "GoodsPrice [goodsId=" + goodsId + ", originalPrice=" + originalPrice + ", salePrice=" + salePrice
				+ ", discountPrice=" + discountPrice + "]";
	}
    
    
}