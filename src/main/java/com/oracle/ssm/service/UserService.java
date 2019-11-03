package com.oracle.ssm.service;

import com.oracle.ssm.model.User;

public interface UserService {

	User findUserById(int id);

	void addUser(User user);

	User getUserByUsername(String username);

}
