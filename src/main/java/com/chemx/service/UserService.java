package com.chemx.service;

import java.util.List;

import com.chemx.model.Reimbursement;
import com.chemx.model.User;

public interface UserService {
	//Create
	
	public void registerUser(User user);
	
	//Update
	
	
	//Read
	

	public User verifyValidUser(String username, String password);
	public User returnVerifiedUser();
	public List<Reimbursement> retrieveReimbByUser(User user);
	
	//Delete 
	
	
	

}
 