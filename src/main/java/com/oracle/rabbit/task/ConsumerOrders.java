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
     * �����ѵ���Ϣ֮���ṩ��һ����Ϣ����ķ���
     */
	@Override
	public void onMessage(Message message) {
		String ordersId=new String(message.getBody());
		Orders orders=ordersService.findOrdersDetailById(Integer.parseInt(ordersId));
		if(orders.getStaus()==1) {
			//��ζ�Ŷ�30���ӹ�ȥ�ˣ���û����? ��ȥ���ˣ������ó�ʧЧ
			//����
			orders.setStaus(-1);
			ordersService.updateOrdersByOrders(orders);
		}else if(orders.getStaus()==2){
			//��ζ��30�����Ѿ�����,��������
		}
		
	}

	
}
