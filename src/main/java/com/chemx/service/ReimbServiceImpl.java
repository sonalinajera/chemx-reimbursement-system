package com.chemx.service;

import java.sql.Timestamp;
import java.util.List;

import com.chemx.dao.ReimbDaoImpl;
import com.chemx.dao.ReimbursementDao;
import com.chemx.model.Reimbursement;

public class ReimbServiceImpl implements ReimbursementService {
	ReimbursementDao reimbDao = new ReimbDaoImpl();
	@Override
	public void addReimbursement(double reimb_amount, int reimb_author, int reimb_type_id,
			String description) {
		reimbDao.addNewReimb(reimb_amount, reimb_author, reimb_type_id, description);
	}
	@Override
	public List<Reimbursement> retrieveReimbByUser(int userID) {
		
		return reimbDao.getAllReimbByUser(userID);
		
		
	}
	@Override
	public List<Reimbursement> retrieveReimbByStatus(int typeID, int userID) {
		// TODO Auto-generated method stub
		return reimbDao.getAllReimbByStatus(typeID, userID);
	}
	@Override
	public void updateReimbStatus(int user, int reimbId, int reimbStatus) {
		
		Timestamp time = getCurrentTimeStamp();
		reimbDao.updateReimbStatus(user, reimbStatus, reimbId, time);
		
	}
	
	public Timestamp getCurrentTimeStamp() {

	    java.util.Date today = new java.util.Date();
	    return new java.sql.Timestamp(today.getTime());
	}
	@Override
	public List<Reimbursement> retriveAllPendingReimb() {
		return reimbDao.getAllPendingReimb();
	}
	
	@Override
    public void h2Init() {
		reimbDao.h2InitDao();
        
    }

    @Override
    public void h2Destroy() {
    	reimbDao.h2DestroyDao();
    }


}
