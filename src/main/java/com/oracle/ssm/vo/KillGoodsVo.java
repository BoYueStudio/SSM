package com.oracle.ssm.vo;

import com.oracle.ssm.model.Goods;
import com.oracle.ssm.model.GoodsPrice;

public class KillGoodsVo {

	private Goods goods;
	private GoodsPrice goodsPrice;
	private String beginTime;
	private int num;
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public GoodsPrice getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(GoodsPrice goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public KillGoodsVo(Goods goods, GoodsPrice goodsPrice, String beginTime, int num) {
		super();
		this.goods = goods;
		this.goodsPrice = goodsPrice;
		this.beginTime = beginTime;
		this.num = num;
	}
	
	

}
