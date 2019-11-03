package com.oracle.ssm.service;

import java.util.List;

import com.oracle.ssm.model.Receiver;

public interface ReceiverService {

	List<Receiver> findReveiversByUserId(int parseInt);

	

}
