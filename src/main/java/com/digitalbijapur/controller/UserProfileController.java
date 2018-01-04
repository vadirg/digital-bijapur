/**
 *  Digital Bijapur.
 */
package com.digitalbijapur.controller;

import java.util.List;

import org.springframework.stereotype.Controller;

import com.digitalbijapur.beans.UserBean;

/**
 * @author GURUNAIK
 *
 */
@Controller
public class UserProfileController {

	/**
	 * This API will be used to register user
	 */
	public void registerUser() {
		// TODO Change the return type as required.
	}

	/**
	 * This API will be used to get particular user
	 * Only respective logged in user should be able to access this API. 
	 */
	public void getUser(String userId) {
		// TODO Change the return type as required.
	}

	/**
	 * This API will be used to get all users
	 * Only ADMIN User should be able to Access this API 
	 *  IS this required ??
	 */
	public List<UserBean> getUsers() {
		// TODO Change the return type as required.
		return null;
	}
 
	/**
	 * This API will be used to edit particular user
	 * Only respective logged in user should be able to access this API. 
	 */
	public void editUser(UserBean userBean) {
		// TODO Change the return type as required.
	}

	/**
	 * This API is used to block the user.
	 * Only ADMIN user should be able to use this API.
	 * @Kuldeep Can it be handled from DB side ??
	 * 
	 * @param userId
	 */
	public void blockUser(String userId) {
		// TODO Change the return type as required.
	}

	
	/**
	 * This API is used to update the used details.
	 * Only respective logged in user should be able to access this API.
	 * 
	 * @param userBeanUpdated
	 */
	public void updateUserRegistrationStatus(UserBean userBeanUpdated) {
		// TODO Change the return type as required.
	}

}
