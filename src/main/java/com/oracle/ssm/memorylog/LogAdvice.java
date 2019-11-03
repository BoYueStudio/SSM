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
					 type="���";
				}else {			
	  		         tablename=methodname.substring(3,  methodname.indexOf("By"));	
	  		         type="���";
				}
		    }
		   
		   if (methodname.contains("delete")) {
     		  tablename=methodname.substring(methodname.indexOf("delete")+6, methodname.indexOf("By"));	
		      type="ɾ��";
		   }
		   
		   if (methodname.contains("update")) {
		  		  tablename=methodname.substring(6, methodname.indexOf("By"));	
			      type="�޸�";
			}
		   
		   if (methodname.contains("get")) {
			   if (methodname.indexOf("By")<0) {
					tablename=methodname.substring(3);	
					 type="��ѯ";
				}else {			
	  		         tablename=methodname.substring(3,  methodname.indexOf("By"));	
	  		         type="��ѯ";
				}
			}
		   
		   if (methodname.contains("find") ) {
			   if (methodname.indexOf("By")<0) {
					tablename=methodname.substring(4);	
					 type="��ѯ";
				}else {			
	  		         tablename=methodname.substring(4,  methodname.indexOf("By"));	
	  		         type="��ѯ";
				}
			}
		   	
			 Object[] arrays=joinpoint.getArgs();
			 StringBuffer rStringBuffer=new StringBuffer();
			 
			 String  param="";
			 int a=0;
			 for (Object  info : arrays) {
				param=info.getClass().getName();
				param=param.substring(param.lastIndexOf(".")+1);
		
			 rStringBuffer.append("����:"+param+",ֵ:"+joinpoint.getArgs()[a]);
			 a++;
			 
			 
			 }
				
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			Date date=new Date();
			SysLog sysLog=new SysLog();
			sysLog.setTableName(tablename);
			sysLog.setParam("������"+methodname+"������"+rStringBuffer);
			sysLog.setOperateType(type);
			sysLog.setOperateTime(date);
			
			
			System.out.println("������:"+methodname);
			System.out.println("������: "+tablename);
			System.out.println("��������:"+type);
	        System.out.println("��������:"+rStringBuffer);
	        System.out.println("����ʱ��:"+sdf.format(date));
	      if (object==null) {
			  sysLog.setResult("�˲���û�з���ֵ");
	      }else{
	    	  sysLog.setResult("����ֵ:"+object);
		  }
	      
	     
	     
	      sysLogService.LogInsert(sysLog);
		
		
		
		
	}

}
