package com.oracle.ssm.mapper;

import com.oracle.ssm.model.GoodsPrice;

public interface GoodsPriceMapper {
    int deleteByPrimaryKey(Integer goodsId);

    int insert(GoodsPrice record);

    int insertSelective(GoodsPrice record);

    GoodsPrice selectByPrimaryKey(Integer goodsId);

    int updateByPrimaryKeySelective(GoodsPrice record);

    int updateByPrimaryKey(GoodsPrice record);
}