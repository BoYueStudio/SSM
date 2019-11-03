package com.oracle.ssm.controller;

import java.awt.List;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.oracle.ssm.model.Goods;
import com.oracle.ssm.model.Orders;
import com.oracle.ssm.model.User;
import com.oracle.ssm.service.GoodsPriceService;
import com.oracle.ssm.service.GoodsService;
import com.oracle.ssm.service.KillGoodsVoService;
import com.oracle.ssm.service.OrdersService;
import com.oracle.ssm.vo.KillGoodsVo;
import com.oracle.ssm.vo.SecKillGoodsVo;

@Controller
public class KillGoodsController {
	
	@Autowired
	private KillGoodsVoService kgvs;
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private OrdersService ordersService;
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private GoodsPriceService priceService;
	@RequestMapping("toKillList.do")
	public ModelAndView toKillList(HttpServletRequest request){
		//单个秒杀
		//秒杀商品信息
		KillGoodsVo kg=kgvs.setKillGoodsInfo(2);
		
		//用户信息
		String host=request.getRemoteAddr();
		String key="session:"+host;
		byte[] bytes=(byte[]) redisTemplate.opsForValue().get(key);
		User user=(User) redisTemplate.getHashValueSerializer().deserialize(bytes);
		
		//抢购剩余数量
		String goodsId="seckillgoods:"+kg.getGoods().getId();
		Long num=redisTemplate.boundListOps(goodsId).size();
		
		ModelAndView mv=new ModelAndView();
		mv.setViewName("killList");
		mv.addObject("user",user);
		mv.addObject("killGoods",kg);
		mv.addObject("num",num);
		
		return mv;
	}
	
	@RequestMapping("setKillInfo.do")
	public void setKillInfo(){
		//单个 假数据
		String key="seckillgoods:2";
		for(int i=1;i<=101;i++){
			redisTemplate.boundListOps(key).rightPush(i);
		}
		
		
	}
	
	@RequestMapping("killGoods.do")
	public  void kill(int id1,int id2,HttpServletResponse resp) throws Exception{
		
		// 单个秒杀 商品id1,用户id2
		String goodsId="seckillgoods:"+id1;//秒杀商品list队列的 key
		String userKey="seccess:kill:"+id1;//秒杀成功用户set集合 key
		String userid=id2+"";
		
		if(redisTemplate.boundListOps(goodsId).size()!=0){//判断秒杀商品是否还有
			if(!redisTemplate.opsForSet().isMember(userKey, userid)){//判断是否已经抢过
				String index= redisTemplate.boundListOps(goodsId).leftPop()+"";
				if(index!=null){
					
					redisTemplate.boundSetOps(userKey).add(userid);//向抢到的set添加用户id
					resp.getWriter().write("1");//System.out.println("秒杀成功！"+index);
				}else{
					resp.getWriter().write("2");//System.out.println("下次再来！");
				}
			}else{
				resp.getWriter().write("3");//System.out.println("你已抢过,请把机会留给别人！");
			}
			
			
		}else{
			resp.getWriter().write("2");//System.out.println("下次再来！");
		}
		
	}
	
	@RequestMapping("/goPay.do")
	public ModelAndView goPay(int id1,int id2,Double money,HttpServletRequest req){//id1商品id,id2用户id
		
		String userKey="seccess:kill:"+id1;//秒杀成功用户set集合 key
		String userid=id2+"";
		if(redisTemplate.opsForSet().isMember(userKey, userid)){//二次检验用户id
			
			Orders orders=new Orders();
			orders.setGoodsId(id1);
			orders.setUserId(id2);
			orders.setUpdateTime(new Date());
			orders.setOrdersMoney(money+"");
			orders.setShopCount(1+"");//抢购数量默认为1
			orders.setStaus(0);//订单状态默认0，未付款
			ordersService.addOrders(orders);
			
			
			ModelAndView mv=new ModelAndView();
			mv.setViewName("killPay");
			mv.addObject("orders", orders);
			return mv;
		}
		
		return null;
		
	}
	
	@RequestMapping("/makeDate.do")
	public void toKillPay() throws Exception{
		//做多个商品的假数据
	//key1 kill:goods:变量日期:id:商品id 商品信息封装 GoodsVo的key
		
	//key2 kill:goods:变量日期:list:商品id 商品的库存序号 list的key
		
	//key3 kill:users:变量日期:set:用户id 抢购成功用户id set集合的key
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date=sdf.format(new Date());
		
		for(int i=1;i<5;i++){//i代表goods的id
			//第一组key value string类型
			String key1="kill:goods:"+date+":id:"+i;
			SecKillGoodsVo vo=new SecKillGoodsVo();
			vo.setGoods(goodsService.findGoodsById(i));
			vo.setStartTime(sdf2.parse("2019-08-18 8:00:00")); 
			vo.setEndTime(sdf2.parse("2019-08-18 23:00:00"));
			vo.setPrice(priceService.findGoodsPriceByGoodsId(i).getDiscountPrice()+"");
			vo.setInitCount(100);
			
			//序列化秒杀商品的封装信息
			byte[] b=redisTemplate.getHashValueSerializer().serialize(vo);
			redisTemplate.boundValueOps(key1).set(b);
			
			//第二组key list list类型
			String key2="kill:goods:"+date+":list:"+i;
			for(int j=1;j<=100;j++){
				redisTemplate.boundListOps(key2).leftPush(j);
			}
			
		}
	
	}
	@RequestMapping("/toManyKillList.do")
	public String manyKillList(HttpServletRequest req){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String date=sdf.format(new Date());
		String goods_key_prefix="kill:goods:"+date+":id:*";
		ArrayList<SecKillGoodsVo> list=new ArrayList<>();
		Set<String> set=redisTemplate.keys(goods_key_prefix);
		
		for(String key:set){
			//反序列化获取商品信息 存到list
			byte[] bs=(byte[]) redisTemplate.boundValueOps(key).get();
			SecKillGoodsVo vo=(SecKillGoodsVo) redisTemplate.getHashValueSerializer().deserialize(bs);
			
			//秒杀抢购页面需要时时更新商品库存数量
			//根据存储商品数量 list的大小 
			vo.setInitCount((redisTemplate.boundListOps("kill:goods:"+date+":list:"+vo.getGoods().getId()).size()).intValue());
			list.add(vo);
			
		}
		
		req.setAttribute("goodsList", list);
		
		return "manyKillList";
	}
	
	@RequestMapping("/manyKillGoods.do")
	public void manyKillGoods(int id,HttpServletRequest req,HttpServletResponse resp) throws Exception{
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String date=sdf.format(new Date());
		
		String key1="kill:goods:"+date+":id:"+id;//存储商品id的vo类 value的key
		String key2="kill:goods:"+date+":list:"+id;//存储数量 list的key
		String key3="kill:users:"+date+":set:"+id;//存储抢到用户的id set的key
		//首先判断登录与否 session
		//判断商品库存数量 list的大小
		//判断抢过没有 set里是否有userId
		//恭喜抢到
		
		String userKey="session:"+req.getRemoteAddr();
		
		if(redisTemplate.hasKey(userKey)){
			//已登录,判断是否是抢购时间
			SecKillGoodsVo vo=(SecKillGoodsVo)redisTemplate.getHashValueSerializer().deserialize((byte[])(redisTemplate.boundValueOps(key1).get()));
			if(vo.getStartTime().before(new Date())){
				// 接着判断商品库存数量
				// 从redisTemplate里获取用户信息，主要是用户id
				byte[] bs = (byte[]) redisTemplate.boundValueOps(userKey).get();
				User user = (User) redisTemplate.getHashValueSerializer().deserialize(bs);
				String userId = user.getId() + "";
				if (redisTemplate.boundListOps(key2).size() > 0) {
					// 有库存，接着判断抢过没有
					if (!redisTemplate.boundSetOps(key3).isMember(userId)) {
						// 没有抢过，
						redisTemplate.boundListOps(key2).rightPop();// 减少库存，弹出list
						redisTemplate.boundSetOps(key3).add(userId);// 记录抢到的用户id
						resp.getWriter().write("5");//恭喜抢到
					} else {
						// 表示已经抢过
						resp.getWriter().write("4");
					}
				} else {
					// 已被抢光了
					resp.getWriter().write("3");
				}
			}else{
				//没有到抢购时间
				resp.getWriter().write("2");
			}
		}else{
			//未登录
			resp.getWriter().write("1");
		}
		
	}
	
	
	

}
