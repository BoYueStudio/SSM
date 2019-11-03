package com.oracle.ssm.service;

import java.util.List;

import com.oracle.ssm.model.SysLog;

public interface SysLogService {

	List<SysLog>  LogGet();
	void LogInsert(SysLog sysLog);

}
