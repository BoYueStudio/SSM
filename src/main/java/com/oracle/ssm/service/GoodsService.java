package com.oracle.ssm.service;

import java.util.List;

import com.oracle.ssm.model.Goods;
import com.oracle.ssm.vo.GoodsVo;
import com.oracle.ssm.vo.SeachGoodsVo;

public interface GoodsService {

	List<Goods> getGoodsByAll();

	void addGoods(Goods goods);

	void deleteGoodsById(int id);

	Goods findGoodsById(int id);

	void updateGoodsById(Goods goods);

	List<Goods> findGoodsBySeachVo(SeachGoodsVo seachGoodsVo);

	void deleteGoodsByIds(String[] array);
	GoodsVo findGoodsVoById(int id);

}
