package com.oracle.ssm.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.ssm.model.Goods;
import com.oracle.ssm.model.GoodsPrice;
import com.oracle.ssm.service.GoodsPriceService;
import com.oracle.ssm.service.GoodsService;
import com.oracle.ssm.service.KillGoodsVoService;
import com.oracle.ssm.vo.KillGoodsVo;

@Service
public class KillGoodsVoServiceImpl implements KillGoodsVoService {
	
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private GoodsPriceService goodsPriceService;
	@Override
	public KillGoodsVo setKillGoodsInfo(int goodId) {
		// TODO Auto-generated method stub
		Goods goods=goodsService.findGoodsById(goodId);
		GoodsPrice goodsPrice=goodsPriceService.findGoodsPriceByGoodsId(goodId);
		Calendar calendar =Calendar.getInstance();
       calendar.set(2019,7,13,17,30,00);//实际时间是8月
       Date date=calendar.getTime();
       KillGoodsVo kg=new KillGoodsVo(goods, goodsPrice,(date.getTime()- new Date().getTime())/1000+"",101);
       
       return kg;
	}

	

}
