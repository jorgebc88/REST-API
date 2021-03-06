package com.finalproject.services;

import com.finalproject.dao.UserDao;
import com.finalproject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServicesImpl implements UserServices {

	@Autowired
	UserDao userDao;

	@Override
	public boolean addUser(User user) throws Exception {
		return userDao.addUser(user);
	}

	@Override
	public User getUserById(long id) throws Exception {
		return userDao.getUserById(id);
	}

	@Override
	public List<User> getUserList() throws Exception {
		return userDao.getUserList();
	}

	@Override
	public boolean deleteUser(long id) throws Exception {
		return userDao.deleteUser(id);
	}

	@Override
	public User login(String name, String password) throws Exception {
		return this.userDao.login(name, password);
	}


}