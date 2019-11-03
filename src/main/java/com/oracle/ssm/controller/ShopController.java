package com.oracle.ssm.controller;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.oracle.ssm.model.Goods;
import com.oracle.ssm.model.User;
import com.oracle.ssm.service.GoodsService;
import com.oracle.ssm.vo.GoodsVo;

@Controller
public class ShopController {

	@Autowired
	private RedisTemplate jedis;
	@Autowired
	private GoodsService goodsService;
	
	/**
	 * 添加商品进购物车
	 * @throws Exception 
	 */
	@RequestMapping("/addShop.do")
	public void addShap(int goodsId,HttpServletRequest request,HttpServletResponse response) throws Exception {
		/**
		 * 购物车的逻辑
		 * 对于登录用户来说，
		 * 购物车数据，可以使用服务端来进行存储。
		 * 对于游客来说，客户端自己来存储.
		 * 登录用户，使用redis来存储   k: 用户id   v List<goods> 序列化
		 * 游客用户，使用cookie    只能存储字符串  key  value 字符串（不能包含中文空格）
		 * JS 当中有一个sessionStorage 
		 * 
		 * 
		 * 
		 */
	
		boolean isLogin=jedis.hasKey("session:"+request.getRemoteAddr());
		if(isLogin==false) {
			//使用cookie来进行存储
			//取到请求当中携带的cookie
			Cookie[] cookies=request.getCookies();
			
			Cookie shopCookie=null;
			for(Cookie cookie:cookies) {
				//判断用户之前有没有存储过购物车
				if(cookie.getName().equals("shop")) {
				
					shopCookie=cookie;
					break;
				}
			}
			if(shopCookie==null) {
				//用户第一次需要存购物车
			    //key shop   value  字符串   json
				GoodsVo goods=goodsService.findGoodsVoById(goodsId);
				goods.setAvaliableCount(1);//设置用户购物车商品数量为1
				List<GoodsVo> goodsList=new ArrayList();
				goodsList.add(goods);
				//将商品集合转成json
				String json=JSON.toJSONString(goodsList);
				//cookie当中是不准许出现中文或特殊字符的
				String key="shop";
				String value=URLEncoder.encode(json, "utf-8");
				Cookie cookie=new Cookie(key,value);
				//设置cookie的生命周期
				cookie.setMaxAge(60*60*24);
				cookie.setPath(request.getContextPath());
				response.addCookie(cookie);
				response.getWriter().write("1");
				
			}else {
				//用户之前购物车是有东西的
				String json=shopCookie.getValue();
				json=URLDecoder.decode(json, "utf-8");
				List<GoodsVo> goodsList=JSON.parseArray(json, GoodsVo.class);
				boolean isExist=false;
				for(GoodsVo goods:goodsList) {
					if(goods.getGoods().getId()==goodsId) {
						isExist=true;
						break;
					}
				}
				if(isExist) {
					response.getWriter().write("2");
				}else {
					GoodsVo goods=goodsService.findGoodsVoById(goodsId);
					goods.setAvaliableCount(1);
					goodsList.add(goods);
					//将商品集合转成json
					String json1=JSON.toJSONString(goodsList);
					//cookie当中是不准许出现中文或特殊字符的
					String key="shop";
					String value=URLEncoder.encode(json1, "utf-8");
					Cookie cookie=new Cookie(key,value);
					//设置cookie的生命周期
					cookie.setMaxAge(60*60*24);
					cookie.setPath(request.getContextPath());
					response.addCookie(cookie);
					response.getWriter().write("1");
					
				}
			}
			
			 
		}else {
			/**用户已经登录过呢
			 * redis实现
			 * key   shop:+用户id
			 * value  序列化的byte[]
			 */
			//先判断是不是第一次存购物车
			byte[] bytes=(byte[]) jedis.boundValueOps("session:"+request.getRemoteAddr()).get();
			User user=(User) jedis.getHashValueSerializer().deserialize(bytes);
			boolean check=jedis.hasKey("shop:"+user.getId());
			if(check) {//已经有购物车了
				byte[] data=(byte[]) jedis.boundValueOps("shop:"+user.getId()).get();
				List<GoodsVo> goodsList=(List<GoodsVo>) jedis.getHashValueSerializer().deserialize(data);
				boolean isGoods=false;
				for(GoodsVo goods:goodsList) {
					if(goods.getGoods().getId()==goodsId) {
						isGoods=true;
						break;
					}
				}
				if(isGoods) {
					response.getWriter().write("2");//表示已经添加
				}else {//第一次购物
					GoodsVo goods=goodsService.findGoodsVoById(goodsId);
					goods.setAvaliableCount(1);
					goodsList.add(goods);
					byte[] data1=jedis.getHashValueSerializer().serialize(goodsList);
					jedis.boundValueOps("shop:"+user.getId()).set(data1);
					response.getWriter().write("1");
					
				}
				
				
				
				
			}else {
				//用户第一次需要存购物车
				GoodsVo goods=goodsService.findGoodsVoById(goodsId);
				goods.setAvaliableCount(1);
				List<GoodsVo> goodsList=new ArrayList();
				goodsList.add(goods);
				byte[] data=jedis.getHashValueSerializer().serialize(goodsList);
				jedis.boundValueOps("shop:"+user.getId()).set(data);
				response.getWriter().write("1");
			}
			
			
			
		}
		
	
	}
	
	
	
	@RequestMapping("/shopList.do")
	public String shopList(HttpServletRequest request) throws Exception {
		/**
		 * 登录没登陆过
		 * 没登录去cookie当中取
		 * 登陆过去redis中取
		 */
		boolean isLogin=jedis.hasKey("session:"+request.getRemoteAddr());
		if(isLogin) {
			byte[] bytes=(byte[]) jedis.boundValueOps("session:"+request.getRemoteAddr()).get();
			User user=(User) jedis.getHashValueSerializer().deserialize(bytes);
			boolean check=jedis.hasKey("shop:"+user.getId());
			if(check) {
				
				byte[] data=(byte[]) jedis.boundValueOps("shop:"+user.getId()).get();
				List<GoodsVo> goodsList=(List<GoodsVo>) jedis.getHashValueSerializer().deserialize(data);
				
				request.setAttribute("goodsList", goodsList);
			}
		}else {
			//没登录过
			Cookie[] cookies=request.getCookies();
			Cookie shopCookie=null;
			for(Cookie cookie:cookies) {
				if(cookie.getName().equals("shop")) {
					shopCookie=cookie;
					break;
				}
			}
			if(shopCookie!=null) {
				String value=shopCookie.getValue();
				String json=URLDecoder.decode(value, "utf-8");
				List<GoodsVo> list=JSON.parseArray(json, GoodsVo.class);
				request.setAttribute("goodsList", list);
			}
		}
		return "carList";
	}
	
	@RequestMapping("/shopNum.do")
	public void updateNumShop(String str,int goodsId, HttpServletRequest request,HttpServletResponse response) throws Exception {
	
		boolean isLogin=jedis.hasKey("session:"+request.getRemoteAddr());
		if(isLogin==false) {
			//用户没有登录，使用cookie来进行存储
			//取到请求当中携带的cookie
			Cookie[] cookies=request.getCookies();
			Cookie shopCookie=null;
			for(Cookie cookie:cookies) {
				if(cookie.getName().equals("shop")) {
					shopCookie=cookie;
					break;
				}
			}
			if(shopCookie!=null) {
				String value=shopCookie.getValue();
				String json=URLDecoder.decode(value, "utf-8");
				List<GoodsVo> list=JSON.parseArray(json, GoodsVo.class);
				for(GoodsVo goodsVo:list){
					if(goodsVo.getGoods().getId()==goodsId){//找到商品id的为goodsid的，并修改
						if("-".equals(str)&&goodsVo.getAvaliableCount()>1){
							goodsVo.setAvaliableCount(goodsVo.getAvaliableCount()-1);
							
						}else if("+".equals(str)) {
							goodsVo.setAvaliableCount(goodsVo.getAvaliableCount()+1);
							
						}
						break;
					}
				}
				
				//将商品集合转成json
				String json2=JSON.toJSONString(list);
				//cookie当中是不准许出现中文或特殊字符的
				String key1="shop";
				String value1=URLEncoder.encode(json2, "utf-8");
				Cookie cookie=new Cookie(key1,value1);
				//设置cookie的生命周期
				cookie.setMaxAge(60*60*24);
				cookie.setPath(request.getContextPath());
				response.addCookie(cookie);
				response.getWriter().write("1");//修改成功
				
			}
			
			
		}else {
			/**用户已经登录过呢
			 * redis实现
			 * key   shop:+用户id
			 * value  序列化的byte[]
			 */
			byte[] bytes=(byte[]) jedis.boundValueOps("session:"+request.getRemoteAddr()).get();
			User user=(User) jedis.getHashValueSerializer().deserialize(bytes);
			boolean check=jedis.hasKey("shop:"+user.getId());
			if(check) {
				//从redis里取出用户商品数据
				byte[] data=(byte[]) jedis.boundValueOps("shop:"+user.getId()).get();
				List<GoodsVo> goodsList=(List<GoodsVo>) jedis.getHashValueSerializer().deserialize(data);
				
				for(GoodsVo goodsVo:goodsList){
					if(goodsVo.getGoods().getId()==goodsId){//找到商品为id的，并修改它的数量
						if("-".equals(str)&&goodsVo.getAvaliableCount()>1){
							goodsVo.setAvaliableCount(goodsVo.getAvaliableCount()-1);
							
						}else if("+".equals(str)) {
							goodsVo.setAvaliableCount(goodsVo.getAvaliableCount()+1);
							
						}
						break;
					}
				}
				
				//把数据修改后存进去
				byte[] data3=jedis.getHashValueSerializer().serialize(goodsList);
				jedis.boundValueOps("shop:"+user.getId()).set(data3);	
				response.getWriter().write("1");//修改成功
			}
			
		}	
	}
	
	@RequestMapping("/shopDel.do")
	public void delShop(int goodsId, HttpServletRequest request,HttpServletResponse response) throws Exception {
	
		boolean isLogin=jedis.hasKey("session:"+request.getRemoteAddr());
		if(isLogin==false) {
			//用户没有登录，使用cookie来进行存储
			//取到请求当中携带的cookie
			Cookie[] cookies=request.getCookies();
			Cookie shopCookie=null;
			for(Cookie cookie:cookies) {
				if(cookie.getName().equals("shop")) {
					shopCookie=cookie;
					break;
				}
			}
			if(shopCookie!=null) {
				String value=shopCookie.getValue();
				String json=URLDecoder.decode(value, "utf-8");
				List<GoodsVo> list=JSON.parseArray(json, GoodsVo.class);
				//找到商品id的为goodsid的，移除
				for (int i=0;i<list.size();i++) {
					if(list.get(i).getGoods().getId()==goodsId){
						list.remove(i);
						break;
					}
				}
				
				//将商品集合转成json
				String json2=JSON.toJSONString(list);
				//cookie当中是不准许出现中文或特殊字符的
				String key1="shop";
				String value1=URLEncoder.encode(json2, "utf-8");
				Cookie cookie=new Cookie(key1,value1);
				//设置cookie的生命周期
				cookie.setMaxAge(60*60*24);
				cookie.setPath(request.getContextPath());
				response.addCookie(cookie);
				response.getWriter().write("1");//删除成功
				
			}
			
			
		}else {
			/**用户已经登录过呢
			 * redis实现
			 * key   shop:+用户id
			 * value  序列化的byte[]
			 */
			byte[] bytes=(byte[]) jedis.boundValueOps("session:"+request.getRemoteAddr()).get();
			User user=(User) jedis.getHashValueSerializer().deserialize(bytes);
			boolean check=jedis.hasKey("shop:"+user.getId());
			if(check) {
				//从redis里取出用户商品数据
				byte[] data=(byte[]) jedis.boundValueOps("shop:"+user.getId()).get();
				List<GoodsVo> goodsList=(List<GoodsVo>) jedis.getHashValueSerializer().deserialize(data);
				//找到商品id为goodid的删除
				for (int i=0;i<goodsList.size();i++) {
					if(goodsList.get(i).getGoods().getId()==goodsId){
						goodsList.remove(i);
						break;
					}
				}
				
				//把数据修改后存进去
				byte[] data3=jedis.getHashValueSerializer().serialize(goodsList);
				jedis.boundValueOps("shop:"+user.getId()).set(data3);	
				response.getWriter().write("1");//删除成功
			}
			
		}	
	}
	
	

}
