package com.oracle.ssm.model;

import java.io.Serializable;
import java.util.Date;

public class Goods implements Serializable {
    private Integer id;

    private String goodsName;

    private Date updateTime;

    private String imgUrl;

    private String goodsDesc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc == null ? null : goodsDesc.trim();
    }

	@Override
	public String toString() {
		return "Goods [id=" + id + ", goodsName=" + goodsName + ", updateTime=" + updateTime + ", imgUrl=" + imgUrl
				+ ", goodsDesc=" + goodsDesc + "]";
	}
    
    
}