package com.chemx.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chemx.controller.LoginController;
import com.chemx.controller.ReimbursementController;
import com.chemx.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AJAXHelper {

	public static void process(HttpServletRequest req, HttpServletResponse res) throws IOException {
//		TODO update the url to match webxml 
		System.out.println("in AJAX HELPPPEERRR");
		System.out.println("AJAX current: " + req.getRequestURI());

		switch (req.getRequestURI()) {
		case "/ChemX/api/getSession":
			System.out.println("case1");
			LoginController.getSession(req, res);
			break;
		case "/ChemX/api/reimbursement":
			System.out.println("case2");
			if (req.getMethod().equals("POST")) {
				ReimbursementController.addReimbursement(req, res);
			} else if (req.getMethod().equals("PUT")) {
				ReimbursementController.updateReimbStatus(req, res);
			} else {
				ReimbursementController.getAllReimbursements(req, res);
			}
			break;
		case "/ChemX/api/reimbursements":
			System.out.println("case3"); 
			ReimbursementController.getAllPendingReimbursements(req, res);
	
			break;
		default:
			// need to find out how to make a default remove the user obj from the session
			System.out.println("Something went wrong!");
			Message error = new Message("Something went wrong!");
			res.getWriter().write(new ObjectMapper().writeValueAsString(error));

		}

	}
}
