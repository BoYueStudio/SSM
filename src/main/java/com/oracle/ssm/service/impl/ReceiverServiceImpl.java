package com.oracle.ssm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.ssm.mapper.ReceiverMapper;
import com.oracle.ssm.model.Receiver;
import com.oracle.ssm.service.ReceiverService;

@Service
public class ReceiverServiceImpl implements ReceiverService{

	@Autowired
	private ReceiverMapper receiverMapper;
	@Override
	public List<Receiver> findReveiversByUserId(int parseInt) {
		// TODO Auto-generated method stub
		return receiverMapper.findReveicersByUserId(parseInt);
	}

	

}
