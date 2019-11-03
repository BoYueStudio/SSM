package com.oracle.ssm.vo;

import java.io.Serializable;

import com.oracle.ssm.model.Goods;

public class GoodsVo implements Serializable {

	private Goods goods;
	private Long salePrice;
	private Integer avaliableCount;
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public Long getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(Long salePrice) {
		this.salePrice = salePrice;
	}
	public Integer getAvaliableCount() {
		return avaliableCount;
	}
	public void setAvaliableCount(Integer avaliableCount) {
		this.avaliableCount = avaliableCount;
	}
	public GoodsVo(Goods goods, Long salePrice, Integer avaliableCount) {
		super();
		this.goods = goods;
		this.salePrice = salePrice;
		this.avaliableCount = avaliableCount;
	}
	public GoodsVo() {
		super();
	}
	
	
	

}
