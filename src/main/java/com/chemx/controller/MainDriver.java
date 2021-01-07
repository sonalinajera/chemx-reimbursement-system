package com.chemx.controller;

import com.chemx.service.UserService;
import com.chemx.service.UserServiceImpl;

public class MainDriver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		UserDaoImpl test = new UserDaoImpl();
		
		UserService userServ = new UserServiceImpl(); 
		
		userServ.verifyValidUser("youngmillie", "bigmoney!");
	
	
	}

}
