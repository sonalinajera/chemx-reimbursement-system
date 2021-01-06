package com.chemx.dao;

import java.sql.Timestamp;
import java.util.List;

import com.chemx.model.Reimbursement;
import com.chemx.model.User;

public interface ReimbursementDao {

	//CREATE
	public void addNewReimb(double reimb_amount, int reimb_author, int reimb_type_id, String description);
		
	//READ 
	public List<Reimbursement> getAllReimb();
	public List<Reimbursement> getAllReimbByStatus (int statusId , int author); 
	public Reimbursement getReimbById(int id);
	public List<Reimbursement> getAllPendingReimb();
	public List<Reimbursement> getAllReimbByUser(int id); // username?
	//UPDATE
	public void updateReimbStatus(int reimb_resolver, int reimb_status_id, int reimb_id, Timestamp time);
	
	//H2 DATABASE FUNCTIONALITY
    public void h2InitDao();
    public void h2DestroyDao();

}
