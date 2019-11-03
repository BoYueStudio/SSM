package com.oracle.rabbit.task;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.oracle.ssm.model.Orders;
import com.oracle.ssm.service.OrdersService;

public class ConsumerOrders implements MessageListener {
	@Autowired
	private OrdersService ordersService;
    /**
     * 当消费到消息之后，提供的一个消息处理的方法
     */
	@Override
	public void onMessage(Message message) {
		String ordersId=new String(message.getBody());
		Orders orders=ordersService.findOrdersDetailById(Integer.parseInt(ordersId));
		if(orders.getStaus()==1) {
			//意味着都30分钟过去了，还没付款? 先去对账，再设置成失效
			//对账
			orders.setStaus(-1);
			ordersService.updateOrdersByOrders(orders);
		}else if(orders.getStaus()==2){
			//意味着30分钟已经付款,不做处理
		}
		
	}

	
}
