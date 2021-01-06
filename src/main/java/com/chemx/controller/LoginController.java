package com.chemx.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.chemx.model.User;
import com.chemx.service.UserService;
import com.chemx.service.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginController {

	static UserService userServ = new UserServiceImpl();
	static final Logger logger = Logger.getLogger(LoginController.class);
	
	public static void main(String[] args) {
		logger.setLevel(Level.ALL);

	}
    

	public static String login(HttpServletRequest req) {

		/*
		 * handle improper http methods
		 */

		if (!req.getMethod().equals("POST")) {
			return "/index.html";
		}
		/*
		 * extracts data from login form sent through the request body
		 */

		String username = req.getParameter("username");
		String password = req.getParameter("password");

		/*
		 * validate against db
		 */

	
		User loggedIn = userServ.verifyValidUser(username, password);
		
		if(loggedIn != null) {
			req.getSession().setAttribute("user", loggedIn);
			if (loggedIn.getRole().equals("employee")) {
				
				logger.info("INFO: User logged in: Username: " + username);

				return "/forwarding/employeeHome";
			} else if (loggedIn.getRole().equals("finance manager")) {
				logger.info("INFO: User logged in: Username: " + username);

				return "/forwarding/managerHome";
			} else {
				return null;
			}
		} else {
			System.out.println("login failed");
			logger.info("failed login attempt with username: " + username);

			return "/forwarding/badlogin";
		}

	}

	public static void getSession(HttpServletRequest req, HttpServletResponse res) {
		
		 try {
			 User test = (User) req.getSession().getAttribute("user");
			res.getWriter().write(new ObjectMapper().writeValueAsString(test));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
