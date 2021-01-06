package com.chemx.service;

import java.sql.Timestamp;
import java.util.List;

import com.chemx.model.Reimbursement;
import com.chemx.model.User;

public interface ReimbursementService {

	//create
		public void addReimbursement(double reimb_amount, int reimb_author, int reimb_type_id,
				String description);
		//read
		public List<Reimbursement> retrieveReimbByUser(int userID);
		public List<Reimbursement> retrieveReimbByStatus(int typeID, int userID);
		public List<Reimbursement> retriveAllPendingReimb();
		//update
		public void updateReimbStatus(int user, int reimbId, int reimbStatus);
		//delete
		
		public Timestamp getCurrentTimeStamp();
		
		//H2 DATABASE FUNCTIONALITY
	    public void h2Init();
	    public void h2Destroy();
}
