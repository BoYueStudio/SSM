package com.oracle.ssm.model;


import java.util.Date;
import java.util.List;

public class Orders {
    private Integer orderId;

    private Date orderTime;

    private Integer userId;

    private Integer goodsId;

    private String ordersMoney;

    private String shopCount;

    private Date updateTime;

    private Integer staus;
    
    private List<Goods> goodsList;

    
    public List<Goods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<Goods> goodsList) {
		this.goodsList = goodsList;
	}

	public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getOrdersMoney() {
        return ordersMoney;
    }

    public void setOrdersMoney(String ordersMoney) {
        this.ordersMoney = ordersMoney == null ? null : ordersMoney.trim();
    }

    public String getShopCount() {
        return shopCount;
    }

    public void setShopCount(String shopCount) {
        this.shopCount = shopCount == null ? null : shopCount.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStaus() {
        return staus;
    }

    public void setStaus(Integer staus) {
        this.staus = staus;
    }
}