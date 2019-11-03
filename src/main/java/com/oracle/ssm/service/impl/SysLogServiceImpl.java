package com.oracle.ssm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.ssm.mapper.SysLogMapper;
import com.oracle.ssm.model.SysLog;
import com.oracle.ssm.service.SysLogService;

@Service
public class SysLogServiceImpl implements SysLogService {
	
	@Autowired
	private SysLogMapper sysLogMapper;
	@Override
	public List<SysLog> LogGet() {
		
		return sysLogMapper.LogGet();
	}

	@Override
	public void LogInsert(SysLog sysLog) {
		sysLogMapper.insertSelective(sysLog);
		
	}

	

}
