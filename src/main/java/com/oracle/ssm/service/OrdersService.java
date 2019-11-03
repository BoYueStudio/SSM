package com.oracle.ssm.service;

import com.oracle.ssm.model.Orders;

public interface OrdersService {

	void addOrders(Orders orders);

	Orders insertOrdersByUserid(int id, String userId);

	Orders findOrdersDetailById(int id);

	void updateOrdersByOrders(Orders orders);

}
