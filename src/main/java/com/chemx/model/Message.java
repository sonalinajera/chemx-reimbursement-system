package com.chemx.model;

public class Message {
	private String messageOne;
	
	public Message() {
		// TODO Auto-generated constructor stub
	}
	
	

	public Message(String messageOne) {
		super();
		this.messageOne = messageOne;
	}

	

	public String getMessageOne() {
		return messageOne;
	}



	public void setMessageOne(String messageOne) {
		this.messageOne = messageOne;
	}



	@Override
	public String toString() {
		return "Message [messageOne=" + messageOne + "]";
	}
	
	
	
}
