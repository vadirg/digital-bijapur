package com.digitalbijapur.beans;

import com.digitalbijapur.utils.digitalBijapurUtil.USER_ROLE;
import com.digitalbijapur.utils.digitalBijapurUtil.USER_STATUS;

public class UserBean {

	private String userId;

	private String password;

	private String firstName;

	private String lastName;

	private String emailId;

	private long phoneNumber;

	private USER_STATUS userStatus;

	private USER_ROLE userRole;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public USER_STATUS getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(USER_STATUS userStatus) {
		this.userStatus = userStatus;
	}

	public USER_ROLE getUserRole() {
		return userRole;
	}

	public void setUserRole(USER_ROLE userRole) {
		this.userRole = userRole;
	}

	@Override
	public String toString() {
		return "UserBean [userId=" + userId + ", password=" + password + ", firstName=" + firstName + ", lastName="
				+ lastName + ", emailId=" + emailId + ", phoneNumber=" + phoneNumber + ", userStatus=" + userStatus
				+ ", userRole=" + userRole + "]";
	}

	// TODO Need to Check if it is required or not.
	public UserBean(String userId, String password, String firstName, String lastName, String emailId, long phoneNumber,
			USER_STATUS userStatus, USER_ROLE userRole) {
		this.userId = userId;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.phoneNumber = phoneNumber;
		this.userStatus = userStatus;
		this.userRole = userRole;
	}

	public UserBean() {
		// TODO Auto-generated constructor stub
	}

}
