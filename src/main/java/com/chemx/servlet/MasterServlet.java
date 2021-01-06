package com.chemx.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chemx.model.User;

public class MasterServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		/*
		 * Helps reroute requests to proper controllers through helper 
		 */
		System.out.println("THIS GET" + (User)req.getAttribute("currentUser"));

//		req.getRequestDispatcher(RequestHelper.process(req )).forward(req, res);
	}
	
	@Override
	protected void  doPost(HttpServletRequest req, HttpServletResponse res ) throws ServletException, IOException {
				
		req.getRequestDispatcher(RequestHelper.process(req, res)).forward(req, res);

	}

}
