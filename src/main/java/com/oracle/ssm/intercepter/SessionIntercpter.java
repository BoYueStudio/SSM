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

		System.out.println("���봦����֮ǰ������������");
//		
//		String path=request.getRequestURI();
//		if(path.contains("Login")){
//			return true;//��¼ҳ��
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
		System.out.println("������������֮�󷵻�modelandview��dispatherservlet֮ǰ����");
		
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
		
	 System.out.println("��ͼ��Ⱦ��ϣ�����ȫ������3");
	 if(arg3!=null){
		 logger.error(arg3.getMessage());
	 }
	 boolean isLogin=jedis.hasKey("session:"+request.getRemoteAddr());
	 System.out.println("dfjdksfjlkdsajfkl:"+isLogin);
		if(isLogin==true) {
			
			//�û���½�ˣ������ο�
			//ʹ��cookie�����д洢,
			//ȡ��������Я����cookie����ӵ����ﳵ
			
			Cookie[] cookies=request.getCookies();
			Cookie shopCookie=null;
			for(Cookie cookie:cookies) {
				if(cookie.getName().equals("shop")) {
					shopCookie=cookie;
					break;
				}
			}
			List<GoodsVo> list1=null;//cookie�е�����
			
			if(shopCookie!=null) {
				//�ο���cookie�еĹ��ﳵ����
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
				if(list1!=null){//cookie������Ʒ
					HashSet<Integer> set=new HashSet<>();
					for(GoodsVo rgoods:goodsList){
						set.add(rgoods.getGoods().getId());
					}
									
					for(GoodsVo cgoods:list1){
						if(set.contains(cgoods.getGoods().getId())){
							//cookie�е���Ʒid����redis�е���Ʒid��
							for(GoodsVo rgoods:goodsList){
								rgoods.setAvaliableCount(rgoods.getAvaliableCount()+cgoods.getAvaliableCount());
								break;
							}
							
						}else{
							goodsList.add(cgoods);
							
						}
					}
					
					//ɾ��cookie
				
					String key="shop";
					String value="";
					Cookie cookie=new Cookie(key,value);
					//����cookie����������
					cookie.setMaxAge(0);
					cookie.setPath(request.getContextPath());
					response.addCookie(cookie);
				
						
						
				}
				
			}else{
				//��¼�ˣ���û�й��ﳵ
				if(list1!=null){
					byte[] data1=jedis.getHashValueSerializer().serialize(list1);
					jedis.boundValueOps("shop:"+user.getId()).set(data1);
					
					//ɾ��cookie
					
					String key="shop";
					String value="";
					Cookie cookie=new Cookie(key,value);
					//����cookie����������
					cookie.setMaxAge(0);
					cookie.setPath(request.getContextPath());
					response.addCookie(cookie);
				}
					
			}
			
		}
		
	}

	

}
