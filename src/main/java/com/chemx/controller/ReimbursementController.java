package com.chemx.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import com.chemx.model.Message;
import com.chemx.model.Reimbursement;
import com.chemx.service.ReimbServiceImpl;
import com.chemx.service.ReimbursementService;
import com.chemx.service.UserService;
import com.chemx.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReimbursementController {

	static UserService userServ = new UserServiceImpl();
	static ReimbursementService reimbServ = new ReimbServiceImpl();

	static final Logger logger = Logger.getLogger(ReimbursementController.class);

	public static void main(String[] args) {
		logger.setLevel(Level.ALL);

	}

	public static void getAllReimbursements(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("param? " + req.getParameter("user"));
		System.out.println("status? " + req.getParameter("status"));
		int userID = 0;
		if (req.getParameter("user") != null) {
			userID = Integer.parseInt(req.getParameter("user"));
		}
		int statusID = 0;
		if (req.getParameter("status") != null) {
			statusID = Integer.parseInt(req.getParameter("status"));
		}

		if (statusID == 0) {

			List<Reimbursement> reimbursements = reimbServ.retrieveReimbByUser(userID);
			
			try {
				res.getWriter().write(new ObjectMapper().writeValueAsString(reimbursements));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (statusID > 0 & (req.getParameter("user") != null)) {
			System.out.println("not null got properly hit");
			List<Reimbursement> userReimbursements = reimbServ.retrieveReimbByStatus(statusID, userID);
			
			try {
				res.getWriter().write(new ObjectMapper().writeValueAsString(userReimbursements));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Message testing = new Message("Something went wrong! Try later");
			try {
				res.getWriter().write(new ObjectMapper().writeValueAsString(testing));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void getAllPendingReimbursements(HttpServletRequest req, HttpServletResponse res) throws IOException {
		System.out.println("this is nulll got properly got hit");

		List<Reimbursement> pendingReimbursements = reimbServ.retriveAllPendingReimb();
		
		try {
			logger.info("Grabbed all pending reimb");
			res.getWriter().write(new ObjectMapper().writeValueAsString(pendingReimbursements));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void addReimbursement(HttpServletRequest req, HttpServletResponse res) throws IOException {

		// creating Json object
		JSONObject obj = new JSONObject();
		// reads json obj
		StringBuffer jsonBuff = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = req.getReader();
			while ((line = reader.readLine()) != null)
				jsonBuff.append(line);
		} catch (Exception e) {
			/* report an error */ }
		JSONObject jsonObject;
		try {

			jsonObject = HTTP.toJSONObject(jsonBuff.toString());
		} catch (JSONException e) {
			// crash and burn
			throw new IOException("Error parsing JSON request string");

		}
		// gets body payload
		JSONObject myBody = new JSONObject(jsonObject.getString("Method"));
		// pull out values
		int userID = (int) myBody.get("userId");
		double amount = Double.parseDouble(myBody.getString("amount"));
		int reimbType = Integer.parseInt(myBody.getString("type"));
		String description = myBody.getString("desc");
		// TODO handle when no userID is present

		reimbServ.addReimbursement(amount, userID, reimbType, description);
		Message testing = new Message("worked");
		res.getWriter().write(new ObjectMapper().writeValueAsString(testing));

	}

	public static void updateReimbStatus(HttpServletRequest req, HttpServletResponse res) throws IOException {
		// creating Json object
		JSONObject obj = new JSONObject();
		// reads json obj
		StringBuffer jsonBuff = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = req.getReader();
			while ((line = reader.readLine()) != null)
				jsonBuff.append(line);
		} catch (Exception e) {
			/* report an error */ }
		JSONObject jsonObject;
		try {

			jsonObject = HTTP.toJSONObject(jsonBuff.toString());
		} catch (JSONException e) {
			// crash and burn
			throw new IOException("Error parsing JSON request string");

		}
		// gets body payload
		JSONObject myBody = new JSONObject(jsonObject.getString("Method"));
		int userID = (int) myBody.get("resolver");
		int reimbID = Integer.parseInt(myBody.getString("reimbID"));
		int newStatus = Integer.parseInt(myBody.getString("newStatus"));

		reimbServ.updateReimbStatus(userID, reimbID, newStatus);

		Message testing = new Message("worked");
		res.getWriter().write(new ObjectMapper().writeValueAsString(testing));
	}

}