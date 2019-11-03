package com.oracle.ssm.service;

import com.oracle.ssm.model.GoodsPrice;

public interface GoodsPriceService {
	
	GoodsPrice findGoodsPriceByGoodsId(int goodsId);
}
