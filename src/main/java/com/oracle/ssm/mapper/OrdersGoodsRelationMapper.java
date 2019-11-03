package com.oracle.ssm.mapper;

import com.oracle.ssm.model.OrdersGoodsRelation;

public interface OrdersGoodsRelationMapper {
    int insert(OrdersGoodsRelation record);

    int insertSelective(OrdersGoodsRelation record);
}