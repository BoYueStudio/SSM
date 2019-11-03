package com.oracle.ssm.intercepter;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.oracle.ssm.model.User;
import com.oracle.ssm.vo.GoodsVo;

public class SessionIntercpter implements HandlerInterceptor{
private Logger logger=LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RedisTemplate jedis;



	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {

		System.out.println("进入处理器之前被拦截器拦截");
//		
//		String path=request.getRequestURI();
//		if(path.contains("Login")){
//			return true;//登录页面
//		}else{
//			HttpSession session=request.getSession(false);
//			if(session!=null){
//				if(session.getAttribute("user")!=null){
//					return true;
//				}else{
//					response.sendRedirect("toLogin.do");	
//					return false;
//				}
//			}else{
//				response.sendRedirect("toLogin.do");
//				return false;
//			}
//		}

		
		String url=request.getRequestURI();
		if(url.contains("Login")||url.contains("shop")||url.contains("Shop")||url.contains("Goods")) {
			return true;
		}else {
			String ip=request.getRemoteAddr();
			String key="session:"+ip;
			
			
			if(jedis.hasKey(key)) {
				byte[] bytes=(byte[]) jedis.opsForValue().get(key);
				User user=(User) jedis.getHashValueSerializer().deserialize(bytes);
				return true;
			}else {
				response.sendRedirect(request.getContextPath()+"/toLogin.do");
				return false;
			}
				
		}
		

	}
	 
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		System.out.println("处理器出来完之后返回modelandview给dispatherservlet之前拦截");
		
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
		
	 System.out.println("视图渲染完毕，请求全部结束3");
	 if(arg3!=null){
		 logger.error(arg3.getMessage());
	 }
	 boolean isLogin=jedis.hasKey("session:"+request.getRemoteAddr());
	 System.out.println("dfjdksfjlkdsajfkl:"+isLogin);
		if(isLogin==true) {
			
			//用户登陆了，不是游客
			//使用cookie来进行存储,
			//取到请求当中携带的cookie，添加到购物车
			
			Cookie[] cookies=request.getCookies();
			Cookie shopCookie=null;
			for(Cookie cookie:cookies) {
				if(cookie.getName().equals("shop")) {
					shopCookie=cookie;
					break;
				}
			}
			List<GoodsVo> list1=null;//cookie中的数据
			
			if(shopCookie!=null) {
				//游客在cookie中的购物车数据
				String value=shopCookie.getValue();
				String json=URLDecoder.decode(value, "utf-8");
				list1=JSON.parseArray(json, GoodsVo.class);
				
			}
			
			byte[] bytes=(byte[]) jedis.boundValueOps("session:"+request.getRemoteAddr()).get();
			User user=(User) jedis.getHashValueSerializer().deserialize(bytes);
			boolean check=jedis.hasKey("shop:"+user.getId());
			if(check) {
				byte[] data=(byte[]) jedis.boundValueOps("shop:"+user.getId()).get();
				List<GoodsVo> goodsList=(List<GoodsVo>) jedis.getHashValueSerializer().deserialize(data);
				if(list1!=null){//cookie中有商品
					HashSet<Integer> set=new HashSet<>();
					for(GoodsVo rgoods:goodsList){
						set.add(rgoods.getGoods().getId());
					}
									
					for(GoodsVo cgoods:list1){
						if(set.contains(cgoods.getGoods().getId())){
							//cookie中的商品id，在redis中的商品id有
							for(GoodsVo rgoods:goodsList){
								rgoods.setAvaliableCount(rgoods.getAvaliableCount()+cgoods.getAvaliableCount());
								break;
							}
							
						}else{
							goodsList.add(cgoods);
							
						}
					}
					
					//删掉cookie
				
					String key="shop";
					String value="";
					Cookie cookie=new Cookie(key,value);
					//设置cookie的生命周期
					cookie.setMaxAge(0);
					cookie.setPath(request.getContextPath());
					response.addCookie(cookie);
				
						
						
				}
				
			}else{
				//登录了，还没有购物车
				if(list1!=null){
					byte[] data1=jedis.getHashValueSerializer().serialize(list1);
					jedis.boundValueOps("shop:"+user.getId()).set(data1);
					
					//删掉cookie
					
					String key="shop";
					String value="";
					Cookie cookie=new Cookie(key,value);
					//设置cookie的生命周期
					cookie.setMaxAge(0);
					cookie.setPath(request.getContextPath());
					response.addCookie(cookie);
				}
					
			}
			
		}
		
	}

	

}
