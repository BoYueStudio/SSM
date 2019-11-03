package com.oracle.ssm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.ssm.mapper.GoodsPriceMapper;
import com.oracle.ssm.model.GoodsPrice;
import com.oracle.ssm.service.GoodsPriceService;

@Service
public class GoodsPriceServiceImpl implements GoodsPriceService{
	
	@Autowired
	private GoodsPriceMapper goodsPriceMapper;

	@Override
	public GoodsPrice findGoodsPriceByGoodsId(int goodsId) {
		// TODO Auto-generated method stub
		return goodsPriceMapper.selectByPrimaryKey(goodsId);
	}
	


}
