package com.oracle.ssm.service.impl;

import java.util.Date;

import org.apache.ibatis.reflection.wrapper.BaseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.ssm.mapper.GoodsPriceMapper;
import com.oracle.ssm.mapper.OrdersGoodsRelationMapper;
import com.oracle.ssm.mapper.OrdersMapper;
import com.oracle.ssm.model.Orders;
import com.oracle.ssm.model.OrdersGoodsRelation;
import com.oracle.ssm.service.OrdersService;

@Service
public class OrdersServiceImpl implements OrdersService{
	
	@Autowired
	private OrdersMapper ordersMapper;
	@Autowired
	private OrdersGoodsRelationMapper odrMapper;
	@Autowired
	private GoodsPriceMapper priceMapper;
	@Override
	public void addOrders(Orders orders) {
		//��������spring���������
		
		ordersMapper.insertOrders(orders);//���붩����
		int ordersId=orders.getOrderId();
		int goodsId=orders.getGoodsId();
	
		OrdersGoodsRelation ogr=new OrdersGoodsRelation();
		ogr.setGoodsId(goodsId);
		ogr.setOrdersId(ordersId);
		odrMapper.insert(ogr);//�����ϵ��
	}
	@Override
	public Orders insertOrdersByUserid(int id, String userId) {
		// ���붩��
		
		Orders orders=new Orders();
		orders.setOrdersMoney(priceMapper.selectByPrimaryKey(id).getDiscountPrice()+"");
		orders.setOrderTime(new Date());
		orders.setShopCount("1");
		//1������δ���� 2�������Ѹ��� 3�������ѷ��� 4�����ջ�
		orders.setStaus(1);
		orders.setUpdateTime(new Date());
		orders.setUserId(Integer.parseInt(userId));
		ordersMapper.insertSelective(orders);
		
		//�ڶ��� ������ϵ��
		OrdersGoodsRelation rgr=new OrdersGoodsRelation();
		rgr.setGoodsId(id);
		rgr.setOrdersId(orders.getOrderId());
		ordersMapper.insertOrdersGoodsRelation(rgr);
		
		return orders;
	}
	@Override
	public Orders findOrdersDetailById(int id) {
		// TODO Auto-generated method stub
		return ordersMapper.findOrdersDetailById(id);
	}
	@Override
	public void updateOrdersByOrders(Orders orders) {
		// TODO Auto-generated method stub
		ordersMapper.updateByPrimaryKey(orders);
	}
	
	
}
