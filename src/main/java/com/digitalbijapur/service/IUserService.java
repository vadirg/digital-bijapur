/**
 * Digital Bijapur.
 */
package com.digitalbijapur.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.digitalbijapur.model.UserBean;

/**
 * @author GURUNAIK
 *
 */
public interface IUserService {

	UserBean save(UserBean user);

	List<UserBean> findAll();

	void delete(long id);

}
