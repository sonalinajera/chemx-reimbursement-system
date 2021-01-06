package com.chemx.service;

import java.util.List;

import com.chemx.dao.UserDao;
import com.chemx.dao.UserDaoImpl;
import com.chemx.model.Reimbursement;
import com.chemx.model.User;

public class UserServiceImpl implements UserService {
	
	UserDao userDao = new UserDaoImpl(); 

	@Override
	public void registerUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User verifyValidUser(String username, String password) {
		username.toLowerCase().trim();
		password.trim();
		User loggedIn = userDao.verifyValidUser(username, password);
		if (loggedIn == null) {
			return null;
		} else {
		return loggedIn;
		}
	}

	@Override
	public User returnVerifiedUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Reimbursement> retrieveReimbByUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
