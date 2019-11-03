package com.oracle.ssm.memorylog;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;

import com.oracle.ssm.model.SysLog;
import com.oracle.ssm.service.SysLogService;
import com.oracle.ssm.service.impl.SysLogServiceImpl;


public class LogAdvice {
	
	@Autowired
	private SysLogService sysLogService;

	public void after(JoinPoint joinpoint,Object object){

		   String methodname=joinpoint.getSignature().getName();
		   String  tablename="";  String type="";
		   
		   if (methodname.contains("add") ) {
				if (methodname.indexOf("By")<0) {
					tablename=methodname.substring(3);	
					 type="添加";
				}else {			
	  		         tablename=methodname.substring(3,  methodname.indexOf("By"));	
	  		         type="添加";
				}
		    }
		   
		   if (methodname.contains("delete")) {
     		  tablename=methodname.substring(methodname.indexOf("delete")+6, methodname.indexOf("By"));	
		      type="删除";
		   }
		   
		   if (methodname.contains("update")) {
		  		  tablename=methodname.substring(6, methodname.indexOf("By"));	
			      type="修改";
			}
		   
		   if (methodname.contains("get")) {
			   if (methodname.indexOf("By")<0) {
					tablename=methodname.substring(3);	
					 type="查询";
				}else {			
	  		         tablename=methodname.substring(3,  methodname.indexOf("By"));	
	  		         type="查询";
				}
			}
		   
		   if (methodname.contains("find") ) {
			   if (methodname.indexOf("By")<0) {
					tablename=methodname.substring(4);	
					 type="查询";
				}else {			
	  		         tablename=methodname.substring(4,  methodname.indexOf("By"));	
	  		         type="查询";
				}
			}
		   	
			 Object[] arrays=joinpoint.getArgs();
			 StringBuffer rStringBuffer=new StringBuffer();
			 
			 String  param="";
			 int a=0;
			 for (Object  info : arrays) {
				param=info.getClass().getName();
				param=param.substring(param.lastIndexOf(".")+1);
		
			 rStringBuffer.append("参数:"+param+",值:"+joinpoint.getArgs()[a]);
			 a++;
			 
			 
			 }
				
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			Date date=new Date();
			SysLog sysLog=new SysLog();
			sysLog.setTableName(tablename);
			sysLog.setParam("方法："+methodname+"参数："+rStringBuffer);
			sysLog.setOperateType(type);
			sysLog.setOperateTime(date);
			
			
			System.out.println("方法名:"+methodname);
			System.out.println("表名字: "+tablename);
			System.out.println("操作类型:"+type);
	        System.out.println("参数类型:"+rStringBuffer);
	        System.out.println("操作时间:"+sdf.format(date));
	      if (object==null) {
			  sysLog.setResult("此操作没有返回值");
	      }else{
	    	  sysLog.setResult("返回值:"+object);
		  }
	      
	     
	     
	      sysLogService.LogInsert(sysLog);
		
		
		
		
	}

}
