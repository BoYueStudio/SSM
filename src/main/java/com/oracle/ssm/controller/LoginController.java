package com.oracle.ssm.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.ssm.model.User;
import com.oracle.ssm.service.UserService;


@Controller

public class LoginController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RedisTemplate redisTemplate;	


	
	@RequestMapping("/toLogin.do")
	public String toLogin(){
		System.out.println("我是拦截器，正在处理业务逻辑");
		return "login";
	}
	/**
	 * 登录
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/Login.do",method=RequestMethod.POST)
	public @ResponseBody User login(@RequestBody User user,HttpServletRequest request){
		
		User user2=userService.getUserByUsername(user.getLoginName());
		if(user2!=null){
			if(user2.getPassword().equals(user.getPassword())){
//				存session
				String host=request.getRemoteAddr();
				String key="session:"+host;
				byte[] bytes=redisTemplate.getHashValueSerializer().serialize(user2);
				redisTemplate.boundValueOps(key).set(bytes);
				return user;
			}else{
				return null;//密码错误
			}
		}else{
			return user2;//用户错误null
		}
		
		
	}
	

}
