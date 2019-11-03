package com.oracle.ssm.mapper;

import java.util.List;

import com.oracle.ssm.model.Goods;
import com.oracle.ssm.vo.GoodsVo;
import com.oracle.ssm.vo.SeachGoodsVo;

public interface GoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKeyWithBLOBs(Goods record);

    int updateByPrimaryKey(Goods record);

	List<Goods> getGoodsAll();

	void addGoods(Goods goods);

	Goods findGoodsById(int id);

	void updateGoodsById(Goods goods);

	List<Goods> findGoodsBySeachVo(SeachGoodsVo seachGoodsVo);

	void deleteGoodsByIds(String[] array);

	GoodsVo findGoodsVoById(int id);
}