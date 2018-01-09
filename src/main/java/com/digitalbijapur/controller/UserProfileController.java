/**
 *  Digital Bijapur.
 */
package com.digitalbijapur.controller;

import java.util.List;

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
import com.digitalbijapur.utils.digitalBijapurUtil.USER_ROLE;
import com.digitalbijapur.utils.digitalBijapurUtil.USER_STATUS;

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
		// TODO Change the return type as required.
		logger.info("Creating User : {}", user);
		 
       /* if (userService.isUserExist(user)) {
            logger.error("Unable to create. A User with name {} already exist", user.getName());
            return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " + 
            user.getName() + " already exist."),HttpStatus.CONFLICT);
        }
        userService.saveUser(user);*/
		System.out.println("User is created: "+user);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.getUserId()).toUri());
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
