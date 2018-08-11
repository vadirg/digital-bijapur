package com.digitalbijapur.domain;

public enum ArticleStatus {
	
	APPROVED("Approved"),
	PENDING("Pending"),
	REJECTED("Rejected");
	
	private String status;

	ArticleStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
	
}
