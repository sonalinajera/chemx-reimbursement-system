package com.chemx.dao;

import com.chemx.model.User;

public interface UserDao {
	
	public  User verifyValidUser(String tryUsername, String tryPassword);
}
