package com.oracle.ssm.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.ssm.model.Goods;
import com.oracle.ssm.model.Orders;
import com.oracle.ssm.model.Receiver;
import com.oracle.ssm.model.User;
import com.oracle.ssm.service.GoodsPriceService;
import com.oracle.ssm.service.GoodsService;
import com.oracle.ssm.service.OrdersService;
import com.oracle.ssm.service.ReceiverService;
import com.oracle.ssm.util.JedisUtil;

@Controller
public class OrdersController {
	@Autowired
	private JedisUtil jedisUtil;
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private OrdersService ordersService;
	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private ReceiverService receiverService;
	@Autowired
	private GoodsPriceService goodsPriceService;
	@Autowired
	private RabbitTemplate  rabbitTemplate;
	
	@RequestMapping("/killOrdersDetail.do")
	public String ordersDetail(int id,HttpServletRequest request) {
		//商品id
		//先确认过用户抢过该商品
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String date=sdf.format(new Date());
		String key3="kill:users:"+date+":set:"+id;//存储抢到用户的id set的key
		String userId=jedisUtil.getUserBySession(request).getId()+"";
		
		if(redisTemplate.boundSetOps(key3).isMember(userId)){
			Orders orders=ordersService.insertOrdersByUserid(id,userId);
			
			//生成订单之后，插入订单号到rabbitmq当中的指定队列.
			org.springframework.amqp.core.MessageProperties prop=new MessageProperties();
			Message message=new Message((orders.getOrderId()+"").getBytes(),prop);
			rabbitTemplate.send(message);
			
			Goods goods=goodsService.findGoodsById(id);
			List<Goods> goodsList=new ArrayList<>();
			goodsList.add(goods);
			orders.setGoodsList(goodsList);
			List<Receiver> receiverList=receiverService.findReveiversByUserId(Integer.parseInt(userId));
			request.setAttribute("orders", orders);
			request.setAttribute("receiverList", receiverList);
			
			//记得删除掉记录用户id的set集合当中的该用户Id，防止二次刷新
			
			return "buyprod";
		}else{
			return "noAuth";//非法访问
		}
		
	}
	
	/**
	 * 订单详情页
	 */
	@RequestMapping("/orders.do")
	public String orders(int id,HttpServletRequest request){
		//id为订单Id
		Orders orders=ordersService.findOrdersDetailById(id);
		Long price=goodsPriceService.findGoodsPriceByGoodsId(orders.getGoodsId()).getOriginalPrice();
		
		//取到订单的失效时间
		Date createdate=orders.getOrderTime();
		Date expiredate=new Date(createdate.getTime()+60*1000);
		request.setAttribute("orders", orders);
		request.setAttribute("price", price);
		if(expiredate.after(new Date())) {
			request.setAttribute("expiredate", expiredate);
		}
		return "order";
	}

}

