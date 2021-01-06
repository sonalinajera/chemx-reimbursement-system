package com.chemx.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chemx.controller.HomeController;
import com.chemx.controller.InvalidCredentialsController;
import com.chemx.controller.LoginController;

public class RequestHelper {

	public static String process(HttpServletRequest req, HttpServletResponse res) throws IOException {
//		TODO update the url to match webxml 
		
		
		switch(req.getRequestURI()) {
		case "/ChemX/forwarding/login":
			return LoginController.login(req);
		case "/ChemX/forwarding/employeeHome":
			System.out.println("case 2");
			return HomeController.employeeHome(req);
		case "/ChemX/forwarding/managerHome":
			System.out.println("case 3");
			return HomeController.managerHome(req);
		case "/ChemX/forwarding/invalidcredentials":
			System.out.println("case 4");
			return InvalidCredentialsController.incorrectCredentials(req);
			default:
				System.out.println("bad login");
				return "/resources/html/badlogin.html";
		}
 
		
	}
}
