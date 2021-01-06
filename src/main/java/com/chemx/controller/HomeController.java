package com.chemx.controller;


import javax.servlet.http.HttpServletRequest;

public class HomeController {

	public static String employeeHome(HttpServletRequest req) {
		// TODO Auto-generated method stub
		return "/resources/html/employeeHome.html";
	}

	public static String managerHome(HttpServletRequest req) {
		// TODO Auto-generated method stub
		return "/resources/html/mangerHome.html";
	}


}

