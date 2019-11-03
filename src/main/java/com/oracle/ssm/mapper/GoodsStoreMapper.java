package com.oracle.ssm.mapper;

import com.oracle.ssm.model.GoodsStore;

public interface GoodsStoreMapper {
    int deleteByPrimaryKey(Integer goodsId);

    int insert(GoodsStore record);

    int insertSelective(GoodsStore record);

    GoodsStore selectByPrimaryKey(Integer goodsId);

    int updateByPrimaryKeySelective(GoodsStore record);

    int updateByPrimaryKey(GoodsStore record);
}