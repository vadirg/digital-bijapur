/**
 *  Digital Bijapur.
 */
package com.digitalbijapur.controller;

import java.util.List;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.digitalbijapur.beans.UserBean;
import com.digitalbijapur.utils.DigitalBijapurUtil;
import com.digitalbijapur.utils.DigitalBijapurUtil.USER_ROLE;
import com.digitalbijapur.utils.DigitalBijapurUtil.USER_STATUS;
import com.digitalbijapur.utils.ResponseBuilder;

/**
 * @author GURUNAIK
 *
 */
@RestController
@RequestMapping("/user")
public class UserProfileController {
	
    public static final Logger logger = LoggerFactory.getLogger(UserProfileController.class);

	/**
	 * This API will be used to register user
	 * @return 
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<String> registerUser(@RequestBody UserBean user, UriComponentsBuilder ucBuilder) {
		logger.info("Creating User : {}", user);
		boolean isInvalidInput = false;
		ResponseBuilder errorResponse = new ResponseBuilder();
		if(null != user) {
			if(null == user.getUserId()) {
				isInvalidInput = true;
				errorResponse.addKeyValuePair("userId", "User Id cannot be null.");
			}
			
			if(null != user.getPassword() && !DigitalBijapurUtil.isValidPassword(user.getPassword())) {
				isInvalidInput = true;
				errorResponse.addKeyValuePair("password", "Invalid password, Please refer to password constraints.");
			}else if(null == user.getPassword()){
				isInvalidInput = true;
				errorResponse.addKeyValuePair("password", "Password cannot be null.");
			}
			
			if(null != user.getEmailId() && !DigitalBijapurUtil.isValidEmail(user.getEmailId())) {
				isInvalidInput = true;
				errorResponse.addKeyValuePair("emailId", "Invalid Email Id, Please refer to email constraints.");
			}else if(null == user.getEmailId()){
				isInvalidInput = true;
				errorResponse.addKeyValuePair("emailId", "Email Id cannot be null.");
			}
			
			if(null == user.getFirstName()) {
				isInvalidInput = true;
				errorResponse.addKeyValuePair("firstName", "First name cannot be null.");
			}
			
			if(0 == user.getPhoneNumber()) {
				isInvalidInput = true;
				errorResponse.addKeyValuePair("phoneNumber", "Please enter phone number.");
			}
			
			if(isInvalidInput) {
				String response = errorResponse.finilizeResponse();
				logger.debug("Response: "+ response);
				HttpHeaders header = new HttpHeaders();
				header.add("Content-Type", "application/json");
				return new ResponseEntity<String>(response,header, HttpStatus.OK);
			}
			
		}
		
		// TODO need to check what does it actually means.
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("user/register/{userId}").buildAndExpand(user.getUserId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	/**
	 * This API will be used to get particular user Only respective logged in user
	 * should be able to access this API.
	 */
	@RequestMapping("/getUser")
	public ResponseEntity<UserBean> getUser() {
		logger.info("Fetching user");
		UserBean user = new UserBean();
		user.setEmailId("vadi@gmail.com");
		user.setFirstName("Vadiraj");
		user.setLastName("gurunaik");
		user.setUserId("vadirajrg");
		user.setPassword("test123");
		user.setPhoneNumber(9738957662L);
		user.setUserRole(USER_ROLE.ADMIN);
		user.setUserStatus(USER_STATUS.VERIFIED);
		return new ResponseEntity<UserBean>(user,HttpStatus.OK);
	}

	/**
	 * This API will be used to get all users Only ADMIN User should be able to
	 * Access this API IS this required ??
	 */
	@RequestMapping("/getUsers")
	public List<UserBean> getUsers() {
		// TODO Change the return type as required.
		return null;
	}

	/**
	 * This API will be used to edit particular user Only respective logged in user
	 * should be able to access this API.
	 */
	@RequestMapping("/update")
	public void editUser(UserBean userBean) {
		// TODO Change the return type as required.
	}

	/**
	 * This API is used to block the user. Only ADMIN user should be able to use
	 * this API.
	 * 
	 * @Kuldeep Can it be handled from DB side ??
	 * 
	 * @param userId
	 */
	@RequestMapping("/block")
	public void blockUser(String userId) {
		// TODO Change the return type as required.
	}

	/**
	 * This API is used to update the used details. Only respective logged in user
	 * should be able to access this API.
	 * 
	 * @param userBeanUpdated
	 */
	@RequestMapping("/updateRegistrationStatus")
	public void updateUserRegistrationStatus(UserBean userBeanUpdated) {
		// TODO Change the return type as required.
	}

}
