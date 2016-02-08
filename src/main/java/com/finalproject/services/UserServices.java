package com.finalproject.services;

import java.util.List;

import com.finalproject.model.User;


public interface UserServices {
	public boolean addUser(User user) throws Exception;
	public User getUserById(long id) throws Exception;
	public List<User> getUserList() throws Exception;
	public boolean deleteUser(long id) throws Exception;
	User login(String name, String password) throws Exception;
}
