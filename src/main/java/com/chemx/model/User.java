package com.chemx.model;

import java.util.ArrayList;
import java.util.List;

public class User {
	
	//STATE
	
	private int userId;
	private String firstName, lastName, username, password, email, role;
	public List<Reimbursement> usersReimbList = new ArrayList<> (); 
	
	/*
	 * No arg constructor
	 * all arg constructor
	 * getters/setterss
	 * toString
	 */
	
	public User() {
		// TODO Auto-generated constructor stub
	}


	public User(int userId, String firstName, String lastName, String username, String password, String email,
			String role) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", username="
				+ username + ", password=" + password + ", email=" + email + ", role=" + role + ", usersReimbList="
				+ usersReimbList + "]";
	}

	public List<Reimbursement> getUsersReimbList() {
		return usersReimbList;
	}


	
	

}
