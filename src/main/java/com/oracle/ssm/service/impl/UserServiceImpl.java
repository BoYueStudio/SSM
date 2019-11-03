package com.oracle.ssm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.oracle.ssm.mapper.UserMapper;
import com.oracle.ssm.model.User;
import com.oracle.ssm.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;

	@Override
	public User findUserById(int id) {
		// TODO Auto-generated method stub
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public void addUser(User user) {
		// TODO Auto-generated method stub
		
		userMapper.insertSelective(user);
		
	}

	@Override
	public User getUserByUsername(String username) {
	
		return userMapper.getUserByUsername(username);
	}

}
