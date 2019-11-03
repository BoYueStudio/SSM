package com.oracle.ssm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.oracle.ssm.model.Goods;
import com.oracle.ssm.model.SysLog;
import com.oracle.ssm.service.SysLogService;

@Controller
public class SysLogController {
	
	@Autowired
	private SysLogService sysLogService;
	/**
	 * 获取所有日志信息
	 * @return
	 */
	@RequestMapping("/logGet.do")
	public ModelAndView LogGet(){
		List<SysLog> sysLogList=sysLogService.LogGet();
		ModelAndView mv=new ModelAndView();
		mv.addObject("sysLogList", sysLogList);
		mv.setViewName("allLog");
		return mv;
	}

}
