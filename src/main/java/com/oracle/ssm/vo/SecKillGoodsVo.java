package com.oracle.ssm.vo;

import java.io.Serializable;
import java.util.Date;

import com.oracle.ssm.model.Goods;

public class SecKillGoodsVo  implements Serializable{

	private Goods goods;
	private Date startTime;
	private Date endTime;
	private String price;
	private Integer initCount;
	
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public Integer getInitCount() {
		return initCount;
	}
	public void setInitCount(Integer initCount) {
		this.initCount = initCount;
	}
	
	

}
