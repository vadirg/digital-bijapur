/**
 * Digital Bijapur.
 */
package com.digitalbijapur.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.digitalbijapur.dao.IUserDao;
import com.digitalbijapur.model.UserBean;
import com.digitalbijapur.service.IUserService;

/**
 * @author GURUNAIK
 *
 */
@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService,IUserService {
	
	@Autowired
	private IUserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		UserBean user = userDao.findByUserName(userName);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), getAuthority());
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}
	
	public List<UserBean> findAll() {
		List<UserBean> list = new ArrayList<>();
		userDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete(long id) {
		userDao.delete(id);
	}

	@Override
    public UserBean save(UserBean user) {
        return userDao.save(user);
    }

	
}
