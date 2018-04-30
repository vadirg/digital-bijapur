package com.digitalbijapur.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.digitalbijapur.model.UserBean;

@Repository
public interface IUserDao extends CrudRepository<UserBean, Long>{
	UserBean findByUserName(String username);
}
