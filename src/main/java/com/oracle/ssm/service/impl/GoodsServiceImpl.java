package com.oracle.ssm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.ssm.mapper.GoodsMapper;
import com.oracle.ssm.model.Goods;
import com.oracle.ssm.service.GoodsService;
import com.oracle.ssm.vo.GoodsVo;
import com.oracle.ssm.vo.SeachGoodsVo;

@Service
public class GoodsServiceImpl implements GoodsService{
	
	@Autowired
	private GoodsMapper goodsMapper;

	public Goods getAg(){
		return goodsMapper.selectByPrimaryKey(1);
	}
	@Override
	public List<Goods> getGoodsByAll() {
		
		return goodsMapper.getGoodsAll();
	}
	@Override
	public void addGoods(Goods goods) {
		
		 goodsMapper.addGoods(goods);
		
	}
	@Override
	public void deleteGoodsById(int id) {
		
		goodsMapper.deleteByPrimaryKey(id);
	}
	@Override
	public Goods findGoodsById(int id) {
		
		return goodsMapper.selectByPrimaryKey(id);
	}
	@Override
	public void updateGoodsById(Goods goods) {
		
		goodsMapper.updateGoodsById(goods);
		
	}
	@Override
	public List<Goods> findGoodsBySeachVo(SeachGoodsVo seachGoodsVo) {
		
		return goodsMapper.findGoodsBySeachVo(seachGoodsVo);
	}
	@Override
	public void deleteGoodsByIds(String[] array) {
		
		goodsMapper.deleteGoodsByIds(array);
	}
	@Override
	public GoodsVo findGoodsVoById(int id) {
		// TODO Auto-generated method stub
		return goodsMapper.findGoodsVoById(id);
	}

}
