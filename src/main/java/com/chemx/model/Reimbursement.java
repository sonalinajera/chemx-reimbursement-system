package com.chemx.model;

public class Reimbursement {
	private int reimbId;
	private double reimbAmount;
	private String dateSubmitted, dateResolved, author, resolver, status, type, description;
	
	public Reimbursement() {
		// TODO Auto-generated constructor stub
	}


	public Reimbursement(int reimbId, double reimbAmount, String dateSubmitted, String dateResolved, String author,
			String resolver, String status, String type, String description) {
		super();
		this.reimbId = reimbId;
		this.reimbAmount = reimbAmount;
		this.dateSubmitted = dateSubmitted;
		this.dateResolved = dateResolved;
		this.author = author;
		this.resolver = resolver;
		this.status = status;
		this.type = type;
		this.description = description;
	}

	@Override
	public String toString() {
		return "Reimbursement [reimbId=" + reimbId + ", reimbAmount=" + reimbAmount + ", dateSubmitted=" + dateSubmitted
				+ ", dateResolved=" + dateResolved + ", author=" + author + ", resolver=" + resolver + ", status="
				+ status + ", type=" + type + ", description=" + description + "]";
	}

	public int getReimbId() {
		return reimbId;
	}

	public void setReimbId(int reimbId) {
		this.reimbId = reimbId;
	}

	public double getReimbAmount() {
		return reimbAmount;
	}

	public void setReimbAmount(double reimbAmount) {
		this.reimbAmount = reimbAmount;
	}

	public String getDateSubmitted() {
		return dateSubmitted;
	}

	public void setDateSubmitted(String dateSubmitted) {
		this.dateSubmitted = dateSubmitted;
	}

	public String getDateResolved() {
		return dateResolved;
	}

	public void setDateResolved(String dateResolved) {
		this.dateResolved = dateResolved;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getResolver() {
		return resolver;
	}

	public void setResolver(String resolver) {
		this.resolver = resolver;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
